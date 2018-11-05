package com.hjx.pzwdshxzt.service.impl;

import com.hjx.pzwdshxzt.mapper.CityMapper;
import com.hjx.pzwdshxzt.model.weather.City;
import com.hjx.pzwdshxzt.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Description
 * 城市实现类
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/8 13:16
 * @Version : 0.0.1
 */
@Service("cityService")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;
    @Override
    public List<City> queryCity(Set<String> sets) {
        return cityMapper.selectCity(sets);
    }

    @Override
    public List<City> queryCity(String city) {
        return cityMapper.selectCityForStr(city);
    }
}
