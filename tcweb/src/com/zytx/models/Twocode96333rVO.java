package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "TwoCode96333rInfo")
public class Twocode96333rVO extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private int ruserId;
	@Column
	private String rcompanyName;
	@Column
	private String ruserName;
	@Column
	private String rtime;  //Èë¿â
	@Column
	private int rcount;
	@Column
	private int rtype;
	@Column
	private String rbeizhu;
    @Column
    private int  rtcount;
	
    @Column
    private int pztcount;
    @Column
    private int dhspcount;
    
    private int ywCompanyIdinfo2;

	static{ 
        ConvertUtil.register(new DateConverter(), java.sql.Date.class); 
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRuserId() {
		return ruserId;
	}
	public void setRuserId(int ruserId) {
		this.ruserId = ruserId;
	}
	public String getRcompanyName() {
		return rcompanyName;
	}
	public void setRcompanyName(String rcompanyName) {
		this.rcompanyName = rcompanyName;
	}
	public String getRuserName() {
		return ruserName;
	}
	public void setRuserName(String ruserName) {
		this.ruserName = ruserName;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	public int getRcount() {
		return rcount;
	}
	public void setRcount(int rcount) {
		this.rcount = rcount;
	}
	public String getRbeizhu() {
		return rbeizhu;
	}
	public void setRbeizhu(String rbeizhu) {
		this.rbeizhu = rbeizhu;
	}
	public int getRtcount() {
		return rtcount;
	}
	public void setRtcount(int rtcount) {
		this.rtcount = rtcount;
	}
	public int getYwCompanyIdinfo2() {
		return ywCompanyIdinfo2;
	}
	public void setYwCompanyIdinfo2(int ywCompanyIdinfo2) {
		this.ywCompanyIdinfo2 = ywCompanyIdinfo2;
	}
	public int getRtype() {
		return rtype;
	}
	public void setRtype(int rtype) {
		this.rtype = rtype;
	}
	public int getPztcount() {
		return pztcount;
	}
	public void setPztcount(int pztcount) {
		this.pztcount = pztcount;
	}
	public int getDhspcount() {
		return dhspcount;
	}
	public void setDhspcount(int dhspcount) {
		this.dhspcount = dhspcount;
	}
	
	
		
}
