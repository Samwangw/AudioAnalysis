package models;

import java.util.ArrayList;
import java.util.Arrays;

import accessing.ReadAudioFile;
import processing.frame.Frame;
import processing.frame.Justification;
import processing.frame.Volume;

/**
 * End point detection
 * 
 * @author Wei Wang
 *
 */
public class EPD {

	public static int[] getEPbyVolume(double[] sigs) {
		int[] endpoints = new int[] { 0, sigs.length - 1 };
		final int WINDOWSIZE = 256;
		final int OVERLAP = 100;
		double max = Float.NEGATIVE_INFINITY;
		double min = Float.POSITIVE_INFINITY;
		try {
			int len = sigs.length;
			int num_window = len / (WINDOWSIZE - OVERLAP) - 1;
			endpoints = new int[] { 0, num_window - 1 };
			double[] vols = new double[num_window];
			for (int i = 0; i < num_window; i++) {
				double[] x = new double[WINDOWSIZE];
				for (int j = 0; j < WINDOWSIZE; j++) {
					x[j] = sigs[i * (WINDOWSIZE - OVERLAP) + j];

				}
				vols[i] = Volume.volume1(x);
			}
			for (int i = 0; i < vols.length; i++) {
				if (vols[i] > max)
					max = vols[i];
				if (vols[i] < min)
					min = vols[i];
			}
			boolean foundstart = false;
			boolean foundend = false;
			double th = max * 0.1;
			for (int i = endpoints[0] + 1; i < vols.length; i++) {
				// System.out.println(i + ":" + vols[i] + " " + max);
				if (foundstart && foundend)
					break;
				if (!foundstart && vols[i] < th) {
					endpoints[0] = i;
				} else
					foundstart = true;
				if (!foundend && vols[vols.length - 1 - i] < th) {
					endpoints[1] = vols.length - 1 - i;
				} else
					foundend = true;
			}
			return endpoints;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static int[] getEPbyVolume(double[][] sig) {
		int[] endpoints = new int[] { 0, sig.length - 1 };
		double max = Float.NEGATIVE_INFINITY;
		double min = Float.POSITIVE_INFINITY;
		try {
			double[] vols = new double[sig.length];
			for (int i = 0; i < sig.length; i++) {
				vols[i] = Volume.volume1(sig[i]);
			}
			for (int i = 0; i < vols.length; i++) {
				if (vols[i] > max)
					max = vols[i];
				if (vols[i] < min)
					min = vols[i];
			}
			boolean foundstart = false;
			boolean foundend = false;
			double th = max * 0.1;
			for (int i = endpoints[0] + 1; i < vols.length; i++) {
				// System.out.println(i + ":" + vols[i] + " " + max);
				if (foundstart && foundend)
					break;
				if (!foundstart && vols[i] < th) {
					endpoints[0] = i;
				} else
					foundstart = true;
				if (!foundend && vols[vols.length - 1 - i] < th) {
					endpoints[1] = vols.length - 1 - i;
				} else
					foundend = true;
			}
			return endpoints;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static int[] getEPbyVolumeTh(double[][] sig) {
		ArrayList<Integer> endpoints = new ArrayList();
		double max = Float.NEGATIVE_INFINITY;
		double min = Float.POSITIVE_INFINITY;
		try {
			double[] vols = new double[sig.length];
			for (int i = 0; i < sig.length; i++) {
				vols[i] = Volume.volume1(sig[i]);
			}
			for (int i = 0; i < vols.length; i++) {
				if (vols[i] > max)
					max = vols[i];
				if (vols[i] < min)
					min = vols[i];
			}
			for (int i = 0; i < vols.length; i++)
				if (vols[i] < ((max - min) * 0.1 + min)) {
					endpoints.add(i);
					System.out.println(max + "    " + vols[i]);
				} else {
					System.out.println(0 + "    " + vols[i]);
				}

			int[] re = new int[endpoints.size()];
			for (int i = 0; i < re.length; i++)
				re[i] = endpoints.get(i);
			return re;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) {
		int[] audios = ReadAudioFile.getSignal("dataset\\sample\\sunday.wav", 16);
		double[] sig = Justification.zero(audios);
		double[][] frames = Frame.getFrames(sig, 256, 128);
		System.out.println(frames.length);
		System.out.println(Arrays.toString(EPD.getEPbyVolume(frames)));
	}

}
