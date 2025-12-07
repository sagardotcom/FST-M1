package activities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

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
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;

public class Activity5 {
	// Declaring the required objects
	AppiumDriver driver; // Parent for AndroidDriver and IOSDriver
	WebDriverWait wait;

	@BeforeClass
	public void setUp() throws MalformedURLException, URISyntaxException {
		// Desired Capabilities
		UiAutomator2Options caps = new UiAutomator2Options();
		caps.setPlatformName("android");
		caps.setAutomationName("UiAutomator2");
		caps.setAppPackage("com.google.android.apps.messaging");
		caps.setAppActivity(".ui.ConversationListActivity");
		caps.noReset(); // Prevents from factory reset

		// Appium Server URL
		URL serverURL = new URI("http://localhost:4723").toURL();

		// Initialize the driver object
		driver = new AndroidDriver(serverURL, caps);

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Test
	public void smsTest() {
		// Find and click the add button
        driver.findElement(AppiumBy.accessibilityId("Start chat")).click();
 
        // Wait for elements to load
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("ContactSearchField")));
 
        // Find the element to add recipient
        driver.findElement(AppiumBy.id("ContactSearchField")).sendKeys("18282832912");
        // Press ENTER
        ((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
 
        // Wait for textbox to appear
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("compose_message_text")));
 
        // Enter text to send
        driver.findElement(AppiumBy.id("compose_message_text")).sendKeys("Hello from Appium");
        // Press Send
        driver.findElement(AppiumBy.accessibilityId("Send SMS")).click();
 
        // Assertion
        String messageTextSent = driver.findElement(AppiumBy.id("message_text")).getText();
        Assert.assertEquals(messageTextSent, "Hello from Appium");

	}

	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}
