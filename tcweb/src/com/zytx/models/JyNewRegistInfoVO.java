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
@Table(name = "JyNewRegistInfo")
public class JyNewRegistInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	
	/** ��ַ */
	@Column
	private java.lang.String address;
	/** ��� */
	@Column
	private java.lang.String registNumber;
	/** ʹ�õǼ�֤���� */
	@Column
	private java.lang.String registCode;
	@Column
	private String registor;   //ע��Ǽ���
	@Column 
	private String registDate;  //ע������
	@Column 
	private String registCompanyName;  //ע�����
	@Column
	private int isusedFlag;  //ʹ�ñ�־
	
	
	
	
	public int getIsusedFlag() {
		return isusedFlag;
	}

	public void setIsusedFlag(int isusedFlag) {
		this.isusedFlag = isusedFlag;
	}

	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(java.lang.String registNumber) {
		this.registNumber = registNumber;
	}

	public java.lang.String getRegistCode() {
		return registCode;
	}

	public void setRegistCode(java.lang.String registCode) {
		this.registCode = registCode;
	}

	public String getRegistor() {
		return registor;
	}

	public void setRegistor(String registor) {
		this.registor = registor;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getRegistCompanyName() {
		return registCompanyName;
	}

	public void setRegistCompanyName(String registCompanyName) {
		this.registCompanyName = registCompanyName;
	}

	
	
	

}