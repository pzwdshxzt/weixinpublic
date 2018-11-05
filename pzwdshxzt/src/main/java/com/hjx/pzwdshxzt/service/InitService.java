package com.hjx.pzwdshxzt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author szzt
 * 系统启动时加载
 */
@Component
public class InitService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitService.class);
    public static final Map<String,Object> maps = new HashMap<>();

    @Override
    public void run(String... args){
        logger.info("开始加载启动项......");
        maps.put("cookie","99D7CCBE579EEA4C4CA68C571EB89E1E");
        logger.info("加载启动项结束......");
    }

}
