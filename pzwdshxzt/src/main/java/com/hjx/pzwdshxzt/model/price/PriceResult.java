package com.hjx.pzwdshxzt.model.price;

/**
 * Description
 * 查询接口返回
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/6 10:20
 * @Version :
 */
public class PriceResult {

    private Integer ok;
    private String msg;
    private Single single;

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }
}
