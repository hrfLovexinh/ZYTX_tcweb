package com.zytx.models;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
@Table(name = "CARINFO")
public class Carinfo extends ActiveRecordBase{
	
	@Id(generate = GeneratorType.NONE)
	private String  carnum;  //���ƺ�
	@Column
	private String  car_type; //��������
	@Column
	private String  car_color; //������ɫ
	@Column
	private String  carnum_color; //������ɫ
	@Column
	private String  car_shelf_num; //���ܺ�
	@Column
	private String  car_enterprise; //��������˾
	@Column
	private int  oil_per; //ÿ������
	@Column
	private String  qy_id; //��ҵID
	@Column
	private String  person; //��ϵ��
	@Column
	private String  phone; //��ϵ�绰
	@Column
	private String  memo; //��ע
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String carType) {
		car_type = carType;
	}
	public String getCar_color() {
		return car_color;
	}
	public void setCar_color(String carColor) {
		car_color = carColor;
	}
	public String getCarnum_color() {
		return carnum_color;
	}
	public void setCarnum_color(String carnumColor) {
		carnum_color = carnumColor;
	}
	public String getCar_shelf_num() {
		return car_shelf_num;
	}
	public void setCar_shelf_num(String carShelfNum) {
		car_shelf_num = carShelfNum;
	}
	public String getCar_enterprise() {
		return car_enterprise;
	}
	public void setCar_enterprise(String carEnterprise) {
		car_enterprise = carEnterprise;
	}
	public int getOil_per() {
		return oil_per;
	}
	public void setOil_per(int oilPer) {
		oil_per = oilPer;
	}
	public String getQy_id() {
		return qy_id;
	}
	public void setQy_id(String qyId) {
		qy_id = qyId;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

}
