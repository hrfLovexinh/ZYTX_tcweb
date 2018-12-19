package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table(name = "TwoCodeElevatorInfo")
public class ElevatorCoordinate extends ActiveRecordBase {

	@Id
	private int id;
	@Column
	private String registNumber;
	@Column
	private String registCode;
	@Column
	private String address;
	@Column
	private String emap_X;
	@Column
	private String emap_Y;
	@Column
	private String dmap_X;
	@Column
	private String dmap_Y;
	@Column
	private String pmap_X;
	@Column
	private String pmap_Y;
	@Column
	private String eddistance;
	@Column
	private String epdistance;
	@Column
	private String dpdistance;
	@Column
	private int dealCoordinate;
	
	
	public int getDealCoordinate() {
		return dealCoordinate;
	}
	public void setDealCoordinate(int dealCoordinate) {
		this.dealCoordinate = dealCoordinate;
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
	public String getRegistCode() {
		return registCode;
	}
	public void setRegistCode(String registCode) {
		this.registCode = registCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmap_X() {
		return emap_X;
	}
	public void setEmap_X(String emap_X) {
		this.emap_X = emap_X;
	}
	public String getEmap_Y() {
		return emap_Y;
	}
	public void setEmap_Y(String emap_Y) {
		this.emap_Y = emap_Y;
	}
	public String getDmap_X() {
		return dmap_X;
	}
	public void setDmap_X(String dmap_X) {
		this.dmap_X = dmap_X;
	}
	public String getDmap_Y() {
		return dmap_Y;
	}
	public void setDmap_Y(String dmap_Y) {
		this.dmap_Y = dmap_Y;
	}
	public String getPmap_X() {
		return pmap_X;
	}
	public void setPmap_X(String pmap_X) {
		this.pmap_X = pmap_X;
	}
	public String getPmap_Y() {
		return pmap_Y;
	}
	public void setPmap_Y(String pmap_Y) {
		this.pmap_Y = pmap_Y;
	}
	public String getEddistance() {
		return eddistance;
	}
	public void setEddistance(String eddistance) {
		this.eddistance = eddistance;
	}
	public String getEpdistance() {
		return epdistance;
	}
	public void setEpdistance(String epdistance) {
		this.epdistance = epdistance;
	}
	public String getDpdistance() {
		return dpdistance;
	}
	public void setDpdistance(String dpdistance) {
		this.dpdistance = dpdistance;
	}
	
	
}
