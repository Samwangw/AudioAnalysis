package processing.frame;

import accessing.ReadAudioFile;
import util.WavHeader;

/**
 * ¶ÌÊ±ÄÜ Á¿
 * 
 * @author Wei Wang
 *
 */
public class Energy {

	public static double energy(int[] frame) {
		double energy = 0;
		for (double s : frame)
			energy += s * s;
		return energy;
	}

	public static double energy(double[] frame) {
		double energy = 0;
		for (double s : frame)
			energy += s * s;
		return energy;
	}

	public static double energy(Frame frame) {
		double energy = 0;
		for (double s : frame.samples)
			energy += s * s;
		return energy;
	}

	public static void main(String args[]) {
		System.out.println("test energy");
		
		String filename = "dataset\\sample\\sunday.wav";
		WavHeader hearder = WavHeader.getWavHeader(filename);
		int bps = hearder.get_fmt().getBitsPerSample();
		int[] audios = ReadAudioFile.getSignal(filename, bps);
		
		int index1 = 9000;
		int framesize = 16;
		double[] sig = new double[framesize];
		for (int i = 0; i < framesize; i++)
			sig[i] = audios[index1 + i];
		System.out.println(energy(sig));
		Frame f = new Frame(sig);
		f.norm();
		System.out.println(energy(f));
	}
}
