package org.sg.driver;

import java.lang.reflect.Method;
import java.util.Map;
import org.sg.methods.AppDependentMethods;
import org.sg.methods.AppIndependentMethods;
import org.sg.methods.Datatable;
import org.sg.methods.TaskModuleMethods;
import org.sg.methods.UserModuleMethods;
import org.sg.reports.ReportUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static TaskModuleMethods taskMethods = null;
	public static UserModuleMethods userMethods = null;
	public static Datatable datatable = null;
	public static ReportUtils reports = null;
	public static String screenshotLocation = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String testDataFile = null;
	public static String moduleName = null;
	
	@BeforeSuite
	public void loadClassFiles() {
		try {
			testDataFile = System.getProperty("user.dir") + "";
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			taskMethods = new TaskModuleMethods();
			userMethods = new UserModuleMethods();
			datatable = new Datatable();
			reports = new ReportUtils();
			extent = reports.startExtentReport("TestAutomationResults");
		}catch(Exception e) {
			System.out.println("Exception in 'loadClassFiles()' method. " + e);
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] getDataProvider(){
		return datatable.createDataProvider("Controller", "Controller");
	}
	
	
	@Test(dataProvider = "testData")
	public void runTestScripts(Map<String, String> data) {
		Class<?> cls = null;
		Object obj = null;
		Method script = null;
		try {
			moduleName = data.get("ModuleName");
			cls = Class.forName(data.get("ClassName"));
			obj = cls.getDeclaredConstructor().newInstance();
			script = obj.getClass().getMethod(data.get("ScriptName"));
			
			boolean blnRes = (boolean) script.invoke(obj);
			if(blnRes) {
				reports.writeResult(null, "Pass", "The testScript '"+data.get("ScriptName")+"' was passed");
			}else {
				reports.writeResult(null, "Fail", "The testScript '"+data.get("ScriptName")+"' was Failed");
			}
		}catch(Exception e) {
			System.out.println("Exception in 'runTestScripts()' method. " + e);
		}finally {
			cls = null;
			obj = null;
			script = null;
		}
	}
}
