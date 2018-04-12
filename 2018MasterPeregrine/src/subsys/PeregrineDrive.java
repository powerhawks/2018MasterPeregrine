package subsys;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import power.hawks.frc.lib.subsys.DriveTrain;
import vars.Motors;
import vars.Pneumatics;

/**
 * Drive train class specifically for Power Hawks' 2018 robot, Peregrine
 * @author Power Hawks Controls
 *
 */
public class PeregrineDrive extends DriveTrain {
	boolean low = false;
	boolean takeoff = false;
	
	/**
	 * Generic constructor that automatically creates a drive train from the TalonGroup of controllers defined in Motors.java
	 */
	public PeregrineDrive() {
		super(Motors.driveLeft, Motors.driveRight);
	}
	
	/**
	 * Shifts gears on the drive train. Displays current gear on the SmartDashboard as a boolean (true is LOW gear)
	 * <br> Note: This is a toggle </br>
	 */
	public void shiftGear() {
		low = !low;
		SmartDashboard.putBoolean("Low Gear:", low); //NOT A DEBUG
		
		if (low) { //Shift to LOW gear
			Pneumatics.shifter.set(Value.kReverse);
			Timer.delay(.25);
		} 
		else { //Shift to HIGH gear
			Pneumatics.shifter.set(Value.kForward);
			Timer.delay(.25);
		}
	}
	
	/**
	 * Engages power takeoff. Displays current state on the SmartDashboard as a boolean (true is PTO engaged)
	 * <br> Note: This is a toggle </br>
	 */
	public void shiftTakeoff() {
		takeoff = !takeoff;
		SmartDashboard.putBoolean("PTO engaged:", takeoff); //NOT A DEBUG

		if (takeoff) { //Engages PTO
			Pneumatics.powerTakeoff.set(Value.kForward);
			Timer.delay(.25);
		} 
		else { //Disengages PTO
			Pneumatics.powerTakeoff.set(Value.kReverse);
			Timer.delay(.25);
		}
	}
}
