package hrmProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Activity2 {
	//Driver Object
	WebDriver driver;
	WebDriverWait wait;
	
	//Setup function
	@BeforeClass
	public void setUp() {
		//Initialize the Driver
		driver= new FirefoxDriver();
		//Initialize The wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//Open the Test Page
		driver.get("http://alchemy.hguy.co/orangehrm");
		
	}
	
	@Test(priority = 1)
	public void printImageUrl() {
		//get the image URL
		WebElement logo = driver.findElement(By.xpath("//div[@id = 'divLogo']/img"));
		System.out.println("This is the image url===============>");
//		String imageUrl= logo.getAttribute("src");
//		System.out.println(imageUrl);
		System.out.println(logo.getAttribute("src"));
	}
	
	//Teardown Function
	@AfterClass
	public void tearDown() {
		
		//close the Browser
		driver.quit();
	}
}
