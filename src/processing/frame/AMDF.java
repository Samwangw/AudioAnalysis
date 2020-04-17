package processing.frame;

import accessing.ReadAudioFile;

/**
 * AMDF (average magnitude difference function) is very close to ACF except that
 * it estimates the distance instead of similarity between a frame s(i), i = 0 ~
 * n-1, and its delayed version
 * 
 * @author Wei Wang
 *
 */
public class AMDF {
	public static int amdf(int[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		double[] amdf = new double[frame.length - 1];
		double value = 0;
		double max_value = Double.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < len; delta++) {
			value = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				value += Math.abs(frame[pos] - frame[pos + delta]);
			}
			amdf[delta - 1] = value;
			if (value > max_value) {
				max_value = value;
			}
		}
		double max_value2 = Double.NEGATIVE_INFINITY;
		int index = 0;
		int th = 10;
		for (int i = 0; i < amdf.length; i++) {
			value = max_value - amdf[i] - max_value * (1F / (amdf.length - 1) * i);
			if (value > max_value2 && i > th) {
				max_value2 = value;
				index = i;
			}
			System.out.println(amdf[i] + "    " + value);
		}
		return index + 1;
	}

	public static int amdf(double[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		double[] amdf = new double[frame.length - 1];
		double value = 0;
		double max_value = Double.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < len; delta++) {
			value = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				value += Math.abs(frame[pos] - frame[pos + delta]);
			}
			amdf[delta - 1] = value;
			if (value > max_value) {
				max_value = value;
			}
		}
		double max_value2 = Double.NEGATIVE_INFINITY;
		int index = 0;
		int th = 10;
		for (int i = 0; i < amdf.length; i++) {
			value = max_value - amdf[i] - max_value * (1F / (amdf.length - 1) * i);
			if (value > max_value2 && i > th) {
				max_value2 = value;
				index = i;
			}
			System.out.println(amdf[i] + "    " + value);
		}
		return index + 1;
	}

	public static void main(String args[]) {
		System.out.println("test average magnitude difference function (AMDF)");
		int[] audios = ReadAudioFile.getSignal("dataset\\sample\\sunday.wav", 16);
		int index1 = 9000;
		int framesize = 512;
		double[] sig = new double[framesize];
		for (int i = 0; i < framesize; i++)
			sig[i] = audios[index1 + i];
		int acf = amdf(sig);
		System.out.println(acf);
	}

}
