package models;

import accessing.ReadAudioFile;
import processing.frame.Frame;
import processing.frame.Volume;
import processing.frame.ZCR;
import util.WavHeader;

public class DTW {
	public static void dtw() {
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static double[][] getFrameVectors2(String filename) {
		final int WINDOWSIZE = 512;
		final int OVERLAP = 100;
		try {
			byte[] audioBytes = ReadAudioFile.getBytes(filename);
			int len = audioBytes.length;
			int num_window = len / (WINDOWSIZE - OVERLAP) - 1;
			double[][] vecs = new double[num_window][];
			for (int i = 0; i < num_window; i++) {
				Complex[] x = new Complex[WINDOWSIZE];
				for (int j = 0; j < WINDOWSIZE; j++) {
					x[j] = new Complex(audioBytes[i * (WINDOWSIZE - OVERLAP) + j], 0);

				}
				Complex[] resutls = FFT.fft(x);

				// set vector for current window
				vecs[i] = getFrameVector(resutls);

			}
			return vecs;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get vectors of all frames.
	 * 
	 * @param filename
	 *            A path name string of an audio file.
	 * @return A 2D array of all vectors and each row corresponds a frame.
	 */
	private static double[][] getFrameVectors(String filename) {
		final int WINDOWSIZE = 256;
		final int OVERLAP = 100;
		try {
			System.out.println("Getting frame vectors from " + filename);
			WavHeader header = WavHeader.getWavHeader(filename);
			int[] audioBytes = ReadAudioFile.getSignal(filename, header.get_fmt().getBitsPerSample());
			double[][] frames = Frame.getFrames(audioBytes, WINDOWSIZE, OVERLAP);
			double[][] vecs = new double[frames.length][];
			for (int i = 0; i < frames.length; i++) {
				// set vector for current window
				vecs[i] = getFrameVector(frames[i]);
			}
			return vecs;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get a vector to represent a frame
	 * 
	 * @param x
	 *            int array of time-domain data in a frame
	 * @return
	 */
	public static double[] getFrameVector(double[] x) {
		return new double[] { Volume.volume2(x) };
		// return new double[] { ZCR.zcr(x) };
		// return Feature.getFFTV(x);
	}

	/**
	 * Get a vector to represent a frame
	 * 
	 * @param fd
	 *            complex array of time-domain data in a frame
	 * @return
	 */
	private static double[] getFrameVector(Complex[] fd) {
		double[] vector = new double[fd.length];
		for (int i = 0; i < fd.length; i++) {
			vector[i] = fd[i].abs();
		}
		return vector;
	}

	public static void main(String[] args) {
		double[][] vec1 = getFrameVectors("dataset\\standard\\1.wav");
		double[][] vec2 = getFrameVectors("dataset\\sample\\2.wav");
		double[][] vec3 = getFrameVectors("dataset\\sample\\3.wav");
		double[][] vec4 = getFrameVectors("dataset\\sample\\4.wav");
		double[][] vec5 = getFrameVectors("dataset\\sample\\5.wav");
		System.out.println(Distance.getDTWDistance(vec1, vec2));
		System.out.println(Distance.getDTWDistance(vec1, vec3));
		System.out.println(Distance.getDTWDistance(vec1, vec4));
		System.out.println(Distance.getDTWDistance(vec1, vec5));

		// double[][] vec6 = getFrameVectors("dataset\\sample\\water1.wav");
		// double[][] vec7 = getFrameVectors("dataset\\sample\\water2.wav");
		// double[][] vec8 = getFrameVectors("dataset\\sample\\apple2.wav");
		// double[][] vec9 =
		// getFrameVectors("dataset\\sample\\teacher3.wav");
		// System.out.println(Distance.getDTWDistance(vec6, vec7));
		// System.out.println(Distance.getDTWDistance(vec6, vec8));
		// System.out.println(Distance.getDTWDistance(vec6, vec9));

		// double[][] vec2 = getFrameVectors("dataset\\sample\\sucstd.wav");
		// double[][] vec3 = getFrameVectors("dataset\\sample\\suc.wav");
		// double[][] vec4 =
		// getFrameVectors("dataset\\sample\\stdshort.wav");
		// double[][] vec5 =
		// getFrameVectors("dataset\\sample\\stdlong.wav");
		// System.out.println(Distance.getDTWDistance(vec2, vec3));
		// System.out.println(Distance.getDTWDistance(vec3, vec4));
		// System.out.println(Distance.getDTWDistance(vec4, vec5));
	}

}
