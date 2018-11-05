package com.hjx.pzwdshxzt.service;

import com.hjx.pzwdshxzt.model.Result;
import com.hjx.pzwdshxzt.model.weather.City;
import com.hjx.pzwdshxzt.model.weather.CityDo;

/**
 * Description
 * 天气接口
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 16:57
 * @Version : 0.0.1
 */
public interface WeatherService {


    /**
     * 根据城市信息查询天气
     * @param city
     */
    Result queryWeather(City city);
}
