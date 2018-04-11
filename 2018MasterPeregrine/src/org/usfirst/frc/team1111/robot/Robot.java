package org.usfirst.frc.team1111.robot;

import java.util.Arrays;
import java.util.List;

import auto.Autonomous;
import auto.Scheduler;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsys.DriveTrain;
import subsys.LEDs;
import subsys.Listener;
import subsys.Shooter;
import vars.Motors;
import vars.Pneumatics;

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
	
//	boolean switchSide, scaleSide, opSwitchSide;
	
	// Auto Chooser Instantiation
	SendableChooser<String> autoChooser = new SendableChooser<>();
	public final static String BASELINE = "BSE";
	public final static String SWITCH = "SWT";
	public final static String SCALE = "SCA";
	public final static String PANIC = "P";
	String autoSelected;
	
	// Starting Position Chooser Instantiation
	SendableChooser<String> startChooser = new SendableChooser<>();
	public final static String POSITION_A = "PA";
	public final static String POSITION_B = "PB";
//	public final static String POSITION_BL = "PBL";
//	public final static String POSITION_BR = "PBR";
	public final static String POSITION_C = "PC";
	String startSelected;

	@Override
	public void robotInit() {
		Alliance curAlliance = DriverStation.getInstance().getAlliance();
//		Alliance curAlliance = DriverStation.Alliance.Blue; //Testing purpose only!
		if (curAlliance.equals(DriverStation.Alliance.Blue)) {
			LEDs.alliance.set(false);
		}
		else if (curAlliance.equals(DriverStation.Alliance.Red)) {
			LEDs.alliance.set(true);
		}
		
		autoChooser.addObject("Baseline", BASELINE);
		autoChooser.addDefault("Switch", SWITCH);
		autoChooser.addObject("Scale", SCALE);
		autoChooser.addObject("PANIC!", PANIC);
		SmartDashboard.putData("Autonomous Priority Chooser", autoChooser);
		
		startChooser.addObject("Position A", POSITION_A);
//		startChooser.addObject("Position B-L", POSITION_BL);
//		startChooser.addObject("Position B-R", POSITION_BR);
		startChooser.addDefault("Position B", POSITION_B);
		startChooser.addObject("Position C", POSITION_C);
		SmartDashboard.putData("Start Position Selector", startChooser);
	}
	
//	public void getSide(String data) {
//		if(data.charAt(0)=='L') {switchSide = true; }
//		else { switchSide = false;  }
//		if(data.charAt(1)=='L') { scaleSide = true; }
//		else { scaleSide = false; }
//		if(data.charAt(2)=='L') {opSwitchSide = true; }
//		else { opSwitchSide  = false;  }
//		SmartDashboard.putBoolean("Our Switch:", switchSide);
//		SmartDashboard.putBoolean("Scale:", scaleSide);
//		SmartDashboard.putBoolean("Opponent's Switch:", opSwitchSide);
//	}

	@Override
	public void autonomousInit() {
		Pneumatics.shifter.set(Value.kForward);
		shooter.changeAngle(true);
		String data = Listener.getFieldData();
		
		if (testData.contains(data)) {
			auto.setFieldConfig(data);
//			getSide(data);
			autoSelected = autoChooser.getSelected();
			startSelected = startChooser.getSelected();
			auto.generatePath(startSelected, autoSelected);
		}
		else {
			auto.generatePath(startSelected);
		}
		
		Motors.resetEncoder(Motors.driveEncoderLeft);
		Motors.resetEncoder(Motors.driveEncoderRight);
		driveTrain.navx.reset();
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
}
