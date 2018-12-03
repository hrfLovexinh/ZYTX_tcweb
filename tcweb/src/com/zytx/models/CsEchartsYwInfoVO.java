package com.zytx.models;

/***********************************************************************
 * Module:  UserInfo.java
 * Author:  Administrator
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;

import net.sf.json.JSONObject;

import com.et.ar.ActiveRecordBase;
import com.et.ar.ConvertUtil;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;

/** userInfo . */
@Table(name = "TwoCodeElevatorInfo")
public class CsEchartsYwInfoVO extends ActiveRecordBase {

	
	
	@Column
    private int companyTotal;

	public int getCompanyTotal() {
		return companyTotal;
	}

	public void setCompanyTotal(int companyTotal) {
		this.companyTotal = companyTotal;
	}
	

	
	
	
	
	
}