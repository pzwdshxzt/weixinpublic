package com.hjx.pzwdshxzt.mapper;

import com.hjx.pzwdshxzt.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Dwxqnswxl
 */
@Mapper
public interface UserMapper {

    /**
     * 新增客户
     *
     * @param user
     * @return
     */
    @Insert("insert into t_user (open_id,szt_num ) values(#{openId},#{sztNum})")
    void insert(User user);

    /**
     * 搜索客户是否已经存在
     *
     * @param openId
     * @return
     */
    @Select("select * from t_user where open_id = #{openId}")
    @Results({@Result(property = "openId", column = "open_id"),
            @Result(property = "sztNum", column = "szt_num"),
            @Result(property = "endTime", column = "end_time")
    })
    List<User> selectByPrimaryKey(String openId);

    /**
     * 修改深圳通卡号
     *
     * @param user
     */
    @Update("update t_user set szt_num = #{sztNum} where open_id = #{openId}")
    void updateSZT(User user);

    /**
     * 修改倒计时时间
     *
     * @param user
     */
    @Update("update t_user set end_time = #{endTime} where open_id = #{openId}")
    void updateEndTime(User user);

    /**
     * 修改经纬度
     *
     * @param user
     */
    @Update("update t_user set local= #{local},address = #{address} where open_id = #{openId}")
    void updateLocal(User user);



}