package auto.cmds;

import subsys.DriveTrain;

public class TurnCommand implements Command {
	DriveTrain driveTrain;
	double target;
	boolean complete = false;
	
	public TurnCommand(DriveTrain dt, double t) {
		driveTrain = dt;
		target = t;
	}

	public void execute() {
		driveTrain.turnTo(target);
		complete = !driveTrain.turning;
	}

	@Override
	public boolean isComplete() {
		return complete;
	}
}
