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
@Table(name = "TwoCodeAreaInfo")
public class AreaInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** 公司代码 */
	@Column
	private java.lang.String areaCode;
	/** 公司名称 */
	@Column
	private java.lang.String area;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}
	public java.lang.String getArea() {
		return area;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	
	
}