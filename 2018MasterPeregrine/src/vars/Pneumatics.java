package vars;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics {
	public static DoubleSolenoid shifter = new DoubleSolenoid(0, 1);
	public static DoubleSolenoid powerTakeoff = new DoubleSolenoid(2, 3);
	public static DoubleSolenoid shooterPiston = new DoubleSolenoid(4, 5);
}
