package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import subsys.DriveTrain;
import vars.ControllerMap;

/**
 * The driver who controls the drive train, shifts gears, and engages PTO
 * @author Power Hawks Controls
 *
 */
public class Driver {
	Joystick joy = new Joystick(ControllerMap.DRIVER_PORT);
	DriveTrain driveTrain;
	boolean driving = true;
	
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
			driveTrain.shiftGear();
		}
		
		if (joy.getRawButton(ControllerMap.DRIVER_TOGGLE)) {
			driving = !driving;
			Timer.delay(.125);
		}
	}
}
