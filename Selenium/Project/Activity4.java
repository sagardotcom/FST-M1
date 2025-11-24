package hrmProject;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Activity4 {
	//Driver Object
	WebDriver driver;
	WebDriverWait wait;
	String empId;
	By pimTabLocator = By.id("menu_pim_viewPimModule");
	String firstName;
	String lastName;
	
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
	public void login() {
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
		//Verify if the hompage is visible
		WebElement dashBoard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='head']/h1"))) ;
		Assert.assertEquals("Dashboard", dashBoard.getText());
	}
	
	@Test(priority = 3)
	public void addEmployee() {
		//Find the PIM TAB and Click it
		driver.findElement(pimTabLocator).click();
		//find and click the add button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAdd"))).click();
		//Fill the first name and last name and click save
		WebElement firstNameField = driver.findElement(By.id("firstName"));
		WebElement lastNameField =  driver.findElement(By.id("lastName"));
		firstNameField.sendKeys("Harry");
		lastNameField.sendKeys("Potter");
		firstName = firstNameField.getAttribute("value");
		lastName = lastNameField.getAttribute("value");
		empId = driver.findElement(By.id("employeeId")).getAttribute("value");
		System.out.println("The First Name of the Employee is =====>"+firstName);
		System.out.println("The Last Name of the Employee is =====>"+lastName);
		System.out.println("The Employee Id is =====> "+ empId);
		driver.findElement(By.id("btnSave")).click();
	}
	
	@Test(priority = 4)
	public void searchEmployee() {
		//Navigate to the PIM Tab
		driver.findElement(pimTabLocator).click();
		//System.out.println("Clicking Pim button now =======>");
		//Enter the Search Parameters
		WebElement empSearchId = driver.findElement(By.id("empsearch_id"));
		empSearchId.sendKeys(empId);
		//System.out.println("sent EMP id now =======>");
		//Click the Search Button
		driver.findElement(By.id("searchBtn")).click();
		//System.out.println("Clicking search button now =======>");
		//Verify the first name and last name
		WebElement fName = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/form/div[4]/table/tbody/tr/td[3]/a"));
		WebElement lName = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/form/div[4]/table/tbody/tr/td[4]/a"));
		//Assert the Names
		Assert.assertEquals(fName.getText(),firstName);
		Assert.assertEquals(lName.getText(), lastName);
		
	}
	
	//Teardown Function
	@AfterClass
	public void tearDown() {
		
		//close the Browser
		driver.quit();
	}
}
