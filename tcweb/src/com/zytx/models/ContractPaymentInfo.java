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
import com.et.ar.exception.ActiveRecordException;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeYwContractPaymentinfo")
public class ContractPaymentInfo extends ActiveRecordBase {

	/** DATA_ID */
	@Id
	private int id;

	/** ¶þÎ¬ÂëID */
	@Column
	private int contractinfoId;
	@Column
	private java.lang.String contractNumber;
	@Column
	private java.lang.String category;
	@Column
	private java.lang.String paymentiname;
	@Column
	private java.sql.Date conditiondate;
	@Column
	private java.math.BigDecimal referenceAmount;
	@Column
	private java.math.BigDecimal percentage;
	@Column
	private java.math.BigDecimal amount;
	@Column
	private java.math.BigDecimal deductions;
	@Column
	private java.math.BigDecimal  actualAmount;
	@Column
	private java.lang.String directionPayment;
	@Column
	private int ticketIsReceived;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.lang.String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(java.lang.String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public int getContractinfoId() {
		return contractinfoId;
	}
	public void setContractinfoId(int contractinfoId) {
		this.contractinfoId = contractinfoId;
	}
	public java.lang.String getCategory() {
		return category;
	}
	public void setCategory(java.lang.String category) {
		this.category = category;
	}
	public java.lang.String getPaymentiname() {
		return paymentiname;
	}
	public void setPaymentiname(java.lang.String paymentiname) {
		this.paymentiname = paymentiname;
	}
	public java.sql.Date getConditiondate() {
		return conditiondate;
	}
	public void setConditiondate(java.sql.Date conditiondate) {
		this.conditiondate = conditiondate;
	}
	public java.math.BigDecimal getReferenceAmount() {
		return referenceAmount;
	}
	public void setReferenceAmount(java.math.BigDecimal referenceAmount) {
		this.referenceAmount = referenceAmount;
	}
	public java.math.BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(java.math.BigDecimal percentage) {
		this.percentage = percentage;
	}
	public java.math.BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	public java.math.BigDecimal getDeductions() {
		return deductions;
	}
	public void setDeductions(java.math.BigDecimal deductions) {
		this.deductions = deductions;
	}
	public java.math.BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(java.math.BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public java.lang.String getDirectionPayment() {
		return directionPayment;
	}
	public void setDirectionPayment(java.lang.String directionPayment) {
		this.directionPayment = directionPayment;
	}
	public int getTicketIsReceived() {
		return ticketIsReceived;
	}
	public void setTicketIsReceived(int ticketIsReceived) {
		this.ticketIsReceived = ticketIsReceived;
	}
	
	
	
	

	
}