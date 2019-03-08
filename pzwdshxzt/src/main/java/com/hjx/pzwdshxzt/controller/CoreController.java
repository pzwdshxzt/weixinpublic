package com.hjx.pzwdshxzt.controller;

import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.model.Lottery.Lottery;
import com.hjx.pzwdshxzt.model.R;
import com.hjx.pzwdshxzt.service.CoreService;
import com.hjx.pzwdshxzt.service.InitService;
import com.hjx.pzwdshxzt.service.LotteryService;
import com.hjx.pzwdshxzt.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hjx
 */
@RestController
@RequestMapping("" )
public class CoreController {

    @Autowired
    private CoreService coreService;
    private static Logger log = LoggerFactory.getLogger(CoreController.class);

    @Autowired
    private LotteryService lotteryService;

    public static final String PATTERN_NUM_STR = "[0-9]*";

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
            log.info("接入成功" );
            return echostr;
        }
        log.error("接入失败" );
        return "";
    }

    /**
     * 抽奖结果
     */
    @RequestMapping(value = "/insertLottery", method = {RequestMethod.GET, RequestMethod.POST})
    public R getResult(@RequestParam(name = "title", required = false) String title,
                       @RequestParam(name = "rule", required = false) String rule,
                       @RequestParam(name = "token", required = false) String token,
                       @RequestParam(name = "time", required = false) String time,
                       @RequestParam(name = "results", required = false) String results) {

        if (!InitService.tokenList.containsKey(token)) {
            InitService.tokenList.put(token, time);
        }

        Lottery lottery = new Lottery();
        lottery.setResults(results);
        lottery.setTitle(title);
        lottery.setRule(rule);
        lottery.setToken(token);
        lottery.setTime(time);
        lottery.setCreateTime(new Date().toString());
        lotteryService.insertLottery(lottery);
        return R.ok();
    }

    /**
     * 验证抽奖结果
     */
    @RequestMapping(value = "/check", method = {RequestMethod.GET, RequestMethod.POST})
    public R checkResult(@RequestParam(name = "token", required = false) String token,
                         @RequestParam(name = "num", required = false) String num) {

        if (!isNumeric(num) || "".equals(num) || num == null) {
            return R.error(522, "请输入数字." );
        }
        if (token == null && "".equals(token)) {
            return R.error(520, "系统异常，请联系Huangjinxing" );
        }
        if (num == null && "".equals(num)) {
            return R.error(521, "请输入你的号码" );
        }
        String s = lotteryService.checkResult(token, num);
        if (s != null && !"".equals(s)) {
            return R.ok().put("data", s);
        }
        return R.ok();
    }

    /**
     * 查询抽奖场次
     */
    @RequestMapping(value = "/queryTokenList", method = {RequestMethod.GET, RequestMethod.POST})
    public R checkResult() {
        if (InitService.tokenList.isEmpty()) {
            HashMap<String, String> s = lotteryService.queryTokenList();
            if (s != null && s.size() > 0) {
                return R.ok().put("data", s);
            }
            return R.ok();
        }
        return R.ok().put("data", InitService.tokenList);
    }


    /**
     * 调用核心业务类接收消息、处理消息跟推送消息
     */
    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public String post(HttpServletRequest req) throws Exception {
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }


    public static Map<String, String> setAppcode() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + Constants.APPCODE);
        return headers;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(PATTERN_NUM_STR);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
