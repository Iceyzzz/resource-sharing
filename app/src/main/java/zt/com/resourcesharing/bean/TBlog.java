package zt.com.resourcesharing.bean;


import java.util.Date;

/**
 * TBlog entity. @author MyEclipse Persistence Tools
 */

public class TBlog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer blogCategoryId;
	private String bwbt;//博文标题
	private String bwjj;//博文简介
	private String bwnr;//博文内容
	private Integer bwdjcs;
	private Date bwcjsj;//博文创建时间

	// Constructors

	/** default constructor */
	public TBlog() {
	}

	/** minimal constructor */
	public TBlog(String bwbt, String bwjj, String bwnr, Integer bwdjcs, Date bwcjsj) {
		this.bwbt = bwbt;
		this.bwjj = bwjj;
		this.bwnr = bwnr;
		this.bwdjcs = bwdjcs;
		this.bwcjsj = bwcjsj;
	}

	/** full constructor */
	public TBlog(Integer userId, Integer blogCategoryId, String bwbt, String bwjj, String bwnr, Integer bwdjcs,
			Date bwcjsj) {
		this.userId = userId;
		this.blogCategoryId = blogCategoryId;
		this.bwbt = bwbt;
		this.bwjj = bwjj;
		this.bwnr = bwnr;
		this.bwdjcs = bwdjcs;
		this.bwcjsj = bwcjsj;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBlogCategoryId() {
		return this.blogCategoryId;
	}

	public void setBlogCategoryId(Integer blogCategoryId) {
		this.blogCategoryId = blogCategoryId;
	}

	public String getBwbt() {
		return this.bwbt;
	}

	public void setBwbt(String bwbt) {
		this.bwbt = bwbt;
	}

	public String getBwjj() {
		return this.bwjj;
	}

	public void setBwjj(String bwjj) {
		this.bwjj = bwjj;
	}

	public String getBwnr() {
		return this.bwnr;
	}

	public void setBwnr(String bwnr) {
		this.bwnr = bwnr;
	}

	public Integer getBwdjcs() {
		return this.bwdjcs;
	}

	public void setBwdjcs(Integer bwdjcs) {
		this.bwdjcs = bwdjcs;
	}

	public Date getBwcjsj() {
		return bwcjsj;
	}

	public void setBwcjsj(Date bwcjsj) {
		this.bwcjsj = bwcjsj;
	}
}