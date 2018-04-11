package vars;

/**
 * Dimensions for the field setup and deadreckoning travel positions.
 * ALL DIMENSIONS ARE IN INCHES
 */
public class Dimensions {
	/**
	 * Starting distance
	 */
	public static final double ST_DIST = 28.5;
	
	/**
	 * Angle to turn for baseline travel from starting position A/C
	 */
	public static final double BL_ANGLE = 18;
	
	/**
	 * Distance to the baseline position for starting position A/C
	 */
	public static final double BL_DIST = 80;
	
	/**
	 * Length of the robot
	 */
	public static final double LENGTH_OF_ROBOT = 38.5;
	public static final double WIDTH_OF_ROBOT = 32.5;
	
	/**
	 * Distance from the driver station to the middle of the switch
	 */
	public static final double DS_TO_M_SW_DIST = 165;
	
	/**
	 * Distance from the switch shooting position to the switch fence
	 */
	public static final double M_SW_TO_FNC_DIST = 42;
	
	/**
	 * Time from the switch shooting position to the switch fence
	 */
	public static final double M_SW_TO_FNC_TIME = 2;
	
	/**
	 * Distance from the driver station to the middle of the scale
	 */
	public static final double DS_TO_M_SC_DIST = 320;
	
	/**
	 * Distance from the scale shooting position to the wall
	 */
	public static final double M_SC_TO_WALL_DIST = 43.5;
	
	/**
	 * Time from the scale shooting position to the wall
	 */
	public static final double M_SC_TO_WALL_TIME_A = .25;
	public static final double M_SC_TO_WALL_TIME_C = 1.25;
	/**
	 * Angle to turn to travel to the left switch from starting position B
	 */
	public static final double M_SW_ANGLE = 48;
	
	/**
	 * Distance to travel to get into shooting position of the left switch from starting position B
	 */
	public static final double M_SW_L_DIST = 90;
	
	/**
	 * Distance to travel to get into shooting position of the right switch from starting position B
	 */
	public static final double M_SW_R_DIST = 80;	
}
