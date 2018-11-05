package com.hjx.pzwdshxzt.model;

import com.hjx.pzwdshxzt.model.weather.CityDo;

/**
 * Description
 * 天气返回json集合
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/8 12:48
 * @Version : 0.0.1
 */
public class Result {

    private String status;
    private String msg;
    private CityDo result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CityDo getResult() {
        return result;
    }

    public void setResult(CityDo result) {
        this.result = result;
    }
}
