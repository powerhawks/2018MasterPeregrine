package subsys;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vars.Motors;
import vars.Pneumatics;

public class Shooter {
	// Velocity variables
	public final int OMEGA_VELOCITY = 150000;
	public final double OMEGA_POWER = 1;
	public final int LOW_SCALE_VELOCITY = 24000;
	public final int HIGH_SCALE_VELOCITY = 26600;
	public final double SCALE_POWER = .6;
	public final int SWITCH_VELOCITY = 7500;
	public final double SWITCH_POWER = .3;
	public final double INTAKE_POWER = .75;
	public final double OUTTAKE_POWER = -.75;
	public final double ARM_SPEED = .3;

	// Flags
	public boolean spunUp = false;
	public boolean shooting = false;

	// Sensors
	DigitalInput boxSensor = new DigitalInput(0);

	// =====OPERATOR METHODS=====
	
	/**
	 * Spins the shooter up to the specified speed and if autoFire is enabled, will automatically fire the box
	 * @param target the desired speed of the shooter
	 * @param autoFire if the box will automatically fire when motors are spun up
	 */
	public void shoot(double power, int target, boolean autoFire) {
		spinUp(power, target);

		if (spunUp && autoFire) { // If motors are spun up, push box into shooter
			setIntakes(1);
			Timer.delay(.5);
			shooting = true;
		}
	}

	private void spinUp(double power, int target) {
		double curSpeedLeft = Motors.shooterEncoderLeft.getSelectedSensorVelocity(0);
		double curSpeedRight = -Motors.shooterEncoderRight.getSelectedSensorVelocity(0);
		double curAvgSpeed = (curSpeedLeft + curSpeedRight) / 2;
		SmartDashboard.putNumber("Shooter Speed Left:", curSpeedLeft); //Debug
		SmartDashboard.putNumber("Shooter Speed Right:", curSpeedRight); //Debug
		SmartDashboard.putNumber("Average Shooter Speed:", curAvgSpeed); //Debug
		
//		if (target > 1) {
//			pid.setSetpoint(target);
//			double speed = pid.getOutput(curSpeed);
//			setShooters(speed);
//		}
		setShooters(power);

		if ((curAvgSpeed > target && curAvgSpeed < target+2000)) {
			spunUp = true;
		} 
		else if(curAvgSpeed > target + 2000) {
			setShooters(0);
		}
		else {
			spunUp = false;
		}
		
		//Future adjust for fixing overspeed on right
		//check
//		int leftTarget = power * 1.2;
//		int rightTarget = power * .8;
//		if((curSpeedLeft > leftTarget && curSpeedLeft < leftTarget + 2000) && (curSpeedRight > rightTarget && curSpeedRight < rightTarget + 2000)){
	//		spunUp = true;
	//	}

//		SmartDashboard.putNumber("Motor Speed:", curSpeed); //Debug
		SmartDashboard.putBoolean("Motors Spun Up:", spunUp); //NOT A DEBUG
	}

	// =====PNEUMATIC METHODS=====

	public void changeAngle(boolean low) {
		if (low) { // LOWERS piston to 30 degrees
			Pneumatics.shooterPiston.set(Value.kForward);
		} 
		else { // RAISES piston to 60 degrees
			Pneumatics.shooterPiston.set(Value.kReverse);
		}
	}

	// =====UTILITY METHODS=====
	
//	/**
//	 * NORMAL!
//	 */
	public void setShooters(double speed) {
		Motors.shooterFrontLeft.set(ControlMode.PercentOutput, speed);
		Motors.shooterFrontRight.set(ControlMode.PercentOutput, -speed);
		Motors.shooterBackLeft.set(ControlMode.PercentOutput, speed);
		Motors.shooterBackRight.set(ControlMode.PercentOutput, -speed);		
	}
	
	/**
	 * EXPERIMENTAL!
	 * @param speed
	 */
//	public void setShooters(double speed) {
//		Motors.shooterFrontLeft.set(ControlMode.PercentOutput, speed);
//		Motors.shooterFrontRight.set(ControlMode.PercentOutput, -(speed - (speed * .25)));
//		Motors.shooterBackLeft.set(ControlMode.PercentOutput, speed);
//		Motors.shooterBackRight.set(ControlMode.PercentOutput, -(speed - (speed * .25)));		
//	}

	public void setIntakes(double speed) {
		Motors.intakeInboardLeft.set(ControlMode.PercentOutput, speed);
		Motors.intakeInboardRight.set(ControlMode.PercentOutput, -speed);
		Motors.intakeOutboardLeft.set(ControlMode.PercentOutput, -speed);
		Motors.intakeOutboardRight.set(ControlMode.PercentOutput, speed);
	}
	
	public void setIntakes(double left, double right) {
		Motors.intakeInboardLeft.set(ControlMode.PercentOutput, right);
		Motors.intakeInboardRight.set(ControlMode.PercentOutput, -left);
		Motors.intakeOutboardLeft.set(ControlMode.PercentOutput, -left);
		Motors.intakeOutboardRight.set(ControlMode.PercentOutput, right);
	}
	
	public void moveIntake(double speed) {
		Motors.armLeft.set(ControlMode.PercentOutput, speed);
		Motors.armRight.set(ControlMode.PercentOutput, -speed);
	}
	
	public void stop() {
		setShooters(0);
		setIntakes(0);
	}
	

	// =====GETTER METHODS=====

	public boolean getBoxPos() {
		return boxSensor.get();
	}
}