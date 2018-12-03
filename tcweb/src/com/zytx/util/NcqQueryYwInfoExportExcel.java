package com.zytx.util;

import java.io.*;  
import java.lang.reflect.*;  
import java.util.*;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import javax.swing.JOptionPane;  
import org.apache.poi.hssf.usermodel.*;  
import org.apache.poi.hssf.util.HSSFColor;  
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString; 


import com.et.ar.exception.ActiveRecordException;
import com.zytx.models.ElevaltorQueryExportVO;
import com.zytx.models.NcqQueryYwInfoExportVO;

  
/** 
 *  
 * 利用开源组件POI3.0.2动态导出EXCEL文档 
 *  
 * 转载时请保留以下信息，注明出处！ 
 *  
 * @author leno 
 *  
 * @version v1.0 
 *  
 * @param <T> 
 *            应用泛型，代表任意一个符合javabean风格的类 
 *  
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx() 
 *  
 *            byte[]表jpg格式的图片数据 
 */  
  
public class NcqQueryYwInfoExportExcel<T> {
	private int  hjetotal  =0;
	
	 public void exportExcel2(String[] headers, Collection<T> dataset,OutputStream out,String sql) {  
		 exportExcel2("Sheet1", headers, dataset, out, "yyyy-MM-dd",sql);  
	 }
	
    public void exportExcel(Collection<T> dataset, OutputStream out) {  
        exportExcel("Sheet1", null, dataset, out, "yyyy-MM-dd");  
    }  
    public void exportExcel(String[] headers, Collection<T> dataset,  
    OutputStream out) {  
        exportExcel("Sheet1", headers, dataset, out, "yyyy-MM-dd");  
    }  
    public void exportExcel(String[] headers, Collection<T> dataset,  
    OutputStream out, String pattern) {  
        exportExcel("Sheet1", headers, dataset, out, pattern);  
    }
    public void exportExcel(String title,String[] headers, Collection<T> dataset,  
    	    OutputStream out) {  
    	        exportExcel(title, headers, dataset, out, "yyyy-MM-dd");  
    	    } 
    
    @SuppressWarnings("unchecked")  
    public void exportExcel2(String title, String[] headers,  
    Collection<T> dataset, OutputStream out, String pattern,String sql) {  
        // 声明一个工作薄  
     //   HSSFWorkbook workbook = new HSSFWorkbook();  
        
        // 最重要的就是使用SXSSFWorkbook，表示流的方式进行操作  
        // 在内存中保持100行，超过100行将被刷新到磁盘  
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);  
        
