package processing.frame;

import accessing.ReadAudioFile;

/**
 * normalized squared difference function(NSDF)
 * 
 * @author Wei Wang
 *
 */
public class NSDF {
	public static int nsdf(int[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		int nsdf = 0;
		float product = 0;
		float max_product = Float.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < len; delta++) {
			product = 0;
			float sums1 = 0;
			float sums2 = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				product += 2 * frame[pos] * frame[pos + delta];
				sums1 += frame[pos] * frame[pos];
				sums2 += frame[pos + delta] * frame[pos + delta];
			}
			product = product / (sums1 + sums2);
			if (product > max_product && delta - nsdf > 10) {
				nsdf = delta;
				max_product = product;
			}
		}
		return nsdf;
	}

	public static int nsdf(double[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		int nsdf = 0;
		float product = 0;
		float max_product = Float.NEGATIVE_INFINITY;
		int len = frame.length;

		for (int delta = 1; delta < len; delta++) {
			product = 0;
			float sums1 = 0;
			float sums2 = 0;
			for (int pos = 0; pos < len - 1 - delta; pos++) {
				product += 2 * frame[pos] * frame[pos + delta];
				sums1 += frame[pos] * frame[pos];
				sums2 += frame[pos + delta] * frame[pos + delta];
			}
			product = product / (sums1 + sums2);
			// System.out.println(delta + " " + product);
			if (product > max_product && delta - nsdf > 10) {
				nsdf = delta;
				max_product = product;
			}
		}
		return nsdf;
	}

	public static void main(String args[]) {
		System.out.println("test normalized squared difference function (NSDF)");
		int[] audios = ReadAudioFile.getSignal("dataset\\sample\\soo.wav", 8);
		double[] sig = Justification.zero(audios);
		double[][] frames = Frame.getFrames(sig, 350, 175);
		for (int i = 0; i < frames.length; i++) {
			int nsdf = nsdf(frames[i]);
			System.out.println(nsdf);
		}
	}
}
