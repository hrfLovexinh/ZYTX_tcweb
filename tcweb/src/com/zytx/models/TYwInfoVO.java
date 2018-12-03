package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TempYwManagerInfo")
public class TYwInfoVO extends ActiveRecordBase{
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
	private String address;
	@Column
	private String area;
	@Column
	private String buildingName;
	@Column
	private String maintainTypecode;
	@Column
	private String dateSpan;
	@Column
	private String userName;
	@Column
	private String companyName;
	@Column
	private int companyId;
	@Column
	private String remark;
	@Column
	private String ywstatus;
	@Column
	private int picNum;
	@Column
	private String contactPhone;
	@Column
	private String threedscanning;
	
	
	
	//2014-08-01  为了利用地理位置进行审核
	@Column
	private String map_X;
	@Column
	private String map_Y;
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
	private int ywSource;
	
	private String qstartTime;
	private String qendTime;
	
	@Column
	private String companyName2;
	@Column
	private String subCompanyName;
	@Column
	private String tcdaddress;
	@Column
	private String tcdbuildingName;
	
	private int eucompanyName;    //0:(电梯与运维人员)单位：一致   1：不一致  100：所有
	
	
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMaintainTypecode() {
		return maintainTypecode;
	}
	public void setMaintainTypecode(String maintainTypecode) {
		this.maintainTypecode = maintainTypecode;
	}
	public String getDateSpan() {
		return dateSpan;
	}
	public void setDateSpan(String dateSpan) {
		this.dateSpan = dateSpan;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	public int getYwSource() {
		return ywSource;
	}
	public void setYwSource(int ywSource) {
		this.ywSource = ywSource;
	}
	public String getThreedscanning() {
		return threedscanning;
	}
	public void setThreedscanning(String threedscanning) {
		this.threedscanning = threedscanning;
	}
	public String getCompanyName2() {
		return companyName2;
	}
	public void setCompanyName2(String companyName2) {
		this.companyName2 = companyName2;
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
	public String getSubCompanyName() {
		return subCompanyName;
	}
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	public String getTcdaddress() {
		return tcdaddress;
	}
	public void setTcdaddress(String tcdaddress) {
		this.tcdaddress = tcdaddress;
	}
	public String getTcdbuildingName() {
		return tcdbuildingName;
	}
	public void setTcdbuildingName(String tcdbuildingName) {
		this.tcdbuildingName = tcdbuildingName;
	}
	public int getEucompanyName() {
		return eucompanyName;
	}
	public void setEucompanyName(int eucompanyName) {
		this.eucompanyName = eucompanyName;
	}
	
	
	
		
}
