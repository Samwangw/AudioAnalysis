package processing.frame;

public class ZCR {

	public static double zcr(double[] x) {
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

}
