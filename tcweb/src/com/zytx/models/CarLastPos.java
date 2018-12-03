package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
@Table(name = "Car_LASTPOS")
public class CarLastPos extends ActiveRecordBase{
	@Id(generate = GeneratorType.NONE)
	 private java.lang.String carnum;
	
	
	@Column
	private java.math.BigDecimal longitude; //����
	@Column
	private java.math.BigDecimal latitude; //γ��
	@Column
	private java.sql.Date gps_time;
  //  private java.sql.Date gps_time; //ʱ��
	
	
	@Column
	private java.sql.Date receive_time; //ʱ��
	@Column
	private java.math.BigDecimal angle; //����
	@Column
	private java.math.BigDecimal speed; //�ٶ�
	@Column
	private java.lang.String state; //״̬
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public java.lang.String getCarnum() {
		return carnum;
	}
	public void setCarnum(java.lang.String carnum) {
		this.carnum = carnum;
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
	public java.sql.Date getGps_time() {
		return gps_time;
	}
	public void setGps_time(java.sql.Date gpsTime) {
		gps_time = gpsTime;
	}
	public java.sql.Date getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(java.sql.Date receiveTime) {
		receive_time = receiveTime;
	}
	public java.math.BigDecimal getAngle() {
		return angle;
	}
	public void setAngle(java.math.BigDecimal angle) {
		this.angle = angle;
	}
	public java.math.BigDecimal getSpeed() {
		return speed;
	}
	public void setSpeed(java.math.BigDecimal speed) {
		this.speed = speed;
	}
	public java.lang.String getState() {
		return state;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	
}
