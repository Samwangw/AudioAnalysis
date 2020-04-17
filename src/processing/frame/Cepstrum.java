package processing.frame;

import accessing.ReadAudioFile;
import models.Complex;
import models.FFT;
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
		try {
			int len = frame.length;
			Complex[] cframes = new Complex[len];
			for (int i = 0; i < len; i++) {
				cframes[i] = new Complex(frame[i], 0);
			}
			Complex[] fftf = FFT.fft(cframes);
			double[] mods = new double[fftf.length];
			for (int i = 0; i < fftf.length; i++) {
				mods[i] = Math.log(fftf[i].abs());
			}
			Complex[] logfftf = new Complex[fftf.length];
			for (int i = 0; i < len; i++) {
				logfftf[i] = new Complex(mods[i], 0);
			}
			Complex[] results = FFT.ifft(logfftf);
			for (int i = 0; i < results.length; i++) {
				System.out.println(fftf[i].abs());
				//System.out.println(mods[i] + "    " + results[i].abs());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		cepstrum(f.samples);
	}

}
