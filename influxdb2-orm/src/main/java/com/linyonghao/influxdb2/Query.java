package com.linyonghao.influxdb2;

import com.alibaba.fastjson2.JSON;
import com.influxdb.query.FluxColumn;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.influxdb2.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static java.time.temporal.ChronoField.MICRO_OF_SECOND;

public class Query<T> {
    String range;
    String bucket;
    private Class<?> clazz;
    private Map<String, Integer> fieldMap;
    private String measurement;

    StringBuilder flux = new StringBuilder();
    Logger logger = LoggerFactory.getLogger(Query.class);

    public Query(String bucket, Class<?> clazz,String measurement, Map<String, Integer> fieldMap) {
        this.bucket = bucket;
        this.clazz = clazz;
        this.fieldMap = fieldMap;
        this.measurement = measurement;
        flux.append("from(bucket: \"").append(bucket).append("\")\n");
    }

    public Query<T> range(Date startTime, Date endTime) {

        startTime = DateUtil.localToUTC(startTime);
        endTime = DateUtil.localToUTC(endTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        flux.append("  |> range(start: ").append(simpleDateFormat.format(startTime)).append(", stop: ")
                .append(simpleDateFormat.format(endTime)).append(")\n");
         return this;
    }

    public Query<T> group(){
        flux.append("  |>group()\n");
        return this;
    }

    public Query<T> range(long startTimestamp,long endTimestamp) {
        flux.append("  |> range(start: ").append(startTimestamp).append(", stop: ")
                .append(endTimestamp).append(")\n");
        return this;
    }

    public Query<T> range(String startTime) {
        flux.append("  |> range(start: ").append(startTime).append(" )\n");
        return this;
    }

    public Query<T> filterEqString(String key,String value){
            flux.append("  |> filter(fn: (r) => r[\""+key+"\"] == \"")
                .append(value)
                .append("\"")
                .append(")\n");
        // join filter
        return this;
    }

    public Query<T> filterMeasurement(){
        flux.append("  |> filter(fn: (r) => r[\"_measurement\"] == \"").append(measurement).append("\")\n");
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

    public Query<T> aggregateWindow(String every, String fn,boolean createEmpty) {
        flux.append("|> aggregateWindow(every: ")
                .append(every)
                .append(", fn:")
                .append(fn);
        if(createEmpty){
            flux.append(" , createEmpty: true");
        }else{
            flux.append(" , createEmpty: false");
        }
            flux.append(")\n");
        return this;
    }

    @Deprecated // TODO: 该方法存在问题 慎用
    public List<T> all() {
        List<FluxTable> query = InfluxdbGlobal.getInstance().getQueryApi().query(build());
        if (query.size() <= 0) {
            return null;
        }
        ArrayList<T> retList = new ArrayList<>();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (int i =0; i< records.size();i++) {
                boolean emptyRecordFlag  = false;
                FluxRecord record = records.get(i);
                String field = (String) record.getValueByKey("_field");
                if (field == null) {
                    emptyRecordFlag = true;
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


    public List<CountWithTime> count(){
        String build = build();
        build += "  |> count()";
        logger.info(build);
        List<FluxTable> query = InfluxdbGlobal.getInstance().getQueryApi().query(build);
        List<CountWithTime> counts = new ArrayList<>();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                Object value = record.getValueByKey("_value");
                if (value == null){
                    value = 0L;
                }
                counts.add(new CountWithTime((Long) value, record.getStart().getEpochSecond()));
            }
        }
        return counts;
    }

    public Query<T> window(String every,boolean createEmpty){
        flux.append("  |> window(every: ")
                .append(every);

        if(createEmpty){
            flux.append(", createEmpty:true");
        }else{
            flux.append(", createEmpty:false");
        }
        flux.append(")\n");
        return this;
    }

    public String build() {
        logger.info(flux.toString());
        return flux.toString();
    }

    public List<CountWithTime> oneValue(boolean lastValueIfNull){
        String build = build();
        List<FluxTable> query = InfluxdbGlobal.getInstance().getQueryApi().query(build);
        List<CountWithTime> counts = new ArrayList<>();
        Long lastValue = 0L;
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                Object value = record.getValueByKey("_value");
                if (value == null && lastValueIfNull){
                    value = (Object) lastValue;
                }
                assert record.getTime() != null;
                long epochSecond = record.getTime().getEpochSecond();
                if(epochSecond == 0){
                    epochSecond = record.getStart().getEpochSecond();
                }
                counts.add(new CountWithTime((Long) value,epochSecond));
                lastValue = (Long) value;
            }

        }
        return counts;
    }
}
