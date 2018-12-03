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
@Table(name = "TwoCodeCompanyInfo")
public class CompanyInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 公司代码 */
	@Column
	private java.lang.String companyCode;
	/** 公司名称 */
	@Column
	private java.lang.String companyName;
	/** 地址 */
	@Column
	private java.lang.String address;
	/** 电话 */
	@Column
	private java.lang.String phone;
	/** 邮编 */
	@Column
	private java.lang.String zip;
	/** 证书编码 */
	@Column
	private java.lang.String certificateCode;
	/** 类型 */
	@Column
	private java.lang.String type;
	/** 证书名称 */
	@Column
	private java.lang.String certificateName;
	@Column
	private java.lang.String contact;
	@Column
	private int ischangeFlag;
	@Column
	private int ispasteyw;
	
	@Column 
	private String wgId;
	@Column  
	private String wgCompanyName;
	@Column 
	private String zzId;
	@Column
	private String zzCompanyName;
	
	private String qstartTime;
	private String qendTime;
	
	@Column
	private int issueetotal;
	@Column
	private String parea;
	@Column
	private String representativor;
	@Column 
	private String area;
	@Column
	private String telephone;
	@Column
	private java.lang.String qualification;
	@Column
	private java.lang.String validity;
	@Column
	private java.lang.String qlevel;
	@Column
	private int hmdFlag;
	@Column
	private int isyanshi;
	@Column
	private String threedscanning;
	@Column
	private int isshengji;
	
	@Column
	private int isBeian;
	
	
	public int getIsBeian() {
		return isBeian;
	}
	public void setIsBeian(int isBeian) {
		this.isBeian = isBeian;
	}
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
		return companyName;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getPhone() {
		return phone;
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
		return contact;
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
	public String getWgId() {
		return wgId;
	}
	public void setWgId(String wgId) {
		this.wgId = wgId;
	}
	public String getWgCompanyName() {
		return wgCompanyName;
	}
	public void setWgCompanyName(String wgCompanyName) {
		this.wgCompanyName = wgCompanyName;
	}
	public String getZzId() {
		return zzId;
	}
	public void setZzId(String zzId) {
		this.zzId = zzId;
	}
	public String getZzCompanyName() {
		return zzCompanyName;
	}
	public void setZzCompanyName(String zzCompanyName) {
		this.zzCompanyName = zzCompanyName;
	}
	public String getQstartTime() {
		return qstartTime;
	}
	public void setQstartTime(String qstartTime) {
		this.qstartTime = qstartTime;
	}
	public String getQendTime() {
		return qendTime;
	}
	public void setQendTime(String qendTime) {
		this.qendTime = qendTime;
	}
	public int getIssueetotal() {
		return issueetotal;
	}
	public void setIssueetotal(int issueetotal) {
		this.issueetotal = issueetotal;
	}
	public String getParea() {
		return parea;
	}
	public void setParea(String parea) {
		this.parea = parea;
	}
	public String getRepresentativor() {
		return representativor;
	}
	public void setRepresentativor(String representativor) {
		this.representativor = representativor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
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
	public int getHmdFlag() {
		return hmdFlag;
	}
	public void setHmdFlag(int hmdFlag) {
		this.hmdFlag = hmdFlag;
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
	public int getIsshengji() {
		return isshengji;
	}
	public void setIsshengji(int isshengji) {
		this.isshengji = isshengji;
	}
	
	
	
    
	
}