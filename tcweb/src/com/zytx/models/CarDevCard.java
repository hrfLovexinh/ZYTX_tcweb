package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
@Table(name = "Car_dev_card")
public class CarDevCard extends ActiveRecordBase{
	@Id(generate = GeneratorType.NONE)
	 private java.lang.String carnum;
	
	
	private int data_id;
	
	@Column
	   private java.lang.String dev_id;
	@Column
	   private java.lang.String simnum;
	@Column
	   private java.lang.String qy_id;
	public java.lang.String getCarnum() {
		return carnum;
	}
	public void setCarnum(java.lang.String carnum) {
		this.carnum = carnum;
	}
	public java.lang.String getDev_id() {
		return dev_id;
	}
	public void setDev_id(java.lang.String devId) {
		dev_id = devId;
	}
	public java.lang.String getSimnum() {
		return simnum;
	}
	public void setSimnum(java.lang.String simnum) {
		this.simnum = simnum;
	}
	public java.lang.String getQy_id() {
		return qy_id;
	}
	public void setQy_id(java.lang.String qyId) {
		qy_id = qyId;
	}
	public int getData_id() {
		return data_id;
	}
	public void setData_id(int dataId) {
		data_id = dataId;
	}
	
}
