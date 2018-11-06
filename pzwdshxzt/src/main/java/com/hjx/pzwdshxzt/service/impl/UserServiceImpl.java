package com.hjx.pzwdshxzt.service.impl;

import com.hjx.pzwdshxzt.mapper.UnSubscribeMapper;
import com.hjx.pzwdshxzt.mapper.UserMapper;
import com.hjx.pzwdshxzt.model.UnSubscribe;
import com.hjx.pzwdshxzt.model.User;
import com.hjx.pzwdshxzt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 * 用户
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/6 16:12
 * @Version : 0.0.1
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UnSubscribeMapper unSubscribeMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);

    }

    @Override
    public User selectUser(String openId) {
        List<User> user = userMapper.selectByPrimaryKey(openId);
        if (user.size() > 0) {
            return user.get(0);
        }
        return null;
    }

    @Override
    public void updateNum(User user) {
        userMapper.updateSZT(user);
    }

    @Override
    public void updateEndTime(User user) {
        userMapper.updateEndTime(user);
    }

    @Override
    public void addUnSubscribe(String openId) {
        unSubscribeMapper.insert(openId);
    }

    @Override
    public void deleteUnSubscribe(String openId) {
        unSubscribeMapper.deleteUnSubscribe(openId);
    }

    @Override
    public UnSubscribe selectUnSubscribe(String openId) {
        List<UnSubscribe> unSubscribes = unSubscribeMapper.selectUnSubscribe(openId);
        if (unSubscribes.size()>0){
            return unSubscribes.get(0);
        }
        return null;
    }

    @Override
    public void updateLocal(User user) {
        userMapper.updateLocal(user);
    }

    @Override
    public void updateUrl(User user) {
        userMapper.updateUrl(user);
    }

}