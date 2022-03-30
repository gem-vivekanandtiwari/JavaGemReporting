package com.qa.gemini.quartzReporting;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.time.Instant;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


public class GemReportingUtility {
	
	
	public static void createReport(String suiteDetail, String stepJson) throws IOException {
		String loc = GemTestReporter2.ReportLocation;
//		String htmlTemplate = FileUtils.readFileToString(new File(ClassLoader.getSystemResource("QuanticReport.html").getFile()), Charset.defaultCharset());
		String htmlTemplate = IOUtils.toString(ClassLoader.getSystemResourceAsStream("QuanticReport.html"), Charset.defaultCharset());
		htmlTemplate = htmlTemplate.replace("var obj = '';","var obj = "+suiteDetail+";");
		htmlTemplate = htmlTemplate.replace("var stepobj = '';", "var stepobj = "+stepJson+";");
		if(loc == null) {
			FileUtils.writeStringToFile(new File("Report/GemEcoTestReport.html"), htmlTemplate, Charset.defaultCharset());
		}
		else{
			FileUtils.writeStringToFile(new File(loc+"/GemEcoTestReport.html"), htmlTemplate, Charset.defaultCharset());
		}
		}
	
	public static long getCurrentTimeInSecond() {
		return Instant.now().getEpochSecond();
	}
	
	public static long getCurrentTimeInMilliSecond() {
		return Instant.now().toEpochMilli();
	}
	
	public static  String getMachineName() {
		 try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			return null;
		}
	 }
	
	public static String getCurrentUserName() {
		return System.getProperty("user.name");
	}

}
