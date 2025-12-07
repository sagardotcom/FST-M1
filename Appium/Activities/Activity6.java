package activities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
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
		driver.get("https://training-support.net/webelements/sliders");
	}
	
	
	@Test
	public void volume75Test() {
		// Wait for page to load
		wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.SeekBar")));
		
		// Get the size of the device
		Dimension dims = driver.manage().window().getSize();
		// Set the start and end points
		Point start = new Point((int) (0.5*dims.getWidth()), (int) (0.77*dims.getHeight()));
		Point end = new Point((int) (0.67*dims.getWidth()), (int) (0.77*dims.getHeight()));
		// Perform swipe
		ActionBase.doSwipe(driver, start, end, 2000);
 
		// Find the percentage text and assert
		String volumeText = driver
			.findElement(AppiumBy.xpath("//android.view.View/android.widget.TextView[contains(@text, '%')]"))
			.getText();
 
		// Assertions
		Assert.assertEquals(volumeText, "75%");
	}
 
	@Test
	public void volume25Test() {
		// Wait for page to load
		wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.SeekBar")));
		
		// Get the size of the screen
		Dimension dims = driver.manage().window().getSize();
		// Set the start and end points
		Point start = new Point((int) (0.5*dims.getWidth()), (int) (0.77*dims.getHeight()));
		Point end = new Point((int) (0.34*dims.getWidth()), (int) (0.77*dims.getHeight()));
		// Perform swipe
		ActionBase.doSwipe(driver, start, end, 2000);
 
		// Get the volume level
		String volumeText = driver
			.findElement(AppiumBy.xpath("//android.view.View/android.widget.TextView[contains(@text, '%')]"))
			.getText();
 
		// Assertions
		Assert.assertEquals(volumeText, "25%");
	}
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}

}
