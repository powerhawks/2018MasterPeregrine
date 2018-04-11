package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsys.LEDs;
import subsys.Shooter;
import vars.ControllerMap;

public class Operator {
	Joystick joy = new Joystick(ControllerMap.OPERATOR_PORT);
	Shooter shooter;
	
	//flags
	boolean override = false;

	public Operator(Shooter s) {
		shooter = s;
	}

	// =====OPERATOR METHODS=====

	/**
	 * Standard wrapper method for the operator
	 */
	public void operate() {
		boolean boxPos = shooter.getBoxPos();
		
		// Intake override
		if (joy.getRawButton(ControllerMap.INTAKE_OVERRIDE)) { //Intake OVERRIDE
			override = !override;
			SmartDashboard.putBoolean("Intake Override:", override);
			Timer.delay(.125);
		}
		
		//Intake arms
		if (joy.getRawAxis(ControllerMap.TRIGGER_LEFT) > 0.5) {
			shooter.moveIntake(-shooter.ARM_SPEED); //CLOSES intake arms
		}
		else if (joy.getRawAxis(ControllerMap.TRIGGER_RIGHT) > 0.5) {
			shooter.moveIntake(shooter.ARM_SPEED); //OPENS intake arms
		}
		
		//Intake
		if (override && !boxPos) { //Intakes box with INDIVIDUAL control
			shooter.setIntakes(joy.getRawAxis(ControllerMap.LEFT_STICK), joy.getRawAxis(ControllerMap.RIGHT_STICK));
			LEDs.shooter.set(true);
		}
		else if (!override && joy.getRawButton(ControllerMap.INTAKE) && !boxPos) { //Intakes with SINGLE control
			shooter.setIntakes(shooter.INTAKE_POWER);
			LEDs.shooter.set(true);
		}
		else if (joy.getRawButton(ControllerMap.OUTTAKE)) { // OUTAKES box
			shooter.setIntakes(shooter.OUTTAKE_POWER);
			LEDs.outtake.set(true);
		}
		else { // STOPS intake
			shooter.setIntakes(0);
			LEDs.shooter.set(false);
			LEDs.outtake.set(false);
		}

		// Shooter logic
		if (joy.getRawButton(ControllerMap.SWITCH_SHOOT) /*&& boxPos*/) { //Shoots for SWITCH
			shooter.shoot(shooter.SWITCH_POWER, shooter.SWITCH_VELOCITY, true);
		} 
		else if (joy.getRawButton(ControllerMap.LOW_SCALE_SHOOT) /*&& boxPos*/) { //Spins up for LOW/MEDIUM SCALE
			shooter.shoot(shooter.SCALE_POWER, shooter.LOW_SCALE_VELOCITY, true);
		}
		else if (joy.getRawButton(ControllerMap.HIGH_SCALE_SHOOT) /*&& boxPos*/) { //Spins up for HIGH SCALE
			shooter.shoot(shooter.SCALE_POWER, shooter.HIGH_SCALE_VELOCITY, true);
		}
		else if (joy.getRawButton(ControllerMap.OMEGA)) { //Spins up for OMEGA PROTOCOL
			shooter.shoot(shooter.OMEGA_POWER, shooter.OMEGA_VELOCITY, true);
		}
		else { // STOPS shooter
			shooter.setShooters(0);
			shooter.spunUp = false;
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