        // 生成一个表格  
        SXSSFSheet sheet = workbook.createSheet(title); 
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 30);
       
        
        // 生成一个样式  :用于表头
        CellStyle style = workbook.createCellStyle();    
        //设置这些样式  
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());  //設置單元格背景
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);   //設置背景填充模式
        style.setBorderBottom(BorderStyle.THIN);    
        style.setBorderLeft(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderTop(BorderStyle.THIN);  
        style.setAlignment(HorizontalAlignment.CENTER);   
        // 生成一个字体  
        Font font = workbook.createFont();   
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());  
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        // 把字体应用到当前的样式  
        style.setFont(font);  
        
        
     // 生成并设置另一个样式,用于设置内容样式  
        CellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());  
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
        style2.setBorderBottom(BorderStyle.THIN);  
        style2.setBorderLeft(BorderStyle.THIN);  
        style2.setBorderRight(BorderStyle.THIN);  
        style2.setBorderTop(BorderStyle.THIN);  
        style2.setAlignment(HorizontalAlignment.CENTER);  
        style2.setVerticalAlignment(VerticalAlignment.CENTER);  
        // 生成另一个字体  
        Font font2 = workbook.createFont(); 
        font2.setFontHeightInPoints((short)12);
        font2.setBold(false);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        
        
        
        //产生统计时间
        CellStyle tjtimestyle = workbook.createCellStyle();   //样式对象   
        tjtimestyle.setAlignment(HorizontalAlignment.CENTER);//   水平
        tjtimestyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String tjtime =df.format(new Date());// new Date()为获取当前系统时间
		tjtimestyle.setFont(font);
		SXSSFCell tjTimercell =null;
		SXSSFRow tjTimerow = sheet.createRow(0); 
		  for(int i=0; i < headers.length;i++){  
			  tjTimercell = tjTimerow.createCell(i);   
			  tjTimercell.setCellStyle(tjtimestyle);
			  if(i==0){
				  HSSFRichTextString tjtimetext = new HSSFRichTextString(tjtime); 
		          tjTimercell.setCellValue("统计时间:"+tjtimetext);  
			  }	  
		  }
		  sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,(headers.length-1))); 
		  
          
         
        
        // 产生表格标题行  
		  SXSSFRow row = sheet.createRow(1);  
        for (short i = 0; i < headers.length; i++) {  
        	SXSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);  
            cell.setCellValue(text);   
        } 
        
        
        // 遍历集合数据，产生数据行  
        List<NcqQueryYwInfoExportVO> list = new ArrayList<NcqQueryYwInfoExportVO>(); 
     // 数据库中存储的数据行  
        int page_size = 1000; 
       
        // 求数据库中待导出数据的行数  
        int list_count = dataset.size();
        // 根据行数求数据提取次数  
        int export_times = list_count % page_size > 0 ? list_count / page_size  
                + 1 : list_count / page_size;  
        int index = 1;  
        // 按次数将数据写入文件  
        for (int j = 0; j < export_times; j++) { 
        	try {
				list = NcqQueryYwInfoExportVO.findBySql(NcqQueryYwInfoExportVO.class, sql, null,null,page_size, j*page_size);
			//	Iterator it =list.iterator();
	        //	 while (it.hasNext()) {  
			//            index++;  
			//            row = sheet.createRow(index);  
			//            T t = (T) it.next(); 
			
				 int len = list.size() < page_size ? list.size() : page_size;  
				 for (int i2 = 0; i2 < len; i2++) {   
					 row  = sheet.createRow(j * page_size + i2 + 2);
					 NcqQueryYwInfoExportVO t =list.get(i2);  
			//		 T t = (T)list.get(i2); 
					 // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
			            Field[] fields = t.getClass().getDeclaredFields();  
			            for (short i = 0; i < fields.length; i++) {  
			            	SXSSFCell cell = row.createCell(i);
			               
			                Field field = fields[i];  
			                String fieldName = field.getName();  
			                String getMethodName = "get"  
			                + fieldName.substring(0, 1).toUpperCase()  
			                + fieldName.substring(1);  
			                try {  
			                    Class tCls = t.getClass();  
			                    Method getMethod = tCls.getMethod(getMethodName,  
			                    new Class[] {});  
			                    Object value = getMethod.invoke(t, new Object[] {});  
			                    // 判断值的类型后进行强制类型转换  
			                    String textValue = null;  
			                    // if (value instanceof Integer) {  
			                    // int intValue = (Integer) value;  
			                    // cell.setCellValue(intValue);  
			                    // } else if (value instanceof Float) {  
			                    // float fValue = (Float) value;  
			                    // textValue = new HSSFRichTextString(  
			                    // String.valueOf(fValue));  
			                    // cell.setCellValue(textValue);  
			                    // } else if (value instanceof Double) {  
			                    // double dValue = (Double) value;  
			                    // textValue = new HSSFRichTextString(  
			                    // String.valueOf(dValue));  
			                    // cell.setCellValue(textValue);  
			                    // } else if (value instanceof Long) {  
			                    // long longValue = (Long) value;  
			                    // cell.setCellValue(longValue);  
			                    // }  
			                    if (value instanceof Boolean) {  
			                        boolean bValue = (Boolean) value;  
			                        textValue = "男";  
			                        if (!bValue) {  
			                            textValue = "女";  
			                        }  
			                    } else if (value instanceof Date) {  
			                        Date date = (Date) value;  
			                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
			                        textValue = sdf.format(date);  
			                    } else if (value instanceof byte[]) {  
			                        // 有图片时，设置行高为60px;  
			                        row.setHeightInPoints(60);  
			                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
			                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
			                        // sheet.autoSizeColumn(i);  
			                        byte[] bsValue = (byte[]) value;  
			                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
			                        1023, 255, (short) 6, index, (short) 6, index);  
			                        anchor.setAnchorType(2);  
			                        
			                    } else { 	
			                        // 其它数据类型都当作字符串简单处理
			                        textValue = value.toString();  
			                        if("0.0".equals(textValue))
			                        	textValue ="";
			                    	
			                    }  
			                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
			                    if (textValue != null) {  
			                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
			                        Matcher matcher = p.matcher(textValue);  
			                        if (matcher.matches()) {  
			                            // 是数字当作double处理  
			                            cell.setCellValue(Double.parseDouble(textValue));  
			                        } else { 
			                      //  	 if(index == (dataset.size()+1))
			                       //      	cell.setCellStyle(style);  
			                      //       else  	
			                      //          cell.setCellStyle(style2);  
			                        	cell.setCellStyle(style2);  
			                  //          HSSFRichTextString richString = new HSSFRichTextString(  
			                  //                  textValue);  
        		                        XSSFRichTextString richString = new XSSFRichTextString(  
						                                   textValue);  
			                            cell.setCellValue(richString);  
			                        }  
			                    } 
			                    if(i == fields.length -1){
			                    	SXSSFCell lastCell = row.createCell(i+1);
			                    	lastCell.setCellStyle(style2); 
			                    	if("".equals(value))
			                    	lastCell.setCellValue("");	
			                    	else
			                    	lastCell.setCellValue(daysBetween(value.toString(),tjtime)-15);
			                    }
			                } catch (SecurityException e) {  
			                    // TODO Auto-generated catch block  
			                    e.printStackTrace();  
			                } catch (NoSuchMethodException e) {  
			                    // TODO Auto-generated catch block  
			                    e.printStackTrace();  
			                } catch (IllegalArgumentException e) {  
			                    // TODO Auto-generated catch block  
			                    e.printStackTrace();  
			                } catch (IllegalAccessException e) {  
			                    // TODO Auto-generated catch block  
			                    e.printStackTrace();  
			                } catch (InvocationTargetException e) {  
			                    // TODO Auto-generated catch block  
			                    e.printStackTrace();  
			                } finally {  
			                    // 清理资源  
			                }  
			            }  
			          
				 }
				  list.clear(); // 每次存储len行，用完了将内容清空，以便内存可重复利用  
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       
   	 
        /*
        while (it.hasNext()) {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next(); 
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            Field[] fields = t.getClass().getDeclaredFields();  
            
            for (short i = 0; i < fields.length; i++) {  
            	SXSSFCell cell = row.createCell(i);
               
                Field field = fields[i];  
                String fieldName = field.getName();  
                String getMethodName = "get"  
                + fieldName.substring(0, 1).toUpperCase()  
                + fieldName.substring(1);  
                try {  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName,  
                    new Class[] {});  
                    Object value = getMethod.invoke(t, new Object[] {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    // if (value instanceof Integer) {  
                    // int intValue = (Integer) value;  
                    // cell.setCellValue(intValue);  
                    // } else if (value instanceof Float) {  
                    // float fValue = (Float) value;  
                    // textValue = new HSSFRichTextString(  
                    // String.valueOf(fValue));  
                    // cell.setCellValue(textValue);  
                    // } else if (value instanceof Double) {  
                    // double dValue = (Double) value;  
                    // textValue = new HSSFRichTextString(  
                    // String.valueOf(dValue));  
                    // cell.setCellValue(textValue);  
                    // } else if (value instanceof Long) {  
                    // long longValue = (Long) value;  
                    // cell.setCellValue(longValue);  
                    // }  
                    if (value instanceof Boolean) {  
                        boolean bValue = (Boolean) value;  
                        textValue = "男";  
                        if (!bValue) {  
                            textValue = "女";  
                        }  
                    } else if (value instanceof Date) {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    } else if (value instanceof byte[]) {  
                        // 有图片时，设置行高为60px;  
                        row.setHeightInPoints(60);  
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
                        1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        
                    } else { 	
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();  
                        if("0.0".equals(textValue))
                        	textValue ="";
                    	
                    }  
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
                    if (textValue != null) {  
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches()) {  
                            // 是数字当作double处理  
                            cell.setCellValue(Double.parseDouble(textValue));  
                        } else { 
                      //  	 if(index == (dataset.size()+1))
                       //      	cell.setCellStyle(style);  
                      //       else  	
                      //          cell.setCellStyle(style2);  
                        	cell.setCellStyle(style2);
                            HSSFRichTextString richString = new HSSFRichTextString(  
                                    textValue);  
                            cell.setCellValue(richString);  
                        }  
                    }  
                } catch (SecurityException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (NoSuchMethodException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (IllegalArgumentException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (InvocationTargetException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } finally {  
                    // 清理资源  
                }  
            }  
        }  
        */
        //产生统计行
      //获取工作表最后一行  
        SXSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));  
        SXSSFCell sumCell = lastRow.createCell(0);
        SXSSFCell sumCell1 = lastRow.createCell(1); 
        SXSSFCell sumCell2= lastRow.createCell(2); 
        SXSSFCell sumCell3= lastRow.createCell(3); 
        SXSSFCell sumCell4= lastRow.createCell(4);    
        SXSSFCell sumCell5= lastRow.createCell(5);  
        sumCell.setCellValue(new XSSFRichTextString("合计"));     
    //    for(int i = 0; i<dataset.size();i++){
         	this.hjetotal=this.hjetotal+ dataset.size();
        	
  //      }
       
        sumCell1.setCellValue(new XSSFRichTextString(String.valueOf(this.hjetotal))); 
        sumCell.setCellStyle(tjtimestyle);
        sumCell1.setCellStyle(tjtimestyle);
        sumCell2.setCellStyle(tjtimestyle);
        sumCell3.setCellStyle(tjtimestyle);
        sumCell4.setCellStyle(tjtimestyle);
        sumCell5.setCellStyle(tjtimestyle);
        
        try {  
            workbook.write(out);  
           
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     *  
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
     *  
     *  
     *  
     * @param title 
     *  
     *            表格标题名 
     *  
     * @param headers 
     *  
     *            表格属性列名数组 
     *  
     * @param dataset 
     *  
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
     *  
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
     *  
     * @param out 
     *  
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
     *  
     * @param pattern 
     *  
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd" 
     */  
  
    @SuppressWarnings("unchecked")  
    public void exportExcel(String title, String[] headers,  
    Collection<T> dataset, OutputStream out, String pattern) {  
        // 声明一个工作薄  
     //   HSSFWorkbook workbook = new HSSFWorkbook();  
        
        // 最重要的就是使用SXSSFWorkbook，表示流的方式进行操作  
        // 在内存中保持100行，超过100行将被刷新到磁盘  
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);  
      
        
        // 生成一个表格  
        SXSSFSheet sheet = workbook.createSheet(title); 
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 18);
        
        // 生成一个样式  :用于表头
        CellStyle style = workbook.createCellStyle();    
        //设置这些样式  
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());  //設置單元格背景
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);   //設置背景填充模式
        style.setBorderBottom(BorderStyle.THIN);    
        style.setBorderLeft(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderTop(BorderStyle.THIN);  
        style.setAlignment(HorizontalAlignment.CENTER);   
        // 生成一个字体  
        Font font = workbook.createFont();   
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());  
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        // 把字体应用到当前的样式  
        style.setFont(font);  
        
        
     // 生成并设置另一个样式,用于设置内容样式  
        CellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());  
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
        style2.setBorderBottom(BorderStyle.THIN);  
        style2.setBorderLeft(BorderStyle.THIN);  
        style2.setBorderRight(BorderStyle.THIN);  
        style2.setBorderTop(BorderStyle.THIN);  
        style2.setAlignment(HorizontalAlignment.CENTER);  
        style2.setVerticalAlignment(VerticalAlignment.CENTER);  
        // 生成另一个字体  
        Font font2 = workbook.createFont(); 
        font2.setFontHeightInPoints((short)12);
        font2.setBold(false);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        
        
        
        //产生统计时间
        CellStyle tjtimestyle = workbook.createCellStyle();   //样式对象   
        tjtimestyle.setAlignment(HorizontalAlignment.CENTER);//   水平
        tjtimestyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String tjtime =df.format(new Date());// new Date()为获取当前系统时间
		tjtimestyle.setFont(font);
		SXSSFCell tjTimercell =null;
		SXSSFRow tjTimerow = sheet.createRow(0); 
		  for(int i=0; i < headers.length;i++){  
			  tjTimercell = tjTimerow.createCell(i);   
			  tjTimercell.setCellStyle(tjtimestyle);
			  if(i==0){
				  HSSFRichTextString tjtimetext = new HSSFRichTextString(tjtime); 
		          tjTimercell.setCellValue("统计时间:"+tjtimetext);  
			  }	  
		  }
		  sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,(headers.length-1))); 
		  
          
         
        
        // 产生表格标题行  
		  SXSSFRow row = sheet.createRow(1);  
        for (short i = 0; i < headers.length; i++) {  
        	SXSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        } 
        
        
        // 遍历集合数据，产生数据行  
        List<ElevaltorQueryExportVO> list = new ArrayList<ElevaltorQueryExportVO>(); 
     // 数据库中存储的数据行  
        int page_size = 1000; 
        Iterator<T> it = dataset.iterator(); 
        // 求数据库中待导出数据的行数  
        int list_count = dataset.size();
        // 根据行数求数据提取次数  
        int export_times = list_count % page_size > 0 ? list_count / page_size  
                + 1 : list_count / page_size;  
        int index = 1;  
        
        
        while (it.hasNext()) {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next(); 
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            Field[] fields = t.getClass().getDeclaredFields();  
            
            for (short i = 0; i < fields.length; i++) {  
            	SXSSFCell cell = row.createCell(i);
               
                Field field = fields[i];  
                String fieldName = field.getName();  
                String getMethodName = "get"  
                + fieldName.substring(0, 1).toUpperCase()  
                + fieldName.substring(1);  
                try {  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName,  
                    new Class[] {});  
                    Object value = getMethod.invoke(t, new Object[] {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    // if (value instanceof Integer) {  
                    // int intValue = (Integer) value;  
                    // cell.setCellValue(intValue);  
                    // } else if (value instanceof Float) {  
                    // float fValue = (Float) value;  
                    // textValue = new HSSFRichTextString(  
                    // String.valueOf(fValue));  
                    // cell.setCellValue(textValue);  
                    // } else if (value instanceof Double) {  
                    // double dValue = (Double) value;  
                    // textValue = new HSSFRichTextString(  
                    // String.valueOf(dValue));  
                    // cell.setCellValue(textValue);  
                    // } else if (value instanceof Long) {  
                    // long longValue = (Long) value;  
                    // cell.setCellValue(longValue);  
                    // }  
                    if (value instanceof Boolean) {  
                        boolean bValue = (Boolean) value;  
                        textValue = "男";  
                        if (!bValue) {  
                            textValue = "女";  
                        }  
                    } else if (value instanceof Date) {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    } else if (value instanceof byte[]) {  
                        // 有图片时，设置行高为60px;  
                        row.setHeightInPoints(60);  
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
                        1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        
                    } else { 	
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();  
                        if("0.0".equals(textValue))
                        	textValue ="";
                    	
                    }  
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
                    if (textValue != null) {  
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches()) {  
                            // 是数字当作double处理  
                            cell.setCellValue(Double.parseDouble(textValue));  
                        } else { /* 
                        	 if(index == (dataset.size()+1))
                             	cell.setCellStyle(style);  
                             else  	
                                cell.setCellStyle(style2);  */
                        	cell.setCellStyle(style2);
                            HSSFRichTextString richString = new HSSFRichTextString(  
                                    textValue);  
                            cell.setCellValue(richString);  
                        }  
                    }  
                } catch (SecurityException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (NoSuchMethodException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (IllegalArgumentException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (InvocationTargetException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } finally {  
                    // 清理资源  
                }  
            }  
        } 
        
        //产生统计行
      //获取工作表最后一行  
        SXSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));  
        SXSSFCell sumCell = lastRow.createCell(0);
        SXSSFCell sumCell1 = lastRow.createCell(1); 
        SXSSFCell sumCell2= lastRow.createCell(2); 
        SXSSFCell sumCell3= lastRow.createCell(3); 
        SXSSFCell sumCell4= lastRow.createCell(4);    
        SXSSFCell sumCell5= lastRow.createCell(5);  
        sumCell.setCellValue(new HSSFRichTextString("合计"));     
    //    for(int i = 0; i<dataset.size();i++){
         	this.hjetotal=this.hjetotal+ dataset.size();
        	
  //      }
       
        sumCell1.setCellValue(new HSSFRichTextString(String.valueOf(this.hjetotal))); 
        sumCell.setCellStyle(tjtimestyle);
        sumCell1.setCellStyle(tjtimestyle);
        sumCell2.setCellStyle(tjtimestyle);
        sumCell3.setCellStyle(tjtimestyle);
        sumCell4.setCellStyle(tjtimestyle);
        sumCell5.setCellStyle(tjtimestyle);
        
        try {  
            workbook.write(out);  
           
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 创建合计行 
     * @param colSum 需要合并到的列索引 
     * @param cellValue 
     */  
     public void createLastSumRow(HSSFWorkbook wb,HSSFSheet sheet,int colSum, String[] cellValue) {  
         HSSFCellStyle cellStyle = wb.createCellStyle(); 
         cellStyle.setAlignment(HorizontalAlignment.CENTER);//    // 指定单元格居中对齐  
         cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 指定单元格垂直居中对齐  
         cellStyle.setWrapText(true);// 指定单元格自动换行  
         // 单元格字体  
         HSSFFont font = wb.createFont();  
         font.setBold(true);
         font.setFontName("宋体");  
         font.setFontHeight((short) 250);  
         cellStyle.setFont(font);  
         //获取工作表最后一行  
         HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));  
         HSSFCell sumCell = lastRow.createCell(0);  
         sumCell.setCellValue(new HSSFRichTextString("合计"));  
         sumCell.setCellStyle(cellStyle); 
         
         
         //合并 最后一行的第零列-最后一行的第一列  
     //    sheet.addMergedRegion(new Region(sheet.getLastRowNum(), (short) 0,sheet.getLastRowNum(), (short) colSum));// 指定合并区域  
         for (int i = 2; i < (cellValue.length + 2); i++) {  
             //定义最后一行的第三列  
             sumCell = lastRow.createCell(i);  
             sumCell.setCellStyle(cellStyle);  
             //定义数组 从0开始。  
             sumCell.setCellValue(new HSSFRichTextString(cellValue[i-2]));  
         }  
     }  
     
     /**  
      * 计算两个日期之间相差的天数  
      * @param smdate 较小的时间 
      * @param bdate  较大的时间 
      * @return 相差天数 
      * @throws ParseException  
      */    
     /** 
     *字符串的日期格式的计算 
     */  
         public static int daysBetween(String smdate,String bdate){
             SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");  
             long between_days =0;
             Calendar cal = Calendar.getInstance();    
             try {
				cal.setTime(sdf.parse(smdate));
				long time1 = cal.getTimeInMillis();
				cal.setTime(sdf.parse(bdate));    
				long time2 = cal.getTimeInMillis();  
				between_days=(time2-time1)/(1000*3600*24);  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
                             
            
                
            
                 
            return Integer.parseInt(String.valueOf(between_days));     
         }  
}  