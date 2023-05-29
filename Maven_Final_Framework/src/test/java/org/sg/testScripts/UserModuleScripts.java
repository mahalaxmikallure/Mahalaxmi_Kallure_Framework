package org.sg.testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.sg.driver.DriverScript;
import org.testng.Assert;

public class UserModuleScripts extends DriverScript{
	/******************************************
	 * Method Name		: TC_LoginLogout
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean TC_LoginLogout() {
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TC_LoginLogout");
			objData = datatable.getDataFromExcel(moduleName, "Users", "TC_101");
			
			//(a) open browser and navigate the URL
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed toload the URL");;
			reports.writeResult(oBrowser, "Screenshot", "URL is navigated successful");
			
			//(b) Enter valid UserName and Password, click on Login button
			Assert.assertTrue(appDep.loginToActiTime(oBrowser, "OldUser", objData.get("userName"), objData.get("password")), "Failed to login to actiTime");
			reports.writeResult(oBrowser, "Screenshot", "Login to ActiTime successful");
			
			//(c) Logout from actiTime
			Assert.assertTrue(appDep.logoutFromActiTime(oBrowser), "Failed, to logout fromthe actiTime");
			reports.writeResult(oBrowser, "Screenshot", "Logout from the ActiTime successful");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'TC_LoginLogout()' testScript. " + e);
			return false;
		}finally {
			oBrowser.quit();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
	
	
	/******************************************
	 * Method Name		: TC_CreateAndDeleteUser
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean TC_CreateAndDeleteUser() {
		WebDriver oBrowser = null;
		String userName = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TC_CreateAndDeleteUser");
			objData = datatable.getDataFromExcel(moduleName, "Users", "TC_102");
			
			//(a) open browser and navigate the URL
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed toload the URL");;
			reports.writeResult(oBrowser, "Screenshot", "URL is navigated successful");
			
			//(b) Enter valid UserName and Password, click on Login button
			Assert.assertTrue(appDep.loginToActiTime(oBrowser, "OldUser", objData.get("userName"), objData.get("password")), "Failed to login tothe ActiTime");
			reports.writeResult(oBrowser, "Screenshot", "Login to ActiTime successful");
			
			//(c) create new user
			userName = userMethods.createUser(oBrowser, objData);
			
			
			//(d) delete the created user
			Assert.assertTrue(userMethods.deleteUser(oBrowser, userName), "Failed to delete the user '"+userName+"'");
			
			
			//(e) Logout from actiTime
			Assert.assertTrue(appDep.logoutFromActiTime(oBrowser), "Failed, to logout fromthe actiTime");
			reports.writeResult(oBrowser, "Screenshot", "Logout from the ActiTime successful");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'TC_CreateAndDeleteUser()' testScript. " + e);
			return false;
		}finally {
			oBrowser.quit();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
	
	
	/******************************************
	 * Method Name		: TC_CreateLoginAndDeleteUser
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean TC_CreateLoginAndDeleteUser() {
		WebDriver oBrowser = null;
		WebDriver oBrowserTwo = null;
		String userName = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TC_CreateLoginAndDeleteUser");
			objData = datatable.getDataFromExcel(moduleName, "Users", "TC_103_1");
			
			//(a) open browser and navigate the URL
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed toload the URL");;
			reports.writeResult(oBrowser, "Screenshot", "URL is navigated successful");
			
			//(b) Enter valid UserName and Password, click on Login button
			Assert.assertTrue(appDep.loginToActiTime(oBrowser, "OldUser", objData.get("userName"), objData.get("password")), "Failed to login tothe ActiTime");
			reports.writeResult(oBrowser, "Screenshot", "Login to ActiTime successful");
			
			//(c) create new user
			userName = userMethods.createUser(oBrowser, objData);
			
			
			//(d) login with newly created user
			objData = datatable.getDataFromExcel(moduleName, "Users", "TC_103_2");
			
			oBrowserTwo = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertTrue(appDep.navigateURL(oBrowserTwo, appInd.getPropData("URL")), "Failed toload the URL");;
			reports.writeResult(oBrowserTwo, "Screenshot", "URL is navigated successful");
			
			Assert.assertTrue(appDep.loginToActiTime(oBrowserTwo, "NewUser", objData.get("userName"), objData.get("password")), "Failed to login tothe ActiTime");
			reports.writeResult(oBrowserTwo, "Screenshot", "Login to ActiTime successful");
			
			//(e) delete the created user
			Assert.assertTrue(userMethods.deleteUser(oBrowser, userName), "Failed to delete the user '"+userName+"'");
			
			
			//(f) Logout from actiTime
			Assert.assertTrue(appDep.logoutFromActiTime(oBrowser), "Failed, to logout fromthe actiTime");
			reports.writeResult(oBrowser, "Screenshot", "Logout from the ActiTime successful");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'TC_CreateLoginAndDeleteUser()' testScript. " + e);
			return false;
		}finally {
			oBrowser.quit();
			oBrowser = null;
			oBrowserTwo.quit();
			oBrowserTwo = null;
			reports.endExtentReport(test);
		}
	}
}