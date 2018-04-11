package util;

public class Utility {
	public static boolean inRange(double x, double target, double bound) {
		return x < target + bound && x > target - bound;
	}

	public static void configurePID(double p, double i, double d, MiniPID pid) {
		pid.setP(p); pid.setI(i); pid.setD(d);
	}
}
