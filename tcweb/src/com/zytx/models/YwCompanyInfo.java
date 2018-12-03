package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table(name = "TwoCodeYwCompanyInfo")
public class YwCompanyInfo extends ActiveRecordBase  {

	@Column
	private int id;
	@Column
	private String filingNumber; 	    			//备案编号*
	@Column
	private String filingDate; 					      //备案时间*
	@Column
	private String companyName; 						 //企业名称
	@Column
	private String companyCode; 					 //信用代码
	private int hmdFlag;
	@Column
	private String registAddress; 				 //企业注册地址*
	@Column
	private String address; 						  //企业办公地址
	private int isBeian;
	@Column
	private String area; 						  //所在区域
	@Column
	private String telephone ;					  //办公电话
	@Column
	private String phone; 						 //值班电话
	@Column
	private String representativor; 			//负责人
	@Column
	private String representativorTel; 			//负责人电话*
	@Column
	private String contact; 					  //联系人
	@Column
	private String contactTel; 					  //联系人电话*
	@Column
	private String officeProof ;				//办公场所证明*
	@Column
	private String safeyManPerson ;				//安全管理员*
	@Column
	private String certificateCode; 			//许可证编号
	@Column
	private String type; 						         //单位类型*
	@Column
	private String qlevel;						  //资质级别
	@Column
	private String validity ;					  //资质有效期
	@Column
	private String filingPerson; 					//备案负责人*
	@Column
	private String filingPersonTel; 			//备案人联系方式*
	@Column
	private String note; 						//备注*
	
	
	public int getHmdFlag() {
		return hmdFlag;
	}
	public void setHmdFlag(int hmdFlag) {
		this.hmdFlag = hmdFlag;
	}
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
	public String getFilingNumber() {
		return filingNumber;
	}
	public void setFilingNumber(String filingNumber) {
		this.filingNumber = filingNumber;
	}
	public String getFilingDate() {
		return filingDate;
	}
	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getRegistAddress() {
		return registAddress;
	}
	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRepresentativor() {
		return representativor;
	}
	public void setRepresentativor(String representativor) {
		this.representativor = representativor;
	}
	public String getRepresentativorTel() {
		return representativorTel;
	}
	public void setRepresentativorTel(String representativorTel) {
		this.representativorTel = representativorTel;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getOfficeProof() {
		return officeProof;
	}
	public void setOfficeProof(String officeProof) {
		this.officeProof = officeProof;
	}
	public String getSafeyManPerson() {
		return safeyManPerson;
	}
	public void setSafeyManPerson(String safeyManPerson) {
		this.safeyManPerson = safeyManPerson;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQlevel() {
		return qlevel;
	}
	public void setQlevel(String qlevel) {
		this.qlevel = qlevel;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getFilingPerson() {
		return filingPerson;
	}
	public void setFilingPerson(String filingPerson) {
		this.filingPerson = filingPerson;
	}
	public String getFilingPersonTel() {
		return filingPersonTel;
	}
	public void setFilingPersonTel(String filingPersonTel) {
		this.filingPersonTel = filingPersonTel;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
