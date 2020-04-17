package models;

import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;

import core.Core;

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
}
