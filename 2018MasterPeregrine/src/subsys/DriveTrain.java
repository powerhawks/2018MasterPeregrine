package subsys;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.MiniPID;
import util.Utility;
import vars.Motors;
import vars.Pneumatics;

/**
 * Basic drive train subsystem for the robot
 * @author Power Hawks Controls
 *
 */
public class DriveTrain {
	// NavX PID Variables
	final double pN = 0.05, iN = 0.0, dN = 0.0; // TODO: Calibrate
	AHRS navx = new AHRS(SPI.Port.kMXP);
	MiniPID pidNavx = new MiniPID(pN, iN, dN);

	// Encoder PID Variables
	double pT = .055, iT = 0, dT = 0; // TODO: Calibrate
	MiniPID pidDist = new MiniPID(pT, iT, dT);
	final double TPI = 1705;
	
	// Timer
	Timer timer = new Timer();

	// Flags
	boolean low = false;
	boolean takeoff = false;
	boolean driving = false;
	boolean turning = false;
	boolean timing = false;

	// Standard Variables
	double speed;
	final double MAX_AUTO_SPEED = .6;

	/**
	 * Initializes the drive train for use and configures PID for autonomous
	 */
	public DriveTrain() {
		// Configure encoder PID
		Motors.driveEncoderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Motors.TIMEOUT);
		Motors.driveEncoderRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Motors.TIMEOUT);
		Motors.resetEncoder(Motors.driveEncoderLeft);
		Motors.resetEncoder(Motors.driveEncoderRight);
		Utility.configurePID(pT, iT, dT, pidDist);
		pidDist.setOutputLimits(MAX_AUTO_SPEED);
		
		// Configure NavX PID
		navx.reset();
		Utility.configurePID(pN, iN, dN, pidNavx);
		pidNavx.setOutputLimits(MAX_AUTO_SPEED);
	}

	
	// =====STANDARD METHODS=====

	
	/**
	 * Drives the robot at the given power
	 * @param power the power of the motors
	 */
	public void drive(double power) {
		power *= .5; //Change this number to change the limiter for power. (1 results in full range)
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, power);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, power);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -power);
		Motors.driveBackRight.set(ControlMode.PercentOutput, -power);
	}
	
	/**
	 * Drives the robot in a tank drive/skidsteer configuration with left/right inputs
	 * @param left the power for the left motors
	 * @param right the power for the right motors
	 */
	public void drive(double left, double right) {
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, left *.5);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, right *.5);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -left *.5);
		Motors.driveBackRight.set(ControlMode.PercentOutput, -right *.5);
	}
	
	/**
	 * Drives the robot at the given power along a certain radial using the NavX
	 * @param power the power of the motors
	 * @param angle the radial to travel along
	 */
	public void driveRadial(double power, double angle) {
		//TODO: Implement
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

	
	// =====AUTO METHODS=====
	
	
	/**
	 * Uses PID and encoders on the drive motors to go a specified distance and hold there
	 * @param dist the distance
	 */
	public void driveDistance(double dist) {
		int deadzone = 1000;
		int desTicks = (int) (TPI * dist);
		SmartDashboard.putNumber("Desired Ticks:", desTicks);
		pidDist.setSetpoint(desTicks);
		int curTicks = Motors.driveEncoderRight.getSelectedSensorPosition(0);
		SmartDashboard.putNumber("Ticks", curTicks);
		
		if (Utility.inRange(curTicks, desTicks, deadzone)) { // STOPS if in deadzone of distance
			drive(0);
			Motors.resetEncoder(Motors.driveEncoderLeft);
			Motors.resetEncoder(Motors.driveEncoderRight);
			driving = false;
		}
		else { // DRIVES until traveled far enough to be in deadzone
			speed = pidDist.getOutput(curTicks);
			drive(-speed);
			driving = true;
		}
		
		updateDashboard();
	}
	
	/**
	 * Uses PID, encoders, and the NavX to travel a certain distance along a certain radial and hold there
	 * @param dist the distance
	 * @param radial the radial to travel on
	 */
	public void driveDistanceRadial(double dist, double radial) {
		//TODO: Implement and integrate with driveRadial()
	}
	
	/**
	 * Uses a timer to drive for a specified time forwards/backwards
	 * @param time the time to drive
	 * @param reverse if the drive train is reversed
	 */
	public void driveTime(double time, boolean reverse) {
		if (!timing) { // Start the timer and flip the TIMING flag
			timer.start();
			timing = true;
		}
		
		if (timer.get() < time) { // Run the motors at MAX AUTO SPEED for the specified time and flip the DRIVING flag
			if (reverse) {
				drive(-MAX_AUTO_SPEED);
			}
			else {
				drive(MAX_AUTO_SPEED); 
			}
			driving = true;
		}
		else { // Reset the timer and flip the TIMING and DRIVING flag
			timer.stop();
			timer.reset();
			timing = false;
			driving = false;
		}
		
		updateDashboard();
	}
	
	/**
	 * Uses a timer and the NavX to travel for a certain time along a certain radial
	 * @param time the time to drive
	 * @param radial the radial to travel on
	 * @param reverse if the drive train is reversed
	 */
	public void driveTimeRadial(double time, double radial, boolean reverse) {
		//TODO: Implement and integrate with driveRadial()
	}
	
	/**
	 * Uses the NavX and PID to turn to a specific angle
	 * @param desAngle the desired angle
	 */
	public void turnTo(double desAngle) {
		int deadzone = 2;
		int curAngle = Math.round(navx.getYaw());
		SmartDashboard.putNumber("Current Angle:", curAngle); // Debug
		pidNavx.setSetpoint(desAngle);
		speed = pidNavx.getOutput(curAngle);

		if ((curAngle - desAngle < deadzone) && (curAngle - desAngle > -deadzone)) { //STOPS if in deadzone of angle
			drive(0, 0); // STOPS motors
			turning = false;
		} 
		else { //TURNS until facing a specified angle
			drive(-speed, speed);
			turning = true;
		}
		
		updateDashboard();
	}
	
	
	// =====UTILITY METHODS=====
	
	
	private void updateDashboard() {
		SmartDashboard.putBoolean("Driving:", driving);
		SmartDashboard.putBoolean("Turning:", turning);
		SmartDashboard.putBoolean("Timing:", timing);
	}
	
	/**
	 * Wrapper for stopping the drive train and reseting the drive encoders
	 */
	public void stop() {
		drive(0);
		Motors.resetEncoder(Motors.driveEncoderLeft);
		Motors.resetEncoder(Motors.driveEncoderRight);
	}
	
	/**
	 * Wrapper for reseting the drive encoders and the NavX
	 */
	public void reset() {
		Motors.resetEncoder(Motors.driveEncoderLeft);
		Motors.resetEncoder(Motors.driveEncoderRight);
		navx.reset();
	}
	
	/**
	 * Getter for if the drive train is currently driving
	 * @return if the drive train is driving
	 */
	public boolean isDriving() {
		return driving;
	}
	
	/**
	 * Getter for if the drive train is currently turning
	 * @return if the drive train is turning
	 */
	public boolean isTurning() {
		return turning;
	}
}
