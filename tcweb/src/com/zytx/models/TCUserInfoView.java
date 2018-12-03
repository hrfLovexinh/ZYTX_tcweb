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

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TCUserInfo")
public class TCUserInfoView extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 用户名称 */
	@Column
	private java.lang.String loginName;
	/** 密码 */
	@Column
	private java.lang.String password;
	/** 角色 */
	@Column
	private int role;
	/** 单位名称 */
	@Column
	private String companyName;
	/** 用户姓名 */
	@Column
	private String userName;
	@Column
	private int userid;
	@Column
    private int companyId;
	
	@Column
	private String threedscanning;
	
	@Column
	private String type;
	@Column
	private String idCard;
	@Column
	private String speEquQualification;
	
	@Column
	private String contactPhone;
	@Column
	private String qualificationvalidate;
	@Column
	private String qregistereddate;
	@Column
	private String comanyType;
	@Column
	private String telephonemobile;
	@Column 
	private int iskaoping;
	@Column
	private int ispcsuper;
	@Column
	private int isyanshi;
	@Column
	private int  isliulan;
    
	
	
	public int getIsliulan() {
		return isliulan;
	}

	public void setIsliulan(int isliulan) {
		this.isliulan = isliulan;
	}

	public int getIspcsuper() {
		return ispcsuper;
	}

	public void setIspcsuper(int ispcsuper) {
		this.ispcsuper = ispcsuper;
	}

	public int getIsyanshi() {
		return isyanshi;
	}

	public void setIsyanshi(int isyanshi) {
		this.isyanshi = isyanshi;
	}

	public int getIskaoping() {
		return iskaoping;
	}

	public void setIskaoping(int iskaoping) {
		this.iskaoping = iskaoping;
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
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(contactPhone);
		return m.replaceAll("").trim(); 
	//	return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getLoginName() {
		return loginName;
	}

	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThreedscanning() {
		return threedscanning;
	}

	public void setThreedscanning(String threedscanning) {
		this.threedscanning = threedscanning;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getComanyType() {
		return comanyType;
	}

	public void setComanyType(String comanyType) {
		this.comanyType = comanyType;
	}

	public String getTelephonemobile() {
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(telephonemobile);
		return m.replaceAll("").trim(); 
	//	return telephonemobile;
	}

	public void setTelephonemobile(String telephonemobile) {
		this.telephonemobile = telephonemobile;
	}
    
	
}