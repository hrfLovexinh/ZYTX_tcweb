package com.zytx.init;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import java.util.TimerTask;

import com.et.ar.ActiveRecordBase;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.zytx.models.YWOutDateVO;
import com.zytx.models.YwInfoVO;
import com.zytx.util.SpringContextUtil;



public class YwOutDateTask extends TimerTask{
	public  int ywcount=0;
	
	public YwOutDateTask(){
		
	}
	public void run(){
		System.out.println("后台查询运维超期线程开始");
	//	if(ywcount==0 || ywcount==30){
			/*
			List<YwInfoVO> items = null;
			String registNumber="";
			String sql="select t.* from YwManagerInfo  t where datediff(day, t.subTime,getDate())>16";
			try {
				items =YwInfoVO.findBySql(YwInfoVO.class, sql);
				if(items != null && items.size()>0){
					Iterator<YwInfoVO> it = items.iterator();
					while(it.hasNext()){
						YwInfoVO  cqYwInfoVO =it.next();
						registNumber = cqYwInfoVO.getRegistNumber();
						ActiveRecordBase.execute(sql, args)
					}
				}
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    */
			try {
				ActiveRecordBase.beginTransaction();
				ActiveRecordBase.execute("exec pro_queryYwOutDate", null);
				ActiveRecordBase.commit();
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("执行存储过程报错");
				try {
					ActiveRecordBase.rollback();
				} catch (TransactionException e1) {
				}
			}
	//	}
		ywcount++;
		System.out.println("现在的ywcount值为----"+ywcount);
		if(ywcount>30){
			ywcount = 0;
		}
	}
 
	
}

