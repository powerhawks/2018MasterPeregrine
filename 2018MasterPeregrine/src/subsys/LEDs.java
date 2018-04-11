package subsys;

import edu.wpi.first.wpilibj.DigitalOutput;

public class LEDs {
	public static DigitalOutput shooter = new DigitalOutput(1);
	public static DigitalOutput outtake = new DigitalOutput(2);
	public static DigitalOutput climb = new DigitalOutput(3);
	public static DigitalOutput auto = new DigitalOutput(4);
	public static DigitalOutput alliance = new DigitalOutput(5);
	
	public static void shooter(boolean activated) {
		reset();
		shooter.set(activated);
	}
	
	public static void outtake(boolean activated) {
		reset();
		outtake.set(activated);
	}
	
	public static void climb(boolean activated) {
		reset();
		climb.set(activated);
	}
	
	public static void auto(boolean activated) {
		reset();
		auto.set(activated);
	}
	
	public static void reset() {
		shooter.set(false);
		outtake.set(false);
		climb.set(false);
		auto.set(false);
	}
}
