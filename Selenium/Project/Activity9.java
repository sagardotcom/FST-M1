package hrmProject;

import java.time.Duration;
import java.util.List;

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

public class Activity9 {
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
	public void getEmergencyDetails() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("menu_pim_viewMyDetails")).click();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("#sidenav > li:nth-child(3) > a:nth-child(1)")).click();
		Thread.sleep(1000);
		
		 // Locate the table element
        WebElement table = driver.findElement(By.id("emgcontact_list")); 

        // Get all table rows
        List<WebElement> rows = table.findElements(By.xpath("//table[@id='emgcontact_list']//tr"));
		
        for(WebElement row : rows) {
        	System.out.println(row.getText());
        }
		
             
        // other way
        int rowCount = driver.findElements(By.xpath("//table[@id='emgcontact_list']//tr")).size();
        int columnCount = driver.findElements(By.xpath("//table[@id='emgcontact_list']//thead//th")).size();
        
//        System.out.println(rowCount);
//        System.out.println("coumn count: "+ columnCount);
        String colFirst = "//table[@id='emgcontact_list']//tr[";
        String colLast= "]//td";
        String fullCol;
        System.out.println("2nd Method =======================>");
        for(int i=1; i<rowCount; i++) {
        	fullCol = colFirst+i+colLast;
        	List<WebElement> cols = driver.findElements(By.xpath(fullCol));
           	for(int j=1;j<columnCount ;j++) {
        		System.out.print(cols.get(j).getText()+"\t\t");
        		
        	}
           	System.out.println();
        }

		
	}
	
	//Teardown Function
	@AfterClass
	public void tearDown() {
		
		//close the Browser
		driver.quit();
	}
}
