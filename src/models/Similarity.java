package models;

import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;

import core.Core;
import util.AudioUtil;

public class Similarity {
	/**
	 * Get the similarity between two wave files using the
	 * FingerprintSimilarityComputer defined in package Musicg developed by
	 * Google.
	 * 
	 * @param sourceFile
	 *            A pathname string of a source wav file
	 * @param targetFile
	 *            A pathname string of a target wav file
	 * @return
	 */
	public static FingerprintSimilarityComputer getMusicgFingerSimComputer(String sourceFile, String targetFile) {
		try {
			Wave w1 = new Wave(sourceFile);
			Wave w2 = new Wave(targetFile);
			FingerprintSimilarityComputer fingerprint = new FingerprintSimilarityComputer(w1.getFingerprint(),
					w2.getFingerprint());
			return fingerprint;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * The similarity is calculated using FingerprintSimilarity in Musicg.
	 * 
	 * @param sourceFile
	 *            A pathname string of a source wav file
	 * @param targetFile
	 *            A pathname string of a target wav file
	 * @return
	 */
	public static float getMusicgFingSim(String sourceFile, String targetFile) {
		try {
			float sim = -1;
			FingerprintSimilarityComputer fingerprint = getMusicgFingerSimComputer(sourceFile, targetFile);
			sim = fingerprint.getFingerprintsSimilarity().getSimilarity();
			if (Core.BEBUG) {
				System.out.println("\r\n" + sourceFile + " -> " + targetFile);
				System.out.println("similarity: " + sim);
			}
			return sim;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static void main(String[] args) {
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
