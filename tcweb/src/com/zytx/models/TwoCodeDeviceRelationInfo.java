package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table(name = "TwoCodeDeviceRelationInfo")
public class TwoCodeDeviceRelationInfo extends ActiveRecordBase{
	@Id
	private int id;
	/** ¶þÎ¬ÂëID */
	@Column
	private java.lang.String twoCodeId;
	@Column
	private java.lang.String  deviceId;
	/** µçÌÝ±àºÅ */
	@Column
	private java.lang.String registNumber;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getTwoCodeId() {
		return twoCodeId;
	}
	public void setTwoCodeId(java.lang.String twoCodeId) {
		this.twoCodeId = twoCodeId;
	}
	public java.lang.String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(java.lang.String deviceId) {
		this.deviceId = deviceId;
	}
	public java.lang.String getRegistNumber() {
		return registNumber;
	}
	public void setRegistNumber(java.lang.String registNumber) {
		this.registNumber = registNumber;
	}
	
	
}
