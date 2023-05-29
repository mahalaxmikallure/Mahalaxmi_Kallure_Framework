package org.sg.reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.sg.driver.DriverScript;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportUtils extends DriverScript{
	/******************************************
	 * Method Name		: startExtentReport
	 * 
	 * 
	 * 
	 ******************************************/
	public ExtentReports startExtentReport(String reportFileName) {
		String resultPath = null;
		File objResFilePath = null;
		File objScreenshotPath = null;
		try {
			resultPath = System.getProperty("user.dir") + "\\target\\Results";
			
			objResFilePath = new File(resultPath);
			if(!objResFilePath.exists()) {
				objResFilePath.mkdirs();
			}
			
			screenshotLocation = resultPath+"//screenshot";
			
			objScreenshotPath = new File(screenshotLocation);
			if(!objScreenshotPath.exists()) {
				objScreenshotPath.mkdirs();
			}
			
			extent = new ExtentReports(resultPath + "\\" + reportFileName + ".html" , false);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", appInd.getPropData("environment"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			return extent;
		}catch(Exception e) {
			System.out.println("Exception in 'startExtentReport()' method. " + e);
			return null;
		}finally {
			resultPath = null;
			objResFilePath = null;
			objScreenshotPath = null;
		}
	}
	
	
	/******************************************
	 * Method Name		: endExtentReport
	 * 
	 * 
	 * 
	 ******************************************/
	public void endExtentReport(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e) {
			System.out.println("Exception in 'endExtentReport()' method. " + e);
		}
	}
	
	
	
	/******************************************
	 * Method Name		: writeResult
	 * 
	 * 
	 * 
	 ******************************************/
	public void writeResult(WebDriver oBrowser, String status, String resultDescription) {
		try {
			switch(status.toLowerCase()) {
				case "pass":
					test.log(LogStatus.PASS, resultDescription);
					break;
				case "fail":
					if(oBrowser!=null) {
						test.log(LogStatus.FAIL, resultDescription + " : " 
								+ test.addScreenCapture(captureScreenshot(oBrowser)));
					}else {
						test.log(LogStatus.FAIL, resultDescription);
					}
					
					break;
				case "info":
					test.log(LogStatus.INFO, resultDescription);
					break;
				case "warning":
					test.log(LogStatus.WARNING, resultDescription);
					break;
				case "exception":
					if(oBrowser!=null) {
						test.log(LogStatus.FATAL, resultDescription + " : " 
								+ test.addScreenCapture(captureScreenshot(oBrowser)));
					}else {
						test.log(LogStatus.FATAL, resultDescription);
					}
					break;
				case "screenshot":
					if(oBrowser!=null) {
						test.log(LogStatus.PASS, resultDescription + " : " 
								+ test.addScreenCapture(captureScreenshot(oBrowser)));
					}else {
						test.log(LogStatus.PASS, resultDescription);
					}
					default:
						System.out.println("Invalid result status '"+status+"'");
			}
		}catch(Exception e) {
			System.out.println("Exception in 'writeResult()' method. " + e);
		}
	}
	
	
	
	/******************************************
	 * Method Name		: captureScreenshot
	 * 
	 * 
	 * 
	 ******************************************/
	public String captureScreenshot(WebDriver oBrowser) {
		File objSource = null;
		String strDestination = null;
		File objDestination = null;
		try {
			strDestination = screenshotLocation + "\\screenshot_" + appInd.getDateTime("hhmmss")+".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			objDestination = new File(strDestination);
			FileHandler.copy(objSource, objDestination);
			return strDestination;
		}catch(Exception e) {
			System.out.println("Exception in 'captureScreenshot()' method. " + e);
			return null;
		}finally {
			objSource = null;
			strDestination = null;
			objDestination = null;
		}
	}
}
