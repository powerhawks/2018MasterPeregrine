package subsys;

import edu.wpi.first.wpilibj.DriverStation;

public class Listener {

	public Listener() {
		// TODO Auto-generated constructor stub
		/** make the comments before each method more descriptive **/
	}

	public static String getFieldData() {
		return DriverStation.getInstance().getGameSpecificMessage();
	}
}
