package com.hjx.pzwdshxzt.model;

/**
 * @author Dwxqnswxl
 *
 */
public class User {
    private String openId;

    private String sztNum;
    private String endTime;
    /**
     * 经纬度
     */
    private String local;
    /**
     * 当前地理位置
     */
    private String address;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getSztNum() {
        return sztNum;
    }

    public void setSztNum(String sztNum) {
        this.sztNum = sztNum == null ? null : sztNum.trim();
    }
}