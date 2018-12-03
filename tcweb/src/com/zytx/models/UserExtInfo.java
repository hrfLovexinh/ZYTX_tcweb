package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.sql.Timestamp;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.BelongsTo;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;


/** userInfo . */
@Table(name = "TwoCodeUserExtInfo")
public class UserExtInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;
	@Column
	private int userid;
	/** ”√ªß–’√˚ */
	@Column
	private java.lang.String userName;
	@Column
	private int companyid;
	@Column
	private String threedscanning;
	@Column
    private String idCard;
	@Column
	private String speEquQualification;
	@Column
	private String contactPhone;
	@Column
	private String telephonemobile;
	@Column 
	private String qualificationvalidate;
	@Column 
	private String qregistereddate;
	@Column 
	private int isyanshi;
	@Column
	private int ispcsuper;
	@Column
	private int isliulan;
	
	@BelongsTo(foreignKey="userid")
	private UserInfo userInfo;
	
	/*
	@BelongsTo(foreignKey="companyid")
	private CompanyInfo companyInfo;
   */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getThreedscanning() {
		return threedscanning;
	}

	public void setThreedscanning(String threedscanning) {
		this.threedscanning = threedscanning;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSpeEquQualification() {
		return speEquQualification;
	}

	public void setSpeEquQualification(String speEquQualification) {
		this.speEquQualification = speEquQualification;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getTelephonemobile() {
		return telephonemobile;
	}

	public void setTelephonemobile(String telephonemobile) {
		this.telephonemobile = telephonemobile;
	}

	public String getQualificationvalidate() {
		return qualificationvalidate;
	}

	public void setQualificationvalidate(String qualificationvalidate) {
		this.qualificationvalidate = qualificationvalidate;
	}

	public String getQregistereddate() {
		return qregistereddate;
	}

	public void setQregistereddate(String qregistereddate) {
		this.qregistereddate = qregistereddate;
	}

	public int getIsyanshi() {
		return isyanshi;
	}

	public void setIsyanshi(int isyanshi) {
		this.isyanshi = isyanshi;
	}

	public int getIspcsuper() {
		return ispcsuper;
	}

	public void setIspcsuper(int ispcsuper) {
		this.ispcsuper = ispcsuper;
	}

	public int getIsliulan() {
		return isliulan;
	}

	public void setIsliulan(int isliulan) {
		this.isliulan = isliulan;
	}

	
	
	
	
	
   /*
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
 */
   

}