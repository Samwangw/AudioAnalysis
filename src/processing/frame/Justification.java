package processing.frame;

public class Justification {
	public static double[] zero(double[] sig) {
		double sum = 0;
		double avg = 0;
		for (double s : sig)
			sum += s;
		avg = sum / sig.length;
		double[] out = new double[sig.length];
		for (int i = 0; i < sig.length; i++)
			out[i] = sig[i] - avg;
		return out;
	}

	public static double[] zero(int[] sig) {
		double sum = 0;
		double avg = 0;
		for (double s : sig)
			sum += s;
		avg = sum / sig.length;
		double[] out = new double[sig.length];
		for (int i = 0; i < sig.length; i++)
			out[i] = sig[i] - avg;
		return out;
	}

	public static double[] norm1(int[] sig, double bps) {
		double out[] = new double[sig.length];
		double factor = Math.pow(2, bps-1);
		for (int i = 0; i < sig.length; i++) {
			out[i] = sig[i] / factor;
		}
		return out;
	}

	public static double[] norm1(double[] sig, double bps) {
		double out[] = new double[sig.length];
		double factor = Math.pow(2, bps)/2.f;
		for (int i = 0; i < sig.length; i++) {
			out[i] = sig[i] / factor;
		}
		return out;
	}
}
