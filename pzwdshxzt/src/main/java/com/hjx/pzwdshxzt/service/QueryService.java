package com.hjx.pzwdshxzt.service;

import com.hjx.pzwdshxzt.model.price.PriceResult;

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
     *
     * @param cardno
     * @return
     */
    String queryBanlance(String cardno) throws Exception;

    /**
     * 查询淘宝优惠
     *
     * @param cardno
     * @return
     */
    PriceResult queryDiscount(String url) throws Exception;
}
