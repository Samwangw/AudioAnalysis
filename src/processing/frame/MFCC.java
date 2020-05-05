package processing.frame;

import java.util.Arrays;

import accessing.ReadAudioFile;
import models.Complex;
import models.FFT;
import util.JFreeChartUtil;
import util.WavHeader;

public class MFCC {

	public static void main(String[] args) {
		System.out.println("test MFCC");
		String filename = "dataset\\sample\\soo.wav";
		WavHeader header = WavHeader.getWavHeader(filename);
		int bps = header.get_fmt().getBitsPerSample();
		int[] audios = ReadAudioFile.getSignal(filename, bps);

		double[] sig = Justification.zero(audios);
		sig = preemphasis(sig);

		double[][] frames = Frame.getFrames(sig, 512, 256);
		for (int i = 0; i < 1; i++) {
			double[] mfcc = mfcc(frames[i], 10, header.get_fmt().getSampleRate());
			// System.out.println(mfcc);
		}
	}

	public static double[] mfcc(double[] frame, int num_filters, double samplerate) {
		double[] mfcc = new double[num_filters];
		// transform frame raw data array into complex array
		int len = frame.length;
		Complex[] cFrame = new Complex[len];
		for (int i = 0; i < len; i++) {
			cFrame[i] = new Complex(frame[i], 0);
		}
		Complex[] fredomain = FFT.fft(cFrame);
		double[] powers = getPowspec(fredomain);
		double[][] fbanks = getFilterbanks(num_filters, powers.length, samplerate);
		for (int i = 0; i < fbanks.length; i++) {
			mfcc[i] = 0;
			for (int j = 0; j < fbanks[i].length; j++) {
				mfcc[i] += powers[j] * fbanks[i][j];
			}
			mfcc[i] = Math.log(mfcc[i]);
			System.out.println(mfcc[i]);
		}

		return mfcc;
	}

	/***
	 * 对信号进行预加重(Pre-Emphasis)。因为高频信号的能量通常较低，因此需要增加高频部分的能量。具体来讲预加重有三个好处：
	 * 增加高频部分的能量使得能量分布更加均衡；防止傅里叶变换的数值计算不稳定问题；有可能增加信噪比(Signal-to-Noise
	 * Ratio/SNR)。
	 * 
	 * @param frame
	 *            frame audio signal
	 * @return
	 */

	public static double[] preemphasis(double[] frame) {
		double[] sig = new double[frame.length];
		sig[0] = frame[0];
		for (int i = 1; i < frame.length; i++)
			sig[i] = frame[i] - 0.97 * frame[i - 1];
		return sig;
	}

	/***
	 * 计算功率谱
	 * 
	 * @param frame
	 * @return
	 */

	public static double[] getPowspec(Complex[] frame) {
		double[] power = new double[frame.length];
		for (int i = 0; i < frame.length / 2 + 1; i++) {
			power[i] = 1.0 / frame.length * (frame[i].re() * frame[i].re() + frame[i].im() * frame[i].im());
		}

		return power;
	}

	public static double[][] getFilterbanks(int num_filters, int sigLength, double samplerate) {
		int[] centers = getFilterCenters(sigLength, num_filters, samplerate, 300, samplerate / 2);
		double[][] fb = new double[num_filters][];
		for (int i = 0; i < num_filters; i++) {
			double[] filter = new double[sigLength / 2 + 1];
			// init filter
			for (int j = 0; j < filter.length; j++)
				filter[j] = 0;
			// build left edge
			for (int j = centers[i]; j <= centers[i + 1]; j++) {
				filter[j] = (j - centers[i]) / (double) (centers[i + 1] - centers[i]);
			}
			// buid right edge
			for (int j = centers[i + 1]; j <= centers[i + 2]; j++) {
				filter[j] = (centers[i + 2] - j) / (double) (centers[i + 2] - centers[i + 1]);
			}
			fb[i] = filter;
		}
//		JFreeChartUtil.createLineChart("output/MFCC.jpg",
//				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }, fb[0], fb[1], fb[2], fb[3], fb[4],
//				fb[5], fb[6], fb[7], fb[8], fb[9]);
		return fb;
	}

	public static double fre2Mel(double fre) {
		return 1125 * Math.log(1 + fre / 700.0);
	}

	public static double mel2Fre(double mel) {
		return 700 * (Math.exp(mel / 1125.0) - 1);
	}

	/***
	 * 
	 * @param len_fft
	 * @param num_filter
	 * @param samplerate
	 * @param minFre
	 * @param maxFre
	 * @return
	 */
	public static int[] getFilterCenters(int len_fft, int num_filter, double samplerate, double minFre, double maxFre) {
		double minMel = fre2Mel(minFre);
		double maxMel = fre2Mel(maxFre);
		double inter = (maxMel - minMel) / (num_filter + 1);
		double[] cs = new double[num_filter + 2];
		cs[0] = minMel;
		cs[cs.length - 1] = maxMel;
		for (int i = 1; i < cs.length - 1; i++) {
			cs[i] = minMel + inter * i;
		}
		for (int i = 0; i < cs.length; i++)
			cs[i] = mel2Fre(cs[i]);
		for (int i = 0; i < cs.length; i++) {
			cs[i] = Math.floor((len_fft + 1) * cs[i] / samplerate);
		}
		int[] centers = new int[cs.length];
		for (int i = 0; i < cs.length; i++) {
			centers[i] = (int) cs[i];
		}
		return centers;
	}
}
