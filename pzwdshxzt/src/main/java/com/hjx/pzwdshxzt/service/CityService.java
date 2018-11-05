package com.hjx.pzwdshxzt.service;

import com.hjx.pzwdshxzt.model.weather.City;

import java.util.List;
import java.util.Set;

/**
 * Description
 * 城市服务
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/8 13:10
 * @Version : 0.0.1
 */
public interface CityService {

    /**
     * 根据集合中的城市名字来查找城市
     * @param sets
     * @return
     */
    List<City> queryCity(Set<String> sets);

    /**
     * 根据城市名字来查找模糊城市
     * @param city
     * @return
     */
    List<City> queryCity(String city);
}
