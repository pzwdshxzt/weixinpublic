package com.hjx.pzwdshxzt.service.impl;

import com.hjx.pzwdshxzt.controller.CoreController;
import com.hjx.pzwdshxzt.model.Result;
import com.hjx.pzwdshxzt.model.UnSubscribe;
import com.hjx.pzwdshxzt.model.User;
import com.hjx.pzwdshxzt.model.message.response.TextMessage;
import com.hjx.pzwdshxzt.model.weather.*;
import com.hjx.pzwdshxzt.service.*;
import com.hjx.pzwdshxzt.util.MessageUtil;
import com.hjx.pzwdshxzt.zyc.FA;
import com.hjx.pzwdshxzt.zyc.Trie;
import com.hjx.pzwdshxzt.zyc.Util;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import nl.flotsam.xeger.Xeger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hjx.pzwdshxzt.constants.Constants.*;


/**
 * @author Dwxqnswxl
 */
@Service("coreService")
public class CoreServiceImpl implements CoreService {

    private static Logger log = LoggerFactory.getLogger(CoreController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private QueryService queryService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private CityService cityService;

    /**
     * 处理微信发来的请求（包括事件的推送）
     *
     * @param request
     * @return
     */
    @Override
    public String processRequest(HttpServletRequest request) throws Exception {
        Map<String, Object> requestMap = init(request);

        return execute(requestMap);
    }


    /**
     * 判断是否是QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }


    private Map<String, Object> init(HttpServletRequest request) {
        String fromUserName = null;
        try {
            log.info(request.toString());
            Map<String, Object> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            fromUserName = (String) requestMap.get("FromUserName");

            User user = null;
            if (fromUserName != null) {
                user = userService.selectUser(fromUserName);
                if (user == null) {
                    user = new User();
                    user.setOpenId(fromUserName);
                    user.setSztNum("");
                    userService.insertUser(user);
                }
            }
            requestMap.put("user", user);
            return requestMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isAlphaDigit(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private String execute(Map<String, Object> map) throws Exception {
        String respMessage = "";
        String respContent = "请求处理异常，请稍候尝试！";
        String fromUserName = (String) map.get("FromUserName");
        String toUserName = (String) map.get("ToUserName");
        String msgType = (String) map.get("MsgType");
        String Event = (String) map.get("Event");

        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(System.currentTimeMillis());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);

        if (Event != null && SUBSCRIBE.equals(Event)) {
            return subscribe(textMessage, fromUserName);
        }
        if (Event != null && UNSUBSCRIBE.equals(Event)) {
            return unsubscribe(textMessage, fromUserName);
        }

        User user = (User) map.get("user");

        switch (msgType) {
            case MessageUtil.REQ_MESSAGE_TYPE_TEXT: {
                String content = (String) map.get("Content");
                if (isTime(content)) {
                    user.setEndTime(content);
                    return updateEndTime(textMessage, user);
                }

                if (content.length() == NINE && isAlphaDigit(content)) {
                    user.setSztNum(content);
                    return updateSZT(textMessage, user);
                }
                if (isSetLocal(content)) {
                    return setmanualLocal(textMessage, content, user);
                }

                if (isReg(content)) {
                    return regBackMessages(textMessage, content);
                }
                if (isRegs(content)) {
                    return regexGen(textMessage, content);
                }

                /**
                 *  如果用户发送表情，则回复同样表情。
                 */
                if (isQqFace(content)) {
                    respContent = content;
                } else {
                    switch (content) {
                        case "1":
                            /**
                             *  查询深圳通余额
                             */
                            String cardno = user.getSztNum();
                            if (cardno != null && !"".equals(cardno)) {
                                respContent = queryService.queryBanlance(cardno);
                                respContent = "您账户(" + cardno + ")\n" + respContent + "\n" + "修改卡号直接发送9位卡号即可";
                            } else {
                                respContent = "你还没设置深圳通卡号\n卡号一般在卡的右下角\n发送卡号即可设置 ";
                            }

                            break;
                        case "2":
                            String endTime = user.getEndTime();
                            if (endTime != null && !"".equals(endTime)) {
                                respContent = getEndTime(endTime);
                            } else {
                                respContent = "你还没设置下班时间，发送 **:**:** 即可设置 ";
                            }
                            break;
                        case "3": {
                            if (user.getLocal() != null) {
                                City city = new City();
                                city.setCity(user.getAddress());
                                Result result = weatherService.queryWeather(city);
                                if (SUCCESSCODE.equals(result.getStatus())) {
                                    CityDo cityDo = result.getResult();
                                    if (cityDo != null) {
                                        StringBuffer buffer = new StringBuffer();
                                        buffer.append("当前日期:" + cityDo.getDate()).append("\n");
                                        buffer.append("当前城市:" + cityDo.getCity()).append("\n");
                                        buffer.append("当前天气:" + cityDo.getWeather()).append("\n");
                                        buffer.append("修改城市直接发送当前的地理位置即可");
                                        respContent = String.valueOf(buffer);
                                    }
                                } else {
                                    respContent = result.getMsg() + "\n修改城市直接发送当前的地理位置即可";
                                }
                            } else {
                                respContent = "你还没设置当前位置，点开右下角+号,发送地理位置即可设置";
                            }
                            break;

                        }
                        case "4":
                            if (user.getLocal() != null) {
                                City city = new City();
                                city.setCity(user.getAddress());
                                Result result = weatherService.queryWeather(city);
                                if (SUCCESSCODE.equals(result.getStatus())) {
                                    CityDo cityDo = result.getResult();
                                    if (cityDo != null) {
                                        StringBuffer buffer = new StringBuffer();
                                        buffer.append("当前日期:" + cityDo.getDate()).append("\n");
                                        buffer.append("当前城市:" + cityDo.getCity()).append("\n");
                                        buffer.append("当前天气:" + cityDo.getWeather()).append("\n");
                                        buffer.append("当前温度:" + cityDo.getTemp() + " " + cityDo.getTemplow() + "-" + cityDo.getTemplow()).append("\n");
                                        buffer.append("当前湿度:" + cityDo.getHumidity()).append("\n");
                                        buffer.append("当前气压:" + cityDo.getPressure()).append("\n");
                                        buffer.append("当前风速:" + cityDo.getWindspeed()).append("\n");
                                        buffer.append("当前风向:" + cityDo.getWinddirect()).append("\n");
                                        buffer.append("风力大小:" + cityDo.getWindpower()).append("\n");
                                        buffer.append("PM2.5:" + cityDo.getAqi().getPm2_5()).append("\n");
                                        buffer.append("修改城市直接发送当前的地理位置即可");
                                        respContent = String.valueOf(buffer);
                                    }
                                } else {
                                    respContent = result.getMsg() + "\n修改城市直接发送当前的地理位置即可";
                                }
                            } else {
                                respContent = "你还没设置当前位置，点开右下角+号,发送地理位置即可设置";
                            }
                            break;
                        case "5":
                            if (user.getLocal() != null) {
                                City city = new City();
                                city.setCity(user.getAddress());
                                Result result = weatherService.queryWeather(city);
                                if (SUCCESSCODE.equals(result.getStatus())) {
                                    CityDo cityDo = result.getResult();
                                    Set<Daily> daily = cityDo.getDaily();
                                    List<Daily> dailies = new ArrayList<>(daily);
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    dailies.sort((a1, a2) -> {
                                        LocalDate p1 = LocalDate.parse(a1.getDate(), formatter);
                                        LocalDate p2 = LocalDate.parse(a2.getDate(), formatter);
                                        return p1.compareTo(p2);
                                    });
                                    if (cityDo != null) {
                                        StringBuffer buffer = new StringBuffer();
                                        buffer.append("当前城市:" + cityDo.getCity()).append("\n");
                                        buffer.append("天气预测如下:").append("\n");
                                        dailies.forEach((Daily d) -> {
                                            buffer.append("日期:" + "\n" + d.getDate() + " " + d.getWeek() + "\n日升时间:" + d.getSunrise() + "\n日落时间:" + d.getSunset()).append("\n");
                                            Weather day = d.getDay();
                                            buffer.append("白天天气:" + day.getWeather() + "\n" +
                                                    "\t最高气温:" + day.getTemphigh() + "\n\t风向:" + day.getWinddirect() + "\n\t风力:" + day.getWindpower()).append("\n");
                                            Weather night = d.getNight();
                                            buffer.append("夜晚天气:" + night.getWeather() + "\n" +
                                                    "\t最低气温:" + night.getTemplow() + "\n\t风向:" + night.getWinddirect() + "\n\t风力:" + night.getWindpower()).append("\n");
                                            buffer.append("------------------\n");

                                        });
                                        buffer.append("修改城市直接发送当前的地理位置即可");
                                        respContent = String.valueOf(buffer);
                                    }
                                } else {
                                    respContent = result.getMsg() + "\n修改城市直接发送当前的地理位置即可";
                                }
                            } else {
                                respContent = "你还没设置当前位置，点开右下角+号,发送地理位置即可设置";
                            }
                            break;
                        case "6":
                            if (user.getLocal() != null) {
                                City city = new City();
                                city.setCity(user.getAddress());
                                Result result = weatherService.queryWeather(city);
                                if (SUCCESSCODE.equals(result.getStatus())) {
                                    CityDo cityDo = result.getResult();
                                    if (cityDo != null) {
                                        Set<DetailInfo> index = cityDo.getIndex();
                                        StringBuffer buffer = new StringBuffer();
                                        buffer.append("当前城市:" + cityDo.getCity()).append("\n");
                                        buffer.append("今天建议如下:").append("\n");
                                        index.forEach((DetailInfo d) -> {
                                            buffer.append(d.getIname() + ":" + d.getIvalue() + "\n建议:" + d.getDetail()).append("\n");
                                            buffer.append("---------------\n");
                                        });
                                        buffer.append("修改城市直接发送当前的地理位置即可");
                                        respContent = String.valueOf(buffer);
                                    }
                                } else {
                                    respContent = result.getMsg() + "\n修改城市直接发送当前的地理位置即可";
                                }
                            } else {
                                respContent = "你还没设置当前位置，点开右下角+号,发送地理位置即可设置";
                            }
                            break;
                        case "7":
                            respContent = "发送 \nreg|表达式 \n例如: \nreg|node \n即可";
                            break;
                        case "8":
                            respContent = "发送 \nregs|字符串|字符串|字符串 \n例如: \nregs|node|nosd|nodes \n即可（至少两个）";
                            break;
                        default: {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("您好，很高兴为您服务：").append("\n");
                            buffer.append("1.查询深圳通余额").append("\n");
                            buffer.append("2.下班倒计时").append("\n");
                            buffer.append("3.查询天气简版").append("\n");
                            buffer.append("4.查询天气详情").append("\n");
                            buffer.append("5.查询天气预测").append("\n");
                            buffer.append("6.查询今日建议").append("\n");
                            buffer.append("7.通过正则表达式生成符合的字符串").append("\n");
                            buffer.append("8.通过字符串数组反向生成符合的正则表达式").append("\n");
                            buffer.append("或者您可以尝试发送表情").append("\n");
                            respContent = String.valueOf(buffer);
                            break;
                        }
                    }
                }
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            break;
            case MessageUtil.REQ_MESSAGE_TYPE_IMAGE: {
                respContent = "您发送的是图片消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            break;
            case MessageUtil.REQ_MESSAGE_TYPE_LOCATION: {
                respContent = "地理位置设置成功@@";
                String local_x = (String) map.get("Location_X");
                String local_y = (String) map.get("Location_Y");
                String address = (String) map.get("Label");
                user.setLocal(local_x + "," + local_y);
                Set<String> sets = getLocal(address);
                List<City> cities = cityService.queryCity(sets);
                if (cities.size() > 0) {
                    user.setAddress(cities.get(0).getCity());
                    userService.updateLocal(user);
                    respContent = respContent + "\n你当前设置的位置为:" + cities.get(0).getCity();
                } else {
                    respContent = "位置搜索不到，设置失败@local:+地理位置 \n例如: @local:深圳 ";
                }
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            break;
            case MessageUtil.REQ_MESSAGE_TYPE_LINK: {
                respContent = "您发送的是链接消息！";
                String url = (String) map.get("Url");
                queryService.getLuckyMenoy(url, "1");
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            break;
            case MessageUtil.REQ_MESSAGE_TYPE_VOICE: {
                respContent = "您发送的是音频消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            break;
            default: {
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
        }

        return respMessage;
    }

    /**
     * 关注事件
     *
     * @return
     */
    private String subscribe(TextMessage textMessage, String openId) {
        UnSubscribe unSubscribe = userService.selectUnSubscribe(openId);
        StringBuffer buffer = new StringBuffer();
        if (unSubscribe != null) {
            userService.deleteUnSubscribe(openId);
            buffer.append("傻屌，还厚着脸皮回来?").append("\n");
            buffer.append("算了，爸爸原谅你了.");
        } else {
            buffer.append("您好，很高兴为您服务：").append("\n");
            buffer.append("1.查询深圳通余额").append("\n");
            buffer.append("2.下班倒计时").append("\n");
            buffer.append("3.查询天气简版").append("\n");
            buffer.append("4.查询天气详情").append("\n");
            buffer.append("5.查询天气预测").append("\n");
            buffer.append("6.查询今日建议").append("\n");
            buffer.append("7.通过正则表达式生成符合的字符串").append("\n");
            buffer.append("8.通过字符串数组反向生成符合的正则表达式").append("\n");
            buffer.append("或者您可以尝试发送表情").append("\n");
        }
        String respContent = String.valueOf(buffer);
        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }

    /**
     * 取关事件
     *
     * @return
     */
    private String unsubscribe(TextMessage textMessage, String openId) {
        userService.addUnSubscribe(openId);
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        String respContent = String.valueOf(buffer);
        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }

    /**
     * 更新深圳通卡号
     *
     * @return
     */
    private String updateSZT(TextMessage textMessage, User user) {
        StringBuffer buffer = new StringBuffer();
        userService.updateNum(user);
        buffer.append("深圳通卡号设置成功!");
        textMessage.setContent(String.valueOf(buffer));
        return MessageUtil.textMessageToXml(textMessage);
    }

    /**
     * 更新倒计时
     *
     * @return
     */
    private String updateEndTime(TextMessage textMessage, User user) {
        StringBuffer buffer = new StringBuffer();
        userService.updateEndTime(user);
        buffer.append("倒计时设置成功!");
        textMessage.setContent(String.valueOf(buffer));
        return MessageUtil.textMessageToXml(textMessage);
    }

    private boolean isTime(String time) {
        Pattern p = Pattern.compile(PATTERNTIME);
        return p.matcher(time).matches();
    }

    public String getEndTime(String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String nowStr = dateFormat.format(new Date());
        String endStr = nowStr.substring(0, nowStr.indexOf(" ")) + " " + time;
        String preStr = "当前时间:" + nowStr + "\n" + "距离您设置的下班时间:\n" + endStr + "\n";
        long l = (dateFormat.parse(endStr).getTime() - dateFormat.parse(nowStr).getTime()) / 1000;
        return l > 0 ? preStr + "还剩下:" + l + "秒" : preStr + "时间已经到了\n 您可以下班了";
    }


    /**
     * 结巴分词把地址分割
     *
     * @param str
     * @return
     */
    private Set<String> getLocal(String str) {
        Set<String> sets = new HashSet<>();
        if (str != null && !"".equals(str)) {
            JiebaSegmenter segmenter = new JiebaSegmenter();
            List<SegToken> process = segmenter.process(str, JiebaSegmenter.SegMode.SEARCH);
            process.forEach((SegToken segToken) -> {
                if (segToken.word.length() >= 2) {
                    sets.add(segToken.word);
                }
            });
        }
        return sets;

    }

    /**
     * 获取字符串中的地址
     *
     * @param str
     * @return
     */
    private String getLocalInStr(String str) {
        if (str != null && !"".equals(str)) {
            int i = str.indexOf(LOCALCODE);
            if (i != -1) {
                String substring = str.substring(7 + i, str.length());
                return substring;
            }
        }
        return null;
    }

    /**
     * 手动设置地理位置
     *
     * @param str
     * @param user
     */
    private String setmanualLocal(TextMessage textMessage, String str, User user) {
        String respcontent = "请输入正确的地址或者地址找不到";
        String localInStr = getLocalInStr(str);
        if (localInStr != null && !"".equals(localInStr)) {
            List<City> cities = cityService.queryCity(localInStr);
            if (cities.size() > 0) {
                user.setAddress(cities.get(0).getCity());
                userService.updateLocal(user);
                respcontent = "您设置的位置:" + cities.get(0).getCity() + "\n设置成功了 \n可以是使用了";
            }
        }
        textMessage.setContent(respcontent);
        return MessageUtil.textMessageToXml(textMessage);
    }

    /**
     * 判断是否是来设置地址的
     *
     * @param s
     * @return
     */
    private boolean isSetLocal(String s) {
        return s.contains(LOCALCODE);
    }

    /**
     * 是否是反推reg
     *
     * @param s
     * @return
     */
    private boolean isReg(String s) {
        if (s.startsWith(REGCODE)) {
            return true;
        }
        return false;
    }

    /**
     * 返回符合正则的字符串
     *
     * @return
     */
    private String regBackMessages(TextMessage textMessage, String s) {

        StringBuffer buffer = new StringBuffer();
        log.info("input:" + s);
        if (s != null && !"".equals(s)) {
            int i = s.indexOf(REGCODE);
            if (i != -1) {
                s = s.substring(4 + i, s.length());
                log.info("catch：" + s);
                try {

                    Xeger generator = new Xeger(s);
                    buffer.append("你输入的正则表达式为:").append("\n");
                    buffer.append(s).append("\n");
                    buffer.append("反推符合的正则表达式的如下:").append("\n");
                    buffer.append("(一次最多五个可能重复)").append("\n");
                    for (int j = 0; j < 5; j++) {
                        buffer.append(generator.generate()).append("\n");
                    }

                } catch (StackOverflowError e) {
                    buffer = new StringBuffer();
                    buffer.append("正则表达式输入不正确!!");
                }
            } else {
                buffer.append("输入格式不正确或者!!").append("\n");
                buffer.append("正则表达式输入不正确!!").append("\n");
            }
        }


        String respContent = String.valueOf(buffer);
        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }


    /**
     * 是否是反推reg
     *
     * @param s
     * @return
     */
    private boolean isRegs(String s) {
        if (s.startsWith(REGSCODE)) {
            return true;
        }
        return false;
    }

    /**
     * 返回Reg
     *
     * @return
     */
    private String regexGen(TextMessage textMessage, String s) {

        StringBuffer buffer = new StringBuffer();
        log.info("input:" + s);
        if (s != null && !"".equals(s)) {
            String[] reg = s.split("\\|");
            log.info("catch：" + reg.toString());
            try {
                Trie t = new Trie();
                for (int i = 1; i < reg.length; i++) {
                    t.insert(reg[i]);
                }

                FA machine = Util.createFAFromTrie(t);
                buffer.append("你输入的需要转换正则表达式的数组如下:").append("\n");
                for (int i = 1; i < reg.length; i++) {
                    buffer.append(reg[i]).append("\n");
                }
                buffer.append("反推符合正则表达式如下:").append("\n");
                buffer.append(machine.genRegEx()).append("\n");

            } catch (Exception e) {
                buffer = new StringBuffer();
                buffer.append("条件输入不正确!!");
            }

        }


        String respContent = String.valueOf(buffer);
        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }
}

