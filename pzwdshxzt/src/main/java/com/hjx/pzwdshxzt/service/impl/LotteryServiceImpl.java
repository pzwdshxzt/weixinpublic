package com.hjx.pzwdshxzt.service.impl;

import com.hjx.pzwdshxzt.mapper.LotteryMapper;
import com.hjx.pzwdshxzt.model.Lottery.Lottery;
import com.hjx.pzwdshxzt.service.InitService;
import com.hjx.pzwdshxzt.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/9 10:13
 * @Version :
 */
@Service("lotteryService")
public class LotteryServiceImpl implements LotteryService {



    @Autowired
    private LotteryMapper lotteryMapper;

    @Override
    public void insertLottery(Lottery lottery) {
        lotteryMapper.insertLottery(lottery);
    }

    @Override
    public String checkResult(String token, String num) {

        Integer i = Integer.parseInt(num);

        List<Map<String, String>> maps = lotteryMapper.queryCheckList(token, i.toString());
        if (maps != null && maps.size()>0){
            return maps.get(0).get("title") + " \n 你已经兑奖过了 : \n " + maps.get(0).get("rule");
        }

        List<Lottery> lists = (List<Lottery>) InitService.results.get(token);
        if (lists == null || lists.size() == 0) {
            lists = lotteryMapper.selectByToken(token);
            if (lists != null && lists.size() > 0) {
                InitService.results.put(token, lists);
            }
        }

        if (lists != null && lists.size() > 0) {

            for (Lottery lottery : lists) {
                String[] split = lottery.getResults().split(" ");

                boolean isLucky = false;
                for (String s : split) {
                    if (s != null && !"".equals(s) && i.equals(Integer.parseInt(s))) {
                        isLucky = true;
                        lotteryMapper.insertCheck(token,i.toString(),lottery.getRule(),new Date().toString(),lottery.getTime(),lottery.getTitle());
                        break;
                    }
                }
                if (isLucky) {
                    return "恭喜你在" + lottery.getTitle() + "中获得 :\n" + lottery.getRule() + "感谢";
                }
            }
            return "sorry,你没有中奖,嘻嘻嘻";
        }
        return null;
    }

    @Override
    public HashMap<String, String> queryTokenList() {
        List<Map<String, String>> lists = lotteryMapper.queryTokenList();
        if (lists != null && lists.size() > 0) {
            HashMap<String, String> maps = new HashMap<>();
            for (Map<String, String> o : lists) {
                maps.put(o.get("token"), o.get("time"));
            }
            return maps;
        }
        return null;
    }


}
