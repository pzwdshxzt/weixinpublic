package com.hjx.pzwdshxzt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.mapper.CityMapper;
import com.hjx.pzwdshxzt.model.weather.City;
import com.hjx.pzwdshxzt.model.weather.CityA;
import com.hjx.pzwdshxzt.service.CoreService;
import com.hjx.pzwdshxzt.util.HttpUtils;
import com.hjx.pzwdshxzt.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author hjx
 */
@RestController
@RequestMapping("")
public class CoreController {

    @Autowired
    private CoreService coreService;
    private static Logger log = LoggerFactory.getLogger(CoreController.class);
    @Autowired
    private CityMapper cityMapper;

    /**
     * 验证是否来自微信服务器的消息
     */
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public String checkSignature(@RequestParam(name = "signature", required = false) String signature,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "echostr", required = false) String echostr) {
        /**
         *  通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
         */
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            log.info("接入成功");
            return echostr;
        }
        log.error("接入失败");
        return "";
    }

    /**
     * 调用核心业务类接收消息、处理消息跟推送消息
     */
    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public String post(HttpServletRequest req) throws Exception {
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }

    @RequestMapping("getCity")
    @ResponseBody
    public String getCity() {
        Map<String, String> headers = setAppcode();
        Map<String, String> querys = new HashMap<>();
        try {
            String ret = HttpUtils.doGet(Constants.CITYHOST, Constants.CITYPATH, headers, querys);

            JSONObject jsonObject = JSON.parseObject(ret);
            CityA cityA = jsonObject.toJavaObject(CityA.class);
            Set<City> cityInfos = cityA.getResult();
            cityInfos.stream().forEach(o -> {
                cityMapper.insertCity(o);
                System.out.println(o.getCity());
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "success";
    }

    public static Map<String, String> setAppcode() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + Constants.APPCODE);
        return headers;
    }

}
