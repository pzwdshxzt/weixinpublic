package com.hjx.pzwdshxzt.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;


/**
 * @author hjx
 */
@Component
public class CustomConfig {

    private static Logger log = LoggerFactory.getLogger(CustomConfig.class);
    private volatile static CustomConfig instance = null;
    public static CustomConfig getInstance() {
        if (null == instance) {
            synchronized (CustomConfig.class){
                if (null == instance){
                    instance = (CustomConfig) SpringHelper.getBean("customConfig");
                }
            }
        }
        return instance;
    }






}
