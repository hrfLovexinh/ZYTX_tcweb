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
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.et.ar.exception.ActiveRecordException;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeYwContractinfo")
public class ContractInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ¶þÎ¬ÂëID */
	@Column
	private java.lang.String contractNumber;
	@Column
	private java.lang.String contractAttribute;
	@Column
	private java.lang.String contractType;
	@Column
	private java.lang.String contractState;
	@Column
	private java.lang.String contractName;
	@Column
	private int contractCompanyId;
	@Column
	private java.lang.String contractCompanyName;
	@Column
	private java.lang.String contractDepartmentName;
	@Column
	private int cascadeTeamId;
	@Column
	private java.lang.String contractFactoryName;
	@Column
	private java.lang.String contractCustomerName;
	@Column
	private java.lang.String contractBuildingName;
	@Column
	private java.lang.String contractCustomerPerson;
	@Column
	private java.lang.String contractYwFrequency;
	@Column
	private java.lang.String contractCustomerAddress;
	@Column
	private java.lang.String contractCustomerTel;
	@Column
	private java.lang.String contractCustomerFax;
	@Column
	private java.lang.String contractAmount;
	@Column
	private java.lang.String contractPaymentWay;
	@Column
	private java.lang.String contractMainItems;
	@Column
	private java.lang.String contractPaymentBeizhu;
	@Column
	private java.lang.String contractDefaultBeizhu;
	@Column
	private java.lang.String contractEditor;
	@Column
	private java.sql.Date contractSigneddate;
	@Column
	private java.sql.Date contractEnddate;
	
	private String qstartTime;
	private String qendTime;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(java.lang.String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public java.lang.String getContractAttribute() {
		return contractAttribute;
	}
	public void setContractAttribute(java.lang.String contractAttribute) {
		this.contractAttribute = contractAttribute;
	}
	public java.lang.String getContractType() {
		return contractType;
	}
	public void setContractType(java.lang.String contractType) {
		this.contractType = contractType;
	}
	public java.lang.String getContractState() {
		return contractState;
	}
	public void setContractState(java.lang.String contractState) {
		this.contractState = contractState;
	}
	public java.lang.String getContractName() {
		return contractName;
	}
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
	}
	public int getContractCompanyId() {
		return contractCompanyId;
	}
	public void setContractCompanyId(int contractCompanyId) {
		this.contractCompanyId = contractCompanyId;
	}
	public java.lang.String getContractCompanyName() {
		return contractCompanyName;
	}
	public void setContractCompanyName(java.lang.String contractCompanyName) {
		this.contractCompanyName = contractCompanyName;
	}
	public java.lang.String getContractDepartmentName() {
		return contractDepartmentName;
	}
	public void setContractDepartmentName(java.lang.String contractDepartmentName) {
		this.contractDepartmentName = contractDepartmentName;
	}
	public int getCascadeTeamId() {
		return cascadeTeamId;
	}
	public void setCascadeTeamId(int cascadeTeamId) {
		this.cascadeTeamId = cascadeTeamId;
	}
	public java.lang.String getContractFactoryName() {
		return contractFactoryName;
	}
	public void setContractFactoryName(java.lang.String contractFactoryName) {
		this.contractFactoryName = contractFactoryName;
	}
	public java.lang.String getContractCustomerName() {
		return contractCustomerName;
	}
	public void setContractCustomerName(java.lang.String contractCustomerName) {
		this.contractCustomerName = contractCustomerName;
	}
	public java.lang.String getContractBuildingName() {
		return contractBuildingName;
	}
	public void setContractBuildingName(java.lang.String contractBuildingName) {
		this.contractBuildingName = contractBuildingName;
	}
	public java.lang.String getContractCustomerPerson() {
		return contractCustomerPerson;
	}
	public void setContractCustomerPerson(java.lang.String contractCustomerPerson) {
		this.contractCustomerPerson = contractCustomerPerson;
	}
	public java.lang.String getContractYwFrequency() {
		return contractYwFrequency;
	}
	public void setContractYwFrequency(java.lang.String contractYwFrequency) {
		this.contractYwFrequency = contractYwFrequency;
	}
	public java.lang.String getContractCustomerAddress() {
		return contractCustomerAddress;
	}
	public void setContractCustomerAddress(java.lang.String contractCustomerAddress) {
		this.contractCustomerAddress = contractCustomerAddress;
	}
	public java.lang.String getContractCustomerTel() {
		return contractCustomerTel;
	}
	public void setContractCustomerTel(java.lang.String contractCustomerTel) {
		this.contractCustomerTel = contractCustomerTel;
	}
	public java.lang.String getContractCustomerFax() {
		return contractCustomerFax;
	}
	public void setContractCustomerFax(java.lang.String contractCustomerFax) {
		this.contractCustomerFax = contractCustomerFax;
	}
	public java.lang.String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(java.lang.String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public java.lang.String getContractPaymentWay() {
		return contractPaymentWay;
	}
	public void setContractPaymentWay(java.lang.String contractPaymentWay) {
		this.contractPaymentWay = contractPaymentWay;
	}
	public java.lang.String getContractMainItems() {
		return contractMainItems;
	}
	public void setContractMainItems(java.lang.String contractMainItems) {
		this.contractMainItems = contractMainItems;
	}
	public java.lang.String getContractPaymentBeizhu() {
		return contractPaymentBeizhu;
	}
	public void setContractPaymentBeizhu(java.lang.String contractPaymentBeizhu) {
		this.contractPaymentBeizhu = contractPaymentBeizhu;
	}
	public java.lang.String getContractDefaultBeizhu() {
		return contractDefaultBeizhu;
	}
	public void setContractDefaultBeizhu(java.lang.String contractDefaultBeizhu) {
		this.contractDefaultBeizhu = contractDefaultBeizhu;
	}
	public java.lang.String getContractEditor() {
		return contractEditor;
	}
	public void setContractEditor(java.lang.String contractEditor) {
		this.contractEditor = contractEditor;
	}
	public java.sql.Date getContractSigneddate() {
		return contractSigneddate;
	}
	public void setContractSigneddate(java.sql.Date contractSigneddate) {
		this.contractSigneddate = contractSigneddate;
	}
	public java.sql.Date getContractEnddate() {
		return contractEnddate;
	}
	public void setContractEnddate(java.sql.Date contractEnddate) {
		this.contractEnddate = contractEnddate;
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
	
	
	
	
	

	
}