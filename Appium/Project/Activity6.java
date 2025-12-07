package project;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Activity6 {
	AppiumDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void setUp() throws MalformedURLException, URISyntaxException {
		// Desired Capabilities
		UiAutomator2Options options = new UiAutomator2Options();
		options.setPlatformName("Android");
		options.setAutomationName("UiAutomator2");
		options.setAppPackage("com.android.chrome");
		options.setAppActivity("com.google.android.apps.chrome.Main");
		options.noReset();

		// Server URL
		URL serverURL = new URI("http://localhost:4723").toURL();

		// Driver initialization
		driver = new AndroidDriver(serverURL, options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// Open page
		driver.get("https://training-support.net/webelements");
	}

	@Test
	public void popupTest() {
		// Wait for page to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.className("android.widget.TextView")));
		// UiScrollable object string
		String UiScrollable = "UiScrollable(UiSelector().scrollable(true))";
		// Scroll to find the Popups card and click it
		WebElement element = driver.findElement(AppiumBy.androidUIAutomator(UiScrollable + ".scrollForward(25).getChildByText(className(\"android.widget.TextView\"), \"Popups\")"));
        element.click();
        
        // Find the button on the page to open a popup with the login form
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.className("android.widget.TextView")));
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id='launcher']")).click();
        
        // Enter username and password
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.className("android.widget.EditText")));
        driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id='username']")).sendKeys("admin");
        driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id='password']")).sendKeys("password");
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@text='Submit']")).click();
        String message = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Login Success!']")).getText();
        // Assert message
        Assert.assertEquals(message, "Login Success!");
        
        }
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}


}
