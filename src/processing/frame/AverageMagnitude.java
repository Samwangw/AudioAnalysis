package processing.frame;

import accessing.ReadAudioFile;
import util.WavHeader;

/**
 * 短 时平均 幅度
 * 
 * @author Wei Wang
 *
 */
public class AverageMagnitude {

	public static double averageMagnitude(int[] frame) {
		double am = 0;
		for (double s : frame)
			am += Math.abs(s);
		return am;
	}

	public static double averageMagnitude(double[] frame) {
		double am = 0;
		for (double s : frame)
			am += Math.abs(s);
		return am;
	}

	public static double averageMagnitude(Frame frame) {
		double am = 0;
		for (double s : frame.samples)
			am += Math.abs(s);
		return am;
	}

	public static void main(String[] args) {
		System.out.println("test average magnitude");

		String filename = "dataset\\sample\\sunday.wav";
		WavHeader hearder = WavHeader.getWavHeader(filename);
		int bps = hearder.get_fmt().getBitsPerSample();
		int[] audios = ReadAudioFile.getSignal(filename, bps);

		int index1 = 9000;
		int framesize = 16;
		double[] sig = new double[framesize];
		for (int i = 0; i < framesize; i++)
			sig[i] = audios[index1 + i];
		System.out.println(averageMagnitude(sig));
		Frame f = new Frame(sig);
		f.norm();
		System.out.println(averageMagnitude(f));

	}

}
