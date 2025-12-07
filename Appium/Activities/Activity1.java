package activities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Activity1 {
	// Declaring the required objects
	AppiumDriver driver; // Parent for AndroidDriver and IOSDriver

	@BeforeClass
	public void setUp() throws MalformedURLException, URISyntaxException {
		File apkFile = new File("src/test/resources/Calculator.apk");
		// Desired Capabilities
		UiAutomator2Options caps = new UiAutomator2Options();
		caps.setPlatformName("android");
		caps.setAutomationName("UiAutomator2");
		caps.setApp(apkFile.getAbsolutePath());
		caps.noReset(); // Prevents from factory reset

		// Appium Server URL
		URL serverURL = new URI("http://localhost:4723").toURL();

		// Initialize the driver object
		driver = new AndroidDriver(serverURL, caps);
	}
	
	@Test
	public void multiplyTest() {
		// Find digit 5 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_5")).click();
		// Find multiply symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("multiply")).click();
		// Find digit 9 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_9")).click();
		// Find equals symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("equals")).click();
		
		// Verify the result text
		String result = driver.findElement(AppiumBy.id("com.google.android.calculator:id/result_final")).getText();
		Assert.assertEquals(result, "45");
	}
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}

}
