package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;

@Table(name = "Twocode96333pdetailInfo")
public class Twocode96333pVO extends ActiveRecordBase{
	@Id
	private int id;
	@Column
	private String registNumber;
	@Column
	private int pcompanyId;
	@Column
	private String pcompanyName;
	@Column
	private int puserId;
	@Column
	private String puserName;  	
	@Column
	private java.sql.Date ptime;
	@Column
	private java.sql.Date subtime;
	@Column
	private String map_X;
	@Column
	private String map_Y;
	@Column
	private String pjzhan1;
	@Column
	private String pjzhan2;
	@Column
	private String pjzhan3;
    @Column
    private String  pbeizhu;
    @Column
	private int psate;
    @Column
    private int pcount;
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
	public String getRegistNumber() {
		return registNumber;
	}
	public void setRegistNumber(String registNumber) {
		this.registNumber = registNumber;
	}
	public int getPcompanyId() {
		return pcompanyId;
	}
	public void setPcompanyId(int pcompanyId) {
		this.pcompanyId = pcompanyId;
	}
	public String getPcompanyName() {
		return pcompanyName;
	}
	public void setPcompanyName(String pcompanyName) {
		this.pcompanyName = pcompanyName;
	}
	public int getPuserId() {
		return puserId;
	}
	public void setPuserId(int puserId) {
		this.puserId = puserId;
	}
	public String getPuserName() {
		return puserName;
	}
	public void setPuserName(String puserName) {
		this.puserName = puserName;
	}
	public java.sql.Date getPtime() {
		return ptime;
	}
	public void setPtime(java.sql.Date ptime) {
		this.ptime = ptime;
	}
	public java.sql.Date getSubtime() {
		return subtime;
	}
	public void setSubtime(java.sql.Date subtime) {
		this.subtime = subtime;
	}
	public String getMap_X() {
		return map_X;
	}
	public void setMap_X(String mapX) {
		map_X = mapX;
	}
	public String getMap_Y() {
		return map_Y;
	}
	public void setMap_Y(String mapY) {
		map_Y = mapY;
	}
	public String getPjzhan1() {
		return pjzhan1;
	}
	public void setPjzhan1(String pjzhan1) {
		this.pjzhan1 = pjzhan1;
	}
	public String getPjzhan2() {
		return pjzhan2;
	}
	public void setPjzhan2(String pjzhan2) {
		this.pjzhan2 = pjzhan2;
	}
	public String getPjzhan3() {
		return pjzhan3;
	}
	public void setPjzhan3(String pjzhan3) {
		this.pjzhan3 = pjzhan3;
	}
	public String getPbeizhu() {
		return pbeizhu;
	}
	public void setPbeizhu(String pbeizhu) {
		this.pbeizhu = pbeizhu;
	}
	public int getPsate() {
		return psate;
	}
	public void setPsate(int psate) {
		this.psate = psate;
	}
	public int getPcount() {
		return pcount;
	}
	public void setPcount(int pcount) {
		this.pcount = pcount;
	}
	public int getDhspcount() {
		return dhspcount;
	}
	public void setDhspcount(int dhspcount) {
		this.dhspcount = dhspcount;
	}
	public int getYwCompanyIdinfo2() {
		return ywCompanyIdinfo2;
	}
	public void setYwCompanyIdinfo2(int ywCompanyIdinfo2) {
		this.ywCompanyIdinfo2 = ywCompanyIdinfo2;
	}
	
	
	
		
}
