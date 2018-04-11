package auto.cmds;

import java.util.ArrayList;

/**
 * Class that allows multiple commands to be executed in parallel
 * @author Braidan
 *
 */
public class CommandGroup implements Command{
	ArrayList<Command> commands;
	
	/**
	 * The CommandGroup class binds together multiple commands and executes, stops, and reports their completion in parallel.
	 * @param coms the ArrayList of commands that will be bound together
	 */
	public CommandGroup(ArrayList<Command> coms) {
		commands = coms;
	}
	
	/**
	 * Execute the commands
	 */
	@Override
	public void execute() {
		for (Command c:commands) {
			c.execute();
		}
	}
	
	/**
	 * Stop the commands
	 */
	@Override
	public void stop() {
		for (Command c:commands) {
			c.stop();
		}
	}

	/**
	 * Reports the completion of all commands in the group
	 */
	@Override
	public boolean isComplete() {		
		for (Command c:commands) {
			if (!c.isComplete()) {
				return false;
			}
		}
		
		return true;
	}

}
