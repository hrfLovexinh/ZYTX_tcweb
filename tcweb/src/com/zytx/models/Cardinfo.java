package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
@Table(name = "CARDINFO")
public class Cardinfo extends ActiveRecordBase{
	
	@Id(generate = GeneratorType.NONE)
	private String  simnum;  //¿¨ºÅ
	@Column
	private String  operators;
	@Column
	private Double  price_per;
	public String getSimnum() {
		return simnum;
	}
	public void setSimnum(String simnum) {
		this.simnum = simnum;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public Double getPrice_per() {
		return price_per;
	}
	public void setPrice_per(Double pricePer) {
		price_per = pricePer;
	}
	
	
	
	
	
	
	

}
