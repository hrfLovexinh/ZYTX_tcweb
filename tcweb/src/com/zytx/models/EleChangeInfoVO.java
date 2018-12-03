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
import com.et.ar.annotations.DependentType;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.HasOne;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeEleChangeInfo")
public class EleChangeInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** registNumber */
	@Column
	private String registNumber;
	@Column
	private String changeTime;
	@Column
	private String handleCompany;
	@Column
	private String handleCompanyCode;
	@Column
	private String operator;
	@Column
	private String operatorTime;
	@Column
	private String changeWay;
	@Column
	private String changeItem;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(String registNumber) {
		this.registNumber = registNumber;
	}

	public String getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}

	public String getHandleCompany() {
		return handleCompany;
	}

	public void setHandleCompany(String handleCompany) {
		this.handleCompany = handleCompany;
	}

	public String getHandleCompanyCode() {
		return handleCompanyCode;
	}

	public void setHandleCompanyCode(String handleCompanyCode) {
		this.handleCompanyCode = handleCompanyCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}

	public String getChangeWay() {
		return changeWay;
	}

	public void setChangeWay(String changeWay) {
		this.changeWay = changeWay;
	}

	public String getChangeItem() {
		return changeItem;
	}

	public void setChangeItem(String changeItem) {
		this.changeItem = changeItem;
	}
	
	

  

	

}