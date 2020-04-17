import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import accessing.ReadAudioFile;
import models.DTW;
import models.Distance;
import models.EPD;
import models.Feature;
import models.Shazam;
import models.Similarity;
import processing.frame.ACF;
import processing.frame.Frame;
import processing.frame.Justification;
import util.AudioUtil;
import util.WavHeader;

public class Start {

	public static void main(String[] args) {
		System.out.println("Main processing start...");
		try {
			// ReadAudioFile.getSignal("dataset\\sample\\teacher1.wav",8);

			// ConverterUtil.convertMP32MAV("dataset\\sample\\4.wma",
			// "dataset\\sample\\4.wav");
			 WavHeader.getWavHeader("dataset\\sample\\sunday.wav").printInfo();
			// compare();
			// AudioUtil.play("dataset\\sample\\jl.wav");

			// testDTWdistance();

			// DTW.dtw();

			// testEDP();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("\r\nProcess end.");
		}
	}

	private static void testDTWdistance() {
		double[] a = { 1, 2, 3, 4, 5 };
		double[] b = { 1, 2, 3, 4, 4, 5 };
		double[] c = { 1, 2, 3 };

		System.out.println(Distance.getDTWDistance(a, b));
		System.out.println(Distance.getDTWDistance(a, c));
		// double[][] c={2,5,7,7,7,7,2};
	}

	private static void compare() {
		// case 1
		Similarity.getMusicgFingSim("dataset\\standard\\1.wav", "dataset\\standard\\1.wav");
		// case 2
		Similarity.getMusicgFingSim("dataset\\standard\\1.wav", "dataset\\sample\\2.wav");
		// case 3
		Similarity.getMusicgFingSim("dataset\\standard\\1.wav", "dataset\\sample\\3.wav");
		// case 4
		Similarity.getMusicgFingSim("dataset\\standard\\1.wav", "dataset\\sample\\4.wav");
		Similarity.getMusicgFingSim("dataset\\standard\\1.wav", "dataset\\sample\\5.wav");
		// case 5
		Similarity.getMusicgFingSim("dataset\\sample\\2.wav", "dataset\\sample\\4.wav");
		Similarity.getMusicgFingSim("dataset\\sample\\3.wav", "dataset\\sample\\4.wav");
		Similarity.getMusicgFingSim("dataset\\sample\\2.wav", "dataset\\sample\\5.wav");
		Similarity.getMusicgFingSim("dataset\\sample\\3.wav", "dataset\\sample\\5.wav");
		Similarity.getMusicgFingSim("dataset\\sample\\4.wav", "dataset\\sample\\5.wav");
		// Similarity.getFingSim_Musicg("dataset\\standard\\1.wav",
		// "dataset\\sample\\012.wav");
		// Similarity.getFingSim_Musicg("dataset\\sample\\2.wav",
		// "dataset\\sample\\012.wav");
		// Similarity.getFingSim_Musicg("dataset\\sample\\3.wav",
		// "dataset\\sample\\012.wav");
		// Similarity.getFingSim_Musicg("dataset\\sample\\4.wav",
		// "dataset\\sample\\012.wav");
		// Similarity.getFingSim_Musicg("dataset\\sample\\5.wav",
		// "dataset\\sample\\012.wav");

	}
}
