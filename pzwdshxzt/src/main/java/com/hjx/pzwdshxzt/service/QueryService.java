package com.hjx.pzwdshxzt.service;

/**
 * Description
 * 查询工具
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/6 16:56
 * @Version :0.0.1
 */
public interface QueryService {
    /**
     * 查询深圳通余额
     * @param cardno
     * @return
     */
    String queryBanlance(String cardno) throws Exception;

    /**
     * 点击红包
     * @param phone
     * @param url
     * @return
     */
    String getLuckyMenoy(String url,String phone) throws Exception;
}
