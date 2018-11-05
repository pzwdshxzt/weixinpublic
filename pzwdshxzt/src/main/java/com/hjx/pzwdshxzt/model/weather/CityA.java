package com.hjx.pzwdshxzt.model.weather;

import com.hjx.pzwdshxzt.model.weather.City;

import java.util.Set;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 16:15
 * @Version :
 */
public class CityA {

    private String status;
    private String msg;
    private Set<City> result;
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
    public Set<City> getResult() {
        return result;
    }
    public void setResult(Set<City> result) {
        this.result = result;
    }
}
