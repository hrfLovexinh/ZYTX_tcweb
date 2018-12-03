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
	private String filingNumber; 	    			//�������*
	@Column
	private String filingDate; 					      //����ʱ��*
	@Column
	private String companyName; 						 //��ҵ����
	@Column
	private String companyCode; 					 //���ô���
	private int hmdFlag;
	@Column
	private String registAddress; 				 //��ҵע���ַ*
	@Column
	private String address; 						  //��ҵ�칫��ַ
	private int isBeian;
	@Column
	private String area; 						  //��������
	@Column
	private String telephone ;					  //�칫�绰
	@Column
	private String phone; 						 //ֵ��绰
	@Column
	private String representativor; 			//������
	@Column
	private String representativorTel; 			//�����˵绰*
	@Column
	private String contact; 					  //��ϵ��
	@Column
	private String contactTel; 					  //��ϵ�˵绰*
	@Column
	private String officeProof ;				//�칫����֤��*
	@Column
	private String safeyManPerson ;				//��ȫ����Ա*
	@Column
	private String certificateCode; 			//���֤���
	@Column
	private String type; 						         //��λ����*
	@Column
	private String qlevel;						  //���ʼ���
	@Column
	private String validity ;					  //������Ч��
	@Column
	private String filingPerson; 					//����������*
	@Column
	private String filingPersonTel; 			//��������ϵ��ʽ*
	@Column
	private String note; 						//��ע*
	
	
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
