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
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeElevatorInfo")
public class ElevaltorQueryExportVO extends ActiveRecordBase {

	
	/** 电梯编号 */
	@Column
	private java.lang.String registNumber;
	@Column 
	private java.lang.String registCode;
	/** 地址 */
	@Column
	private java.lang.String address;
	/**楼盘名称*/
	@Column
	private String buildingName;
	/** 内部使用编号 */
	/**所属区域*/
	@Column
	private String area; 
	
	@Column 
	private String jdbCompanyName;
	@Column
	private java.lang.String useNumber;
	/** 电梯注册码 */
	@Column
	private java.lang.String nextInspectDate;	
	@Column
	private String wgCompanyName;
	@Column
	private String ywCompanyName;
	@Column
	private String zzCompanyName;
	@Column
	private String azCompanyName;
	@Column
	private String subTime;   //最近一次运维时间
	
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public java.lang.String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(java.lang.String registNumber) {
		this.registNumber = registNumber;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(java.lang.String useNumber) {
		this.useNumber = useNumber;
	}

	public java.lang.String getRegistCode() {
		return registCode;
	}

	public void setRegistCode(java.lang.String registCode) {
		this.registCode = registCode;
	}

	
	public java.lang.String getNextInspectDate() {
		return nextInspectDate;
	}

	public void setNextInspectDate(java.lang.String nextInspectDate) {
		this.nextInspectDate = nextInspectDate;
	}


	public String getWgCompanyName() {
		return wgCompanyName;
	}

	public void setWgCompanyName(String wgCompanyName) {
		this.wgCompanyName = wgCompanyName;
	}

	public String getYwCompanyName() {
		return ywCompanyName;
	}

	public void setYwCompanyName(String ywCompanyName) {
		this.ywCompanyName = ywCompanyName;
	}

	public String getZzCompanyName() {
		return zzCompanyName;
	}

	public void setZzCompanyName(String zzCompanyName) {
		this.zzCompanyName = zzCompanyName;
	}

	public String getAzCompanyName() {
		return azCompanyName;
	}

	public void setAzCompanyName(String azCompanyName) {
		this.azCompanyName = azCompanyName;
	}

	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}


	public String getJdbCompanyName() {
		return jdbCompanyName;
	}

	public void setJdbCompanyName(String jdbCompanyName) {
		this.jdbCompanyName = jdbCompanyName;
	}

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}

	
	
}