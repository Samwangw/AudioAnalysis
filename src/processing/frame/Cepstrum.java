package processing.frame;

import accessing.ReadAudioFile;
import util.WavHeader;

/**
 * µ¹ÆµÆ× cepstrum
 * 
 * @author Wei Wang
 *
 */
public class Cepstrum {

	public static double cepstrum(double[] frame) {
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		double cep = 0;
		return cep;
	}

	public static void main(String[] args) {
		System.out.println("test Cepstrum");
		String filename = "dataset\\sample\\soo.wav";
		WavHeader header = WavHeader.getWavHeader(filename);
		int[] audios = ReadAudioFile.getSignal(filename, header.get_fmt().getBitsPerSample());
		int index1 = 15000;
		int framesize = 512;
		double[] sig = new double[framesize];
		for (int i = 0; i < framesize; i++)
			sig[i] = audios[index1 + i];
		Frame f = new Frame(sig);
		f.show();

	}

}
