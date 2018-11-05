package com.hjx.pzwdshxzt.service;

import com.hjx.pzwdshxzt.model.UnSubscribe;
import com.hjx.pzwdshxzt.model.User;

/**
 * Description
 * 用户信息
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/6 16:09
 * @Version : 0.0.1
 */
public interface UserService {

    /**
     * 新增用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 校验是否存在openId
     * @param openId
     * @return
     */
    User selectUser(String openId);

    /**
     * 修改深圳通卡号
     * @param user
     */
    void updateNum(User user);
    /**
     * 修改倒计时时间
     * @param user
     */
    void updateEndTime(User user);

    /**
     * 新增取消关注用户
     */
    void addUnSubscribe(String openId);
    /**
     * 删除取消关注用户
     */
    void deleteUnSubscribe(String openId);
    /**
     * 删除取消关注用户
     */
    UnSubscribe selectUnSubscribe(String openId);
    /**
     * 修改地理位置
     */
    void updateLocal(User user);

}
