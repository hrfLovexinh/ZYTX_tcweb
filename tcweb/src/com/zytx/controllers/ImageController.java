package com.zytx.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import com.et.ar.ConvertUtil;
import com.et.mvc.Controller;
import com.et.mvc.JsonView;
import com.et.mvc.View;
import com.et.mvc.filter.BeforeFilter;
import com.zytx.converters.DateConverter;
import com.zytx.converters.TimestampConverter;
import com.zytx.models.ImageVO;
import com.zytx.models.CarDevCard;
import com.zytx.models.UserInfo;
import com.zytx.models.ImageInfo;
import com.zytx.models.ImageInfo2;

public class ImageController extends ApplicationController{  
	/*

	 public View imagelist(int page, int rows) throws Exception{
	   //   long total = ImageVO.countBySql(ImageVO.class, "exec 按用户获取图片信息  ?", new Object[] { "test" });
	   //   long total =ImageVO.execute("exec 按用户获取图片信息 ?", new Object[] { "test" });
	  //     List<ImageVO>  items = ImageVO.findBySql(ImageVO.class, "exec 按用户获取图片信息  ?", new Object[] { "test" });   
	   //    List<ImageVO> items = ImageVO.findBySql(ImageVO.class, "select CARNUM as carnum,DEV_ID as dev_id from image_201105", null, null, rows, (page-1)*rows);
		 UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
		 String userName =userinfo.getUSER_NAME();
		 System.out.println("ImageController---25---登陆名称---"+userName);
	//	 UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from UserInfo where USER_NAME= ?",new Object[] { "tjt" });
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
		 
		 //2011-08-12新加的
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		 String nowDate  = String.valueOf(df.format(new Date()));
		 String nowMothTable ="image_"+nowDate.substring(0, 4)+nowDate.substring(5, 7);
		 String sql ="";
		 String sql2="";
		 sql = "select count(*) from "+nowMothTable+" where carnum in "+"("+carNumStr+")";
		 sql2 = "select * from "+nowMothTable+" where carnum in "+"("+carNumStr+")";
		 long total = ImageInfo.countBySql(ImageInfo.class, sql, null);
	//	 long total = ImageInfo.countBySql(ImageInfo.class, "select count(*) from image_201106 where carnum in "+"("+carNumStr+")", null);
	//	 long total = ImageInfo.count(ImageInfo.class, "carnum in "+"("+carNumStr+")", null);
		 System.out.println("------"+total);
	//	 List<ImageInfo>  items3 = ImageInfo.findAll(ImageInfo.class, "carnum in "+"("+carNumStr+")",null,null,rows, (page-1)*rows);
		 List<ImageInfo2> items3 = ImageInfo2.findBySql(ImageInfo2.class, sql2, null, null, rows, (page-1)*rows);
		 System.out.println("------"+items3.size());
	        Map<String, Object> result = new HashMap<String, Object>();
	        result.put("total", total);
	        result.put("rows", items3);
	   //     System.out.println("---"+items.size());
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
	//	 UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from UserInfo where USER_NAME= ?",new Object[] { "tjt" });
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
	 
	 /*
	 public View query(ImageVO info,int page, int rows)throws Exception{
		 String carnum ="";
		 String dev_id ="";
		 String startTime ="";
		 String endTime ="";
		 
		 carnum ="%"+info.getCarnum()+"%";
		 dev_id ="%"+info.getDev_id()+"%";
		 startTime=info.getStartTime();
		 endTime=info.getEndTime();
		 System.out.println("查询条件1-----"+info.getCarnum());
		 
		 String conditions ="carnum like  ? and dev_id like ? and image_time >= ? ";
		 Object[] param;
		 if(!"".equals(endTime)){
			 conditions +=" and image_time <= ? ";
			 param = new Object[]{carnum,dev_id,startTime,endTime};
		 }
		 else{
			 param = new Object[]{carnum,dev_id,startTime};
		 }
		 
		 String carNumStr ="";
		 carNumStr=carNumStrByUserName();
		 if(!"".equals(carNumStr)){
			 carNumStr =" and carnum in "+"("+carNumStr+")";
			 conditions +=carNumStr;
		 }
		 System.out.println("最后查询条件-----"+conditions);
		 long total = ImageInfo.count(ImageInfo.class, conditions, param);
		 List<ImageInfo>  items = ImageInfo.findAll(ImageInfo.class, conditions, param,null,rows, (page-1)*rows);	 
		 System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
	 }
	 
	 
	 public View query(ImageVO info,int page, int rows)throws Exception{
		 String carnum ="";
		 String dev_id ="";
		 String startTime ="";
		 String endTime ="";
		 
		 carnum ="%"+info.getCarnum()+"%";
		 dev_id ="%"+info.getDev_id()+"%";
		 startTime=info.getStartTime();
		 endTime=info.getEndTime();
		 System.out.println("查询条件1-----"+info.getCarnum());
		 
		 String conditions ="carnum like  ? and dev_id like ? and image_time >= ? ";
		 Object[] param;
		 if(!"".equals(endTime)){
			 conditions +=" and image_time <= ? ";
			 param = new Object[]{carnum,dev_id,startTime,endTime};
		 }
		 else{
			 param = new Object[]{carnum,dev_id,startTime};
		 }
		 
		 String carNumStr ="";
		 carNumStr=carNumStrByUserName();
		 if(!"".equals(carNumStr)){
			 carNumStr =" and carnum in "+"("+carNumStr+")";
			 conditions +=carNumStr;
		 }
		 System.out.println("最后查询条件-----"+conditions);
		 
		 String tableNameStr ="";
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		 String nowTime =String.valueOf(df.format(new Date()));
		 String startTime2=nowTime.substring(0, 7)+"-01 00:00:00";
		 String endTime2 =nowTime;
		 System.out.println("默认开始时间---"+startTime2+"---默认结束时间---"+endTime2);
		
		 if(!"".equals(startTime)){
			 startTime2 = startTime+" 00:00:00";
		 }
		 if(!"".equals(endTime)){
			 endTime2 = endTime+" 23:59:59";
		 }
		 System.out.println("输入开始时间---"+startTime2+"---输入结束时间---"+endTime2);
		 
		 List<ImageInfo2> tableList =ImageInfo2.findBySql(ImageInfo2.class, "exec getImgTables  ?,?", new Object[] { startTime2,endTime2 });
		 System.out.println("---tableList---"+tableList.size());
		 
		 int length =0;
		 length = tableList.size();
		 Object[] tableNameObject = new Object[length];
		 for(int i=0;i<length;i++){
			 tableNameObject[i]=tableList.get(i).getTablename();
			 System.out.println("第"+i+"个"+"表名---"+tableNameObject[i]);
		 }
		 
		 if(length==0){
		  Map<String, Object> result = new HashMap<String, Object>();	 
		  result.put("total", 0);
		  result.put("rows", null);
		  return new JsonView(result);
		 }
		 else{
		 String sqlStr =" select * from  "+tableNameObject[0];
		 String sqlStr2="";
		 for(int i=0;i<length-1;i++)
		 {
			 sqlStr +=" union all select * from "+tableNameObject[i+1];
		 }
		 sqlStr2 ="select * from ( "+sqlStr+" ) a where "+conditions;
		 sqlStr = "select count(*) from ( "+sqlStr+" ) a where "+conditions;
		 
		 System.out.println("最终sql---"+sqlStr);
	//	 System.arraycopy(param, 0,tableNameObject, length, param.length);
		 long total =ImageInfo2.countBySql(ImageInfo2.class, sqlStr, param);
		 List<ImageInfo2> items=ImageInfo2.findBySql(ImageInfo2.class, sqlStr2, param, null, rows, (page-1)*rows);
		
		
	   // long total = ImageInfo.count(ImageInfo.class, conditions, param);
	  //  List<ImageInfo>  items = ImageInfo.findAll(ImageInfo.class, conditions, param,null,rows, (page-1)*rows);	
		 
		  System.out.println("------"+items.size());
		  Map<String, Object> result = new HashMap<String, Object>();
		  result.put("total", total);
		  result.put("rows", items);
		  return new JsonView(result);
		 }
	 }
	 
	 
	 public View edit(int id,String image_time)throws Exception{
		    System.out.println("--image_time--"+image_time);
		    
		    String tableName="image_"+image_time.substring(0, 4)+image_time.substring(5, 7);
		    String sql ="select * from " + tableName + " where data_id = ? ";
		    
		    ImageInfo2 imageInfo = ImageInfo2.findFirstBySql(ImageInfo2.class, sql, new Object[]{id});
		  //  ImageInfo imageInfo = ImageInfo.find(ImageInfo.class, id); 
	    	Map<String, Object> result = new HashMap<String, Object>();
	    	if(imageInfo!=null){
	    		
	    	if(imageInfo.getCarnum()==null)
	    		result.put("carnum", "");
	    	else
	    	result.put("carnum", imageInfo.getCarnum());
	    	
	    	if(imageInfo.getDev_id()==null)
	    		result.put("dev_id", "");
	    	else	
	    	result.put("dev_id", imageInfo.getDev_id());
	    	
	    	
	    	result.put("image_id", imageInfo.getImage_id());
	    	
	    	if(imageInfo.getImage_type()==0){
	    		result.put("image_type", "未知");
	    	}
	    	if(imageInfo.getImage_type()==1){
	    		result.put("image_type", "实时提取");
	    	}
	    	if(imageInfo.getImage_type()==2){
	    		result.put("image_type", "紧急报警图片");
	    	}
	    	if(imageInfo.getImage_type()==3){
	    		result.put("image_type", "事故报警图片");
	    	}
	    //	result.put("image_type", imageInfo.getImage_type());
	    	
	    	if(imageInfo.getImage_format()==0){
	    		result.put("image_format", "未知");	
	    	}
	    	
	    	if(imageInfo.getImage_format()==1){
	    		result.put("image_format", "352*288");	
	    	}
	    	
	    	if(imageInfo.getImage_format()==2){
	    		result.put("image_format", "640*480");	
	    	}
	    	
	    	if(imageInfo.getImage_time()==null)
	    		result.put("image_time", "");
	    	else
	    	result.put("image_time", (imageInfo.getImage_time()).substring(0, 19));
	    	
	    	if(imageInfo.getReceive_time()==null)
	    		result.put("receive_time", "");
	    	else
	    	result.put("receive_time", (imageInfo.getReceive_time()).substring(0, 19));
	    	
	    	if(imageInfo.getImage_path()==null)
	    	   result.put("image_path", "");
	    	else
	    	result.put("image_path", imageInfo.getImage_path());
	    	}
	    	return new JsonView(result);
	    }
	 
	 public String findImagePath(int id,String image_time)throws  Exception{
		 String tableName="image_"+image_time.substring(0, 4)+image_time.substring(5, 7);
		 String sql ="select * from " + tableName + " where data_id = ? ";
		    
		 ImageInfo2 imageInfo = ImageInfo2.findFirstBySql(ImageInfo2.class, sql, new Object[]{id});
	//	 ImageInfo imageInfo = ImageInfo.find(ImageInfo.class, id); 
		 if(imageInfo!=null){
			 return imageInfo.getImage_path();
		 }
		 return "";
	 }      */
} 
