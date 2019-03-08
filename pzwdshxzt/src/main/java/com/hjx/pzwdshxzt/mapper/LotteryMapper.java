package com.hjx.pzwdshxzt.mapper;

import com.hjx.pzwdshxzt.model.Lottery.Lottery;
import com.hjx.pzwdshxzt.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * @author Dwxqnswxl
 */
@Mapper
public interface LotteryMapper {

    /**
     * 新增
     *
     * @param lottery
     * @return
     */
    @Insert("insert into t_lottery (title,results,time,rule,token,createTime) values(#{title},#{results},#{time},#{rule},#{token},#{createTime})" )
    void insertLottery(Lottery lottery);


    /**
     * 搜索客户是否已经存在
     *
     * @param token
     * @return
     */
    @Select("select results,title,time,rule,token from t_lottery where token = #{token}" )
    @Results({@Result(property = "results", column = "results" ),
            @Result(property = "title", column = "title" ),
            @Result(property = "time", column = "time" ),
            @Result(property = "rule", column = "rule" ),
            @Result(property = "token", column = "token" )
    })
    List<Lottery> selectByToken(String token);

    /**
     * 查询所有Token
     *
     * @return
     */
    @Options(statementType = StatementType.CALLABLE)
    @Select("select token,time from t_lottery GROUP BY token,time" )
    List<Map<String, String>> queryTokenList();


    /**
     * 查询是否中奖
     *
     * @return
     */
    @Select("select token,title,rule from t_check where token = #{token} and num = #{num}" )
    List<Map<String, String>> queryCheckList(@Param("token" ) String token, @Param("num" ) String num);

    /**
     * 插入兑奖信息
     *
     * @param token
     * @param num
     * @param rule
     * @param createTime
     * @param time
     * @param title
     */
    @Insert("insert into t_check (token,num,rule,createTime,time,title) values(#{token},#{num},#{rule},#{createTime},#{time},#{title})" )
    void insertCheck(@Param("token" ) String token, @Param("num" ) String num, @Param("rule" ) String rule, @Param("createTime" ) String createTime, @Param("time" ) String time, @Param("title" ) String title);
}