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
    }

    @Test
    public void testJieba(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> process = segmenter.process("广东省深圳市深福保盈福大厦(深圳市福田区市花路14)", JiebaSegmenter.SegMode.INDEX);
        for (SegToken segToken :process){
            System.out.println(segToken.word);
        }

    }

}
