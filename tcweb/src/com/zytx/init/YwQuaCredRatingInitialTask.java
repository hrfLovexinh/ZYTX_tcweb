package com.zytx.init;

import java.util.Calendar;
import java.util.Date;

import com.et.ar.exception.ActiveRecordException;
import com.zytx.models.YwQuaCredRatingVO;

public class YwQuaCredRatingInitialTask implements Runnable {
    
	
	@Override
	public void run() {
		Calendar cal = Calendar.getInstance();  
        cal.add(cal.MONTH, 1);  
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM");
		String s = format1.format(cal.getTime()); 
		try {
			YwQuaCredRatingVO.execute("exec pro_initialYwQuaCredRating ?", new Object[]{s});
		} catch (ActiveRecordException e) {
			System.out.println("pro_initialYwQuaCredRating--³ö´í");
		}
	}

}
