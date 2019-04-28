package zt.com.resourcesharing.bean;

import java.sql.Time;

/**
 * TDataresource entity. @author MyEclipse Persistence Tools
 */

public class TDataresource implements java.io.Serializable {

	// Fields

	private Integer id;

	private TUser userId;
	private String zybt;
	private String zylx;
	private String gjbq;
	private String zyjj;
	private Time scsj;
	private Integer djcs;
	private String zylj;

	// Constructors

	/** default constructor */
	public TDataresource() {
	}

	/** minimal constructor */
	public TDataresource(String zybt, String zylx, String gjbq, String zyjj, Time scsj, Integer djcs, String zylj) {
		this.zybt = zybt;
		this.zylx = zylx;
		this.gjbq = gjbq;
		this.zyjj = zyjj;
		this.scsj = scsj;
		this.djcs = djcs;
		this.zylj = zylj;
	}

	/** full constructor */
	public TDataresource(TUser userId, String zybt, String zylx, String gjbq, String zyjj, Time scsj, Integer djcs,
			String zylj) {
		this.userId = userId;
		this.zybt = zybt;
		this.zylx = zylx;
		this.gjbq = gjbq;
		this.zyjj = zyjj;
		this.scsj = scsj;
		this.djcs = djcs;
		this.zylj = zylj;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public TUser getUserId() {
		return userId;
	}

	public void setUserId(TUser userId) {
		this.userId = userId;
	}

	public String getZybt() {
		return this.zybt;
	}

	public void setZybt(String zybt) {
		this.zybt = zybt;
	}

	public String getZylx() {
		return this.zylx;
	}

	public void setZylx(String zylx) {
		this.zylx = zylx;
	}

	public String getGjbq() {
		return this.gjbq;
	}

	public void setGjbq(String gjbq) {
		this.gjbq = gjbq;
	}

	public String getZyjj() {
		return this.zyjj;
	}

	public void setZyjj(String zyjj) {
		this.zyjj = zyjj;
	}

	public Time getScsj() {
		return this.scsj;
	}

	public void setScsj(Time scsj) {
		this.scsj = scsj;
	}

	public Integer getDjcs() {
		return this.djcs;
	}

	public void setDjcs(Integer djcs) {
		this.djcs = djcs;
	}

	public String getZylj() {
		return this.zylj;
	}

	public void setZylj(String zylj) {
		this.zylj = zylj;
	}

}