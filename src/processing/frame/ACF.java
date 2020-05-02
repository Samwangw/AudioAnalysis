package processing.frame;

import java.util.Arrays;

import accessing.ReadAudioFile;
import util.WavHeader;
import util.JFreeChartUtil;

/**
 * auto-correlation function (ACF) for pitch tracking. This is a time-domain
 * method which estimates the similarity between a frame and its delayed version
 * via the auto-correlation function
 * 
 * @author Wei Wang
 *
 */
public class ACF {

	private int acf;

	public ACF(int[] frame) {
		this.acf = acf(frame);
	}

	public static int acf(int[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		int acf = 0;
		float product = 0;
		float max_product = Float.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < len; delta++) {
			product = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				product += frame[pos] * frame[pos + delta];
			}
			if (product > max_product) {
				acf = delta;
				max_product = product;
			}
		}
		return acf;
	}

	public static int acf(int[] frame, int K) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		int acf = 0;
		float product = 0;
		float max_product = Float.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < K; delta++) {
			product = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				product += frame[pos] * frame[pos + delta];
			}
			if (product > max_product) {
				acf = delta;
				max_product = product;
			}
		}
		return acf;
	}

	public static int acf(double[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		int acf = 0;
		float product = 0;
		float max_product = Float.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < len; delta++) {
			product = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				product += frame[pos] * frame[pos + delta];
			}
			// System.out.println(delta + " " + product);
			if (product > max_product && delta - acf > 10) {
				acf = delta;
				max_product = product;
			}
		}
		return acf;
	}

	public static int acf(double[] frame, int K) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		int acf = 0;
		float product = 0;
		float max_product = Float.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < K; delta++) {
			product = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				product += frame[pos] * frame[pos + delta];
			}
			// System.out.println(delta + " " + product);
			if (product > max_product && delta - acf > 10) {
				acf = delta;
				max_product = product;
			}
		}
		return acf;
	}

	public double frequency(double sampleRate) {
		return sampleRate / (double) this.acf;
	}

	public static void main(String args[]) {
		System.out.println("test auto-correlation function (ACF)");

		String filename = "dataset\\sample\\sunday.wav";
		WavHeader hearder = WavHeader.getWavHeader(filename);
		int bps = hearder.get_fmt().getBitsPerSample();
		int[] audios = ReadAudioFile.getSignal(filename, bps);

		int index1 = 9000;
		int framesize = 512;
		double[] sig = new double[framesize];
		for (int i = 0; i < framesize; i++)
			sig[i] = audios[index1 + i];
		JFreeChartUtil.createLineChart(sig, "output/acf.jpg");
		int acf = acf(sig);
		System.out.println(acf);
	}

}
