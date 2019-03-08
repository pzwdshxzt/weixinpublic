package com.hjx.pzwdshxzt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Description
 * 定时器·
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/8 17:18
 * @Version : 0.0.1
 */
@Configuration
@EnableScheduling
public class SchedulingController {

    private final Logger logger = LoggerFactory.getLogger(CoreController.class);


    @Scheduled(cron = "0 0 3 * * ?" )
    public void getToken() {
        logger.info("定时任务启动~~" );
    }
}
