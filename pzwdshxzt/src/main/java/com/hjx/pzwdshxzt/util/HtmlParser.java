package com.hjx.pzwdshxzt.util;


import com.hjx.pzwdshxzt.constants.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {


    /**
     * 获取value值
     *
     * @param e
     * @return
     */
    public static String getValue(Element e) {
        return e.attr("value" );
    }

    /**
     * 获取
     * <tr>
     * 和
     * </tr>
     * 之间的文本
     *
     * @param e
     * @return
     */
    public static String getText(Element e) {
        return e.text();
    }

    /**
     * 识别属性id的标签,一般一个html页面id唯一
     *
     * @param body
     * @param id
     * @return
     */
    public static Element getID(String body, String id) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        Elements elements = doc.select("#" + id);
        // 返回第一个
        return elements.first();
    }

    /**
     * 识别属性class的标签
     *
     * @param body
     * @param classTag
     * @return
     */
    public static Elements getClassTag(String body, String classTag) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        return doc.select("." + classTag);
    }

    /**
     * 获取tr标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTR(Element e) {
        return e.getElementsByTag("tr" );
    }

    /**
     * 获取td标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTD(Element e) {
        return e.getElementsByTag("td" );
    }

    /**
     * 获取表元组
     *
     * @param table
     * @return
     */
    public static List<List<String>> getTables(Element table) {
        List<List<String>> data = new ArrayList<>();

        for (Element etr : table.select("tr" )) {
            List<String> list = new ArrayList<>();
            for (Element etd : etr.select("td" )) {
                String temp = etd.text();
                //增加一行中的一列
                list.add(temp);
            }
            //增加一行
            data.add(list);
        }
        return data;
    }

    /**
     * 获取DIvs表元组
     *
     * @param div
     * @return
     */
    public static List<String> getDivs(Element div) {
        List<String> list = new ArrayList<>();
        for (Element etr : div.select("div" )) {
            String temp = etr.text();
            //增加一行中的一列
            list.add(temp);
        }
        return list;
    }


    /**
     * 读html文件
     *
     * @param fileName
     * @return
     */
    public static String readHtml(String fileName) {
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getSZTBanlance(String html) {

        Document doc = Jsoup.parse(html);
        Element table = doc.select("table" ).get(1);
        List<List<String>> list = getTables(table);
        System.out.println(list.toString());
        return list.get(0).size() > 3 ? list.get(0).get(2) + "\n" + list.get(0).get(3) + "" : Constants.SUCCESSCODE1;
    }

    public static String getErrorMsg(String html) {

        Document doc = Jsoup.parse(html);
        Element div = doc.select("div" ).get(1);
        List<String> list = getDivs(div);
        System.out.println(list.toString());
        return list.size() > 1 ? list.size() == 2 ? list.get(1) : list.get(1) : Constants.SUCCESSCODE1;
    }


}
