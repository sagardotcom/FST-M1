package project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Activity2 {
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
	public void editTask() {
		// Find the element to long press
		WebElement firstTask = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@resource-id='com.app.todolist:id/tv_exlv_task_name')[1]"));

		// Create finger input
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence longPress = new Sequence(finger, 1);
		// Move finger to element
		longPress.addAction(finger.createPointerMove(Duration.ZERO, Origin.fromElement(firstTask), 0, 0));
		// Finger down (press)
		longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		// Hold for 2 seconds
		longPress.addAction(new org.openqa.selenium.interactions.Pause(finger,Duration.ofSeconds(2)));
		// Finger up (release)
		longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		// Perform the sequence
		driver.perform(Arrays.asList(longPress));
		
		// Click edit task
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='android:id/title' and @text='Edit To-Do Task']"));
		// Set deadline for the task
		driver.findElement(AppiumBy.id("com.app.todolist:id/tv_todo_list_deadline")).click();
		driver.findElement(AppiumBy.accessibilityId("22 November 2025")).click();
		driver.findElement(AppiumBy.id("com.app.todolist:id/bt_deadline_ok")).click();
		driver.findElement(AppiumBy.id("com.app.todolist:id/bt_new_task_ok")).click();
		
		// Assertions
		String deadline = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.app.todolist:id/tv_exlv_task_deadline' and @text='Deadline: 22.11.2025']")).getText();
		Assert.assertEquals(deadline, "Deadline: 22.11.2025");

	}

	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}
