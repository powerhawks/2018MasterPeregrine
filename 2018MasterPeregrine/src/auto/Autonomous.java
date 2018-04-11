package auto;

import java.util.ArrayList;

import org.usfirst.frc.team1111.robot.Robot;

import auto.cmds.MoveDistCommand;
import auto.cmds.MoveTimeCommand;
import auto.cmds.CylinderCommand;
import auto.cmds.ShootCommand;
import auto.cmds.TurnCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import power.hawks.frc.lib.Command;
import power.hawks.frc.lib.Scheduler;
import subsys.DriveTrain;
import subsys.Shooter;
import vars.Dimensions;

/**
 * Class for Peregrine's autonomous mode. 
 * Will automatically select and generate a path based on user priority, starting position, and field configuration.
 * @author Power Hawks Controls
 *
 */
public class Autonomous {
	// Commands Instantiation
	/**
	 * Commands to be executed during autonomous. Can include multiple commands in a CommandGroup
	 */
	public ArrayList<Command> commands = new ArrayList<Command>();
	/**
	 * The scheduler for executing autonomous commands
	 */
	public Scheduler scheduler = new Scheduler();
	
	// Flags
	boolean panic = false;
	
	// Subsystem Instantiation
	DriveTrain driveTrain;
	Shooter shooter;
	
	//Misc. Variables
	String fieldConfig;
	
	/**
	 * @param dt Drivetrain of the robot
	 * @param s Shooter of the robot
	 */
	public Autonomous(DriveTrain dt, Shooter s) {
		driveTrain = dt;
		shooter = s;
	}
	
	
	// =====BEGIN AUTO PATH DEFINITIONS=====
	

