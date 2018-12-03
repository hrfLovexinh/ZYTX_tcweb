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
public class YwstatisticsVO extends ActiveRecordBase {
     
	@Column
    private String companyName;
    @Column
    private int etotal;  //电梯数量
    @Column
    private int eywtotal;  //维保数量
    @Column
    private int dailingetotal; //停用数量 
    @Column
    private int uncqetotal; //未运维电梯数量
    
    @Column
    private int ncqetotal;  //当前超期数量
    @Column
    private int utotal;  //总维保人员数
    @Column 
    private int nutotal; //在维保人员数
    @Column
    private String ywcountbyperson; //人均维保数
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public int getEtotal() {
		return etotal;
	}


	public void setEtotal(int etotal) {
		this.etotal = etotal;
	}


	public int getEywtotal() {
		return eywtotal;
	}


	public void setEywtotal(int eywtotal) {
		this.eywtotal = eywtotal;
	}


	public int getDailingetotal() {
		return dailingetotal;
	}


	public void setDailingetotal(int dailingetotal) {
		this.dailingetotal = dailingetotal;
	}


	public int getUncqetotal() {
		return uncqetotal;
	}


	public void setUncqetotal(int uncqetotal) {
		this.uncqetotal = uncqetotal;
	}


	public int getNcqetotal() {
		return ncqetotal;
	}


	public void setNcqetotal(int ncqetotal) {
		this.ncqetotal = ncqetotal;
	}


	public int getUtotal() {
		return utotal;
	}


	public void setUtotal(int utotal) {
		this.utotal = utotal;
	}


	public int getNutotal() {
		return nutotal;
	}


	public void setNutotal(int nutotal) {
		this.nutotal = nutotal;
	}


	public String getYwcountbyperson() {
		return ywcountbyperson;
	}


	public void setYwcountbyperson(String ywcountbyperson) {
		this.ywcountbyperson = ywcountbyperson;
	}

    
    
   	
}