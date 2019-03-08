package com.hjx.pzwdshxzt.mapper;

import com.hjx.pzwdshxzt.model.UnSubscribe;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Description
 * 取消关注表
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/7 12:44
 * @Version : 0.0.1
 */
@Mapper
public interface UnSubscribeMapper {
    /**
     * 新增客户
     *
     * @param openId
     * @return
     */
    @Insert("insert into t_unsubscribe (open_id) values(#{openId})" )
    void insert(String openId);

    /**
     * 删除取消关注用户
     *
     * @param openId
     */
    @Delete("delete from t_unsubscribe where open_id = #{openId}" )
    void deleteUnSubscribe(String openId);


    /**
     * 搜索客户是否已经存在
     *
     * @param openId
     * @return
     */
    @Select("select * from t_unsubscribe where open_id = #{openId}" )
    @Results({@Result(property = "openId", column = "open_id" )
    })
    List<UnSubscribe> selectUnSubscribe(String openId);

}
