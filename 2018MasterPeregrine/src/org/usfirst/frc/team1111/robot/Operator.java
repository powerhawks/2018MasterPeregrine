package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsys.Shooter;
import vars.ControllerMap;

/**
 * The basic class for the operator who controls every other subsystem on the robot except for the drive train
 * @author Power Hawks Controls
 *
 */
public class Operator {
	Joystick joy = new Joystick(ControllerMap.OPERATOR_PORT);
	Shooter shooter;
	
	//Flags
	boolean override = false;

	/**
	 * Operator who controls every robot subsystem except the drive train
	 * @param s the shooter subsystem
	 */
	public Operator(Shooter s) {
		shooter = s;
	}

	// =====OPERATOR METHODS=====

	/**
	 * Standard wrapper method for the operator
	 */
	@SuppressWarnings("deprecation")
	public void operate() {
		boolean boxPos = shooter.getBoxPos();
		
		// Intake override logic
		if (joy.getRawButton(ControllerMap.INTAKE_OVERRIDE)) { //Intake OVERRIDE
			override = !override;
			SmartDashboard.putBoolean("Intake Override:", override);
			Timer.delay(.125);
		}
		
		//Intake arms logic
		if (joy.getRawAxis(ControllerMap.TRIGGER_LEFT) > 0.5) {
			shooter.moveIntakeArms(-shooter.ARM_SPEED); //CLOSES intake arms
		}
		else if (joy.getRawAxis(ControllerMap.TRIGGER_RIGHT) > 0.5) {
			shooter.moveIntakeArms(shooter.ARM_SPEED); //OPENS intake arms
		}
		
		//Intake logic
		if (override && !boxPos) { //Intakes box with INDIVIDUAL control
			shooter.setIntakes(joy.getRawAxis(ControllerMap.LEFT_STICK), joy.getRawAxis(ControllerMap.RIGHT_STICK));
		}
		else if (!override && joy.getRawButton(ControllerMap.INTAKE) && !boxPos) { //Intakes with SINGLE control
			shooter.setIntakes(shooter.INTAKE_POWER);
		}
		else if (joy.getRawButton(ControllerMap.OUTTAKE)) { // OUTAKES box
			shooter.setIntakes(shooter.OUTTAKE_POWER);
		}
		else { // STOPS intake
			shooter.setIntakes(0);
		}

		// Shooter logic
		if (joy.getRawButton(ControllerMap.SWITCH_SHOOT)) { //Shoots for SWITCH
			shooter.shoot(shooter.SWITCH_POWER, shooter.SWITCH_VELOCITY, true);
		} 
		else if (joy.getRawButton(ControllerMap.LOW_SCALE_SHOOT)) { //Spins up for LOW/MEDIUM SCALE
			shooter.shoot(shooter.SCALE_POWER, shooter.LOW_SCALE_VELOCITY, true);
		}
		else if (joy.getRawButton(ControllerMap.HIGH_SCALE_SHOOT)) { //Spins up for HIGH SCALE
			shooter.shoot(shooter.SCALE_POWER, shooter.HIGH_SCALE_VELOCITY, true);
		}
		else if (joy.getRawButton(ControllerMap.OMEGA)) { //Spins up for OMEGA PROTOCOL
			shooter.shoot(shooter.OMEGA_POWER, shooter.OMEGA_VELOCITY, true);
		}
		else { // STOPS shooter
			shooter.setShooters(0);
		}

		// Piston logic
		if (joy.getPOV() == ControllerMap.UP_DPAD) { // RAISE shooter
			shooter.changeAngle(false);
		}
		else if (joy.getPOV() == ControllerMap.DOWN_DPAD) { // LOWER shooter
			shooter.changeAngle(true);
		}
	}
}
