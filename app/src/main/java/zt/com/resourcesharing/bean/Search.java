package zt.com.resourcesharing.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/17.
 */
//各分类内容
public class Search {
    private int id;
    private String title;
    private String click;
    private String writer;
    private Date time;
    private String category;
    private String brief;
    private String content;

    public Search() {
    }

    public Search(String title, String click, String writer, Date time, String category, String brief, String content) {
        this.title = title;
        this.click = click;
        this.writer = writer;
        this.time = time;
        this.category = category;
        this.brief = brief;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
