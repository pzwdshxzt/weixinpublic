package com.hjx.pzwdshxzt.model.weather;

import java.util.Set;

/**
 * Description
 * 天气查询回来的实体类
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 17:03
 * @Version : 0.0.1
 */
public class CityDo {

    private String city;
    private String cityId;
    private String cityCode;
    private String ip;
    private String location;
    private String date;
    private String week;
    private String weather;
    private String temp;
    private String temphigh;
    private String templow;
    private String img;
    private String humidity;
    private String pressure;
    private String windspeed;
    private String winddirect;
    private String windpower;
    private String updatetime;
    private Set<DetailInfo> index;
    private Set<Hourly> hourly;
    private Aqi aqi;
    private Set<Daily> daily;


    public Set<Daily> getDaily() {
        return daily;
    }
    public void setDaily(Set<Daily> daily) {
        this.daily = daily;
    }
    public Aqi getAqi() {
        return aqi;
    }
    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }
    public Set<Hourly> getHourly() {
        return hourly;
    }
    public void setHourly(Set<Hourly> hourly) {
        this.hourly = hourly;
    }
    public Set<DetailInfo> getIndex() {
        return index;
    }
    public void setIndex(Set<DetailInfo> index) {
        this.index = index;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getTemp() {
        return temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getTemphigh() {
        return temphigh;
    }
    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
    }
    public String getTemplow() {
        return templow;
    }
    public void setTemplow(String templow) {
        this.templow = templow;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getHumidity() {
        return humidity;
    }
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
    public String getPressure() {
        return pressure;
    }
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    public String getWindspeed() {
        return windspeed;
    }
    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }
    public String getWinddirect() {
        return winddirect;
    }
    public void setWinddirect(String winddirect) {
        this.winddirect = winddirect;
    }
    public String getWindpower() {
        return windpower;
    }
    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }
    public String getUpdatetime() {
        return updatetime;
    }
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
