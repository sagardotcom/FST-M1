package project;

import java.io.File;
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

public class Activity1 {
	// Declaring the required objects
	AppiumDriver driver; // Parent for AndroidDriver and IOSDriver
	WebDriverWait wait;

	@BeforeClass
	public void setUp() throws MalformedURLException, URISyntaxException {
		File apkFile = new File("src/test/resources/ts-todo-list-v1.apk");
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
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}
	
	@Test
	public void createTask() {
		WebElement addTask = driver.findElement(AppiumBy.id("com.app.todolist:id/fab_new_task"));
		WebElement taskName = driver.findElement(AppiumBy.id("com.app.todolist:id/et_new_task_name"));
		WebElement saveTask = driver.findElement(AppiumBy.id("com.app.todolist:id/bt_new_task_ok"));
		// Add task 1
		addTask.click();
		taskName.sendKeys("Complete Activity 1 with priority high");
		saveTask.click();
		// Add task 2
		addTask.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.app.todolist:id/et_new_task_name")));
		taskName.sendKeys("Complete Activity 2 with priority medium");
		saveTask.click();
		// Add task 3
		addTask.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.app.todolist:id/et_new_task_name")));
		taskName.sendKeys("Complete Activity 3 with priority low");
		saveTask.click();
		
		// Assert task name
		List<WebElement> tasks = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.app.todolist:id/tv_exlv_task_name'"));
		Assert.assertEquals(tasks.get(0).getText(), "Complete Activity 1 with priority high");
		Assert.assertEquals(tasks.get(1).getText(), "Complete Activity 2 with priority medium");
		Assert.assertEquals(tasks.get(2).getText(), "Complete Activity 3 with priority low");
	}
	
	
	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}
