package com.hjx.pzwdshxzt;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class PzwdshxztApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("https://item.m.jd.com/product/6772447.html?&utm_source=iosapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=CopyURL&ad_od=share&ShareTm=UD6R1Yp4mFzsyndBeaX4TkjMthMyLzMT%2BUhRHlehTFxE6PRhZ9r%2BoJvDlpkD0%2BTGFpMbxwBJDAC/FEVIZtPHmBeY2lK1Y5sJkZ%2BCZ10IcGWS8i6at49inVxYnh%2BGEz79M9PaSQ6UYXsLc79Q9Pd8ChplD%2B6FgQirJ2siGKbboXI=".length());
    }

    @Test
    public void testJieba() {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> process = segmenter.process("广东省深圳市深福保盈福大厦(深圳市福田区市花路14)", JiebaSegmenter.SegMode.INDEX);
        for (SegToken segToken : process) {
            System.out.println(segToken.word);
        }

    }

}
