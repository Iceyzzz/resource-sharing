package zt.com.resourcesharing.bean;

import java.sql.Time;

/**
 * AbstractTBlogcomment entity provides the base persistence definition of the
 * TBlogcomment entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTBlogcomment implements java.io.Serializable {

	// Fields

	private int id;
	private TUser user;
	private TBlog blog;

	private Time plsj;
	private String plnr;

	// Constructors

	/** default constructor */
	public AbstractTBlogcomment() {
	}

	/** full constructor */
	public AbstractTBlogcomment(TUser user,TBlog blog,Time plsj, String plnr) {
		this.user=user;
		this.blog=blog;
		this.plsj = plsj;
		this.plnr = plnr;
	}

	// Property accessors


	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public TBlog getBlog() {
		return blog;
	}

	public void setBlog(TBlog blog) {
		this.blog = blog;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Time getPlsj() {
		return this.plsj;
	}

	public void setPlsj(Time plsj) {
		this.plsj = plsj;
	}

	public String getPlnr() {
		return this.plnr;
	}

	public void setPlnr(String plnr) {
		this.plnr = plnr;
	}

}