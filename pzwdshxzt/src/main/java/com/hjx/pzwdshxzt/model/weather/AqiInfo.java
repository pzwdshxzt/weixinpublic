package com.hjx.pzwdshxzt.model.weather;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 17:06
 * @Version :
 */
public class AqiInfo {
    private String level;
    private String color;
    private String affect;
    private String measure;
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getAffect() {
        return affect;
    }
    public void setAffect(String affect) {
        this.affect = affect;
    }
    public String getMeasure() {
        return measure;
    }
    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
