package com.hjx.pzwdshxzt.model.message.request;

public class TextMessage extends RequestBaseMessage {

    /**
     *  消息内容
     */

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
