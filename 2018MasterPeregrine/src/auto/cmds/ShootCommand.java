package auto.cmds;

import power.hawks.frc.lib.auto.cmds.Command;
import subsys.Shooter;

/**
 * Command that shoots a block from the robot
 * @author Power Hawks Controls
 *
 */
public class ShootCommand implements Command {
	Shooter shooter;
	double power;
	int target;
	boolean complete = false;
	
	/**
	 * Shoots a block from the robot
	 * @param s the shooter subsystem
	 * @param p the power of the shooter
	 * @param t the velocity trigger
	 */
	public ShootCommand(Shooter s, double p, int t) {
		shooter = s;
		power = p;
		target = t;
	}
	
	public void execute() {
		shooter.shoot(power, target, true);
		complete = shooter.isShooting();
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void stop() {
		shooter.stop();
	}	
}
