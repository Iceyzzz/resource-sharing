package zt.com.resourcesharing.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/17.
 */
//博客圈内容
public class Content {
    private String name;
    private Date time;
    private String details;

    public Content() {
    }

    public Content(String name, Date time, String details) {
        this.name = name;
        this.time = time;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
