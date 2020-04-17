package processing.frame;

public class Volume {
	/**
	 * Get the volume by sum of absolute samples within each frame
	 * 
	 * @param frame
	 *            amplitudes
	 * @return
	 */
	public static double volume1(double[] x) {
		double vol = 0;
		float sum = 0;
		float avg = 0;
		for (double s : x)
			sum += s;
		avg = sum / x.length;
		for (double s : x)
			vol += Math.abs(s - avg);
		return vol;
	}

	/**
	 * Get the volume by 10 times the 10-based logarithm of the sum of sample
	 * squares.
	 * 
	 * @param x
	 *            frame amplitudes
	 * @return
	 */
	public static double volume2(double[] x) {
		double vol = 0;
		float sum = 0;
		float avg = 0;
		for (double s : x)
			sum += s;
		avg = sum / (float) x.length;
		for (double s : x)
			vol += (s - avg) * (s - avg);
		vol = 10 * Math.log10(vol + Float.MIN_VALUE);
		return vol;
	}

}
