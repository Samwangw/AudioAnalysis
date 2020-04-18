package processing.frame;

import accessing.ReadAudioFile;
import util.WavHeader;

public class Frame {
	public double[] samples;

	public Frame(int numSamples) {
		this.samples = new double[numSamples];
		for (int i = 0; i < numSamples; i++)
			this.samples[i] = 0;
	}

	public Frame(int[] samples) {
		this.samples = new double[samples.length];
		for (int i = 0; i < samples.length; i++)
			this.samples[i] = samples[i];
	}

	public Frame(double[] samples) {
		this.samples = samples;
	}

	public void norm() {
		double sum = 0;
		for (double d : this.samples)
			sum += d;
		double avg = sum / this.samples.length;
		for (int i = 0; i < this.samples.length; i++)
			this.samples[i] -= avg;
	}

	public void show() {
		for (int i = 0; i < this.samples.length; i++)
			System.out.println(this.samples[i]);
	}

	public double[] downSample(int step) {
		int len = this.samples.length;
		step++;
		double[] result = new double[len];
		for (int i = 0; i < len; i++)
			result[i] = 0;
		for (int i = 0; i * step < len; i++) {
			result[i] = this.samples[i * step];
		}
		return result;
	}

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

	public static void main(String args[]) {
		System.out.println("test average magnitude difference function (AMDF)");

		String filename = "dataset\\sample\\sunday.wav";
		WavHeader hearder = WavHeader.getWavHeader(filename);
		int bps = hearder.get_fmt().getBitsPerSample();
		int[] audios = ReadAudioFile.getSignal(filename, bps);

		int index1 = 9000;
		int framesize = 16;
		double[] sig = new double[framesize];
		for (int i = 0; i < framesize; i++)
			sig[i] = audios[index1 + i];
		Frame f = new Frame(sig);
		f.show();
		f.norm();
		System.out.println();
		f.show();
	}
}
