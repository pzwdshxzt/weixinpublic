package com.hjx.pzwdshxzt.model.price;

/**
 * Description
 * S
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/6 10:50
 * @Version :
 */
public class Shop {

    private Integer id;
    private Integer siteid;
    private String sitename;
    private String title;
    private Double price;
    private String url;
    private String iszy;
    private String youhui;
    private String updatetime;
    private Integer commentCount;
    private String img;
    private String baichuan_gylink;
    private String baichuan_yslink;
    private String baichuan_itemid;
    private AlimamaPid alimamaPID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteid() {
        return siteid;
    }

    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIszy() {
        return iszy;
    }

    public void setIszy(String iszy) {
        this.iszy = iszy;
    }

    public String getYouhui() {
        return youhui;
    }

    public void setYouhui(String youhui) {
        this.youhui = youhui;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBaichuan_gylink() {
        return baichuan_gylink;
    }

    public void setBaichuan_gylink(String baichuan_gylink) {
        this.baichuan_gylink = baichuan_gylink;
    }

    public String getBaichuan_yslink() {
        return baichuan_yslink;
    }

    public void setBaichuan_yslink(String baichuan_yslink) {
        this.baichuan_yslink = baichuan_yslink;
    }

    public String getBaichuan_itemid() {
        return baichuan_itemid;
    }

    public void setBaichuan_itemid(String baichuan_itemid) {
        this.baichuan_itemid = baichuan_itemid;
    }

    public AlimamaPid getAlimamaPID() {
        return alimamaPID;
    }

    public void setAlimamaPID(AlimamaPid alimamaPID) {
        this.alimamaPID = alimamaPID;
    }
}
