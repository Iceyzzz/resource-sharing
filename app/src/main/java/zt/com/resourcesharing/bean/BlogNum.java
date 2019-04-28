package zt.com.resourcesharing.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/2.
 */

public class BlogNum {
    private int id;
    private String name;
    private Date time;
    private String click;
    private String content;

    public BlogNum() {
    }

    public BlogNum(String name, Date time, String click, String content) {
        this.name = name;
        this.time = time;
        this.click = click;
        this.content = content;
    }

    public BlogNum(int id, String name, Date time, String click, String content) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.click = click;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
