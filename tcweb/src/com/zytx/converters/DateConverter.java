package com.zytx.converters;

import com.et.ar.Converter;

public class DateConverter implements Converter{
	@Override 
    public Object convert(Object value) { 
            if (value == null) { 
                    return null; 
            } 
            String s = value.toString().substring(0, 10);   //ȡyyyy-mm-dd 
       //     System.out.println("-----"+s);
            return java.sql.Date.valueOf(s); 
    } 

}
