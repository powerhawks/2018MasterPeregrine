package subsys;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * Triggers the different functions for the LED strips mounted under the shooter
 * <br>Note: The LEDs do not work and therefore this class should only be used for reference </br>
 * @author Power Hawks Controls
 *
 */
@Deprecated
public class LEDs {
	static DigitalOutput shooter = new DigitalOutput(1);
	static DigitalOutput outtake = new DigitalOutput(2);
	static DigitalOutput climb = new DigitalOutput(3);
	static DigitalOutput auto = new DigitalOutput(4);
	static DigitalOutput alliance = new DigitalOutput(5);
	
	/**
	 * Activates the LEDs for shooting
	 * @param activated if the LEDs for shooting are active
	 */
	public static void shooter(boolean activated) {
		reset();
		shooter.set(activated);
	}
	
	/**
	 * Activates the LEDs for outtaking
	 * @param activated if the LEDs for ottaking are active
	 */
	public static void outtake(boolean activated) {
		reset();
		outtake.set(activated);
	}
	
	/**
	 * Activates the LEDs for climbing
	 * @param activated if the LEDs for climbing are active
	 */
	public static void climb(boolean activated) {
		reset();
		climb.set(activated);
	}
	
	/**
	 * Activates the LEDs for autonomous
	 * @param activated if the LEDs for autonomous are active
	 */
	public static void auto(boolean activated) {
		reset();
		auto.set(activated);
	}
	
	/**
	 * Resets the LEDs
	 */
	public static void reset() {
		shooter.set(false);
		outtake.set(false);
		climb.set(false);
		auto.set(false);
	}
}
