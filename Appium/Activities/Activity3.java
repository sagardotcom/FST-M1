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

public class Activity3 {
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
	public void addTest() {
		// Find digit 5 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_5")).click();
		// Find add symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("plus")).click();
		// Find digit 9 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_9")).click();
		// Find equals symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("equals")).click();

		// Verify the result text
		String result = driver.findElement(AppiumBy.id("com.google.android.calculator:id/result_final")).getText();
		Assert.assertEquals(result, "14");
	}
	
	@Test
	public void subtractTest() {
		// Find digit 10 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_1")).click();
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_0")).click();
		// Find minus symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("minus")).click();
		// Find digit 5 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_5")).click();
		// Find equals symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("equals")).click();

		// Verify the result text
		String result = driver.findElement(AppiumBy.id("com.google.android.calculator:id/result_final")).getText();
		Assert.assertEquals(result, "5");
	}
	
	@Test
	public void multiplyTest() {
		// Find digit 5 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_5")).click();
		// Find multiply symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("multiply")).click();
		// Find digit 100 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_1")).click();
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_0")).click();
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_0")).click();
		// Find equals symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("equals")).click();

		// Verify the result text
		String result = driver.findElement(AppiumBy.id("com.google.android.calculator:id/result_final")).getText();
		Assert.assertEquals(result, "500");
	}
	@Test
	public void divisionTest() {
		// Find digit 50 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_5")).click();
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_0")).click();
		// Find division symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("divide")).click();
		// Find digit 2 and tap it
		driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_2")).click();
		// Find equals symbol and tap it
		driver.findElement(AppiumBy.accessibilityId("equals")).click();

		// Verify the result text
		String result = driver.findElement(AppiumBy.id("com.google.android.calculator:id/result_final")).getText();
		Assert.assertEquals(result, "25");
	}

	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}
