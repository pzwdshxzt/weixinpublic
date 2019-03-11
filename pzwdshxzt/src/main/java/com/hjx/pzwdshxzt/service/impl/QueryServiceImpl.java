package com.hjx.pzwdshxzt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.model.price.PriceResult;
import com.hjx.pzwdshxzt.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;

import static com.hjx.pzwdshxzt.util.HtmlParser.getErrorMsg;
import static com.hjx.pzwdshxzt.util.HtmlParser.getSZTBanlance;

/**
 * Description
 * 查询工具
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/8/6 16:57
 * @Version : 0.1.1
 */
@Component
@Service("queryService" )
public class QueryServiceImpl implements QueryService {

    private static Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

    @Value("${custom.szt}" )
    private String querySztUrl;

    /**
     * 查询深圳余额
     * @param cardno
     * @return
     * @throws Exception
     */
    @Override
    public String queryBanlance(String cardno){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofMillis(3 * 1000))
                .uri(URI.create("http://query.shenzhentong.com:8080/sztnet/qryCard.do"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .POST(HttpRequest.BodyPublishers.ofString("cardno=" + cardno))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.info(e.getMessage());
            return Constants.SUCCESSCODE1;
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return Constants.SUCCESSCODE1;
        }

        String html = response.body();
        if (html != null && !"".equals(html)) {
            String sztBanlance = getSZTBanlance(html);
            if (Constants.SUCCESSCODE1.equals(sztBanlance)) {
                sztBanlance = getErrorMsg(html);
            }
            return sztBanlance;
        }
        return Constants.SUCCESSCODE1;
    }

    @Override
    public PriceResult queryDiscount(String url){
        TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // TODO Auto-generated method stub
            }
            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // TODO Auto-generated method stub
            }
        } };

        SSLParameters sslParams = new SSLParameters();
        sslParams.setEndpointIdentificationAlgorithm("");
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
            return null;
        }
        //取消主机名验证
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification","true");
        try {
            sc.init(null, trustAllCertificates, new SecureRandom());
        } catch (KeyManagementException e) {
            log.info(e.getMessage());
            return null;
        }
        String requestUrl = null;
        try {
            requestUrl = "https://apapia.manmanbuy.com/ChromeWidgetServices/WidgetServices.ashx?p_url=" +  URLEncoder.encode(url, "utf-8" ) + "&methodName=getBiJiaInfo_wxsmall&jgzspic=no";
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            return null;
        }
        HttpClient client = HttpClient.newBuilder()
                .sslContext(sc)
                .sslParameters(sslParams).build();
        HttpRequest request = HttpRequest.newBuilder().timeout(Duration.ofMillis(4 * 1000))
                .uri(URI.create(requestUrl))
                .header("user-agent", "Shortcuts/732 CFNetwork/975.0.3 Darwin/18.2.0")
                .build();


        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return null;
        }

        String html = response.body();
        if (html != null && !"".equals(html)) {
            JSONObject jsonObject = JSON.parseObject(html);
            try {
                PriceResult priceResult = jsonObject.toJavaObject(PriceResult.class);
                return priceResult;
            } catch (Exception e) {
                log.info(e.getMessage());
                return null;
            }
        }
        return null;
    }

}
