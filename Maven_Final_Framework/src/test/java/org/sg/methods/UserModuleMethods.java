package org.sg.methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.sg.driver.DriverScript;
import pages.UsersPage;

public class UserModuleMethods extends DriverScript implements UsersPage{
	public String createUser(WebDriver oBrowser, Map<String, String> objData) {
		String userName = null;
		try {
			oBrowser.findElement(obj_USERS_Menu).click();
			appInd.waitForElement(oBrowser, obj_AddUser_Btn, "Clickable", "", 10);
			oBrowser.findElement(obj_AddUser_Btn).click();
			appInd.waitForElement(oBrowser, obj_CreateUser_Btn, "Clickable", "", 10);
			
			oBrowser.findElement(obj_User_FirstName_Edit).sendKeys(objData.get("User_FirstName"));
			oBrowser.findElement(obj_User_LastName_Edit).sendKeys(objData.get("User_LastName"));
			oBrowser.findElement(obj_User_Email_Edit).sendKeys(objData.get("User_Email"));
			oBrowser.findElement(obj_User_UserName_Edit).sendKeys(objData.get("User_UserName"));
			oBrowser.findElement(obj_User_Password_Edit).sendKeys(objData.get("User_Password"));
			oBrowser.findElement(obj_User_PasswordCopy_Edit).sendKeys(objData.get("User_RetypePassword"));
			oBrowser.findElement(obj_CreateUser_Btn).click();
			
			userName = objData.get("User_LastName") + ", " + objData.get("User_FirstName");
			appInd.waitForElement(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"), "Text", userName, 10);			
			
			if(oBrowser.findElements(By.xpath("//div[@class='name']/span[text()='"+userName+"']")).size() > 0) {
				reports.writeResult(oBrowser, "Pass", "The new user created successful");
				return userName;
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to create the new user");
				return null;
			}
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in the 'createUser()' method. " + e);
			return null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: deleteUser()
	 * purpose			: it is to delete the user in the actiTime application
	 * Author			: SG Tester
	 * Date created 	: 15th-May-2023
	 * Date Modified	:
	 * Reviewer Name	: SG Admin
	 * Arguments		: oBrowser, userName
	 * return Type		: boolean
	 * Example			: boolean blnRes = deleteUser(oBrowser, userName);
	 *******************************************/
	public boolean deleteUser(WebDriver oBrowser, String userName) {
		try {
			appInd.waitForElement(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"), "Clickable", "", 10);
			oBrowser.findElement(By.xpath("//div[@class='name']/span[text()='"+userName+"']")).click();
			appInd.waitForElement(oBrowser, obj_DeleteUser_Btn, "Clickable", "", 10);
			
			appInd.waitForElement(oBrowser, obj_DeleteUser_Btn, "Clickable", "", 10);
			oBrowser.findElement(obj_DeleteUser_Btn).click();
			Thread.sleep(2000);
			oBrowser.switchTo().alert().accept();
			Thread.sleep(2000);
			
			appInd.waitForElement(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"), "Invisibility", "", 10);
			
			if(oBrowser.findElements(By.xpath("//div[@class='name']/span[text()='"+userName+"']")).size() > 0) {
				reports.writeResult(oBrowser, "Fail", "Failed to delete the user '"+userName+"'");
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The user '"+userName+"' is deleted successful");
				return true;
			}
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in the 'deleteUser()' method. " + e);
			return false;
		}
	}
}
