package subsys.sensors;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SerialPort;
import vars.Motors;

public class PixyCam {

	SerialPort Serial = new SerialPort(9600, SerialPort.Port.kUSB1);

	public PixyCam() {
		// TODO Auto-generated constructor stub
	}

	public void pixy() {
		try {
			String pixy_s = Serial.readString().trim();
			if (pixy_s.length() > 0) {
				System.out.println(pixy_s);
				Integer x = Integer.parseInt(pixy_s);
				if (x > 180) {
					Motors.driveFrontRight.set(ControlMode.PercentOutput, .15);
					Motors.driveFrontLeft.set(ControlMode.PercentOutput, .15);
					Motors.driveBackLeft.set(ControlMode.PercentOutput, .15);
					Motors.driveBackRight.set(ControlMode.PercentOutput, .15);

				} else if (x < 140) {
					Motors.driveFrontRight.set(ControlMode.PercentOutput, -.15);
					Motors.driveFrontLeft.set(ControlMode.PercentOutput, -.15);
					Motors.driveBackLeft.set(ControlMode.PercentOutput, -.15);
					Motors.driveBackRight.set(ControlMode.PercentOutput, -.15);
				} else if (x > 140 && x < 180) {
					System.out.println("stop");
					Motors.driveFrontRight.set(ControlMode.PercentOutput, 0);
					Motors.driveFrontLeft.set(ControlMode.PercentOutput, 0);
					Motors.driveBackLeft.set(ControlMode.PercentOutput, 0);
					Motors.driveBackRight.set(ControlMode.PercentOutput, 0);
				}

			}
		} catch (Exception e) {
			System.out.println("error");
		}
	}
}