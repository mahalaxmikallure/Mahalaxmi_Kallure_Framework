package org.sg.methods;

import org.openqa.selenium.WebDriver;
import org.sg.driver.DriverScript;
import org.testng.Assert;

import pages.LoginPage;
import pages.UsersPage;

public class AppDependentMethods extends DriverScript implements LoginPage, UsersPage{
	/******************************************
	 * Method Name		: navigateURL
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean navigateURL(WebDriver oBrowser, String strURL) {
		try {
			oBrowser.navigate().to(strURL);
			appInd.waitForElement(oBrowser, obj_Login_Btn, "Clickable", "", 10);			
			Assert.assertTrue(appInd.compareValues(oBrowser, oBrowser.getTitle(), "actiTIME - Login"), "Failed to load the URL");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in the 'navigateURL()' method. " + e);
			return false;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: loginToActiTime
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean loginToActiTime(WebDriver oBrowser, String userStatus, String strUserName, String strPassword) {
		try {
			oBrowser.findElement(obj_UserName_Edit).sendKeys(strUserName);
			oBrowser.findElement(obj_Password_Edit).sendKeys(strPassword);
			oBrowser.findElement(obj_Login_Btn).click();
			
			if(userStatus.equalsIgnoreCase("NewUser")) {
				appInd.waitForElement(oBrowser, obj_StartExporingActiTime_Btn, "Clickable", "", 10);
								
				Assert.assertTrue(appInd.verifyText(oBrowser, obj_WelcomeScreen_Label, "Text", "Welcome to actiTIME!"), "Failed to get the welcome screen message");
				
				oBrowser.findElement(obj_StartExporingActiTime_Btn).click();
				appInd.waitForElement(oBrowser, obj_SaveChanges_Btn, "Clickable", "", 10);
			}else {
				appInd.waitForElement(oBrowser, obj_SaveChanges_Btn, "Clickable", "", 10);
			}
			
			
			Assert.assertTrue(appInd.verifyText(oBrowser, obj_TimeTrack_Label, "Text", "Enter Time-Track"), "Failed to login to the actiTime");
			//Handle the shortcut window
			if(oBrowser.findElements(obj_ShortCut_Window).size() > 0) {
				oBrowser.findElement(obj_ShortCut_Close_Btn).click();
			}
			reports.writeResult(oBrowser, "Pass", "Failed to login tothe ActiTime application");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in the 'loginToActiTime()' method. " + e);
			return false;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: logoutFromActiTime
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean logoutFromActiTime(WebDriver oBrowser) {
		try {
			oBrowser.findElement(obj_Logout_Btn).click();
			appInd.waitForElement(oBrowser, obj_LoginHeader_Label, "Text", "Please identify yourself", 10);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, obj_LoginLog_Img), "Failed to logout from the actiTime");
			reports.writeResult(oBrowser, "Pass", "Logout from actiTime was successful");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in the 'logoutFromActiTime()' method. " + e);
			return false;
		}
	}
}
