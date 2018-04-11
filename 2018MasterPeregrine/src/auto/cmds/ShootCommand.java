package auto.cmds;

import subsys.Shooter;

public class ShootCommand implements Command {
	Shooter shooter;
	double power;
	int target;
	
	boolean complete = false;
	
	public ShootCommand(Shooter s, double p, int t) {
		shooter = s;
		power = p;
		target = t;
	}
	
	public void execute() {
		shooter.shoot(power, target, true);
		complete = shooter.spunUp;
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
