package com.zytx.converters;

import com.et.ar.Converter;

public class TimestampConverter implements Converter{
	@Override 
    public Object convert(Object value) {  System.out.println("--a---");
            if (value == null) { 
                    return null; 
            } 
            String s = value.toString();   
         
            return java.sql.Timestamp.valueOf(s); 
    } 
}
