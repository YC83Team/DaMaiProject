package com.yc.damai.po;

import java.sql.Timestamp;

public class DmComment {
	private Integer id;

    private Integer uid;

    private Integer oiid;

    private String comment;

    private String image1;
    
    private String image2;
    
    private String image3;
    
    private String image4;

    private Integer pdesc;

    private Integer anonymous;

    private Timestamp createtime;
    
    private Double level;

	public DmComment() {
		super();
	}

	public DmComment(Integer id, Integer uid, Integer oiid, String comment, String image1, String image2, String image3,
			String image4, Integer pdesc, Integer anonymous, Timestamp createtime) {
		super();
		this.id = id;
		this.uid = uid;
		this.oiid = oiid;
		this.comment = comment;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.image4 = image4;
		this.pdesc = pdesc;
		this.anonymous = anonymous;
		this.createtime = createtime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getOiid() {
		return oiid;
	}

	public void setOiid(Integer oiid) {
		this.oiid = oiid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public Integer getPdesc() {
		return pdesc;
	}

	public void setPdesc(Integer pdesc) {
		this.pdesc = pdesc;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Double getLevel() {
		return level;
	}

	public void setLevel(Double level) {
		this.level = level;
	}

   
}
