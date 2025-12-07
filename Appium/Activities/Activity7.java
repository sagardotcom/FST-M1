package activities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

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

public class Activity7 {
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
		driver.get("https://training-support.net/webelements/lazy-loading");
	}
	
	@Test
	public void uiScrollableTest() {
		// Wait for page to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.className("android.widget.Image")));
		// UiScrollable object string
		String UiScrollable = "UiScrollable(UiSelector().scrollable(true))";
 
		// Find all the image elements on the page
		List<WebElement> imageElements = driver.findElements(AppiumBy.className("android.widget.Image"));
		// Print the number of images
		System.out.println("Before scroll: " + imageElements.size());
 
		// Scroll to required element
		String imageText = driver.findElement(AppiumBy.androidUIAutomator(UiScrollable + ".scrollForward(25).getChildByText(className(\"android.widget.Image\"), \"Helen\")")).getText();
		System.out.println("Found " + imageText + "!");
 
		// Get image elements after scroll
		imageElements = driver.findElements(AppiumBy.className("android.widget.Image"));
		// Print the number of images after scroll
		System.out.println("After scroll: " + imageElements.size());
 
		// Assertions
		Assert.assertEquals(imageElements.size(), 3);
	}
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
 
}
