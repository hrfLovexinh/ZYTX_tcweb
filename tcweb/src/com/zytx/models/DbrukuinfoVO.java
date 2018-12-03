package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
@Table(name = "TwoCodeElevatorInfo")
public class DbrukuinfoVO extends ActiveRecordBase{
	
	@Column       
	private String registNumberrestr;
	@Column
	private String registCoderestr;
	public String getRegistNumberrestr() {
		return registNumberrestr;
	}
	public void setRegistNumberrestr(String registNumberrestr) {
		this.registNumberrestr = registNumberrestr;
	}
	public String getRegistCoderestr() {
		return registCoderestr;
	}
	public void setRegistCoderestr(String registCoderestr) {
		this.registCoderestr = registCoderestr;
	}
	
	
	
	
	
	
	
	
	

}
