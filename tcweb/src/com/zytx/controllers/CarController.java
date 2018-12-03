package com.zytx.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.TransactionException;
import com.et.ar.ActiveRecordBase;
import com.et.mvc.JsonView;
import com.et.mvc.View;
import com.zytx.models.CarLastPos;
import com.zytx.models.Cardinfo;
import com.zytx.models.CarinfoVO;
import com.zytx.models.Devinfo;
import com.zytx.models.QyInfo;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.Carinfo;



public class CarController extends ApplicationController{ /*
	 public View carlist(int page, int rows) throws Exception{
	   
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 System.out.println("CarController---22---登陆名称---"+userName);
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
		 
		 long total =CarinfoVO.countBySql(CarinfoVO.class, "select count(*)  from car_dev_card cdc left join carinfo c on cdc.carnum =c.carnum "+ 
		 		" left join devinfo d on cdc.dev_id =d.dev_id "+" left join qyinfo q on cdc.qy_id =q.qy_id " +  " left join car_lastpos cp on cdc.carnum=cp.carnum where cdc.carnum in "+"("+carNumStr+")" , null);
	     
		 System.out.println("------"+total);
		 List<CarinfoVO>  items3 = CarinfoVO.findBySql(CarinfoVO.class, "select cdc.*,cp.longitude as longitude,cp.latitude as latitude,cp.speed as speed,cp.angle as angle,cp.gps_time as gps_time,c.car_type as car_type,c.car_color as car_color,c.person as person,c.phone as phone,c.carnum_color as carnum_color,d.dev_type as dev_type,q.qy_name as qy_name from car_dev_card cdc left join carinfo c on cdc.carnum =c.carnum "+ 
			 		" left join devinfo d on cdc.dev_id =d.dev_id "+" left join qyinfo q on cdc.qy_id =q.qy_id " +  " left join car_lastpos cp on cdc.carnum=cp.carnum where cdc.carnum in "+"("+carNumStr+")" , null, null, rows, (page-1)*rows);
	
		 System.out.println("------"+items3.size());
	        Map<String, Object> result = new HashMap<String, Object>();
	        result.put("total", total);
	        result.put("rows", items3);
	
	        return new JsonView(result);
	    
	    }
	 
	 protected String carNumStrByUserName()throws Exception{
		 String carNumStr ="";
		 String qyID ="";
		 String qyStr ="";
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getUSER_NAME();
		 System.out.println("ImageController---81---登陆名称---"+userName);
		 UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from UserInfo where USER_NAME= ?",new Object[] { userName });
	
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
		 return carNumStr;
	 }
	 
	 public View query(CarinfoVO info,int page, int rows)throws Exception{
		 String carnum ="";
		 String dev_id ="";
		 String simnum ="";
		 String dev_type ="";
		 String qy_name="";
		 String car_type="";
		 String car_color="";
		 String person="";
		 String phone="";
		 
		 carnum ="%"+info.getCarnum()+"%";
		 dev_id ="%"+info.getDev_id()+"%";
		 simnum="%"+info.getSimnum()+"%";
		 dev_type="%"+info.getDev_type()+"%";
		 qy_name="%"+info.getQy_name()+"%";
		 
		 car_type="%"+info.getCar_type()+"%";
		 car_color="%"+info.getCar_color()+"%";
		 person="%"+info.getPerson()+"%";
		 phone="%"+info.getPhone()+"%";
		 System.out.println("查询条件1-----"+info.getCarnum());
		 
		 String cartypeCondition ="";
		 String carcolorCondition ="";
		 String personCondition ="";
		 String phoneConditon ="";
		 if("".equals(info.getCar_type())){
			 cartypeCondition+=" or c.car_type is null ";
		 }
		 if("".equals(info.getCar_color())){
			 carcolorCondition+=" or c.car_color is null "; 
		 }
		 if("".equals(info.getPerson())){
			 personCondition+=" or c.person is null "; 
		 }
		 if("".equals(info.getPhone())){
			 phoneConditon+=" or c.phone is null "; 
		 }
		 
		 String conditions =" cdc.carnum like  ? and cdc.dev_id like ? and cdc.simnum like ? and d.dev_type like ? and q.qy_name like ? and ( c.car_type like ? "+cartypeCondition+")"+" and ( c.car_color like ? "+carcolorCondition+")"+"and ( c.person like ? "+personCondition+")"+"and ( c.phone like ? "+phoneConditon+")";
		 Object[] param;
		 
		 param = new Object[]{carnum,dev_id,simnum,dev_type,qy_name,car_type,car_color,person,phone};
		 
		 String carNumStr ="";
		 carNumStr=carNumStrByUserName();
		 if(!"".equals(carNumStr)){
			 carNumStr =" and cdc.carnum in "+"("+carNumStr+")";
			 conditions +=carNumStr;
		 }
		 System.out.println("最后查询条件-----"+conditions);

		 long total = CarinfoVO.countBySql(CarinfoVO.class,"select count(*) from car_dev_card cdc left join carinfo c on cdc.carnum =c.carnum "+ 
		 		" left join devinfo d on cdc.dev_id =d.dev_id "+" left join qyinfo q on cdc.qy_id =q.qy_id " +  " left join car_lastpos cp on cdc.carnum=cp.carnum where "+conditions, param);
	
		 List<CarinfoVO>  items = CarinfoVO.findBySql(CarinfoVO.class,"select cdc.*,cp.longitude as longitude,cp.latitude as latitude,cp.speed as speed,cp.angle as angle,cp.gps_time as gps_time,c.car_type as car_type,c.car_color as car_color,c.person as person,c.phone as phone,c.carnum_color as carnum_color,d.dev_type as dev_type,q.qy_name as qy_name from car_dev_card cdc left join carinfo c on cdc.carnum =c.carnum "+ 
			 		" left join devinfo d on cdc.dev_id =d.dev_id "+" left join qyinfo q on cdc.qy_id =q.qy_id " +  " left join car_lastpos cp on cdc.carnum=cp.carnum where "+conditions , param, null, rows, (page-1)*rows);
	
		 System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 public View edit(int id)throws Exception{ 
		  CarinfoVO carinfoVO = CarinfoVO.findFirstBySql(CarinfoVO.class,"select cdc.*,cp.longitude as longitude,cp.latitude as latitude,cp.speed as speed,cp.angle as angle,cp.gps_time as gps_time,c.car_type as car_type,c.car_color as car_color,c.carnum_color as carnum_color,c.person as person,c.phone as phone,d.dev_type as dev_type,q.qy_name as qy_name from car_dev_card cdc left join carinfo c on cdc.carnum =c.carnum "+ 
			 		" left join devinfo d on cdc.dev_id =d.dev_id "+" left join qyinfo q on cdc.qy_id =q.qy_id " +  " left join car_lastpos cp on cdc.carnum=cp.carnum where cdc.data_id = ?", new Object[] { id }); 
	    	Map<String, Object> result = new HashMap<String, Object>();
	    	if(carinfoVO!=null){
	    		
	    	if(carinfoVO.getCarnum()==null)
	    		result.put("carnum", "");	
	    	else
	    	result.put("carnum", carinfoVO.getCarnum());
	    	
	    	if(carinfoVO.getDev_id()==null)
	    		result.put("dev_id", "");
	    	else
	    	result.put("dev_id", carinfoVO.getDev_id());
	    	
	    	if(carinfoVO.getQy_id()==null)
	    		result.put("qy_id2", "");
	    	else
	    	result.put("qy_id2", carinfoVO.getQy_id());
	    	
	    	if(carinfoVO.getQy_name()==null)
	    		result.put("qy_name", "");
	    	else
	    	result.put("qy_name", carinfoVO.getQy_name());
	    	
	    	if(carinfoVO.getSimnum()==null)
	    		result.put("simnum", "");
	    	else
	    	result.put("simnum", carinfoVO.getSimnum());
	    	
	    	if(carinfoVO.getDev_type()==null)
	    		result.put("dev_type", "");
	    	else
	    	result.put("dev_type", carinfoVO.getDev_type());
	    	
	    	if(carinfoVO.getCar_type()==null)
	    		result.put("car_type", "");
	    	else
	    	result.put("car_type", carinfoVO.getCar_type());
	    	
	    	if(carinfoVO.getCar_color()==null)
	    		result.put("car_color", "");
	    	else
	    	result.put("car_color", carinfoVO.getCar_color());
	    	
	    	if(carinfoVO.getCarnum_color()==null)
	    		result.put("carnum_color", "");
	    	else
	    	result.put("carnum_color", carinfoVO.getCarnum_color());
	    	
	    	if(carinfoVO.getLongitude()==null)
	    		result.put("longitude", "");
	    	else
	    	result.put("longitude", carinfoVO.getLongitude());
	    	
	    	if(carinfoVO.getLatitude()==null)
	    		result.put("latitude", "");
	    	else
	    	result.put("latitude", carinfoVO.getLatitude());
	    	
	    	if(carinfoVO.getSpeed()==null)
	    		result.put("speed", "");
	    	else
	    	result.put("speed", carinfoVO.getSpeed());
	    	
	    	if(carinfoVO.getAngle()==null)
	    		result.put("angle", "");
	    	else
	    	result.put("angle", carinfoVO.getAngle());
	    	
	    	if(carinfoVO.getGps_time()==null)
	    		result.put("gps_time", "");
	    	else
	    	result.put("gps_time", carinfoVO.getGps_time());
	    	
	    	if(carinfoVO.getPhone()==null)
	    		result.put("phone", "");
	    	else
	    	result.put("phone", carinfoVO.getPhone());
	    	
	    	if(carinfoVO.getPerson()==null)
	    		result.put("person", "");
	    	else
	    	result.put("person", carinfoVO.getPerson());
	    	}
	    	return new JsonView(result);
	    }
	 
	
	 
	 public String findImagePath(int id)throws  Exception{
		 ImageInfo imageInfo = ImageInfo.find(ImageInfo.class, id); 
		 if(imageInfo!=null){
			 return imageInfo.getImage_path();
		 }
		 return "";
	 }
	 
	 
	 
	 public View getQyNameList(int page, int rows) throws Exception{
    //	 List<QyInfo> items = QyInfo.findAll(QyInfo.class, null);
    	// Map<String, Object> result = new HashMap<String, Object>();
    	// result.put("data", items);
    	// System.out.println("group--->"+new JsonView(result));
		 
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getLoginName();
		 System.out.println("CarController---22---登陆名称---"+userName);
		 List<CarinfoVO>items =CarinfoVO.findBySql(CarinfoVO.class, "exec 按用户获取部门信息  ?", new Object[] { userName });
         return new JsonView(items);
    }
	 
	 public String add(CarinfoVO carVO) {
		String result = "failure";

		boolean carinfoFlag = false;
		boolean devinfoFlag = false;
		boolean cardinfoFlag = false;
		boolean flag = false;
		boolean carlastposFlag =false;
		
		
		try{
		String carNum =carVO.getCarnum();
		Carinfo carinfoExist =Carinfo.findFirstBySql(Carinfo.class, "select * from CARINFO where CARNUM= ?",new Object[] { carNum });
		if(carinfoExist!=null){
			return "exist";
		}
		} catch (ActiveRecordException ee) {
			ee.printStackTrace();}
		try {
			ActiveRecordBase.beginTransaction();
			System.out.println("开始carinfo");
			// carinfo
			Carinfo carinfo = new Carinfo();
			carinfo.setCarnum(carVO.getCarnum());
			carinfo.setCar_type(carVO.getCar_type());
			carinfo.setCar_color(carVO.getCar_color());
			carinfo.setCarnum_color(carVO.getCarnum_color());
			carinfo.setPerson(carVO.getPerson());
			carinfo.setPhone(carVO.getPhone());
			carinfo.setQy_id(carVO.getQy_name());
				carinfoFlag = carinfo.save();	System.out.println("结束carinfo");
			
           
			if (carinfoFlag) {	System.out.println("开始devinfo");
				Devinfo devinfo = new Devinfo();
				devinfo.setDev_id(carVO.getDev_id());
				devinfo.setDev_type(carVO.getDev_type());
				
					devinfoFlag = devinfo.save();	System.out.println("结束devinfo");
				
			}

			if (devinfoFlag) {
				Cardinfo cardinfo = new Cardinfo();	System.out.println("开始cardinfo");
				cardinfo.setSimnum(carVO.getSimnum());
				
					cardinfoFlag = cardinfo.save();	System.out.println("结束cardinfo");
				
			}

			if (cardinfoFlag){	System.out.println("开始cardevCard");
				CarDevCard carDevCard =new CarDevCard();
				carDevCard.setCarnum(carVO.getCarnum());
				carDevCard.setDev_id(carVO.getDev_id());
				carDevCard.setQy_id(carVO.getQy_name());
				carDevCard.setSimnum(carVO.getSimnum());
				
				flag =carDevCard.save();System.out.println("结束cardevCard");
				
			}
			
			if (flag){ System.out.println("开始carlastPos");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//	String nowDate  = String.valueOf(sdf.format(new Date()));
			try{
			Date nowDate = sdf.parse(sdf.format(new Date()));
			
			System.out.println("nowDate---"+nowDate);
			System.out.println("nowDate.getTime()---"+nowDate.getTime());
		//	System.out.println("nowDate2---"+java.sql.Date.valueOf(nowDate));
			java.sql.Date sqlDate=new java.sql.Date(nowDate.getTime());
            System.out.println("--sqlDate----"+sqlDate);
			CarLastPos  carLastPos = new CarLastPos();
			carLastPos.setCarnum(carVO.getCarnum());
			carLastPos.setLongitude(new BigDecimal(-1.000000));
			carLastPos.setLatitude(new BigDecimal(-1.000000));
			carLastPos.setGps_time(sqlDate);
			carLastPos.setReceive_time(sqlDate);
			carLastPos.setAngle(new BigDecimal(0.0));
			carLastPos.setSpeed(new BigDecimal(0.0));	
			carLastPos.setState("0");
			
			carlastposFlag=carLastPos.save();System.out.println("结束carlastPos");
			}catch (Exception e){
				e.printStackTrace();
			}
			}
           
			ActiveRecordBase.commit();
		} catch (ActiveRecordException ex) {
			ex.printStackTrace();
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException et) {
				et.printStackTrace();
			}
		}
		if (carlastposFlag) {
			result = "success";
		} else {
			result = "failure";
		}
		return result;
	}
	 
	public String update(int id) throws Exception {
		
		String result = "failure";
		String carNum = "";
		String devid = "";
		String simNum = "";
		CarDevCard carDevCard = CarDevCard.findFirstBySql(CarDevCard.class,
				"select * from Car_dev_card where data_id= ?",
				new Object[] { id });
		if (carDevCard != null) {
			carNum = carDevCard.getCarnum();
			devid = carDevCard.getDev_id();
			System.out.println("-----devid"+devid);
			simNum = carDevCard.getSimnum();

			try {
				ActiveRecordBase.beginTransaction();
				
				Cardinfo.updateAll(Cardinfo.class, " simnum = ?", new Object[] { request.getParameter("simnum")}, "simnum = ?", new Object[] { simNum });
				System.out.println("更新CARDINFO");
			
				Devinfo.updateAll(Devinfo.class, " dev_id = ? ,dev_type = ?", new Object[] { request.getParameter("dev_id"),request.getParameter("dev_type")}, "dev_id = ?", new Object[] { devid });
				System.out.println("更新DEVINFO");
				
				Carinfo.updateAll(Carinfo.class, " carnum = ? ,car_type = ? , car_color = ? , carnum_color = ? , person = ? , phone = ? , qy_id = ? ", new Object[] { request.getParameter("carnum"),request.getParameter("car_type"),request.getParameter("car_color"),request.getParameter("carnum_color"),request.getParameter("person"),request.getParameter("phone"),request.getParameter("qy_name")}, "carnum = ?", new Object[] { carNum });
				System.out.println("更新CARINFO");
				
				CarDevCard.updateAll(CarDevCard.class, " carnum = ? , dev_id = ? , simnum = ? , qy_id = ?  ", new Object[] { request.getParameter("carnum"),request.getParameter("dev_id"),request.getParameter("simnum"),request.getParameter("qy_name")}, "data_id = ?", new Object[] { id });
				System.out.println("更新Car_dev_card");
				
				result = "success";
			
			
				ActiveRecordBase.commit();
			} catch (ActiveRecordException ex) {
				ex.printStackTrace();
				try {
					ActiveRecordBase.rollback();
				} catch (TransactionException et) {
					et.printStackTrace();
				}
			}

		}
	
		return result;
	}
	 
	 
	 public String delete(int data_id)throws Exception{
	   	String result="";
	   	String carNum="";
	   	String devid="";
	   	String simNum="";
	   	CarDevCard carDevCard = CarDevCard.findFirstBySql(CarDevCard.class, "select * from Car_dev_card where data_id= ?",new Object[] { data_id });
	   	if(carDevCard!=null){
	   		carNum=carDevCard.getCarnum();
	   		devid=carDevCard.getDev_id();
	   		simNum=carDevCard.getSimnum();
	 
	   	try {
			ActiveRecordBase.beginTransaction();
			
			Cardinfo cardinfo = Cardinfo.findFirstBySql(Cardinfo.class, "select * from CARDINFO where simnum= ?",new Object[] { simNum });
			if(cardinfo.destroy()==1){
				System.out.println("cardinfo删除成功！");
				
			};
			
		
			Devinfo devinfo = Devinfo.findFirstBySql(Devinfo.class, "select * from DEVINFO where dev_id= ?",new Object[] { devid });
			
			if(devinfo.destroy()==1){
				System.out.println("devinfo删除成功！");
			};
			
			Carinfo carinfo = Carinfo.findFirstBySql(Carinfo.class, "select * from CARINFO where carnum= ?",new Object[] { carNum });
			if(carinfo.destroy()==1){
				System.out.println("carinfo删除成功！");
			}
			
				
			if (carDevCard.destroy() == 1) {
				System.out.println("carDevCard删除成功！");
			//	result = "success";
			}
			
			CarLastPos carLastPos =CarLastPos.findFirstBySql(CarLastPos.class, "select * from Car_LASTPOS where carnum= ?",new Object[] { carNum });
			if(carLastPos.destroy() == 1){
				System.out.println("carLasPos删除成功！");
				result = "success";
			}
				
			ActiveRecordBase.commit();	
	   	}catch (ActiveRecordException ex) {
			ex.printStackTrace();
			try {
				ActiveRecordBase.rollback();
			} catch (TransactionException et) {
				et.printStackTrace();
			}
		}
	   	
	   	}
	   
	   	 return result;	
	   }   */
}
