package project;

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

public class Activity4 {
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
	public void todoListTest() {
		// Wait for page to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.className("android.widget.TextView")));
		// UiScrollable object string
		String UiScrollable = "UiScrollable(UiSelector().scrollable(true))";
		// Scroll to find the To-Do List card and click it
		WebElement element = driver.findElement(AppiumBy.androidUIAutomator(UiScrollable + ".scrollForward(25).getChildByText(className(\"android.widget.TextView\"), \"To-Do List\")"));
        element.click();

        // Find the input field on the page and enter three tasks
		driver.findElement(AppiumBy.id("todo-input")).sendKeys("Add tasks to list");
		driver.findElement(AppiumBy.id("todo-add")).click();
		driver.findElement(AppiumBy.id("todo-input")).sendKeys("Get number of tasks");
		driver.findElement(AppiumBy.id("todo-add")).click();
		driver.findElement(AppiumBy.id("todo-input")).sendKeys("Clear the list");
		driver.findElement(AppiumBy.id("todo-add")).click();
		
		// Click on each of the tasks added to strike them out
		List<WebElement> checkboxes = driver.findElements(AppiumBy.className("android.widget.CheckBox"));
		for(int i=2; i< checkboxes.size(); i++) {
			checkboxes.get(i).click();
		}
		
		// Add assertions to verify the count of the number of tasks in the list
		// Hint: There are 2 tasks already present in the list.
		Assert.assertEquals(3, checkboxes.size()-2);
	}
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}
