package vars;

/**
 * Controller mapping based on primary drive team preferences preferences
 * @author Power Hawks Controls
 *
 */
@SuppressWarnings("javadoc")
public class ControllerMap {
	/* driver controls */
	public static final int DRIVER_PORT = 0;
	public static final int LEFT_STICK = 1; // drive left side
	public static final int RIGHT_STICK = 5; // drive right side
	public static final int SHIFT_GEAR = 6; // switch gears
	public static final int DRIVER_TOGGLE = 5; // enable power take off
	
	/* operatoc00r controls */
	public static final int OPERATOR_PORT = 1;
	public static final int HOOK_EXTEND = 3;
	public static final int INTAKE = 1; // intake cube
	public static final int INTAKE_OVERRIDE = 7; //Override
//	public static final int R_INTAKE = 1; // intake right side
//	public static final int L_INTAKE = 1; // intake left side
	public static final int OUTTAKE = 2; // expel cube
	public static final int OMEGA = 3; //Spits out cube from shooter motor
	public static final int SWITCH_SHOOT = 5; // speed up for switch
	public static final int LOW_SCALE_SHOOT = 4;
	public static final int HIGH_SCALE_SHOOT = 6; // speed up for scale
//	public static final int SHOOT = 3; // move cube into launch
	public static final int UP_DPAD = 0; // 60 degrees
	public static final int DOWN_DPAD = 180; // 30 degrees
	public static final int TRIGGER_LEFT = 2; //Move arms in
	public static final int TRIGGER_RIGHT = 3; //Move arms out
}
