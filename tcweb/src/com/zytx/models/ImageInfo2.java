package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;




public class ImageInfo2 extends ActiveRecordBase{
	@Column
	private int data_id;
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
	private java.sql.Date  image_time;*/
	@Column
	private String  image_time;
	/*
	@Column
	private java.sql.Timestamp  receive_time;
	*/
	@Column
	private String receive_time;
	@Column
	private int  image_format;
	@Column
	private String  momo;
	@Column
	private String tablename;
	/*
	static{ 
    //    ConvertUtil.register(new TimestampConverter(),java.sql.Timestamp.class); 
    }*/
	
	public int getData_id() {
		return data_id;
	}
	public void setData_id(int dataId) {
		data_id = dataId;
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
	
   
	
	public String getImage_time() {
		return image_time;
	}
	public void setImage_time(String imageTime) {
		image_time = imageTime;
	}
	public String getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(String receiveTime) {
		receive_time = receiveTime;
	}
	
  /*
	public java.sql.Timestamp getReceive_time() {
		return receive_time;
	}
	
	public void setReceive_time(java.sql.Timestamp receiveTime) {
		receive_time = receiveTime;
	}
	*/
	public int getImage_format() {
		return image_format;
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
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
	

}
