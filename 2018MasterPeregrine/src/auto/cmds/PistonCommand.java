package auto.cmds;

import subsys.Shooter;

public class PistonCommand implements Command {
	Shooter shooter;
	boolean low;
	boolean complete = false;
	
	public PistonCommand(Shooter s, boolean l) {
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
	
}
