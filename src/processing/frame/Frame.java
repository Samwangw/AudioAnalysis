package processing.frame;

public class Frame {
	public static double[][] getFrames(int[] sigs, int framesize, int overlap) {
		int len = sigs.length / (framesize - overlap) - 1;
		double[][] frames = new double[len][];
		for (int i = 0; i < len; i++) {
			double[] frame = new double[framesize];
			for (int j = 0; j < framesize; j++) {
				frame[j] = sigs[i * (framesize - overlap) + j];
			}
			frames[i] = frame;
		}
		return frames;
	}

	public static double[][] getFrames(double[] sigs, int framesize, int overlap) {
		int len = sigs.length / (framesize - overlap) - 1;
		double[][] frames = new double[len][];
		for (int i = 0; i < len; i++) {
			double[] frame = new double[framesize];
			for (int j = 0; j < framesize; j++) {
				frame[j] = sigs[i * (framesize - overlap) + j];
			}
			frames[i] = frame;
		}
		return frames;
	}
}
