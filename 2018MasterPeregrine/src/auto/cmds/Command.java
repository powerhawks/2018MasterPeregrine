package auto.cmds;

public interface Command {
	public double target = 0;
	public boolean complete = false;
	
	/**
	 * Execution method for the command
	 * @return if the command is still running or has finished
	 */
	public void execute();
	
	/**
	 * Stops the subsystem
	 */
	public void stop();
	
	/**
	 * Returns if the command is complete or not
	 */
	public boolean isComplete();
}
