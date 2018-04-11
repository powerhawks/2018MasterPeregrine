package vars;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * General class for the motors of Peregrine. Motors that need to be reversed:
 * 1) driveBackLeft 2) driveBackRight 3) shooterFrontRight 4) shooterBackRight
 * 5) intakeRight
 * 
 * @author Braidan
 *
 */
public class Motors {
	// DRIVE TRAIN motors
	public static TalonSRX driveFrontLeft = new TalonSRX(46);
	public static TalonSRX driveFrontRight = new TalonSRX(55);
	public static TalonSRX driveBackLeft = new TalonSRX(47);
	public static TalonSRX driveBackRight = new TalonSRX(39);
	public static TalonSRX driveEncoderLeft = driveBackLeft;
	public static TalonSRX driveEncoderRight = driveFrontRight;
	
	// CLIMB motors
	public static TalonSRX telescopeHook = new TalonSRX(62);
		
	// SHOOTER motors
	public static TalonSRX shooterFrontLeft = new TalonSRX(59);
	public static TalonSRX shooterFrontRight = new TalonSRX(49);
	public static TalonSRX shooterBackLeft = new TalonSRX(41);
	public static TalonSRX shooterBackRight = new TalonSRX(43);
	public static TalonSRX shooterEncoderLeft = shooterBackLeft;
	public static TalonSRX shooterEncoderRight = shooterBackRight;
			
	// INTAKE motors
	public static TalonSRX intakeInboardLeft = new TalonSRX(60);
	public static TalonSRX intakeInboardRight = new TalonSRX(50);
	public static TalonSRX intakeOutboardLeft = new TalonSRX(56);
	public static TalonSRX intakeOutboardRight = new TalonSRX(61); 
	public static TalonSRX armLeft = new TalonSRX (62);
	public static TalonSRX armRight = new TalonSRX (44); 

	
	// Miscellaneous values
	public static final int TIMEOUT = 200; // ms
	
	public static void resetEncoder(TalonSRX motor) {
		motor.setSelectedSensorPosition(0, 0, TIMEOUT);
	}
}
