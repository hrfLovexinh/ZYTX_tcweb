package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.sql.Timestamp;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.DateConverter2;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeDdElevatorInfo")
public class SystjInfoVO extends ActiveRecordBase {
     
	@Column
    private String area;
    @Column
    private int etotal;  //电梯数量
    @Column
    private int setotal; //停用数量
    
    @Column
    private int ncqetotal;  //维保超期数量
    @Column
    private int tsrtotal; //投诉数量
    
    @Column
    private int undotsrtotal;  //投诉未处理
    @Column
    private float nutotal;
    @Column 
    private float cql;
   
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public int getEtotal() {
		return etotal;
	}


	public void setEtotal(int etotal) {
		this.etotal = etotal;
	}


	public int getSetotal() {
		return setotal;
	}


	public void setSetotal(int setotal) {
		this.setotal = setotal;
	}


	public int getNcqetotal() {
		return ncqetotal;
	}


	public void setNcqetotal(int ncqetotal) {
		this.ncqetotal = ncqetotal;
	}


	public int getTsrtotal() {
		return tsrtotal;
	}


	public void setTsrtotal(int tsrtotal) {
		this.tsrtotal = tsrtotal;
	}


	public int getUndotsrtotal() {
		return undotsrtotal;
	}


	public void setUndotsrtotal(int undotsrtotal) {
		this.undotsrtotal = undotsrtotal;
	}


	public float getNutotal() {
		return nutotal;
	}


	public void setNutotal(float nutotal) {
		this.nutotal = nutotal;
	}


	public float getCql() {
		return cql;
	}


	public void setCql(float cql) {
		this.cql = cql;
	}

	
	
}