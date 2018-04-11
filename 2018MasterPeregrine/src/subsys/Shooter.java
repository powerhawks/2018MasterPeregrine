package subsys;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vars.Motors;
import vars.Pneumatics;

/**
 * The shooter subsystem for the robot
 * @author Power Hawks Controls
 *
 */
public class Shooter {
	// Velocity variables
	@SuppressWarnings("javadoc")
	public final int OMEGA_VELOCITY = 150000;
	@SuppressWarnings("javadoc")
	public final double OMEGA_POWER = 1;
	@SuppressWarnings("javadoc")
	public final int LOW_SCALE_VELOCITY = 24000;
	@SuppressWarnings("javadoc")
	public final int HIGH_SCALE_VELOCITY = 26600;
	@SuppressWarnings("javadoc")
	public final double SCALE_POWER = .6;
	@SuppressWarnings("javadoc")
	public final int SWITCH_VELOCITY = 7500;
	@SuppressWarnings("javadoc")
	public final double SWITCH_POWER = .3;
	@SuppressWarnings("javadoc")
	public final double INTAKE_POWER = .75;
	@SuppressWarnings("javadoc")
	public final double OUTTAKE_POWER = -.75;
	@SuppressWarnings("javadoc")
	public final double ARM_SPEED = .3;

	// Flags
	boolean spunUp = false;
	boolean shooting = false;

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
			shooting = true;
			Timer.delay(.5);
		}
	}

	private void spinUp(double power, int target) {
		double curSpeedLeft = Motors.shooterEncoderLeft.getSelectedSensorVelocity(0);
		double curSpeedRight = -Motors.shooterEncoderRight.getSelectedSensorVelocity(0);
		double curAvgSpeed = (curSpeedLeft + curSpeedRight) / 2;
		double deadzone = 2000;

		setShooters(power);

		if ((curAvgSpeed > target && curAvgSpeed < target+deadzone)) {
			spunUp = true;
		} 
		else if(curAvgSpeed > target+deadzone) {
			setShooters(0);
		}
		else {
			spunUp = false;
		}
		SmartDashboard.putBoolean("Motors Spun Up:", spunUp); //NOT A DEBUG
	}

	// =====PNEUMATIC METHODS=====
	
	
	/**
	 * Raises/lowers the shooter
	 * @param low if the shooter should be in the LOW position
	 */
	public void changeAngle(boolean low) {
		if (low) { // LOWERS piston to 30 degrees
			Pneumatics.shooterPiston.set(Value.kForward);
		} 
		else { // RAISES piston to 60 degrees
			Pneumatics.shooterPiston.set(Value.kReverse);
		}
	}

	// =====UTILITY METHODS=====
	
	/**
	 * Sets the shooter motors to the specified power
	 * @param power the power of the motors 
	 */
	public void setShooters(double power) {
		Motors.shooterFrontLeft.set(ControlMode.PercentOutput, power);
		Motors.shooterFrontRight.set(ControlMode.PercentOutput, -power);
		Motors.shooterBackLeft.set(ControlMode.PercentOutput, power);
		Motors.shooterBackRight.set(ControlMode.PercentOutput, -power);		
	}

	/**
	 * Sets the intake motors to the specified power
	 * @param power the power of the motors
	 */
	public void setIntakes(double power) {
		Motors.intakeInboardLeft.set(ControlMode.PercentOutput, power);
		Motors.intakeInboardRight.set(ControlMode.PercentOutput, -power);
		Motors.intakeOutboardLeft.set(ControlMode.PercentOutput, -power);
		Motors.intakeOutboardRight.set(ControlMode.PercentOutput, power);
	}
	
	/**
	 * Allows for each side of the intake to be individually controlled using the joysticks
	 * <br>Note: this didn't work so well so only use this for reference</br>
	 * @param left the power of the left motors
	 * @param right the power of the right motors
	 */
	@Deprecated
	public void setIntakes(double left, double right) {
		Motors.intakeInboardLeft.set(ControlMode.PercentOutput, right);
		Motors.intakeInboardRight.set(ControlMode.PercentOutput, -left);
		Motors.intakeOutboardLeft.set(ControlMode.PercentOutput, -left);
		Motors.intakeOutboardRight.set(ControlMode.PercentOutput, right);
	}
	
	/**
	 * Moves the intake arms as if it were a toggle (arms could either be in the forward or backward state; there is no fine control)
	 * <br>Note: the arm motors are always on and they overheat after approximately 5-7 minutes</br>
	 * @param power the power of the intake arm motors
	 */
	public void moveIntakeArms(double power) {
		Motors.armLeft.set(ControlMode.PercentOutput, power);
		Motors.armRight.set(ControlMode.PercentOutput, -power);
	}
	
	/**
	 * Wrapper for stopping the shooter and intake motors
	 */
	public void stop() {
		setShooters(0);
		setIntakes(0);
	}
	

	// =====GETTER METHODS=====
	
	
	/**
	 * Gets if the box position sensor has been tripped
	 * @return if the box is in position
	 */
	public boolean getBoxPos() {
		return boxSensor.get();
	}
	
	/**
	 * Gets if the shooter is spun up to the target velocity
	 * @return if the shooter is spun up
	 */
	public boolean isSpunUp() {
		return spunUp;
	}
	
	/**
	 * Gets if the shooter is currently shooting
	 * @return if the shooter is shooting
	 */
	public boolean isShooting() {
		return shooting;
	}
}