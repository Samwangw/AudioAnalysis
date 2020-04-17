package processing.sample;

import accessing.ReadAudioFile;
import processing.frame.Justification;
import util.IOUtil;
import util.WavHeader;

/**
 * Sample point
 * @author Wei Wang
 *
 */
public class Sample {
	public static void main(String[] args) {
		System.out.println("test sample");
		String filename = "dataset\\sample\\sunday.wav";

		WavHeader hearder = WavHeader.getWavHeader(filename);
		int bps = hearder.get_fmt().getBitsPerSample();
		int[] intaudio = ReadAudioFile.getSignal(filename, bps);
		int index = 15000;
		int framesize = 100;

		double[] sig = Justification.norm1(intaudio, bps);
		
		
		for (int i = 0; i < framesize; i++) {
			System.out.println(sig[index+i]);
		}
	}
}
