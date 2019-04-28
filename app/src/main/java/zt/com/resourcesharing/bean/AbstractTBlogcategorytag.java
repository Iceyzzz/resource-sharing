package zt.com.resourcesharing.bean;

/**
 * AbstractTBlogcategorytag entity provides the base persistence definition of
 * the TBlogcategorytag entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTBlogcategorytag implements java.io.Serializable {

	// Fields

	private int id;
	private TUser user;
	private String flmc;

	// Constructors

	/** default constructor */
	public AbstractTBlogcategorytag() {
	}

	/** full constructor */
	public AbstractTBlogcategorytag(TUser user,String flmc) {
		this.user=user;
		this.flmc = flmc;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlmc() {
		return this.flmc;
	}

	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}

}