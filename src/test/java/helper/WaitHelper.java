package helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.TimeoutException;

public class WaitHelper {

	
	private WebDriver driver;
	
	public WaitHelper(WebDriver driver){
		this.driver = driver;
	}
	
	public boolean waitForElement(WebElement element, long timeOutInSeconds){
		try {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		return true;
		}
		catch(TimeoutException e){
			return false;
		}
		 
	}
	public boolean waitForURL(long timeOutInSeconds){
		try {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.urlContains("result"));
		return true;
		}
		catch(TimeoutException e){
			return false;
		}
		 
	}
}
