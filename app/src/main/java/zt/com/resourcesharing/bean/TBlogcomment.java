package zt.com.resourcesharing.bean;

import java.sql.Time;

/**
 * TBlogcomment entity. @author MyEclipse Persistence Tools
 */
public class TBlogcomment extends AbstractTBlogcomment implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TBlogcomment() {
	}

	/** full constructor */
	public TBlogcomment(TUser user,TBlog blog,Time plsj, String plnr) {
		super(user,blog,plsj, plnr);
	}

}
