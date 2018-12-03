package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TwoCodeYWOutDate")
public class YWOutDateVO extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String registNumber;
	@Column
	private String properWhTime;
	@Column
	private int isProcessFlag;
	@Column
	private String dealWithTime;
	@Column
	private int beyondTime;
	@Column
	private String beyondLevel;
	@Column
	private String address;
	@Column
	private String companyName;
	@Column
	private java.sql.Date endTime;
	@Column
	private int tcOutDateFlag;
	@Column
	private String jdbCompanyName;
	@Column
	private String buildingName;
	
	
	private String qstartTime;
	private String qendTime;
	@Column
	private String area;
	private int  townshipStreets;
	private int companyId;
	@Column
	private String userName;
	@Column
    private String telephonemobile;
	
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
	public String getProperWhTime() {
		return properWhTime;
	}
	public void setProperWhTime(String properWhTime) {
		this.properWhTime = properWhTime;
	}
	public String getDealWithTime() {
		return dealWithTime;
	}
	public void setDealWithTime(String dealWithTime) {
		this.dealWithTime = dealWithTime;
	}
	public String getBeyondLevel() {
		return beyondLevel;
	}
	public void setBeyondLevel(String beyondLevel) {
		this.beyondLevel = beyondLevel;
	}
	public int getIsProcessFlag() {
		return isProcessFlag;
	}
	public void setIsProcessFlag(int isProcessFlag) {
		this.isProcessFlag = isProcessFlag;
	}
	public int getBeyondTime() {
		return beyondTime;
	}
	public void setBeyondTime(int beyondTime) {
		this.beyondTime = beyondTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public java.sql.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.sql.Date endTime) {
		this.endTime = endTime;
	}
	public int getTcOutDateFlag() {
		return tcOutDateFlag;
	}
	public void setTcOutDateFlag(int tcOutDateFlag) {
		this.tcOutDateFlag = tcOutDateFlag;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getTownshipStreets() {
		return townshipStreets;
	}
	public void setTownshipStreets(int townshipStreets) {
		this.townshipStreets = townshipStreets;
	}
	public String getJdbCompanyName() {
		return jdbCompanyName;
	}
	public void setJdbCompanyName(String jdbCompanyName) {
		this.jdbCompanyName = jdbCompanyName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTelephonemobile() {
		return telephonemobile;
	}
	public void setTelephonemobile(String telephonemobile) {
		this.telephonemobile = telephonemobile;
	}
	
	
	
}
