package com.hjx.pzwdshxzt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.model.Result;
import com.hjx.pzwdshxzt.model.weather.City;
import com.hjx.pzwdshxzt.model.weather.CityDo;
import com.hjx.pzwdshxzt.service.WeatherService;
import com.hjx.pzwdshxzt.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * 天气接口实现
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 16:59
 * @Version : 0.0.1
 */
@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {


    @Override
    public Result queryWeather(City city) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + Constants.APPCODE);
            Map<String, String> querys = new HashMap<>();
            querys.put("city", city.getCity());
            querys.put("citycode", city.getCitycode());
            querys.put("cityid", city.getCityid());
            querys.put("ip", "");
            querys.put("location", city.getLocal());
            String html = HttpUtils.doGet(Constants.HOST, Constants.PATH, headers, querys);
            JSONObject jsonObject = JSON.parseObject(html);
            Result cityDo = jsonObject.toJavaObject(Result.class);
            return cityDo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
