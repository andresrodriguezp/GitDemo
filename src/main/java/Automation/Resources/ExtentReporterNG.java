package Automation.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	public static ExtentReports GetReportObject()
	{
		//here we are creating a dinamyc path where to save the reports by using "user.dir" is will always start since the project in this case "ExtetndReports"
		//we are saving it in a string
		String path = System.getProperty("user.dir")+"//reports/index.html";
		
		//here we are creating object of extentsparkreporter
		//this clase is responsible of creating a htm file and do the configurations for the reports
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		
		//setting the report name
		reporter.config().setReportName("Web Automation Results");
		
		//setting documnet tittle
		reporter.config().setDocumentTitle("Test Results");
		
		//creating object of ExtentReports class, this is the main class, the one in charge of drive all the reporting execution
		ExtentReports extent = new ExtentReports();
		
		//making my main class extentreports has knowledge of the extentsparkreporter class
		extent.attachReporter(reporter);
		
		//setting the tester name
		extent.setSystemInfo("Tester", "Andres");
		
		return extent;
	}

}
