package com.linyonghao.influxdb2;

import com.alibaba.fastjson2.JSON;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;
import com.linyonghao.influxdb2.exception.NotFoundMeasurementException;
import org.apache.commons.text.CaseUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class InfluxMapper<T> {
    String measurement;
    Map<String, Integer> fieldMap = new ConcurrentHashMap<>();
    public static final int TAG = 0;
    public static final int FIELD = 1;
    private Class<?> clazz;


    public void loadMetaInfo(Class<?> clazz) throws NotFoundMeasurementException {
        this.clazz = clazz;
        boolean hasMeasurement = clazz.isAnnotationPresent(Measurement.class);
        if (!hasMeasurement) {
            throw new NotFoundMeasurementException();
        }
        Measurement measurementAnnotation = clazz.getAnnotation(Measurement.class);
        measurement = measurementAnnotation.name().equals("") ? clazz.getTypeName() : measurementAnnotation.name();

        // 遍历属性
        for (Field field : clazz.getDeclaredFields()) {
            Tag tagAnnotation = field.getAnnotation(Tag.class);
            if (tagAnnotation != null) {
                String name = tagAnnotation.name();
                if (StringUtils.hasLength(name)) {
                    fieldMap.put(tagAnnotation.name(), InfluxMapper.TAG);
                } else {
                    fieldMap.put(field.getName(), TAG);
                }
            }

            DBField fieldAnnotation = field.getAnnotation(DBField.class);
            if (fieldAnnotation != null) {
                String name = fieldAnnotation.name();
                if (StringUtils.hasLength(name)) {
                    fieldMap.put(name, InfluxMapper.FIELD);
                } else {
                    fieldMap.put(field.getName(), FIELD);
                }
            }
        }
    }


    public void insert(T data, long msTime) {
        if (!StringUtils.hasLength(measurement)) {
            try {
                loadMetaInfo(data.getClass());
            } catch (NotFoundMeasurementException e) {
                e.printStackTrace();
            }
        }

        // 构建反射取值
        Class<?> clazz = data.getClass();
        Point point = new Point(measurement);

        fieldMap.forEach((k, v) -> {
            try {
                System.out.println(clazz);
                Method method = clazz.getMethod("get" + captureName(k));
                Class<?> returnType = method.getReturnType();
                Object ret = method.invoke(data);


                if (v == FIELD) {
                    if (!returnType.isPrimitive()) {
                        returnType = String.class;
                        ret = JSON.toJSONString(ret);
                    }
                    if(returnType.getTypeName().equals("int")){
                        returnType = Number.class;
                    }

                    point.getClass().getMethod("addField", String.class, returnType).invoke(point, k, ret);
                }

                if (v == TAG) {
                    point.getClass().getMethod("addTag", String.class, String.class).invoke(point, k, String.valueOf(ret));
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                try {
                    throw new Exception("请实现get" + captureName(k) + "方法");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        });

        point.time(msTime, WritePrecision.MS);
        WriteApiBlocking writeApiBlocking = InfluxdbGlobal.getInstance().getWriteApiBlocking();
        writeApiBlocking.writePoint(point);
    }


    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    public Query<T> query() {
        return new Query<T>(InfluxdbGlobal.getBucketName(), clazz, measurement, fieldMap);
    }

    public void init(Class<?> clazz) {
        this.clazz = clazz;
        try {
            loadMetaInfo(clazz);
        } catch (NotFoundMeasurementException e) {
            throw new RuntimeException(e);
        }
    }


}
