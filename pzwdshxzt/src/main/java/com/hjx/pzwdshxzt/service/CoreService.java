package com.hjx.pzwdshxzt.service;

import javax.servlet.http.HttpServletRequest;


public interface CoreService {
    /**
     * 处理请求回复
     * @param request
     * @return
     */
    public  String processRequest(HttpServletRequest request) throws Exception;
}
