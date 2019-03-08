package com.hjx.pzwdshxzt.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.model.price.PriceResult;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;

import static com.hjx.pzwdshxzt.util.HtmlParser.getErrorMsg;
import static com.hjx.pzwdshxzt.util.HtmlParser.getSZTBanlance;

/**
 * http
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2019/3/8 16:06
 * @Version :
 */
public class HttpUtils {


    public static void testHttpClient() throws IOException, InterruptedException {
        //1.set connect timeout
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        //2.set read timeout
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://openjdk.java.net/"))
                .timeout(Duration.ofMillis(5009))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
//        HttpUtils.testHttpClient();
//        testSzt();
        testUrl();
    }

    public static void testSzt() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://query.shenzhentong.com:8080/sztnet/qryCard.do"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .POST(HttpRequest.BodyPublishers.ofString("cardno=686797062"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String html = response.body();
        if (html != null && !"".equals(html)) {
            String sztBanlance = getSZTBanlance(html);
            if (Constants.SUCCESSCODE1.equals(sztBanlance)) {
                sztBanlance = getErrorMsg(html);

            }
            System.out.println(sztBanlance);
        }

    }


    public static void testUrl() throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {

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


//        HostnameVerifier trustAllHostnames = new HostnameVerifier() {
//        	@Override
//        	public boolean verify(String hostname, SSLSession session) {
//        		return true; // Just allow them all.
//        	}
//         };

        int timeoutInSeconds = 60;
        SSLParameters sslParams = new SSLParameters();
        sslParams.setEndpointIdentificationAlgorithm("");
        SSLContext sc = SSLContext.getInstance("SSL");
        //取消主机名验证
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification","true");
        sc.init(null, trustAllCertificates, new SecureRandom());

        String url = "http://m.qobez.top/h.3kyM6OC";
        String requestUrl = "https://apapia.manmanbuy.com/ChromeWidgetServices/WidgetServices.ashxp_url=?p_url=" +  URLEncoder.encode(url, "utf-8" ) + "&jsoncallback=&methodName=getBiJiaInfo_wxsmall&jgzspic=no";

        HttpClient client = HttpClient.newBuilder()
                .sslContext(sc)
                .sslParameters(sslParams).build();
        HttpRequest request = HttpRequest.newBuilder().timeout(Duration.ofMillis(timeoutInSeconds * 1000))
                .uri(URI.create(requestUrl))
//                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("User-Agent", "Shortcuts/732 CFNetwork/975.0.3 Darwin/18.2.0")
//                .POST(HttpRequest.BodyPublishers.ofString("p_url=" +  URLEncoder.encode(url, "utf-8" ) + "&jsoncallback=&methodName=getBiJiaInfo_wxsmall&jgzspic=no"))
                .build();


        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String html = response.body();
        if (html != null && !"".equals(html)) {
            JSONObject jsonObject = JSON.parseObject(html);
            try {
                PriceResult priceResult = jsonObject.toJavaObject(PriceResult.class);
                System.out.println(priceResult.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

