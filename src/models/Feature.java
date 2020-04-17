package models;

public class Feature {
	public static double getVolumn1(double[] x) {
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

	public static double getVolumn2(double[] x) {
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

	public static double getZCR(double[] x) {
		float zcr = 0;
		float sum = 0;
		float avg = 0;
		for (double s : x)
			sum += s;
		avg = sum / (float) x.length;

		for (int i = 0; i < x.length - 1; i++)
			if ((x[i] - avg) * (x[i + 1] - avg) < 0)
				zcr += 1.0;
		return zcr;
	}

	public static double[] getFFTV(int[] x) {
		float sum = 0;
		float avg = 0;
		float var = 0;
		for (int s : x) {
			sum += s;
		}
		avg = sum / (float) x.length;
		for (int s : x) {
			var += (s - avg) * (s - avg);
		}
		double[] xp = new double[x.length];
		if (0 == var)
			for (int i = 0; i < xp.length; i++) {
				xp[i] = 0;
			}
		else
			for (int i = 0; i < xp.length; i++) {
				xp[i] = (x[i] - avg) / Math.sqrt(var / ((float) x.length - 1));
			}
		Complex[] fftx = new Complex[xp.length];
		for (int i = 0; i < xp.length; i++) {
			fftx[i] = new Complex(xp[i], 0);
		}
		Complex[] results = FFT.fft(fftx);
		double[] vector = new double[results.length];
		for (int i = 0; i < results.length; i++) {
			vector[i] = results[i].abs() / (float) results.length;
		}
		return vector;
	}

}
