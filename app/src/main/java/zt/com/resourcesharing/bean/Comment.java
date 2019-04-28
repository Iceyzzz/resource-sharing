package zt.com.resourcesharing.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/1.
 */
//评论类
public class Comment {
    private String cName;
    private Date cTime;
    private String comments;

    public Comment() {
    }

    public Comment(String cName, Date cTime, String comments) {
        this.cName = cName;
        this.cTime = cTime;
        this.comments = comments;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
