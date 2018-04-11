package auto.cmds;

import subsys.DriveTrain;

public class MoveTimeCommand implements Command {
	DriveTrain driveTrain;
	double target;
	boolean reverse;
	public boolean complete = false;
	
	public MoveTimeCommand(DriveTrain dt, double t, boolean r) {
		driveTrain = dt;
		target = t;
		reverse = r;
	}
	
	@Override
	public void execute() {
		driveTrain.driveTime(target, reverse);
		complete = !driveTrain.driving;
	}
	
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void stop() {
		driveTrain.stop();
	}
}
