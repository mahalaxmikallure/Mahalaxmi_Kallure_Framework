package pages;

import org.openqa.selenium.By;

public interface UsersPage {
	public By obj_USERS_Menu = By.xpath("//div[text()='USERS']");
	public By obj_AddUser_Btn = By.xpath("//div[text()='Add User']");
	public By obj_CreateUser_Btn = By.xpath("//span[text()='Create User']");
	public By obj_User_FirstName_Edit = By.xpath("//input[@name='firstName']");
	public By obj_User_LastName_Edit = By.xpath("//input[@name='lastName']");
	public By obj_User_Email_Edit = By.xpath("//input[@name='email']");
	public By obj_User_UserName_Edit = By.xpath("//input[@name='username']");
	public By obj_User_Password_Edit = By.xpath("//input[@name='password']");
	public By obj_User_PasswordCopy_Edit = By.xpath("//input[@name='passwordCopy']");
	public By obj_DeleteUser_Btn = By.xpath("//button[contains(text(), 'Delete User')]");
	public By obj_Logout_Btn = By.xpath("//a[@id='logoutLink']");

}
