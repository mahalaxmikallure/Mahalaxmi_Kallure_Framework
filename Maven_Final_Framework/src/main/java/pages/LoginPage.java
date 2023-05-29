package pages;

import org.openqa.selenium.By;

public interface LoginPage {
	public By obj_Login_Btn = By.xpath("//a[@id='loginButton']");
	public By obj_UserName_Edit = By.id("username");
	public By obj_Password_Edit = By.name("pwd");
	public By obj_StartExporingActiTime_Btn = By.xpath("//span[text()='Start exploring actiTIME']");
	public By obj_WelcomeScreen_Label = By.xpath("//div[@class='firstPart']");
	public By obj_SaveChanges_Btn = By.xpath("//input[@id='SubmitTTButton']");
	public By obj_TimeTrack_Label = By.xpath("//td[@class='pagetitle']");
	public By obj_ShortCut_Window = By.xpath("//div[@id='gettingStartedShortcutsMenuWrapper']");
	public By obj_ShortCut_Close_Btn = By.id("gettingStartedShortcutsMenuCloseId");
	public By obj_LoginHeader_Label = By.id("headerContainer");
	public By obj_LoginLog_Img = By.xpath("//img[contains(@src, 'timer.png')]");
	

}
