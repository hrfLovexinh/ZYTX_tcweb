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
public class MapElevaltorInfoVO extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	
	@Column
	private java.lang.String registNumber;
	/** µÿ÷∑ */
	@Column
	private java.lang.String address;
	
	@Column
	private String map_X;
	@Column
	private String map_Y;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getMap_X() {
		return map_X;
	}
	public void setMap_X(String mapX) {
		map_X = mapX;
	}
	public String getMap_Y() {
		return map_Y;
	}
	public void setMap_Y(String mapY) {
		map_Y = mapY;
	}
	
	
   	
}