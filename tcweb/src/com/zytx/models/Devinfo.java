package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
@Table(name = "DEVINFO")
public class Devinfo extends ActiveRecordBase{
	
	@Id(generate = GeneratorType.NONE)
	private String  dev_id;  //设备号
	@Column
	private String  dev_type; //设备类型
	@Column
	private String  dev_provider; //设备供应商
	@Column
	private String  pass; //设备口令
	@Column
	private int  motion_cyc; 
	@Column
	private int  still_cyc; 
	@Column
	private int  max_speed; 
	@Column
	private String  dev_ver;

	private java.sql.Date start_net_time;
	
	private java.sql.Date end_net_time;

	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public String getDev_id() {
		return dev_id;
	}

	public void setDev_id(String devId) {
		dev_id = devId;
	}

	public String getDev_type() {
		return dev_type;
	}

	public void setDev_type(String devType) {
		dev_type = devType;
	}

	public String getDev_provider() {
		return dev_provider;
	}

	public void setDev_provider(String devProvider) {
		dev_provider = devProvider;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getMotion_cyc() {
		return motion_cyc;
	}

	public void setMotion_cyc(int motionCyc) {
		motion_cyc = motionCyc;
	}

	public int getStill_cyc() {
		return still_cyc;
	}

	public void setStill_cyc(int stillCyc) {
		still_cyc = stillCyc;
	}

	public int getMax_speed() {
		return max_speed;
	}

	public void setMax_speed(int maxSpeed) {
		max_speed = maxSpeed;
	}

	public String getDev_ver() {
		return dev_ver;
	}

	public void setDev_ver(String devVer) {
		dev_ver = devVer;
	}

	public java.sql.Date getStart_net_time() {
		return start_net_time;
	}

	public void setStart_net_time(java.sql.Date startNetTime) {
		start_net_time = startNetTime;
	}

	public java.sql.Date getEnd_net_time() {
		return end_net_time;
	}

	public void setEnd_net_time(java.sql.Date endNetTime) {
		end_net_time = endNetTime;
	}
	
	
	
	
	
	

}
