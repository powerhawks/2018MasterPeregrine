package auto.cmds;

import subsys.DriveTrain;

public class MoveDistCommand implements Command {
	DriveTrain driveTrain;
	double target;
	double angle;
	public boolean complete = false;
	
	public MoveDistCommand(DriveTrain dt, double t, double a) {
		driveTrain = dt;
		target = t;
		angle = a;
	}
	
	public void execute() {
		driveTrain.driveDistance(target);
//		driveTrain.turnTo(angle);
		complete = !driveTrain.driving;
	}

	@Override
	public boolean isComplete() {
		return complete;
	}
}
