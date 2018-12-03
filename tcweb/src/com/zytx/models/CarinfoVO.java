package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.zytx.converters.DateConverter;

public class CarinfoVO extends ActiveRecordBase{
	@Column
	private int data_id;  
	@Column
	private String  carnum;  //���ƺ�
	@Column
	private String  dev_id;  //�豸ID
	@Column 
	private String  qy_id;  //����ID
	@Column
	private String  qy_name; //��������
	@Column
	private String  simnum; //ͨѶ����
	@Column
	private String  dev_type; //�ն����� 
	@Column
	private String  person; //��ϵ��
	@Column
	private String  phone; //��ϵ�绰
	@Column
	private String  car_type; //��������
	@Column
	private String  car_color; //������ɫ
	@Column
	private String  carnum_color; //������ɫ
	@Column
	private java.math.BigDecimal longitude; //����
	@Column
	private java.math.BigDecimal latitude; //γ��
	@Column
	private java.math.BigDecimal speed; //�ٶ�
	@Column
	private java.math.BigDecimal angle; //����
	@Column
	private java.sql.Date gps_time; //ʱ��
	/*
	private String qy_id;
	
	private String startTime;
	
	private String endTime;
	*/
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	
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
	
	
	public String getQy_id() {
		return qy_id;
	}
	public void setQy_id(String qyId) {
		qy_id = qyId;
	}
	public String getQy_name() {
		return qy_name;
	}
	public void setQy_name(String qyName) {
		qy_name = qyName;
	}
	public String getSimnum() {
		return simnum;
	}
	public void setSimnum(String simnum) {
		this.simnum = simnum;
	}
	public String getDev_type() {
		return dev_type;
	}
	public void setDev_type(String devType) {
		dev_type = devType;
	}
	
	
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String carType) {
		car_type = carType;
	}
	public String getCar_color() {
		return car_color;
	}
	public void setCar_color(String carColor) {
		car_color = carColor;
	}
	public String getCarnum_color() {
		return carnum_color;
	}
	public void setCarnum_color(String carnumColor) {
		carnum_color = carnumColor;
	}
	public java.math.BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(java.math.BigDecimal longitude) {
		this.longitude = longitude;
	}
	public java.math.BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(java.math.BigDecimal latitude) {
		this.latitude = latitude;
	}
	public java.math.BigDecimal getSpeed() {
		return speed;
	}
	public void setSpeed(java.math.BigDecimal speed) {
		this.speed = speed;
	}
	public java.math.BigDecimal getAngle() {
		return angle;
	}
	public void setAngle(java.math.BigDecimal angle) {
		this.angle = angle;
	}
	public java.sql.Date getGps_time() {
		return gps_time;
	}
	public void setGps_time(java.sql.Date gpsTime) {
		gps_time = gpsTime;
	}
	

}
