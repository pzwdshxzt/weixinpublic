package com.hjx.pzwdshxzt.service.impl;

import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.service.QueryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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


    @Value("${custom.szt}" )
    private String querySztUrl;

    /**
     * 查询深圳余额
     * @param cardno
     * @return
     * @throws Exception
     */
    @Override
    public String queryBanlance(String cardno) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://query.shenzhentong.com:8080/sztnet/qryCard.do"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .POST(HttpRequest.BodyPublishers.ofString("cardno=" + cardno))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

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

}
