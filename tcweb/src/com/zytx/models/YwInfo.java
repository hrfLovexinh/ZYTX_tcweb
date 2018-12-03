package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "YwManagerInfo")
public class YwInfo extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String registNumber;
	@Column
	private int userId;
	@Column
	private String ywKind;
	@Column
	private String startTime;
	@Column
	private String endTime;
	@Column
	private String sPosition;
	@Column
	private String ePosition;
	@Column
	private String subTime;
	@Column
	private String remark;
	@Column
	private String twoCodeId;
	@Column 
	private String maintainTypecode;
	@Column
	private String ywstatus;
	@Column 
	private int picNum;
	@Column 
	private int flexStartx;
	@Column
	private int flexStarty;
	@Column
	private int flexEndx;
	@Column
	private int flexEndy;
	
	private String address;
	
	private int ywCompanyId;
	
	/*
	@Column
	private String map_X;
	@Column
	private String map_Y;
	*/
	@Column
	private String map_X0;
	@Column
	private String map_Y0;
	@Column
	private String map_X1;
	@Column
	private String map_Y1;
	@Column
	private String map_X2;
	@Column
	private String map_Y2;
	
	@Column
	private String threedscanning;
	
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getYwKind() {
		return ywKind;
	}
	public void setYwKind(String ywKind) {
		this.ywKind = ywKind;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getsPosition() {
		return sPosition;
	}
	public void setsPosition(String sPosition) {
		this.sPosition = sPosition;
	}
	public String getePosition() {
		return ePosition;
	}
	public void setePosition(String ePosition) {
		this.ePosition = ePosition;
	}
	public String getSubTime() {
		return subTime;
	}
	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTwoCodeId() {
		return twoCodeId;
	}
	public void setTwoCodeId(String twoCodeId) {
		this.twoCodeId = twoCodeId;
	}
	public String getMaintainTypecode() {
		return maintainTypecode;
	}
	public void setMaintainTypecode(String maintainTypecode) {
		this.maintainTypecode = maintainTypecode;
	}
	public int getYwCompanyId() {
		return ywCompanyId;
	}
	public void setYwCompanyId(int ywCompanyId) {
		this.ywCompanyId = ywCompanyId;
	}
	public String getYwstatus() {
		return ywstatus;
	}
	public void setYwstatus(String ywstatus) {
		this.ywstatus = ywstatus;
	}
	public int getPicNum() {
		return picNum;
	}
	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}
	public int getFlexStartx() {
		return flexStartx;
	}
	public void setFlexStartx(int flexStartx) {
		this.flexStartx = flexStartx;
	}
	public int getFlexStarty() {
		return flexStarty;
	}
	public void setFlexStarty(int flexStarty) {
		this.flexStarty = flexStarty;
	}
	public int getFlexEndx() {
		return flexEndx;
	}
	public void setFlexEndx(int flexEndx) {
		this.flexEndx = flexEndx;
	}
	public int getFlexEndy() {
		return flexEndy;
	}
	public void setFlexEndy(int flexEndy) {
		this.flexEndy = flexEndy;
	}
	
	public String getMap_X0() {
		return map_X0;
	}
	public void setMap_X0(String mapX0) {
		map_X0 = mapX0;
	}
	public String getMap_Y0() {
		return map_Y0;
	}
	public void setMap_Y0(String mapY0) {
		map_Y0 = mapY0;
	}
	public String getMap_X1() {
		return map_X1;
	}
	public void setMap_X1(String mapX1) {
		map_X1 = mapX1;
	}
	public String getMap_Y1() {
		return map_Y1;
	}
	public void setMap_Y1(String mapY1) {
		map_Y1 = mapY1;
	}
	public String getMap_X2() {
		return map_X2;
	}
	public void setMap_X2(String mapX2) {
		map_X2 = mapX2;
	}
	public String getMap_Y2() {
		return map_Y2;
	}
	public void setMap_Y2(String mapY2) {
		map_Y2 = mapY2;
	}
	public String getThreedscanning() {
		return threedscanning;
	}
	public void setThreedscanning(String threedscanning) {
		this.threedscanning = threedscanning;
	}
	
	
}
