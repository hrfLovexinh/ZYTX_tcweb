package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@Table(name = "TwoCodeCompanyInfo")
public class CompanyInfoNew extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ��˾���� */
	@Column
	private java.lang.String companyCode;
	/** ��˾���� */
	@Column
	private java.lang.String companyName;
	/** ��ַ */
	@Column
	private java.lang.String address;
	/** �绰 */
	@Column
	private java.lang.String phone;
	/** �ʱ� */
	@Column
	private java.lang.String zip;
	/** ֤����� */
	@Column
	private java.lang.String certificateCode;
	/** ���� */
	@Column
	private java.lang.String type;
	/** ֤������ */
	@Column
	private java.lang.String certificateName;
	@Column
	private java.lang.String contact;
	@Column
	private int ischangeFlag;
	@Column
	private int ispasteyw;
	@Column
	private java.lang.String telephone;
	@Column
	private java.lang.String qualification;
	@Column
	private java.lang.String validity;
	@Column
	private java.lang.String qlevel;
	@Column
	private java.lang.String representativor;
	@Column
	private String parea;
	@Column
	private int isyanshi;
	@Column
	private String threedscanning;
	
	/*
	@HasOne(foreignKey="id")
	private UserExtInfo userExtInfo;
	*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}
	public java.lang.String getCompanyName() {
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(companyName);
		return m.replaceAll("").trim(); 
	//	return companyName;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	public java.lang.String getAddress() {
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(address);
		return m.replaceAll("").trim();  
	//	return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getPhone() {
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(phone);
		return m.replaceAll("").trim();
	//	return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getZip() {
		return zip;
	}
	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}
	public java.lang.String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(java.lang.String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(java.lang.String certificateName) {
		this.certificateName = certificateName;
	}
	public java.lang.String getContact() {
	//	String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������\\s*|\t|\r|\n]"; 
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(contact);
		return m.replaceAll("").trim();  
	//	return contact;
	}
	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}
	public int getIschangeFlag() {
		return ischangeFlag;
	}
	public void setIschangeFlag(int ischangeFlag) {
		this.ischangeFlag = ischangeFlag;
	}
	public int getIspasteyw() {
		return ispasteyw;
	}
	public void setIspasteyw(int ispasteyw) {
		this.ispasteyw = ispasteyw;
	}
	public java.lang.String getTelephone() {
		String regEx="['\\s*|\t|\r|\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(telephone);
		return m.replaceAll("").trim(); 
	//	return telephone;
	}
	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}
	public java.lang.String getQualification() {
		return qualification;
	}
	public void setQualification(java.lang.String qualification) {
		this.qualification = qualification;
	}
	public java.lang.String getValidity() {
		return validity;
	}
	public void setValidity(java.lang.String validity) {
		this.validity = validity;
	}
	public java.lang.String getQlevel() {
		return qlevel;
	}
	public void setQlevel(java.lang.String qlevel) {
		this.qlevel = qlevel;
	}
	public java.lang.String getRepresentativor() {
		return representativor;
	}
	public void setRepresentativor(java.lang.String representativor) {
		this.representativor = representativor;
	}
	public String getParea() {
		return parea;
	}
	public void setParea(String parea) {
		this.parea = parea;
	}
	public int getIsyanshi() {
		return isyanshi;
	}
	public void setIsyanshi(int isyanshi) {
		this.isyanshi = isyanshi;
	}
	public String getThreedscanning() {
		return threedscanning;
	}
	public void setThreedscanning(String threedscanning) {
		this.threedscanning = threedscanning;
	}
	
	
	
	/*
	public UserExtInfo getUserExtInfo() {
		return userExtInfo;
	}
	public void setUserExtInfo(UserExtInfo userExtInfo) {
		this.userExtInfo = userExtInfo;
	}
	*/
    
	
}