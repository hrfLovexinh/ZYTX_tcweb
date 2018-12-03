package com.zytx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.CustomPieURLGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;

import java.awt.Font;

import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.*;

import com.et.ar.exception.ActiveRecordException;
import com.zytx.models.YwInfoVO;



public class YwtjDisplayChart{
	private    DefaultPieDataset dataset = new DefaultPieDataset();
	private  int userId =0;
	private  CustomPieURLGenerator generator=null;
	long total=0;
	long averageyw =0;
	public YwtjDisplayChart(int userId){
		this.userId = userId;
	}
	public void  setValue(String key,Double value){
		dataset.setValue(key, value);
	}
	 
	public void ywtjquery(int userId){ 
		
		long totalzc =0;
		long totalds =0;
		long totalwz =0;
		long totalfz =0;
		long totalph =0;
		long totaltyc =0;
		long totalhsc =0;
		long totalwzc =0;
		if(userId>0){
	//		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//		String s = format1.format(new Date());
			 try {
				averageyw =YwInfoVO.countBySql(YwInfoVO.class, "select isnull(AVG(ywResult),0.0) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 ", new Object[] { userId });
				System.out.println("averageyw--->"+averageyw);
				total =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 ", new Object[] { userId });
				System.out.println("total--->"+total);
				totalzc =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=100", new Object[] { userId });
				System.out.println("totalzc--->"+totalzc);
				totalds =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=90", new Object[] { userId });
				System.out.println("totalds--->"+totalds);
				totalwz =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=80", new Object[] { userId });
				System.out.println("totalwz--->"+totalwz);
				totalfz =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=20", new Object[] { userId });
				System.out.println("totalfz--->"+totalfz);
				totalph =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=10", new Object[] { userId });
				System.out.println("totalph--->"+totalph);
				totaltyc =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=50", new Object[] { userId });
				System.out.println("totalph--->"+totaltyc);
				totalhsc =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=40", new Object[] { userId });
				System.out.println("totalhsc--->"+totalhsc);
				totalwzc =YwInfoVO.countBySql(YwInfoVO.class, "select count(*) from YwManagerInfo where userId = ? and   datediff(day,CONVERT(Datetime,startTime,120),getdate()) < 31 and ywResult=30", new Object[] { userId });
				System.out.println("totalhsc--->"+totalwzc);
				
				if(totalzc>0)
				dataset.setValue("����", totalzc);
				if(totalds>0)
				dataset.setValue("��ʧ", totalds);
				if(totalwz>0)
				dataset.setValue("δ֪", totalwz);
				if(totalfz>0)
				dataset.setValue("����", totalfz);
				if(totalph>0)
				dataset.setValue("�ƻ�", totalph);
				if(totaltyc>0)
				dataset.setValue("ͼ�쳣", totaltyc);
				if(totalhsc>0)
				dataset.setValue("�����", totalhsc);
				if(totalwzc>0)
				dataset.setValue("λ�ô�", totalwzc);
				
				//���볬����
				Map<String,String> map = new HashMap<String,String>();
				map.put("����","link.jsp");
				map.put("��ʧ","link.jsp");
				map.put("δ֪","link.jsp");
				map.put("����","link.jsp");
				map.put("�ƻ�","link.jsp");
				map.put("ͼ�쳣","link.jsp");
				map.put("�����","link.jsp");
				map.put("λ�ô�","link.jsp");
				generator = new CustomPieURLGenerator();
				generator.addURLs(map); 
		
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String generatePieChart(String title,HttpSession session,PrintWriter pw){  
		ywtjquery(this.userId);
		
		String filename = null;
		//����char����
		PiePlot plot =new PiePlot(dataset);
		
		plot.setURLGenerator(generator);
	//	plot.setURLGenerator(new StandardPieURLGenerator("link.jsp"));   //���볬����


		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		plot.setLabelGap(0.02D);     
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
		JFreeChart chart = new JFreeChart("����", JFreeChart.DEFAULT_TITLE_FONT,plot,true);

        chart.setBackgroundPaint(java.awt.Color.white);
        if(total>0){
        chart.setTitle(title+":"+"��һ���¹���ά"+total+"̨����,ƽ����ά������"+averageyw);
        ChartRenderingInfo info=new ChartRenderingInfo(new StandardEntityCollection());
 	   
	    try {
			filename = ServletUtilities.saveChartAsPNG(chart, 700, 450, info, session);
			ChartUtilities.writeImageMap(pw, filename, info,true);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(filename);
        }
       
	  return  filename;
	}  
}
