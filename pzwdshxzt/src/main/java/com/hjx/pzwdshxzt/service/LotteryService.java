package com.hjx.pzwdshxzt.service;

import com.hjx.pzwdshxzt.model.Lottery.Lottery;

import java.util.HashMap;
import java.util.List;

/**
 * Description
 * 抽奖
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/9 10:05
 * @Version :
 */
public interface LotteryService {

    /**
     * 插入lottery
     *
     * @param lottery
     */
    void insertLottery(Lottery lottery);

    /**
     * 查询中奖信息
     *
     * @param token
     */
    String checkResult(String token, String num);

    /**
     * 查询所有Token
     */
    HashMap<String, String> queryTokenList();

}
