package com.zytx.controllers;

import java.util.Iterator;
import java.util.List;

import com.et.mvc.View;
import com.zytx.models.CarDevCard;
import com.zytx.models.ImageVO;
import com.zytx.models.UserInfo;

public class AccidentController extends ApplicationController{
/*	public View accidentlist(int page, int rows) throws Exception{
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getUSER_NAME();
		 System.out.println("AccidentController---10---µÇÂ½Ãû³Æ---"+userName);
		 UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from UserInfo where USER_NAME= ?",new Object[] { userName });
		 String qyID ="";
		 String qyStr ="";
		 String carNumStr ="";
		 if(user!=null){
			 qyID = user.getQY_ID();
			 List<ImageVO>  items = ImageVO.findBySql(ImageVO.class, "exec get_childid2  ?", new Object[] { qyID });
			 if(items!=null && items.size()>0){
			 Iterator<ImageVO> it =	items.iterator();
			 while(it.hasNext()){
				 ImageVO myvo =it.next();
				 qyStr+=myvo.getQy_id();
				 qyStr+=",";
			 }
			 }
		 }
		 if(!"".equals(qyStr)){
			 qyStr = qyStr.substring(0, qyStr.length()-1);
		 }
		 System.out.println("---qyStr---"+qyStr);
		
		 List<CarDevCard>  items2 = CarDevCard.findAll(CarDevCard.class, "QY_ID in "+"("+qyStr+")");
		 System.out.println("------"+items2.size());
		 if(items2!=null && items2.size()>0){
			 Iterator<CarDevCard> it2=items2.iterator();
			 while(it2.hasNext()){
				 CarDevCard carDevCard = it2.next();
				 carNumStr+="'"+carDevCard.getCarnum()+"'";
				 carNumStr+=",";
			 }
		 }
		 if(!"".equals(carNumStr)){
			 carNumStr =carNumStr.substring(0, carNumStr.length()-1);
		 }
		 System.out.println("---carNumStr---"+carNumStr);
		 
	} */
}
