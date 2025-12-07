package activities;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.util.Arrays;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.AppiumDriver;

public class ActionBase {
	// Set the pointer input type
	static PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
	
	public static void doSwipe(AppiumDriver driver, Point start, Point end, int duration) {
		// Create sequence of actions to perform a swipe
        Sequence swipe = new Sequence(finger, 1).
            addAction(finger.createPointerMove(ofMillis(0), viewport(), start.getX(), start.getY())).
            addAction(finger.createPointerDown(LEFT.asArg())).
            addAction(finger.createPointerMove(ofMillis(duration), viewport(), end.getX(), end.getY())).
            addAction(finger.createPointerUp(LEFT.asArg()));
        
        // Perform the sequence
        driver.perform(Arrays.asList(swipe));
    }
}
