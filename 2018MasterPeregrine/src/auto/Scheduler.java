package auto;

import java.util.ArrayList;

import auto.cmds.Command;

public class Scheduler {
	//Flags
	boolean done = false;
	boolean running = false;
	
	//Command list variables
	int i = 0;
	ArrayList<Command> commands;
	
	public Scheduler() {
		
	}
	
	/**
	 * Constructor for scheduler that takes in an ArrayList of commands
	 * @param coms and ArrayList of commands to execute
	 */
	public Scheduler(ArrayList<Command> coms) {
		commands = coms;
	}
	
	/**
	 * Runs the commands that are generated from generatePath().
	 */
	public void run() {
		Command com = commands.get(i);
		
		if (!done) {
			com.execute();
		}
		
		if (!com.isComplete()) {
			running = true;
		} 
		else {
			com.stop();
			
			if (i < commands.size()-1) {
				i++;
			}
			else {
				done = true;
				running = false;
			}
		}
	}
	
	public void addCommands(ArrayList<Command> coms) {
		commands = coms;
	}
}
