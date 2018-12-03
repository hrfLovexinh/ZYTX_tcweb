package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.DateConverter2;

@Table(name = "TwoCodeWarnInfo")
public class AlarmInfo extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String twoCodeId;
	@Column
	private String warnTime;
	@Column
	private String registNumber;
	@Column
	private int picNum;
	@Column
	private int warn_Class_ID;
	@Column
	private int  alarmSource;
	@Column
	private int is_Process;
	@Column
	private String process_Timer;
	@Column
	private int process_User_ID;
	@Column
	private String process_Remark;
	@Column
	private int picDataType;
	@Column
	private String address;
	@Column
	private String userName;
	
	
	
	private String map_X;   //报警的坐标
	private String map_Y;
	
	private String map_X0;  //二维码标签原始的坐标
	private String map_Y0; 
	
	
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
	
	public String getTwoCodeId() {
		return twoCodeId;
	}
	public void setTwoCodeId(String twoCodeId) {
		this.twoCodeId = twoCodeId;
	}

	public int getPicNum() {
		return picNum;
	}
	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}

	public int getWarn_Class_ID() {
		return warn_Class_ID;
	}
	public void setWarn_Class_ID(int warnClassID) {
		warn_Class_ID = warnClassID;
	}
	public int getAlarmSource() {
		return alarmSource;
	}
	public void setAlarmSource(int alarmSource) {
		this.alarmSource = alarmSource;
	}
	public int getIs_Process() {
		return is_Process;
	}
	public void setIs_Process(int isProcess) {
		is_Process = isProcess;
	}
	
	public String getWarnTime() {
		return warnTime;
	}
	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}
	public String getProcess_Timer() {
		return process_Timer;
	}
	public void setProcess_Timer(String processTimer) {
		process_Timer = processTimer;
	}
	public int getProcess_User_ID() {
		return process_User_ID;
	}
	public void setProcess_User_ID(int processUserID) {
		process_User_ID = processUserID;
	}
	public String getProcess_Remark() {
		return process_Remark;
	}
	public void setProcess_Remark(String processRemark) {
		process_Remark = processRemark;
	}
	public int getPicDataType() {
		return picDataType;
	}
	public void setPicDataType(int picDataType) {
		this.picDataType = picDataType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	
	
}
