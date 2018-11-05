package com.hjx.pzwdshxzt.mapper;

import com.hjx.pzwdshxzt.model.weather.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * Description
 * 城市
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 16:19
 * @Version : 0.0.1
 */
@Mapper
public interface CityMapper {

    @Insert("INSERT INTO t_city (cityid,parentid,citycode,city) values(#{cityid},#{parentid},#{citycode},#{city})")
    void insertCity(City city);

    @Select("<script>"
            + "SELECT * FROM t_city <if test='set != null'> WHERE city in "
            + "<foreach item='item' collection='set' open='(' close=')' separator=','>"
            + "#{item}"
            + "</foreach>"
            + "AND parentid != 0 And cityid not in (SELECT parentid FROM t_city)</if></script>")
    List<City> selectCity(@Param("set")Set<String> sets);
    @Select("<script>"
            + "SELECT * FROM t_city "
            + "<where>"
            + "<bind name='cityOne' value=\"'%' + city + '%'\" />"
            + "<if test='cityOne != null'>AND city like #{cityOne}</if>"
            + "</where>"
            + "</script>")
    List<City> selectCityForStr(@Param("city") String city);
}
