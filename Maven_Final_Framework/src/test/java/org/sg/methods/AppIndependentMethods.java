package org.sg.methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sg.driver.DriverScript;
import org.testng.Assert;

public class AppIndependentMethods extends DriverScript{
	/******************************************
	 * Method Name		: getPropData
	 * 
	 * 
	 * 
	 ******************************************/
	public String getPropData(String keyName) {
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\Configuration\\configuration.properties");
			prop = new Properties();
			prop.load(fin);
			return prop.getProperty(keyName);
		}catch(Exception e) {
			System.out.println("Exception in the 'getPropData()' method " + e);
			return null;
		}finally
		{
			try {
				fin.close();
				fin = null;
				prop = null;
			}catch(Exception e) {}
		}
	}
	
	
	
	/******************************************
	 * Method Name		: getDateTime
	 * 
	 * 
	 * 
	 ******************************************/
	public String getDateTime(String format) {
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(format);
			return sdf.format(dt);
		}catch(Exception e) {
			System.out.println("Exception in the 'getDateTime()' method " + e);
			return null;
		}finally {
			dt = null;
			sdf = null;
		}
	}
	
	
	/******************************************
	 * Method Name		: getDateTime
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean waitForElement(WebDriver oBrowser, By objBy, String waitReason, String expectedText, int timeOut) {
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(timeOut));
			switch(waitReason.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, expectedText));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(objBy));
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid wait reason '"+waitReason+"' was specified");
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in the 'waitForElement()' method. " + e);
			return false;
		}finally {
			oWait = null;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: launchBrowser
	 * 
	 * 
	 * 
	 ******************************************/
	public WebDriver launchBrowser(String browserName) {
		WebDriver oDriver = null;
		ChromeOptions options = null;
		try {
			switch(browserName.toLowerCase()) {
				case "chrome":
					options = new ChromeOptions();
					options.addArguments("--remote-allow-origins=*");
					System.setProperty("webdriver.chrome.driver", ".\\Library\\drivers\\chromedriver.exe");
					oDriver = new ChromeDriver(options);
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", ".\\Library\\drivers\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				case "edge":
					System.setProperty("webdriver.edge.driver", ".\\Library\\drivers\\msedgedriver.exe");
					oDriver = new EdgeDriver();
					break;
				default:
					reports.writeResult(null, "Fail", "Invalid browser name '"+browserName+"' was specified");
			}
			
			if(oDriver!=null) {
				oDriver.manage().window().maximize();
				reports.writeResult(oDriver, "Pass", "The '"+browserName+"' browser has opened successful");
				return oDriver;
			}else {
				reports.writeResult(null, "Fail", "Failed to launch the '"+browserName+"' browser");
				return null;
			}
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in the 'launchBrowser()' method. " + e);
			return null;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: verifyElementPresent
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyElementPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				reports.writeResult(oBrowser, "Pass", "The Element '"+String.valueOf(objBy)+"' present in the DOM");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to find the Element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in the 'verifyElementPresent()' method. " + e);
			return false;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: verifyElementNotPresent
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyElementNotPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				reports.writeResult(oBrowser, "Fail", "The Element '"+String.valueOf(objBy)+"' still present in the DOM");
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The Element '"+String.valueOf(objBy)+"' was removed from the DOM");
				return true;
			}
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in the 'verifyElementNotPresent()' method. " + e);
			return false;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: verifyText
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyText(WebDriver oBrowser, By objBy, String strObjectType, String expectedText) {
		List<WebElement> oEles = null;
		Select oSelect = null;
		String actualText = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				switch(strObjectType.toLowerCase()) {
					case "text":
						actualText = oEles.get(0).getText();
						break;
					case "value":
						actualText = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSelect = new Select(oEles.get(0));
						actualText = oSelect.getFirstSelectedOption().getText();
						break;
				}
				
				Assert.assertTrue(appInd.compareValues(oBrowser, actualText, expectedText), "Comparision failed");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The Element '"+String.valueOf(objBy)+"' not present in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in the 'verifyText()' method. " + e);
			return false;
		}
	}
	
	
	
	/******************************************
	 * Method Name		: compareValues
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean compareValues(WebDriver oBrowser, String actualText, String expectedText) {
		try {
			if(actualText.equalsIgnoreCase(expectedText)) {
				reports.writeResult(oBrowser, "Pass", "The Actual '"+actualText+"' & Expected '"+expectedText+"' values are matched successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "Mis-match in the Actual '"+actualText+"' & Expected '"+expectedText+"' values");
				return false;
			}
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in the 'compareValues()' method. " + e);
			return false;
		}
	}
}