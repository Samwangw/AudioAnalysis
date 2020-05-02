package processing.frame;

import accessing.ReadAudioFile;
import util.JFreeChartUtil;
import util.WavHeader;

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

	public static void main(String args[]) {
		System.out.println("test volume");
		String filename = "dataset\\standard\\1.wav";
		final int WINDOWSIZE = 256;
		final int OVERLAP = 128;
		try {
			String[] files = new String[3];
			System.out.println("Getting frame vectors from " + filename);
			files[0] = filename;
			WavHeader header = WavHeader.getWavHeader(filename);
			int[] audioBytes = ReadAudioFile.getSignal(filename, header.get_fmt().getBitsPerSample());
			double[][] frames = Frame.getFrames(audioBytes, WINDOWSIZE, OVERLAP);
			double[] volume1 = new double[frames.length];
			for (int i = 0; i < frames.length; i++) {
				// get vector for current frame
				volume1[i] = volume2(frames[i]);
			}

			filename = "dataset\\sample\\2.wav";
			System.out.println("Getting frame vectors from " + filename);
			files[1] = filename;
			header = WavHeader.getWavHeader(filename);
			audioBytes = ReadAudioFile.getSignal(filename, header.get_fmt().getBitsPerSample());
			frames = Frame.getFrames(audioBytes, WINDOWSIZE, OVERLAP);
			double[] volume2 = new double[frames.length];
			for (int i = 0; i < frames.length; i++) {
				// get vector for current frame
				volume2[i] = volume2(frames[i]);
			}

			filename = "dataset\\sample\\3.wav";
			System.out.println("Getting frame vectors from " + filename);
			files[2] = filename;
			header = WavHeader.getWavHeader(filename);
			audioBytes = ReadAudioFile.getSignal(filename, header.get_fmt().getBitsPerSample());
			frames = Frame.getFrames(audioBytes, WINDOWSIZE, OVERLAP);
			double[] volume3 = new double[frames.length];
			for (int i = 0; i < frames.length; i++) {
				// get vector for current frame
				volume3[i] = volume2(frames[i]);
			}

			JFreeChartUtil.createLineChart("output/volume test.jpg", files, volume1, volume2, volume3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
