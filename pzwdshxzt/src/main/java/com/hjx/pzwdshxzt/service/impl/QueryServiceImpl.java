package com.hjx.pzwdshxzt.service.impl;

import com.hjx.pzwdshxzt.constants.Constants;
import com.hjx.pzwdshxzt.service.QueryService;
import com.hjx.pzwdshxzt.util.HttpUtils;
import com.hjx.pzwdshxzt.util.NetUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

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
@Service("queryService")
public class QueryServiceImpl implements QueryService {


    @Value("${custom.szt}")
    private String querySztUrl;

    @Override
    public String queryBanlance(String cardno) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        Map<String, String> params = new HashMap<String, String>();
        params.put("cardno", "686797062");

        String html  = NetUtils.get(querySztUrl,headers, params, null);

        if (html != null && !"".equals(html)) {
            String sztBanlance = getSZTBanlance(html);
            if (Constants.SUCCESSCODE1.equals(sztBanlance)){
                sztBanlance =  getErrorMsg(html);
                return sztBanlance;
            }
            return sztBanlance;
        }

        return Constants.SUCCESSCODE1;
    }


    @Override
    public String getLuckyMenoy(String url, String phone) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        Map<String, String> querys = new HashMap<>();
        String html = NetUtils.post(url,null,"");

        return Constants.SUCCESSCODE1;
    }

}
