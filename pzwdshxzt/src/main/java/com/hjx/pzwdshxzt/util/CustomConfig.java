package com.hjx.pzwdshxzt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hjx
 */
@Component
public class CustomConfig {

    private static Logger log = LoggerFactory.getLogger(CustomConfig.class);
    private volatile static CustomConfig instance = null;

    public static CustomConfig getInstance() {
        if (null == instance) {
            synchronized (CustomConfig.class) {
                if (null == instance) {
                    instance = (CustomConfig) SpringHelper.getBean("customConfig" );
                }
            }
        }
        return instance;
    }


}
