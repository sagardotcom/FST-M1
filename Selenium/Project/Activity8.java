package hrmProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Activity8 {
	//Driver Object
	WebDriver driver;
	WebDriverWait wait;
	String comment;
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
	public void applyLeave() throws InterruptedException {
		driver.findElement(By.id("menu_dashboard_index")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/div[1]/div/div/div/fieldset/div/div/table/tbody/tr/td[4]/div/a/img")).click();
		WebElement select = driver.findElement(By.id("applyleave_txtLeaveType"));
		//create Select object
		Select dropdown = new Select(select);
		dropdown.selectByIndex(1);
		
		
		WebElement fromDate = driver.findElement(By.id("applyleave_txtFromDate"));
		fromDate.clear();
		fromDate.sendKeys("2025-10-31");
		
		WebElement toDate = driver.findElement(By.id("applyleave_txtToDate"));
		toDate.clear();
		toDate.sendKeys("2025-10-31");
		
		WebElement commentText = driver.findElement(By.id("applyleave_txtComment"));
		
		commentText.sendKeys("This is a sample leave1");
		comment=commentText.getAttribute("value");
		
		
		driver.findElement(By.xpath("//input[@id='applyBtn']")).click();

	}
	
	@Test(priority = 4)
		public void checkStatus() throws InterruptedException{
		Thread.sleep(1000);
		driver.findElement(By.id("menu_leave_viewMyLeaveList")).click();
		WebElement allSelected = driver.findElement(By.id("leaveList_chkSearchFilter_checkboxgroup_allcheck"));

		if(allSelected.isSelected()) {
			allSelected.click();
		}
		
		driver.findElement(By.id("leaveList_chkSearchFilter_1")).click();
		
		driver.findElement(By.id("btnSearch")).click();
		
		
		WebElement currentCell = driver.findElement(By.xpath("//span[contains(text(), '"+comment+"')]/ancestor::td"));
		System.out.println(currentCell.getText());
		
		

//		// This XPath selects the immediately preceding 'td' sibling of the current cell
//        WebElement previousCell = currentCell.findElement(By.xpath("./preceding-sibling::td[6]"));
//        System.out.println("======================================>");
//        System.out.println(previousCell.getText());
		//above code is not working as expected
		//By is a class, relativeLocater is a class
        By emailLocator = RelativeLocator.with(By.tagName("td")).toLeftOf(By.xpath("//span[contains(text(), '"+comment+"')]"));
		String statusText= driver.findElement(emailLocator).getText();
        System.out.println(statusText);
        
		WebElement status= driver.findElement(By.cssSelector("td.left:nth-child(6) > a:nth-child(1)"));
		Assert.assertEquals(status.getText(), "Pending Approval(1.00)");
	}
	
	@Test(priority = 5)
	public void cancelPendingLeaves() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("menu_leave_viewMyLeaveList")).click();
		WebElement allSelected = driver.findElement(By.id("leaveList_chkSearchFilter_checkboxgroup_allcheck"));

		if(allSelected.isSelected()) {
			allSelected.click();
		}
		Thread.sleep(1000);
		driver.findElement(By.id("leaveList_chkSearchFilter_1")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("btnSearch")).click();
		
		WebElement select = driver.findElement(By.id("select_leave_action_264"));
		//create Select object
		Select dropdown = new Select(select);
		dropdown.selectByIndex(1);
		driver.findElement(By.id("btnSave")).click();
	}
	
	//Teardown Function
	@AfterClass
	public void tearDown() {
		
		//close the Browser
		driver.quit();
	}
}
