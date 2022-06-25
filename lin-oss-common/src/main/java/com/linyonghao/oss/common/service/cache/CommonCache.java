package com.linyonghao.oss.common.service.cache;

import org.omg.CORBA.PUBLIC_MEMBER;

public abstract class CommonCache<T> {
    // 获取
    public int hitNum;
    public int accessNum;
    public Class<T> guideType;

    public Class<T> getGuideType() {
        return guideType;
    }

    public void setGuideType(Class<T> guideType) {
        this.guideType = guideType;
    }

    public float getHitNum() {
        return hitNum;
    }

    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    public int getAccessNum() {
        return accessNum;
    }

    public void setAccessNum(int accessNum) {
        this.accessNum = accessNum;
    }

    public void incrementHitNumber(){
        this.hitNum ++;
    }

    public void incrementAccessNum(){
        this.accessNum++;
    }

    public float hitRate(){
        return (float) ((1.0* hitNum) / accessNum);
    }

    public String generateKey(String... args){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i] );
            if(i != args.length -1){
                stringBuilder.append(":");
            }
        }
        return stringBuilder.toString();
    }

    public float getHitRate(){
        return (float) ((1.0 * hitNum) / accessNum);
    }






}
