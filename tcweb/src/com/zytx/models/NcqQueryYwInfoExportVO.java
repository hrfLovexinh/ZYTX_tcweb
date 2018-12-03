package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "YwManagerInfo")
public class NcqQueryYwInfoExportVO extends ActiveRecordBase{
	@Column
	private String registNumber;
	@Column
	private String registCode;
	@Column
	private String address;
	@Column
	private String buildingName;
	@Column
	private String companyName;
	@Column
	private String area;
	@Column
	private String jdbCompanyName;
	@Column
	private String userName;
	@Column
	private String telephonemobile;
	@Column
	private String endTime; 
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public String getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(String registNumber) {
		this.registNumber = registNumber;
	}

	
	
	public String getRegistCode() {
		return registCode;
	}

	public void setRegistCode(String registCode) {
		this.registCode = registCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getJdbCompanyName() {
		return jdbCompanyName;
	}

	public void setJdbCompanyName(String jdbCompanyName) {
		this.jdbCompanyName = jdbCompanyName;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}
