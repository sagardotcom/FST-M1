package examples;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class FirstTest {
	// Declaring the required objects
	AppiumDriver driver; // Parent for AndroidDriver and IOSDriver
	WebDriverWait wait;
	
	@BeforeClass
	public void setUp() throws MalformedURLException, URISyntaxException {
		// Desired Capabilities
		UiAutomator2Options caps = new UiAutomator2Options();
		caps.setPlatformName("android");
		caps.setAutomationName("UiAutomator2");
		caps.setApp("path/to/calculator.apk");
		caps.noReset(); // Prevents from factory reset
		
		// Appium Server URL
		URL serverURL = new URI("http://localhost:4723").toURL();
		
		// Initialize the driver object
		driver = new AndroidDriver(serverURL, caps);		
	}
	
	@BeforeClass
	public void iosSetUp() throws MalformedURLException, URISyntaxException {
		// Desired Capabilities
		XCUITestOptions caps = new XCUITestOptions();
		caps.setPlatformName("ios");
		caps.setAutomationName("XCUITest");
		caps.setApp("path/to/calculator.ipa");
		caps.noReset();
		
		// Appium Server URL
		URL serverURL = new URI("http://localhost:4723").toURL();
		
		// Initialize the driver object
		driver = new IOSDriver(serverURL, caps);
	}
	
	@Test
	public void testMethod() {
		// Test steps
	}
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}

