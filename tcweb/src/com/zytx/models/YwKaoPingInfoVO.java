package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.io.File;
import java.sql.Timestamp;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.HasOne;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeYwQuaCredRating")
public class YwKaoPingInfoVO extends ActiveRecordBase {

	
	@Column
	private java.lang.String ratingCompanyName;
	/** ¹«Ë¾Ãû³Æ */
	@Column
	private java.lang.String ywCompanyName;
	@Column
	private int scoreTotal;
	@Column
	private java.lang.String ratingUserName;
	@Column
	private java.lang.String detailratingDate;
	
	private int companyId;
	private int ywCompanyId;
	private String ratingDate;
	
	
	
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getYwCompanyId() {
		return ywCompanyId;
	}
	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}
	public String getRatingDate() {
		return ratingDate;
	}
	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
	public java.lang.String getRatingCompanyName() {
		return ratingCompanyName;
	}
	public void setRatingCompanyName(java.lang.String ratingCompanyName) {
		this.ratingCompanyName = ratingCompanyName;
	}
	public java.lang.String getYwCompanyName() {
		return ywCompanyName;
	}
	public void setYwCompanyName(java.lang.String ywCompanyName) {
		this.ywCompanyName = ywCompanyName;
	}
	public int getScoreTotal() {
		return scoreTotal;
	}
	public void setScoreTotal(int scoreTotal) {
		this.scoreTotal = scoreTotal;
	}
	public java.lang.String getRatingUserName() {
		return ratingUserName;
	}
	public void setRatingUserName(java.lang.String ratingUserName) {
		this.ratingUserName = ratingUserName;
	}
	public java.lang.String getDetailratingDate() {
		return detailratingDate;
	}
	public void setDetailratingDate(java.lang.String detailratingDate) {
		this.detailratingDate = detailratingDate;
	}
	
	
	
}