package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import power.hawks.frc.lib.subsys.DriveTrain;
import vars.ControllerMap;
import vars.Pneumatics;

/**
 * The driver who controls the drive train, shifts gears, and engages PTO
 * @author Power Hawks Controls
 *
 */
public class Driver {
	Joystick joy = new Joystick(ControllerMap.DRIVER_PORT);
	DriveTrain driveTrain;
	boolean driving = true;
	private boolean low = false;
	
	/**
	 * The driver of the robot
	 * @param dt the drive train subsystem
	 */
	public Driver(DriveTrain dt) {
		driveTrain = dt;
	}	
	
	/**
	 * Wrapper for operating tank drive and shifting gearbox
	 */
	public void drive() {
		if (driving) {
			driveTrain.drive(joy.getRawAxis(ControllerMap.LEFT_STICK), joy.getRawAxis(ControllerMap.RIGHT_STICK));
		}
		
		if (joy.getRawButton(ControllerMap.SHIFT_GEAR)) {
			shiftGear();
		}
		
		if (joy.getRawButton(ControllerMap.DRIVER_TOGGLE)) {
			driving = !driving;
			Timer.delay(.125);
		}
	}
	
	/**
	 * Shifts gears on the drive train. Displays current gear on the SmartDashboard as a boolean (true is LOW gear)
	 * <br> Note: This is a toggle </br>
	 */
	private void shiftGear() {
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
}
