package zt.com.resourcesharing.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/17.
 */
//各分类内容
public class Essay {
    private int id;
    private String eTitle;
    private String eClick;
    private String eWriter;
    private Date eTime;
    private String eCategory;
    private String eBrief;

    public Essay() {
    }

    public Essay(String eTitle, String eClick, String eWriter, Date eTime, String eCategory, String eBrief) {
        this.eTitle = eTitle;
        this.eClick = eClick;
        this.eWriter = eWriter;
        this.eTime = eTime;
        this.eCategory = eCategory;
        this.eBrief = eBrief;
    }

    public Essay(int id, String eTitle, String eClick, String eWriter, Date eTime, String eCategory, String eBrief) {
        this.id = id;
        this.eTitle = eTitle;
        this.eClick = eClick;
        this.eWriter = eWriter;
        this.eTime = eTime;
        this.eCategory = eCategory;
        this.eBrief = eBrief;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String geteTitle() {
        return eTitle;
    }

    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }

    public String geteClick() {
        return eClick;
    }

    public void seteClick(String eClick) {
        this.eClick = eClick;
    }

    public String geteWriter() {
        return eWriter;
    }

    public void seteWriter(String eWriter) {
        this.eWriter = eWriter;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    public String geteCategory() {
        return eCategory;
    }

    public void seteCategory(String eCategory) {
        this.eCategory = eCategory;
    }

    public String geteBrief() {
        return eBrief;
    }

    public void seteBrief(String eBrief) {
        this.eBrief = eBrief;
    }
}
