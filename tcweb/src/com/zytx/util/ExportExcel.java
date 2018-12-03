package com.zytx.util;

import java.io.*;  
import java.lang.reflect.*;  
import java.util.*;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.text.SimpleDateFormat;  
import javax.swing.JOptionPane;  
import org.apache.poi.hssf.usermodel.*;  
import org.apache.poi.hssf.util.HSSFColor;  
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

  
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
  
public class ExportExcel<T> {  
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
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title); 
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 18);  
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
       
        // 设置这些样式  
      //设置这些样式  
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());  //設置單元格背景
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);   //設置背景填充模式
        style.setBorderBottom(BorderStyle.THIN);    
        style.setBorderLeft(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderTop(BorderStyle.THIN);  
        style.setAlignment(HorizontalAlignment.CENTER);   
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());  
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        // 把字体应用到当前的样式  
        style.setFont(font);  
     // 生成并设置另一个样式,用于设置内容样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());  
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
        style2.setBorderBottom(BorderStyle.THIN);  
        style2.setBorderLeft(BorderStyle.THIN);  
        style2.setBorderRight(BorderStyle.THIN);  
        style2.setBorderTop(BorderStyle.THIN);  
        style2.setAlignment(HorizontalAlignment.CENTER);  
        style2.setVerticalAlignment(VerticalAlignment.CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont(); 
        font2.setFontHeightInPoints((short)12);
        font2.setBold(false);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        
        
        
        //产生统计时间
        HSSFCellStyle tjtimestyle = workbook.createCellStyle();   //样式对象
        
        tjtimestyle.setAlignment(HorizontalAlignment.CENTER);//   水平
        tjtimestyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String tjtime =df.format(new Date());// new Date()为获取当前系统时间
		tjtimestyle.setFont(font);
		HSSFCell tjTimercell =null;
		HSSFRow tjTimerow = sheet.createRow(0);
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
        HSSFRow row = sheet.createRow(1);  
        for (short i = 0; i < headers.length; i++) {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        // 遍历集合数据，产生数据行  
        
        Iterator<T> it = dataset.iterator();  
        int index = 1;  
        while (it.hasNext()) {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next(); 
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            Field[] fields = t.getClass().getDeclaredFields();  
            
            	
            
            for (short i = 0; i < fields.length; i++) {  
                HSSFCell cell = row.createCell(i);
               
                
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
                        	 if(index == (dataset.size()+1))
                             	cell.setCellStyle(style);  
                             else  	
                                cell.setCellStyle(style2);  
                            HSSFRichTextString richString = new HSSFRichTextString(  
                                    textValue);  
                       //     HSSFFont font3 = workbook.createFont();  
                      //      font3.setColor(HSSFColor.BLACK.index);  
                     //       richString.applyFont(font3);
                    //        cell.setCellStyle(style2);  
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
}  