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
@Table(name = "TwoCodeGjqueryInfo ")
public class GjQueryInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	@Column
	private java.lang.String qregistNumber ;

	@Column
	private java.lang.String  qregistCode;
	
	@Column
	private java.lang.String  qarea;
	
	@Column
	private java.lang.String  qbuildingName;
	
	@Column
	private int qywCompanyId ;
    
	@Column 
	private java.lang.String qmContractVdate;
	
	@Column 
	private java.lang.String qmContractVdate2;
	
	@Column
	private java.lang.String qinspectDate;

	@Column
	private java.lang.String qinspectDate2;
	
	@Column
	private java.lang.String qeleType;
	
	@Column
	private java.lang.String qregistCode2;
	
	@Column
	private int qzzCompanyId;
	
	@Column
	private java.lang.String qfactoryNum; 
	
	@Column
	private int qazCompanyId;
	
	@Column
	private java.lang.String  quseDate;
	
	@Column
	private java.lang.String  quseDate2;
	
	@Column
	private int qwgCompanyId;
	
	@Column
	private java.lang.String quseNumber;
	
	@Column
	private java.lang.String loginName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getQregistNumber() {
		return qregistNumber;
	}
	public void setQregistNumber(java.lang.String qregistNumber) {
		this.qregistNumber = qregistNumber;
	}
	public java.lang.String getQregistCode() {
		return qregistCode;
	}
	public void setQregistCode(java.lang.String qregistCode) {
		this.qregistCode = qregistCode;
	}
	public java.lang.String getQarea() {
		return qarea;
	}
	public void setQarea(java.lang.String qarea) {
		this.qarea = qarea;
	}
	public java.lang.String getQbuildingName() {
		return qbuildingName;
	}
	public void setQbuildingName(java.lang.String qbuildingName) {
		this.qbuildingName = qbuildingName;
	}
	public int getQywCompanyId() {
		return qywCompanyId;
	}
	public void setQywCompanyId(int qywCompanyId) {
		this.qywCompanyId = qywCompanyId;
	}
	
	
	public java.lang.String getQmContractVdate() {
		return qmContractVdate;
	}
	public void setQmContractVdate(java.lang.String qmContractVdate) {
		this.qmContractVdate = qmContractVdate;
	}
	public java.lang.String getQmContractVdate2() {
		return qmContractVdate2;
	}
	public void setQmContractVdate2(java.lang.String qmContractVdate2) {
		this.qmContractVdate2 = qmContractVdate2;
	}
	public java.lang.String getQinspectDate() {
		return qinspectDate;
	}
	public void setQinspectDate(java.lang.String qinspectDate) {
		this.qinspectDate = qinspectDate;
	}
	public java.lang.String getQinspectDate2() {
		return qinspectDate2;
	}
	public void setQinspectDate2(java.lang.String qinspectDate2) {
		this.qinspectDate2 = qinspectDate2;
	}
	public java.lang.String getQeleType() {
		return qeleType;
	}
	public void setQeleType(java.lang.String qeleType) {
		this.qeleType = qeleType;
	}
	public java.lang.String getQregistCode2() {
		return qregistCode2;
	}
	public void setQregistCode2(java.lang.String qregistCode2) {
		this.qregistCode2 = qregistCode2;
	}
	public int getQzzCompanyId() {
		return qzzCompanyId;
	}
	public void setQzzCompanyId(int qzzCompanyId) {
		this.qzzCompanyId = qzzCompanyId;
	}
	public java.lang.String getQfactoryNum() {
		return qfactoryNum;
	}
	public void setQfactoryNum(java.lang.String qfactoryNum) {
		this.qfactoryNum = qfactoryNum;
	}
	public int getQazCompanyId() {
		return qazCompanyId;
	}
	public void setQazCompanyId(int qazCompanyId) {
		this.qazCompanyId = qazCompanyId;
	}
	public java.lang.String getQuseDate() {
		return quseDate;
	}
	public void setQuseDate(java.lang.String quseDate) {
		this.quseDate = quseDate;
	}
	public java.lang.String getQuseDate2() {
		return quseDate2;
	}
	public void setQuseDate2(java.lang.String quseDate2) {
		this.quseDate2 = quseDate2;
	}
	public int getQwgCompanyId() {
		return qwgCompanyId;
	}
	public void setQwgCompanyId(int qwgCompanyId) {
		this.qwgCompanyId = qwgCompanyId;
	}
	public java.lang.String getQuseNumber() {
		return quseNumber;
	}
	public void setQuseNumber(java.lang.String quseNumber) {
		this.quseNumber = quseNumber;
	}
	public java.lang.String getLoginName() {
		return loginName;
	}
	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}
	
	
	
}