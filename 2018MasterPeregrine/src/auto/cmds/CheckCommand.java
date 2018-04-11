package auto.cmds;

import subsys.DriveTrain;
import subsys.sensors.Ultrasonic;
import util.Utility;

public class CheckCommand implements Command {
	DriveTrain driveTrain;
	Ultrasonic[] ultrasonics;
	double xPos;
	double xDZ;
	double yPos;
	double yDZ;
	boolean complete = false;
	
	public CheckCommand(DriveTrain dt, double x, double dx, double y, double dy, Ultrasonic[] u) {
		driveTrain = dt;
		xPos = x;
		xDZ = dx;
		yPos = y;
		yDZ = dy;
		ultrasonics = u;
	}
	
	public void execute() {
		if (Utility.inRange(ultrasonics[0].getDistance(), xPos, xDZ)) {
			if (Utility.inRange(ultrasonics[1].getDistance(), yPos, yDZ)) {
				complete = true;
			}
			else {
				complete = compensate(1, ultrasonics[1].getDistance());
			}
		}
		else {
			complete = compensate(0, ultrasonics[0].getDistance());
		}
	}
	
	private boolean compensate(int axis, double curReading) {
		double error;
		if (axis == 0) { //X-Axis compensation
			error = xPos - curReading;
			driveTrain.turnTo(0);
			if (!driveTrain.turning) {
				return false;
			}
			else if (!driveTrain.driving) {
				driveTrain.driveDistance(error);
				return false;
			}
			else {
				return true;
			}
		}
		else { //Y-Axis compensation
			error = yPos - curReading;
			driveTrain.driveDistance(error);
			return driveTrain.driving;
		}
	}

	@Override
	public boolean isComplete() {
		return complete;
	}
}
