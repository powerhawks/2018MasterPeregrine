package auto.cmds;

import subsys.Shooter;

/**
 * Command that raises/lowers the shooter of the robot
 * @author Power Hawks Controls
 *
 */
public class CylinderCommand implements Command {
	Shooter shooter;
	boolean low;
	boolean complete = false;
	
	/**
	 * Raises or lowers the shooter
	 * @param s the shooter of the robot
	 * @param l if the shooter will be lowered
	 */
	public CylinderCommand(Shooter s, boolean l) {
		shooter = s;
		low = l;
	}
	
	@Override
	public void execute() {
		shooter.changeAngle(low);
		complete = true;
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void stop() {
		
	}
}
