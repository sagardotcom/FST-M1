package project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import activities.ActionBase;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Activity3 {
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
	public void completeTask() {
		// Mark the first two tasks as complete
		driver.findElement(AppiumBy.xpath("(//android.widget.CheckBox[@resource-id='com.app.todolist:id/cb_task_done'])[1]")).click();
		driver.findElement(AppiumBy.xpath("(//android.widget.CheckBox[@resource-id='com.app.todolist:id/cb_task_done'])[2]")).click();
		
		// Long press the third task to edit it
		// Find the element to long press
		WebElement thirdTask = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@resource-id='com.app.todolist:id/tv_exlv_task_name')[3]"));

		// Create finger input
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence longPress = new Sequence(finger, 1);
		// Move finger to element
		longPress.addAction(finger.createPointerMove(Duration.ZERO, Origin.fromElement(thirdTask), 0, 0));
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
				
		// Slide the progress bar to 50% and save the details
		// Get the size of the device
		Dimension dims = driver.manage().window().getSize();
		// Set the start and end points
		Point start = new Point((int) (0.5*dims.getWidth()), (int) (0.77*dims.getHeight()));
		Point end = new Point((int) (0.61*dims.getWidth()), (int) (0.55*dims.getHeight()));
		// Perform swipe
		ActionBase.doSwipe(driver, start, end, 2000);
		// Save
		driver.findElement(AppiumBy.id("com.app.todolist:id/bt_new_task_ok")).click();
		
		
		// Click the option icon and select “Completed tasks”
		driver.findElement(AppiumBy.accessibilityId("More options")).click();
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.app.todolist:id/title' and @text='Completed tasks']")).click();
		
		// Make an assertion to ensure that only 2 tasks as shown
		List<WebElement> completedTasks = driver.findElements(AppiumBy.xpath("//android.widget.RelativeLayout[@resource-id='com.app.todolist:id/rl_exlv_task_group_root']"));
		Assert.assertEquals(completedTasks.size(), 2);
	}

	@AfterClass
	public void tearDown() {
		// Close the app
		driver.quit();
	}
}
