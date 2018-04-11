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

public class DriveTrain {
	// NavX PID Variables
	final double pN = 0.05, iN = 0.0, dN = 0.0; // TODO: Calibrate
	public AHRS navx = new AHRS(SPI.Port.kMXP);
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
	public boolean driving = false;
	public boolean turning = false;
	boolean timing = false;

	// Standard Variables
	double speed;
	final double MAX_AUTO_SPEED = .6;

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

	
	public void drive(double speed) {
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, speed *.50);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, speed *.5);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -speed *.5);
		Motors.driveBackRight.set(ControlMode.PercentOutput, -speed *.5);
	}

	public void drive(double left, double right) {
		Motors.driveFrontLeft.set(ControlMode.PercentOutput, left *.5);
		Motors.driveFrontRight.set(ControlMode.PercentOutput, right *.5);
		Motors.driveBackLeft.set(ControlMode.PercentOutput, -left *.5);
		Motors.driveBackRight.set(ControlMode.PercentOutput, -right *.5);
	}

	public void shiftGear() {
		low = !low;
		SmartDashboard.putBoolean("Low Gear:", low); // NOT A DEBUG
		
		if (low) { // Shift to LOW gear
			Pneumatics.shifter.set(Value.kReverse);
			Timer.delay(.25);
		} 
		else { // Shift to HIGH gear
			Pneumatics.shifter.set(Value.kForward);
			Timer.delay(.25);
		}
	}

	public void shiftTakeoff() {
		takeoff = !takeoff;
		SmartDashboard.putBoolean("PTO engaged:", takeoff); // NOT A DEBUG

		if (takeoff) { // Activates power takeoff
			Pneumatics.powerTakeoff.set(Value.kForward);
			Timer.delay(.25);
		} 
		else {
			Pneumatics.powerTakeoff.set(Value.kReverse);
			Timer.delay(.25);
		}
	}

	
	// =====AUTO METHODS=====
	
	
	public void driveDistance(double dist) {
		int deadzone = 1000;
		int desTicks = (int) (TPI * dist);
		SmartDashboard.putNumber("Desired Ticks:", desTicks);
		pidDist.setSetpoint(desTicks);
		int curTicks = Motors.driveEncoderRight.getSelectedSensorPosition(0); // TODO: Verify that the encoder reads properly
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
	
	public void stop() {
		drive(0);
		Motors.resetEncoder(Motors.driveEncoderLeft);
		Motors.resetEncoder(Motors.driveEncoderRight);
	}
}
