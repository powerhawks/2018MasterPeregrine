package auto.cmds;

import power.hawks.frc.lib.Command;
import subsys.DriveTrain;

/**
 * Command for moving the robot for a certain time
 * @author Power Hawks Controls
 *
 */
public class MoveTimeCommand implements Command {
	DriveTrain driveTrain;
	double target;
	Double angle;
	boolean reverse;
	boolean complete = false;
	
	/**
	 * Moves the robot for a certain time
	 * @param dt the drive train of the robot
	 * @param t the time to drive for
	 * @param r reverse the drive train
	 */
	public MoveTimeCommand(DriveTrain dt, double t, boolean r) {
		driveTrain = dt;
		target = t;
		reverse = r;
	}
	
	/**
	 * Moves the robot for a certain time on a certain radial
	 * @param dt the drive train of the robot
	 * @param t the time to drive for
	 * @param a the radial to travel on
	 * @param r reverse the drive train
	 */
	public MoveTimeCommand(DriveTrain dt, double t, double a, boolean r) {
		driveTrain = dt;
		target = t;
		angle = a;
		reverse = r;
	}
	
	@Override
	public void execute() {
		if (angle == null) {
			driveTrain.driveTime(target, reverse); 
		}
		else {
//			driveTrain.driveTimeRadial(target, angle, reverse); //TODO Implement
		}
		complete = !driveTrain.isDriving();
	}
	
	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void stop() {
		driveTrain.stop();
	}
}
