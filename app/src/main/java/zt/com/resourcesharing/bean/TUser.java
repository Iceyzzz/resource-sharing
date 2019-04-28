package zt.com.resourcesharing.bean;


import java.util.Date;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */

public class TUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String yhnc;
	private String xm;
	private String zh;
	private String dlmm;
	private String xb;
	private Date csny;
	private String grjj;
	private String jybs;

	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** minimal constructor */
	public TUser(String zh, String dlmm, String xb, Date csny, String jybs) {
		this.zh = zh;
		this.dlmm = dlmm;
		this.xb = xb;
		this.csny = csny;
		this.jybs = jybs;
	}

	/** full constructor */
	public TUser(String yhnc, String xm, String zh, String dlmm, String xb, Date csny, String grjj, String jybs) {
		this.yhnc = yhnc;
		this.xm = xm;
		this.zh = zh;
		this.dlmm = dlmm;
		this.xb = xb;
		this.csny = csny;
		this.grjj = grjj;
		this.jybs = jybs;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYhnc() {
		return this.yhnc;
	}

	public void setYhnc(String yhnc) {
		this.yhnc = yhnc;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getZh() {
		return this.zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

	public String getDlmm() {
		return this.dlmm;
	}

	public void setDlmm(String dlmm) {
		this.dlmm = dlmm;
	}

	public String getXb() {
		return this.xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}
	public Date getCsny() {
		return this.csny;
	}
	public void setCsny(Date csny) {
		this.csny = csny;
	}

	public String getGrjj() {
		return this.grjj;
	}

	public void setGrjj(String grjj) {
		this.grjj = grjj;
	}

	public String getJybs() {
		return this.jybs;
	}

	public void setJybs(String jybs) {
		this.jybs = jybs;
	}

}