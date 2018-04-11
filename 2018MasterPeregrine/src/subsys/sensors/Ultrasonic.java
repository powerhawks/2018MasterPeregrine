package subsys.sensors;

import java.util.Arrays;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {
	AnalogInput input;
	double vScale = 5.0/512.0;
	
	public final static int AFT = 0;
	public final static int PORT = 1;
	public final static int STBD = 2;
	
	public Ultrasonic(int p) {
		input = new AnalogInput(p);
	}
	
	public double getData() {
		return vScale / map(input.getValue(), 0, 4096, 0, 5);
	}
	
	public double getDistance() {
		double[] data = new double[3];
		
		for (int x = 0; x < 3; x++) {
			data[x] = getData();
		}
		Arrays.sort(data);
		
		return data[1];
	}
	
	private static double map(double x, double inMin, double inMax, double outMin, double outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
}
