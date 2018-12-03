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
public class JobPerformanceVO extends ActiveRecordBase {
     
	@Column
    private String itemName;
    @Column
    private int jjtotal;  //锦江数量
    @Column
    private int qytotal; //青羊数量
    
    @Column
    private int jntotal;  //维保超期数量
    @Column
    private int whtotal; //投诉数量
    @Column
    private int chtotal;
    @Column
    private int gxtotal;
    @Column
    private int tfxtotal;
    @Column
    private int lqytotal;
    @Column
    private int qbjtotal;
    @Column
    private int xdtotal;
    @Column 
    private int wjtotal;
    @Column 
    private int jttotal;
    @Column 
    private int sltotal;
    @Column
    private int pxtotal;
    @Column
    private int dytotal;
    @Column
    private int xjtotal;
    @Column
    private int pjtotal;
    @Column
    private int qltotal;
    @Column
    private int djytotal;
    @Column
    private int pztotal;
    @Column
    private int cztotal;
    @Column
    private int jytotal;
	
	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getJjtotal() {
		return jjtotal;
	}

	public void setJjtotal(int jjtotal) {
		this.jjtotal = jjtotal;
	}

	public int getQytotal() {
		return qytotal;
	}

	public void setQytotal(int qytotal) {
		this.qytotal = qytotal;
	}

	public int getJntotal() {
		return jntotal;
	}

	public void setJntotal(int jntotal) {
		this.jntotal = jntotal;
	}

	public int getWhtotal() {
		return whtotal;
	}

	public void setWhtotal(int whtotal) {
		this.whtotal = whtotal;
	}

	public int getChtotal() {
		return chtotal;
	}

	public void setChtotal(int chtotal) {
		this.chtotal = chtotal;
	}

	public int getGxtotal() {
		return gxtotal;
	}

	public void setGxtotal(int gxtotal) {
		this.gxtotal = gxtotal;
	}

	public int getLqytotal() {
		return lqytotal;
	}

	public void setLqytotal(int lqytotal) {
		this.lqytotal = lqytotal;
	}

	public int getQbjtotal() {
		return qbjtotal;
	}

	public void setQbjtotal(int qbjtotal) {
		this.qbjtotal = qbjtotal;
	}

	public int getXdtotal() {
		return xdtotal;
	}

	public void setXdtotal(int xdtotal) {
		this.xdtotal = xdtotal;
	}

	public int getWjtotal() {
		return wjtotal;
	}

	public void setWjtotal(int wjtotal) {
		this.wjtotal = wjtotal;
	}

	public int getJttotal() {
		return jttotal;
	}

	public void setJttotal(int jttotal) {
		this.jttotal = jttotal;
	}

	public int getSltotal() {
		return sltotal;
	}

	public void setSltotal(int sltotal) {
		this.sltotal = sltotal;
	}

	public int getPxtotal() {
		return pxtotal;
	}

	public void setPxtotal(int pxtotal) {
		this.pxtotal = pxtotal;
	}

	public int getDytotal() {
		return dytotal;
	}

	public void setDytotal(int dytotal) {
		this.dytotal = dytotal;
	}

	public int getXjtotal() {
		return xjtotal;
	}

	public void setXjtotal(int xjtotal) {
		this.xjtotal = xjtotal;
	}

	public int getDjytotal() {
		return djytotal;
	}

	public void setDjytotal(int djytotal) {
		this.djytotal = djytotal;
	}

	public int getPztotal() {
		return pztotal;
	}

	public void setPztotal(int pztotal) {
		this.pztotal = pztotal;
	}

	public int getCztotal() {
		return cztotal;
	}

	public void setCztotal(int cztotal) {
		this.cztotal = cztotal;
	}

	public int getJytotal() {
		return jytotal;
	}

	public void setJytotal(int jytotal) {
		this.jytotal = jytotal;
	}

	public int getTfxtotal() {
		return tfxtotal;
	}

	public void setTfxtotal(int tfxtotal) {
		this.tfxtotal = tfxtotal;
	}

	public int getPjtotal() {
		return pjtotal;
	}

	public void setPjtotal(int pjtotal) {
		this.pjtotal = pjtotal;
	}

	public int getQltotal() {
		return qltotal;
	}

	public void setQltotal(int qltotal) {
		this.qltotal = qltotal;
	}
    
	

	

	
	
}