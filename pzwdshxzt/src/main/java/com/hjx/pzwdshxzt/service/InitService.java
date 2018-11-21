package com.hjx.pzwdshxzt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author szzt
 * 系统启动时加载
 */
@Component
public class InitService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitService.class);
    public static final Map<String,Object> maps = new HashMap<>();
    public static final Map<String,Object> results = new HashMap<>();
    public static final Map<String,Object> tokenList = new HashMap<>();


    @Autowired
    private LotteryService lotteryService;

    @Override
    public void run(String... args){
        logger.info("开始加载启动项......");
        maps.put("cookie","99D7CCBE579EEA4C4CA68C571EB89E1E");
        HashMap<String, String> lists = lotteryService.queryTokenList();
        if (lists!=null && lists.size()>0){
            tokenList.putAll(lists);
        }
        logger.info("加载启动项结束......");
    }

}
