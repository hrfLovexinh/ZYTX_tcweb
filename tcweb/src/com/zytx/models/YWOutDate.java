package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TwoCodeYWOutDate")
public class YWOutDate extends ActiveRecordBase{
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
	private String endTime;
	@Column
	private int tcOutDateFlag;
	
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
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getTcOutDateFlag() {
		return tcOutDateFlag;
	}
	public void setTcOutDateFlag(int tcOutDateFlag) {
		this.tcOutDateFlag = tcOutDateFlag;
	}
	
	
	
}
