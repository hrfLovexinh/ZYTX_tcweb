package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.zytx.converters.DateConverter;


public class ImageVO extends ActiveRecordBase{
	@Column
	private String  carnum;
	@Column
	private String  dev_id;
	@Column
	private int  image_id;
	@Column
	private int  image_type;
	@Column
	private String  image_path; 
	/*
	@Column
	private java.sql.Date  image_time;
	@Column
	private java.sql.Date  reveive_time;
	*/
	@Column
	private java.sql.Timestamp  image_time;
	@Column
	private java.sql.Timestamp  receive_time;
	
	@Column
	private int  image_format;
	@Column
	private String  momo;
	@Column
	private String qy_id;
	
	private String startTime;
	
	private String endTime;
	
	static{ 
    //    ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    //    ConvertUtil.register(new DateConverter2(),java.sql.Timestamp.class); 
    }

	
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public String getDev_id() {
		return dev_id;
	}
	public void setDev_id(String devId) {
		dev_id = devId;
	}
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int imageId) {
		image_id = imageId;
	}
	public int getImage_type() {
		return image_type;
	}
	public void setImage_type(int imageType) {
		image_type = imageType;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String imagePath) {
		image_path = imagePath;
	}
	
	/*
	public java.sql.Date getImage_time() {
		return image_time;
	}
	public void setImage_time(java.sql.Date imageTime) {
		image_time = imageTime;
	}
	public java.sql.Date getReveive_time() {
		return reveive_time;
	}
	public void setReveive_time(java.sql.Date reveiveTime) {
		reveive_time = reveiveTime;
	}
	*/
	
	
	public int getImage_format() {
		return image_format;
	}
	public java.sql.Timestamp getImage_time() {
		return image_time;
	}
	public void setImage_time(java.sql.Timestamp imageTime) {
		image_time = imageTime;
	}
	public java.sql.Timestamp getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(java.sql.Timestamp receiveTime) {
		receive_time = receiveTime;
	}
	public void setImage_format(int imageFormat) {
		image_format = imageFormat;
	}
	public String getMomo() {
		return momo;
	}
	public void setMomo(String momo) {
		this.momo = momo;
	}
	public String getQy_id() {
		return qy_id;
	}
	public void setQy_id(String qyId) {
		qy_id = qyId;
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
	
}
