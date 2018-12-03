package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;

import net.sf.json.JSONObject;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeElevatorInfo")
public class CSElevaltorInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	@Column
	private String dtzs;
	@Column
	private String rysl;
	@Column
	private String rtpb;
	@Column
	private int dailingCount;
	@Column
	private int shemiCount;
	@Column
	private int ywcqCount;
	@Column
	private int ywjjcqCount;
	@Column
	private int remarkCount;
	@Column 
	private int ywCompanyID;
	@Column
	private String ratingDate;
	@Column
	private java.math.BigDecimal tScore;
	@Column
	private int tSort;
	@Column
	private int jjdtCount;
	@Column
	private int qydtCount;
	@Column
	private int jndtCount;
	@Column
	private int whdtCount;
	@Column
	private int chdtCount;
	@Column
	private int gxdtCount;
	@Column
	private int tfxdtCount;
	@Column
	private int lqydtCount;
	@Column
	private int qbjdtCount;
	@Column
	private int xddtCount;
	@Column
	private int wjdtCount;
	@Column
	private int jtdtCount;
	@Column
	private int sldtCount;
	@Column
	private int pddtCount;
	@Column
	private int dydtCount;
	@Column
	private int  pjdtCount;
	@Column
	private int xjdtCount;
	@Column
	private int djydtCount;
	@Column
	private int pzdtCount;
	@Column
	private int qldtCount;
	@Column
	private int czdtCount;
	@Column
	private int jydtCount;
	@Column 
	private int yqcdtCount;
	@Column 
	private int erqcdtCount;
	@Column 
	private int sqcdtCount;
	
	
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDtzs() {
		return dtzs;
	}

	public void setDtzs(String dtzs) {
		this.dtzs = dtzs;
	}

	public String getRysl() {
		return rysl;
	}

	public void setRysl(String rysl) {
		this.rysl = rysl;
	}

	public String getRtpb() {
		return rtpb;
	}

	public void setRtpb(String rtpb) {
		this.rtpb = rtpb;
	}

	public int getDailingCount() {
		return dailingCount;
	}

	public void setDailingCount(int dailingCount) {
		this.dailingCount = dailingCount;
	}

	public int getShemiCount() {
		return shemiCount;
	}

	public void setShemiCount(int shemiCount) {
		this.shemiCount = shemiCount;
	}

	public int getYwcqCount() {
		return ywcqCount;
	}

	public void setYwcqCount(int ywcqCount) {
		this.ywcqCount = ywcqCount;
	}

	public int getYwjjcqCount() {
		return ywjjcqCount;
	}

	public void setYwjjcqCount(int ywjjcqCount) {
		this.ywjjcqCount = ywjjcqCount;
	}

	public int getYwCompanyID() {
		return ywCompanyID;
	}

	public void setYwCompanyID(int ywCompanyID) {
		this.ywCompanyID = ywCompanyID;
	}

	public String getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}

	public java.math.BigDecimal gettScore() {
		return tScore;
	}

	public void settScore(java.math.BigDecimal tScore) {
		this.tScore = tScore;
	}

	public int gettSort() {
		return tSort;
	}

	public void settSort(int tSort) {
		this.tSort = tSort;
	}

	public int getRemarkCount() {
		return remarkCount;
	}

	public void setRemarkCount(int remarkCount) {
		this.remarkCount = remarkCount;
	}

	public int getJjdtCount() {
		return jjdtCount;
	}

	public void setJjdtCount(int jjdtCount) {
		this.jjdtCount = jjdtCount;
	}

	public int getQydtCount() {
		return qydtCount;
	}

	public void setQydtCount(int qydtCount) {
		this.qydtCount = qydtCount;
	}

	public int getJndtCount() {
		return jndtCount;
	}

	public void setJndtCount(int jndtCount) {
		this.jndtCount = jndtCount;
	}

	public int getWhdtCount() {
		return whdtCount;
	}

	public void setWhdtCount(int whdtCount) {
		this.whdtCount = whdtCount;
	}

	public int getChdtCount() {
		return chdtCount;
	}

	public void setChdtCount(int chdtCount) {
		this.chdtCount = chdtCount;
	}

	public int getGxdtCount() {
		return gxdtCount;
	}

	public void setGxdtCount(int gxdtCount) {
		this.gxdtCount = gxdtCount;
	}

	public int getTfxdtCount() {
		return tfxdtCount;
	}

	public void setTfxdtCount(int tfxdtCount) {
		this.tfxdtCount = tfxdtCount;
	}

	public int getLqydtCount() {
		return lqydtCount;
	}

	public void setLqydtCount(int lqydtCount) {
		this.lqydtCount = lqydtCount;
	}

	public int getQbjdtCount() {
		return qbjdtCount;
	}

	public void setQbjdtCount(int qbjdtCount) {
		this.qbjdtCount = qbjdtCount;
	}

	public int getXddtCount() {
		return xddtCount;
	}

	public void setXddtCount(int xddtCount) {
		this.xddtCount = xddtCount;
	}

	public int getWjdtCount() {
		return wjdtCount;
	}

	public void setWjdtCount(int wjdtCount) {
		this.wjdtCount = wjdtCount;
	}

	public int getJtdtCount() {
		return jtdtCount;
	}

	public void setJtdtCount(int jtdtCount) {
		this.jtdtCount = jtdtCount;
	}

	public int getSldtCount() {
		return sldtCount;
	}

	public void setSldtCount(int sldtCount) {
		this.sldtCount = sldtCount;
	}

	public int getPddtCount() {
		return pddtCount;
	}

	public void setPddtCount(int pddtCount) {
		this.pddtCount = pddtCount;
	}

	public int getDydtCount() {
		return dydtCount;
	}

	public void setDydtCount(int dydtCount) {
		this.dydtCount = dydtCount;
	}

	public int getPjdtCount() {
		return pjdtCount;
	}

	public void setPjdtCount(int pjdtCount) {
		this.pjdtCount = pjdtCount;
	}

	public int getXjdtCount() {
		return xjdtCount;
	}

	public void setXjdtCount(int xjdtCount) {
		this.xjdtCount = xjdtCount;
	}

	public int getDjydtCount() {
		return djydtCount;
	}

	public void setDjydtCount(int djydtCount) {
		this.djydtCount = djydtCount;
	}

	public int getPzdtCount() {
		return pzdtCount;
	}

	public void setPzdtCount(int pzdtCount) {
		this.pzdtCount = pzdtCount;
	}

	public int getQldtCount() {
		return qldtCount;
	}

	public void setQldtCount(int qldtCount) {
		this.qldtCount = qldtCount;
	}

	public int getCzdtCount() {
		return czdtCount;
	}

	public void setCzdtCount(int czdtCount) {
		this.czdtCount = czdtCount;
	}

	public int getJydtCount() {
		return jydtCount;
	}

	public void setJydtCount(int jydtCount) {
		this.jydtCount = jydtCount;
	}

	public int getYqcdtCount() {
		return yqcdtCount;
	}

	public void setYqcdtCount(int yqcdtCount) {
		this.yqcdtCount = yqcdtCount;
	}

	public int getErqcdtCount() {
		return erqcdtCount;
	}

	public void setErqcdtCount(int erqcdtCount) {
		this.erqcdtCount = erqcdtCount;
	}

	public int getSqcdtCount() {
		return sqcdtCount;
	}

	public void setSqcdtCount(int sqcdtCount) {
		this.sqcdtCount = sqcdtCount;
	}
	
	

	
	
	
	
	
	
}