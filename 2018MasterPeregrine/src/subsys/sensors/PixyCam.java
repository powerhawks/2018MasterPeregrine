package subsys.sensors;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SerialPort;
import vars.Motors;

/**
 * The PixyCam Sensor used on the robot
 * <br>Note: The PixyCam was never implemented or used however, this code was tested with moderate success. Recommend using this as a reference only. </br>
 * @author Power Hawks Controls
 *
 */
@Deprecated
public class PixyCam {
	SerialPort Serial = new SerialPort(9600, SerialPort.Port.kUSB1);
	
	/**
	 * Centered the robot on an object as it was detected by the PixyCam
	 * <br>Note: this was run only experimentally and mainly just moved the robot back and forth based on the proximity of the detected object</br>
	 */
	public void pixy() {
		try {
			String pixy_s = Serial.readString().trim();
			if (pixy_s.length() > 0) {
				System.out.println(pixy_s); //Debug
				Integer x = Integer.parseInt(pixy_s);
				if (x > 180) { //Drive FORWARDS if the object is too far away
					Motors.driveFrontRight.set(ControlMode.PercentOutput, .15);
					Motors.driveFrontLeft.set(ControlMode.PercentOutput, .15);
					Motors.driveBackLeft.set(ControlMode.PercentOutput, .15);
					Motors.driveBackRight.set(ControlMode.PercentOutput, .15);

				} 
				else if (x < 140) { //Drive BACKWARDS if the object is too close
					Motors.driveFrontRight.set(ControlMode.PercentOutput, -.15);
					Motors.driveFrontLeft.set(ControlMode.PercentOutput, -.15);
					Motors.driveBackLeft.set(ControlMode.PercentOutput, -.15);
					Motors.driveBackRight.set(ControlMode.PercentOutput, -.15);
				} 
				else if (x > 140 && x < 180) { //STOP if the object is in the right range
					System.out.println("stop");
					Motors.driveFrontRight.set(ControlMode.PercentOutput, 0);
					Motors.driveFrontLeft.set(ControlMode.PercentOutput, 0);
					Motors.driveBackLeft.set(ControlMode.PercentOutput, 0);
					Motors.driveBackRight.set(ControlMode.PercentOutput, 0);
				}

			}
		} 
		catch (Exception e) {
			System.out.println("error");
		}
	}
}