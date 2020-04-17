package processing.frame;

import accessing.ReadAudioFile;
import models.Complex;
import models.FFT;
import util.WavHeader;

public class HPS {

	public static void hps() {

	}

	public static void main(String[] args) {
		System.out.println("test Harmonic product spectrum (HPS)");
		String filename = "dataset\\sample\\soo.wav";

		WavHeader hearder = WavHeader.getWavHeader(filename);
		int bps = hearder.get_fmt().getBitsPerSample();
		int[] intaudio = ReadAudioFile.getSignal(filename, bps);
		double[] audios = Justification.norm1(intaudio, bps);
		int index1 = 15000;
		int framesize = 512;
		int padding = 15;

		Complex[] x = new Complex[framesize + padding * framesize];
		for (int j = 0; j < framesize; j++) {
			x[j] = new Complex(audios[index1 + j], 0);
		}
		for (int j = 0; j < padding * framesize; j++) {
			x[framesize + j] = new Complex(0, 0);
		}
		Complex[] results = FFT.fft(x);
		double[] fu = new double[results.length];
		for (int i = 0; i < results.length; i++) {
			fu[i] = Math.pow(results[i].abs(), 2);
			if (i < 512)
				System.out.println(intaudio[index1 + i] + "    " + (audios[index1 + i]) + "    " + fu[i]);
		}
	}
}