	/**
	 * Generates a path to BASELINE from starting position A
	 */
	public void genABaseline() {
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.DS_TO_M_SW_DIST, 0)); //Drive 164 in FOWARDS
//		commands.add(new MoveTimeCommand(driveTrain, 2, true)); //Drive 28.5 in BACKWARDS
	}
	
	/**
	 * Generates a path to SWITCH from starting position A
	 */
	public void genASwitch() {
//		commands.add(new MoveTimeCommand(driveTrain, 2, true)); //Drive 151 in FORWARDS
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.DS_TO_M_SW_DIST, 0)); // Drive FORWARD 48.0
		commands.add(new TurnCommand(driveTrain, 90)); //Turn to 90 degrees LEFT
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SW_TO_FNC_TIME, false)); //Drive for SOME sec BACKWARDS
		commands.add(new ShootCommand(shooter, shooter.SWITCH_POWER, shooter.SWITCH_VELOCITY)); //Shoots into SWITCH
	}
	
	/**
	 * Generates a path to SCALE from starting position A
	 */
	public void genAScale() {
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.DS_TO_M_SC_DIST, 0)); //Drive 290.5 in FORWARD
		commands.add(new TurnCommand(driveTrain, 90)); //Turns 90 degrees LEFT
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SC_TO_WALL_TIME_A, true)); //Drive for SOME sec FORWARDS
		commands.add(new CylinderCommand(shooter, false)); //RAISES shooter
		commands.add(new ShootCommand(shooter, shooter.SCALE_POWER, shooter.HIGH_SCALE_VELOCITY)); //Shoots into SCALE
	}
	
	/**
	 * Generates a path to BASELINE from starting position B
	 */
	public void genBBaseline() {
		commands.add(new MoveDistCommand(driveTrain, -12, 0)); //Drive 28.5 in FOWARDS
		commands.add(new TurnCommand(driveTrain, -Dimensions.M_SW_ANGLE)); //Turn to 48 degrees LEFT -- orients to SWITCH shooting position
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.M_SW_L_DIST, -Dimensions.M_SW_ANGLE)); //Drive 91.5 in FORWARD -- drives to shooting position
		commands.add(new TurnCommand(driveTrain, 0)); //Turn LEFT 132 degrees -- orients to shooter to fence
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SW_TO_FNC_TIME, false)); //Drive BACKWARDS to SWITCH fence
//		commands.add(new MoveTimeCommand(driveTrain, 4, false));
	}
	
	/**
	 * Generates a path to LEFT SWITCH from starting position B
	 */
	public void genBSwitchLeft() {
		commands.add(new MoveDistCommand(driveTrain, -12, 0)); //Drive 12 in FORWARD
		commands.add(new TurnCommand(driveTrain, -Dimensions.M_SW_ANGLE)); //Turn to 48 degrees LEFT -- orients to SWITCH shooting position
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.M_SW_L_DIST, -Dimensions.M_SW_ANGLE)); //Drive 91.5 in FORWARD -- drives to shooting position
		commands.add(new TurnCommand(driveTrain, 0)); //Turn LEFT 132 degrees -- orients to shooter to fence
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SW_TO_FNC_TIME, false)); //Drive BACKWARDS to SWITCH fence
		commands.add(new ShootCommand(shooter, shooter.SWITCH_POWER, shooter.SWITCH_VELOCITY)); //Shoots into SWITCH -- TODO: Determine if viable
	}
	
	/**
	 * Generates a path to RIGHT SWITCH from starting position B
	 */
	public void genBSwitchRight() {
		commands.add(new MoveDistCommand(driveTrain, -12, 0)); //Drive 28.5 in FORWARD
		commands.add(new TurnCommand(driveTrain, Dimensions.M_SW_ANGLE)); //Turn to 33 degrees RIGHT -- orients to SWITCH shooting position
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.M_SW_R_DIST, Dimensions.M_SW_ANGLE)); //Drive 73 in FORWARD
		commands.add(new TurnCommand(driveTrain, 0)); //Turn RIGHT 147 degrees
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SW_TO_FNC_TIME, false)); //Drive BACKWARDS to SWITCH fence
		commands.add(new ShootCommand(shooter, shooter.SWITCH_POWER, shooter.SWITCH_VELOCITY)); //Shoots into SWITCH
	}
	
	/**
	 * Generates a path to BASELINE from starting position C
	 */
	public void genCBaseline() {
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.DS_TO_M_SW_DIST, 0)); //Drive 28.5 in FORWARD
//		commands.add(new MoveTimeCommand(driveTrain, 2, true)); //Drive 28.5 in FORWARD
	}
	
	/**
	 * Generates a path to SWITCH from starting position C
	 */
	public void genCSwitch() {
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.DS_TO_M_SW_DIST, 0)); //Drive 28.5 in FORWARDS
		commands.add(new TurnCommand(driveTrain, -90)); //Turn to 90 degrees RIGHT
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SW_TO_FNC_TIME, true)); //Drive for SOME sec BACKWARDS
		commands.add(new ShootCommand(shooter, shooter.SWITCH_POWER, shooter.SWITCH_VELOCITY)); //Shoots into SWITCH
	}
	
	/**
	 * Generates a path to SCALE from starting position C
	 */
	public void genCScale() {
		commands.add(new MoveDistCommand(driveTrain, -Dimensions.DS_TO_M_SC_DIST, 0)); //Drive 290.5 in FORWARDS
		commands.add(new TurnCommand(driveTrain, -90)); //Turns 90 degrees RIGHT
		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SC_TO_WALL_TIME_C, true)); //Drive for SOME sec backwards
		commands.add(new CylinderCommand(shooter, false)); //RAISES shooter
		commands.add(new ShootCommand(shooter, shooter.SCALE_POWER, shooter.HIGH_SCALE_VELOCITY)); //Shoots into SCALE
	}
	
	/**
	 * Generates a PANIC PATH from any starting position
	 */
	public void genPanic() {
		commands.add(new MoveTimeCommand(driveTrain, 3, true)); //Drive 7.5 sec FORWARDS
//		commands.add(new MoveTimeCommand(driveTrain, Dimensions.M_SW_TO_FNC_TIME, true)); //Drive BACKWARDS to SWITCH fence

	}
	
	
	// =====BEGIN AUTO PATH GENERATION METHODS=====
	
	
	/**
	 * Generates a baseline path from any starting position and trips the PANIC flag
	 * @param sp the starting position of the robot (passed from the SmartDashboard)
	 */
	public void generatePath(String sp) {
		if (sp.equals(Robot.POSITION_A)) {
			genABaseline();
		}
		else if (sp.equals(Robot.POSITION_B)) {
			genBBaseline();
		}
		else if (sp.equals(Robot.POSITION_C)) {
			genCBaseline();
		}
		
		panic = true;
		SmartDashboard.putBoolean("PANIC:", panic);
		scheduler.addCommands(commands);
	}
	
	/**
	 * Analyzes the field configuration and user preferences from the SmartDashboard and generates the according path
	 * @param sp the starting position of the robot (passed from the SmartDashboard
	 * @param ad the user-desired objective
	 */
	public void generatePath(String sp, String ad) {
		char swtPos = fieldConfig.charAt(0);
		char scaPos = fieldConfig.charAt(1);
		
		if (ad.equals(Robot.PANIC)) {
			genPanic();
			panic = true;
		}
		
		if (sp.equals(Robot.POSITION_A) && !panic) {
			if (ad.equals(Robot.BASELINE)) { //User prioritizes BASELINE
				genABaseline();
			}
			else if (ad.equals(Robot.SWITCH) && swtPos == 'L') { //User prioritizes SWITCH and it is on same side
				genASwitch();
			}
			else if (ad.equals(Robot.SWITCH) && swtPos == 'R' && scaPos == 'L') { //User prioritizes SWITCH but is not available but SCALE is
				genAScale();
			}
			else if (ad.equals(Robot.SCALE) && scaPos == 'L') { //User prioritizes SCALE and it is on same side
				genAScale();
			}
			else if (ad.equals(Robot.SCALE) && scaPos == 'R' && swtPos == 'L') { //User prioritizes SCALE but is not available but SWITCH is
				genASwitch();
			}
			else { //If nothing is available go to BASELINE and trigger PANIC
				genABaseline();
				panic = true;
			}
		}
		else if (sp.equals(Robot.POSITION_B)) {
			if (ad.equals(Robot.BASELINE)) { //User prioritizes BASELINE
				genBBaseline();
			}
			else if (ad.equals(Robot.SWITCH) && swtPos == 'L') { //User prioritizes SWITCH and it is on the LEFT side
				genBSwitchLeft();
			}
			else if (ad.equals(Robot.SWITCH) && swtPos == 'R') { //User prioritizes SWITCH and it is on the RIGHT side
				genBSwitchRight();
			}
			else if (ad.equals(Robot.SCALE)) { //User "accidentally" chooses SCALE and automatically corrects to go for SWITCH
				generatePath(sp, Robot.SWITCH);
			}
			else { //If nothing works go to BASELINE and trigger PANIC
				genBBaseline();
				panic = true;
			}
		}
		else if (sp.equals(Robot.POSITION_C)) {
			if (ad.equals(Robot.BASELINE)) { //User prioritizes BASELINE
				genCBaseline();
			}
			else if (ad.equals(Robot.SWITCH) && swtPos == 'R') { //User prioritizes SWITCH and it is on the same side
				genCSwitch();
			}
			else if (ad.equals(Robot.SWITCH) && swtPos == 'L' && scaPos == 'R') { //User prioritizes SWITCH but is not available but SCALE is
				genCScale();
			}
			else if (ad.equals(Robot.SCALE) && scaPos == 'R') { //User prioritizes SCALE and it is on same side
				genCScale();
			}
			else if (ad.equals(Robot.SCALE) && scaPos == 'L' && swtPos == 'R') { //User prioritizes SCALE but is not available but SWITCH is
				genCSwitch();
			}
			else { //If nothing is available go to BASELINE and trigger PANIC
				genCBaseline();
				panic = true;
			}
		}
	
		SmartDashboard.putBoolean("PANIC:", panic);
		scheduler.addCommands(commands);
	}
	
	
	// =====BEGIN UTILITY METHODS=====
	
	
	/**
	 * Setter for the field configuration
	 * @param data the field configuration from the FMS
	 * <br> See testData in Robot.java for examples </br>
	 */
	public void setFieldConfig(String data) {
		fieldConfig = data;
	}
	
	/**
	 * Resets the flags in autonomous and clears any commands and the scheduler
	 */
	public void reset() {
		panic = false;
		commands.clear();
		scheduler.reset();
	}
}