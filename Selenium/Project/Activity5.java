package hrmProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Activity5 {
	//Driver Object
	WebDriver driver;
	WebDriverWait wait;
	
	//Setup function
	@BeforeClass
	public void setUp() {
		//Initialize the Driver
		driver= new FirefoxDriver();
		//Initialize The wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		//Open the Test Page
		driver.get("http://alchemy.hguy.co/orangehrm");
		
	}
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		//get the Username and password fields
		WebElement nameField = driver.findElement(By.id("txtUsername"));
		WebElement passwordField = driver.findElement(By.id("txtPassword"));
		WebElement loginButton = driver.findElement(By.id("btnLogin"));
		//Send values
		nameField.sendKeys("orange");
		passwordField.sendKeys("orangepassword123");
		//Assertion
		System.out.println("Name Sent is ====>"+nameField.getAttribute("value"));
		System.out.println("Password Sent is =====>"+passwordField.getAttribute("value"));
		//Click login button
		loginButton.click();
		
	}
	
	@Test(priority = 2)
	public void verifyDashboard(){
		WebElement dashBoard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='head']/h1"))) ;
		Assert.assertEquals("Dashboard", dashBoard.getText());
	}
	
	@Test(priority = 3)
	public void editUserDetails() throws InterruptedException {
		driver.findElement(By.id("menu_pim_viewMyDetails")).click();
		driver.findElement(By.id("btnSave")).click();
		WebElement name= driver.findElement(By.id("personal_txtEmpFirstName"));
		name.clear();
		name.sendKeys("Harry");
		WebElement lastName = driver.findElement(By.id("personal_txtEmpLastName"));
		lastName.clear();
		lastName.sendKeys("Potter");
		WebElement select = driver.findElement(By.id("personal_cmbNation"));
		//create Select object
		Select dropdown = new Select(select);
		dropdown.selectByIndex(4);
		System.out.println("The Option using index is: "+ dropdown.getFirstSelectedOption().getText() );
		
		WebElement male = driver.findElement(By.id("personal_optGender_1"));
		WebElement female = driver.findElement(By.id("personal_optGender_2"));
		
		if(male.isSelected()) {
			female.click();
		}else {
			male.click();
		}
		WebElement dob =  driver.findElement(By.id("personal_DOB"));
		dob.clear();
		dob.sendKeys("2022-02-23");
		driver.findElement(By.id("menu_pim_viewMyDetails")).click();
		
	}
	
	//Teardown Function
	@AfterClass
	public void tearDown() {
		
		//close the Browser
		driver.quit();
	}
}
