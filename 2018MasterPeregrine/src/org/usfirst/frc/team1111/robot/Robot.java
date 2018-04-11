package org.usfirst.frc.team1111.robot;

import java.util.Arrays;
import java.util.List;

import auto.Autonomous;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsys.DriveTrain;
import subsys.Shooter;
import vars.Pneumatics;

/**
 * <p>Master Robot class for Power Hawks Robotics (FRC 1111) 2018 robot, Peregrine</p>
 * <p>Peregrine has the most sophisticated autonomous in recent memory and combines the best aspects of
 * Iterative and Command-based robots into our novel Hybrid control.</p>
 * @author Power Hawks Controls
 *
 */
public class Robot extends IterativeRobot {
	// Subsystem Instantiation
	DriveTrain driveTrain = new DriveTrain();
	Shooter shooter = new Shooter();
	Compressor myComp = new Compressor();

	// Drive Team Instantiation
	Driver driver = new Driver(driveTrain);
	Operator operator = new Operator(shooter);
	Autonomous auto = new Autonomous(driveTrain, shooter);
	List<String> testData = Arrays.asList("LLL", "LRL", "RRR", "RLR");
		
	// Auto Chooser Instantiation
	SendableChooser<String> autoChooser = new SendableChooser<>();
	@SuppressWarnings("javadoc")
	public final static String BASELINE = "BSE";
	@SuppressWarnings("javadoc")
	public final static String SWITCH = "SWT";
	@SuppressWarnings("javadoc")
	public final static String SCALE = "SCA";
	@SuppressWarnings("javadoc")
	public final static String PANIC = "P";
	String autoSelected;
	
	// Starting Position Chooser Instantiation
	SendableChooser<String> startChooser = new SendableChooser<>();
	@SuppressWarnings("javadoc")
	public final static String POSITION_A = "PA";
	@SuppressWarnings("javadoc")
	public final static String POSITION_B = "PB";
	@SuppressWarnings("javadoc")
	public final static String POSITION_C = "PC";
	String startSelected;

	@Override
	public void robotInit() {
		autoChooser.addObject("Baseline", BASELINE);
		autoChooser.addDefault("Switch", SWITCH);
		autoChooser.addObject("Scale", SCALE);
		autoChooser.addObject("PANIC!", PANIC);
		SmartDashboard.putData("Autonomous Priority Chooser", autoChooser);
		
		startChooser.addObject("Position A", POSITION_A);
		startChooser.addDefault("Position B", POSITION_B);
		startChooser.addObject("Position C", POSITION_C);
		SmartDashboard.putData("Start Position Selector", startChooser);
	}

	@Override
	public void autonomousInit() {
		Pneumatics.shifter.set(Value.kForward); //Sets robot into LOW gear for improved control
		shooter.changeAngle(true); //LOWERS the shooter
		String data = getFieldData();
		autoSelected = autoChooser.getSelected();
		startSelected = startChooser.getSelected();
		driveTrain.reset();
		
		if (testData.contains(data)) {
			auto.setFieldConfig(data);
			auto.generatePath(startSelected, autoSelected);
		}
		else {
			auto.generatePath(startSelected);
		}
	}

	@Override
	public void autonomousPeriodic() {
		auto.scheduler.run();
	}

	@Override
	public void teleopPeriodic() {
		driver.drive();
		operator.operate();
	}
	
	@Override
	public void disabledInit() {
		auto.reset();
	}
	
	/**
	 * Wrapper for getting the field configuration from the FMS
	 * @return the field configuration
	 */
	public String getFieldData() {
		return DriverStation.getInstance().getGameSpecificMessage();
	}
}
