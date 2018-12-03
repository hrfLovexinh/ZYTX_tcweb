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
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

  
/** 
 *  
 * ���ÿ�Դ���POI3.0.2��̬����EXCEL�ĵ� 
 *  
 * ת��ʱ�뱣��������Ϣ��ע�������� 
 *  
 * @author leno 
 *  
 * @version v1.0 
 *  
 * @param <T> 
 *            Ӧ�÷��ͣ���������һ������javabean������ 
 *  
 *            ע������Ϊ�˼������boolean�͵�����xxx��get����ʽΪgetXxx(),������isXxx() 
 *  
 *            byte[]��jpg��ʽ��ͼƬ���� 
 */  
  
public class ElevatorQueryExportExcel<T> {
	private int  hjetotal  =0;
	
	
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
     * ����һ��ͨ�õķ�����������JAVA�ķ�����ƣ����Խ�������JAVA�����в��ҷ���һ��������������EXCEL ����ʽ�����ָ��IO�豸�� 
     *  
     *  
     *  
     * @param title 
     *  
     *            �������� 
     *  
     * @param headers 
     *  
     *            ��������������� 
     *  
     * @param dataset 
     *  
     *            ��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ� 
     *  
     *            javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����) 
     *  
     * @param out 
     *  
     *            ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ����������� 
     *  
     * @param pattern 
     *  
     *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyy-MM-dd" 
     */  
  
    @SuppressWarnings("unchecked")  
    public void exportExcel(String title, String[] headers,  
    Collection<T> dataset, OutputStream out, String pattern) {  
        // ����һ��������  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // ����һ�����  
        HSSFSheet sheet = workbook.createSheet(title); 
        // ���ñ��Ĭ���п��Ϊ15���ֽ�  
        sheet.setDefaultColumnWidth((short) 18);
        
        // ����һ����ʽ  :���ڱ�ͷ
        HSSFCellStyle style = workbook.createCellStyle();  
        //������Щ��ʽ  
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());  //�O�Æ�Ԫ�񱳾�
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);   //�O�ñ������ģʽ
        style.setBorderBottom(BorderStyle.THIN);    
        style.setBorderLeft(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderTop(BorderStyle.THIN);  
        style.setAlignment(HorizontalAlignment.CENTER);   
        // ����һ������  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());  
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        // ������Ӧ�õ���ǰ����ʽ  
        style.setFont(font);  
        
        
     // ���ɲ�������һ����ʽ,��������������ʽ  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());  
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
        style2.setBorderBottom(BorderStyle.THIN);  
        style2.setBorderLeft(BorderStyle.THIN);  
        style2.setBorderRight(BorderStyle.THIN);  
        style2.setBorderTop(BorderStyle.THIN);  
        style2.setAlignment(HorizontalAlignment.CENTER);  
        style2.setVerticalAlignment(VerticalAlignment.CENTER);  
        // ������һ������  
        HSSFFont font2 = workbook.createFont(); 
        font2.setFontHeightInPoints((short)12);
        font2.setBold(false);  
        // ������Ӧ�õ���ǰ����ʽ  
        style2.setFont(font2);  
        
        
        
        //����ͳ��ʱ��
        HSSFCellStyle tjtimestyle = workbook.createCellStyle();   //��ʽ����   
        tjtimestyle.setAlignment(HorizontalAlignment.CENTER);//   ˮƽ
        tjtimestyle.setVerticalAlignment(VerticalAlignment.CENTER);// ��ֱ
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
		String tjtime =df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		tjtimestyle.setFont(font);
		HSSFCell tjTimercell =null;
		HSSFRow tjTimerow = sheet.createRow(0);
		  for(int i=0; i < headers.length;i++){  
			  tjTimercell = tjTimerow.createCell(i); 
			  tjTimercell.setCellStyle(tjtimestyle);
			  if(i==0){
				  HSSFRichTextString tjtimetext = new HSSFRichTextString(tjtime); 
		          tjTimercell.setCellValue("ͳ��ʱ��:"+tjtimetext);  
			  }	  
		  }
		  sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,(headers.length-1))); 
		  
          
         
        
        // ������������  
        HSSFRow row = sheet.createRow(1);  
        for (short i = 0; i < headers.length; i++) {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        } 
        
        
        // �����������ݣ�����������  
        Iterator<T> it = dataset.iterator();  
        int index = 1;  
        while (it.hasNext()) {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next(); 
            // ���÷��䣬����javabean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ  
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
                    // �ж�ֵ�����ͺ����ǿ������ת��  
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
                        textValue = "��";  
                        if (!bValue) {  
                            textValue = "Ů";  
                        }  
                    } else if (value instanceof Date) {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    } else if (value instanceof byte[]) {  
                        // ��ͼƬʱ�������и�Ϊ60px;  
                        row.setHeightInPoints(60);  
                        // ����ͼƬ�����п��Ϊ80px,ע�����ﵥλ��һ������  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
                        1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        
                    } else { 	
                        // �����������Ͷ������ַ����򵥴���
                        textValue = value.toString();  
                        if("0.0".equals(textValue))
                        	textValue ="";
                    	
                    }  
                    // �������ͼƬ���ݣ�������������ʽ�ж�textValue�Ƿ�ȫ�����������  
                    if (textValue != null) {  
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches()) {  
                            // �����ֵ���double����  
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
                    // ������Դ  
                }  
            }  
        } 
        
        //����ͳ����
      //��ȡ���������һ��  
        HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));  
        HSSFCell sumCell = lastRow.createCell(0);
        HSSFCell sumCell1 = lastRow.createCell(1); 
        HSSFCell sumCell2= lastRow.createCell(2); 
        HSSFCell sumCell3= lastRow.createCell(3); 
        HSSFCell sumCell4= lastRow.createCell(4);    
        HSSFCell sumCell5= lastRow.createCell(5);  
        sumCell.setCellValue(new HSSFRichTextString("�ϼ�"));     
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
     * �����ϼ��� 
     * @param colSum ��Ҫ�ϲ����������� 
     * @param cellValue 
     */  
     public void createLastSumRow(HSSFWorkbook wb,HSSFSheet sheet,int colSum, String[] cellValue) {  
         HSSFCellStyle cellStyle = wb.createCellStyle(); 
         cellStyle.setAlignment(HorizontalAlignment.CENTER);//    // ָ����Ԫ����ж���  
         cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// ָ����Ԫ��ֱ���ж���  
         cellStyle.setWrapText(true);// ָ����Ԫ���Զ�����  
         // ��Ԫ������  
         HSSFFont font = wb.createFont();  
         font.setBold(true);
         font.setFontName("����");  
         font.setFontHeight((short) 250);  
         cellStyle.setFont(font);  
         //��ȡ���������һ��  
         HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));  
         HSSFCell sumCell = lastRow.createCell(0);  
         sumCell.setCellValue(new HSSFRichTextString("�ϼ�"));  
         sumCell.setCellStyle(cellStyle); 
         
         
         //�ϲ� ���һ�еĵ�����-���һ�еĵ�һ��  
     //    sheet.addMergedRegion(new Region(sheet.getLastRowNum(), (short) 0,sheet.getLastRowNum(), (short) colSum));// ָ���ϲ�����  
         for (int i = 2; i < (cellValue.length + 2); i++) {  
             //�������һ�еĵ�����  
             sumCell = lastRow.createCell(i);  
             sumCell.setCellStyle(cellStyle);  
             //�������� ��0��ʼ��  
             sumCell.setCellValue(new HSSFRichTextString(cellValue[i-2]));  
         }  
     }  
}  