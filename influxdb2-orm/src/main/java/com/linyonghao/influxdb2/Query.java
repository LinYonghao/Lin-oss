package com.linyonghao.influxdb2;

import com.alibaba.fastjson2.JSON;
import com.influxdb.query.FluxColumn;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Query<T> {
    String range;
    String bucket;
    private Class<?> clazz;
    private Map<String, Integer> fieldMap;
    private String measurement;
    private String aggregateWindow;

    public Query(String bucket, Class<?> clazz, Map<String, Integer> fieldMap) {
        this.bucket = bucket;
        this.clazz = clazz;
        this.fieldMap = fieldMap;
    }

    public Query<T> range(String startTime, String endTime) {
        range = "  |> range(start: " + startTime + ", stop: " + endTime + ")\n";
        return this;
    }

    public Query<T> setBucketName(String name) {
        bucket = name;
        return this;
    }

    public Query<T> setMeasurement(String measurement) {
        this.measurement = measurement;
        return this;
    }

    public Query<T> aggregateWindow(String every, String fn, String column) {
        aggregateWindow = "|> aggregateWindow(every: " + every + ", fn:" + fn + " , column: \"" + column + "\")";
        return this;
    }

    public List<T> all() {
        List<FluxTable> query = InfluxdbGlobal.getInstance().getQueryApi().query(build());
        if (query.size() <= 0) {
            return null;
        }
        ArrayList<T> retList = new ArrayList<>();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (int i =0; i< records.size();i++) {
                FluxRecord record = records.get(i);
                String field = (String) record.getValueByKey("_field");
                if (field == null) {
                    return null;
                }
                Object o;
                try {
                    if(retList.size() != records.size()){
                        // 首次新增元素
                        o = clazz.newInstance();
                        Instant instant = (Instant) record.getValueByKey("_time");
                        clazz.getField("time").set(o, instant.getEpochSecond());
                    }else{
                        o = retList.get(i);
                    }

                    Object objValue = record.getValueByKey("_value");
                    if (clazz.getField(field).getType().isPrimitive()) {
                        clazz.getField(field).set(o, objValue);
                    } else {
                        clazz.getField(field).set(o, JSON.parseObject(String.valueOf(objValue), clazz.getField(field).getType()));
                    }

                    fieldMap.forEach((k, v) -> {
                        try {
                            Object value = record.getValueByKey("_value");
                            if (clazz.getField(field).getType().isPrimitive()) {
                                clazz.getField(field).set(o, record.getValueByKey("_value"));
                            } else {
                                clazz.getField(field).set(o, JSON.parseObject(String.valueOf(value), clazz.getField(field).getType()));
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    });
                    retList.add((T) o);


                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                // time
            }

        }
        return retList;
    }

    public String build() {
        /**
         * from(bucket: "lin_oss")
         *   |> range(start: v.timeRangeStart, stop: v.timeRangeStop)
         *   |> filter(fn: (r) => r["_measurement"] == "upload_log")
         *   |> aggregateWindow(every: 10m, fn:sum , column: "_value")
         *   |> yield()
         */

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from(bucket: \"" + bucket + "\")\n");
        if (range != null) {
            stringBuilder.append(range);
        }

        if (aggregateWindow != null) {
            stringBuilder.append(aggregateWindow);
        }

        return stringBuilder.toString();


    }


}
