package models;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import accessing.ReadAudioFile;

public class Shazam {
	public static void run(String filename) {
		System.out.println("Shazam is running.");
		try {
			File fileIn = new File(filename);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			int bitsPerSample = audioInputStream.getFormat().getSampleSizeInBits();
			boolean little = !audioInputStream.getFormat().isBigEndian();
			System.out.println(bitsPerSample);
			System.out.println(little);

			byte[] audioByte = ReadAudioFile.getBytes(filename);
			int[] audioSample = getSamples(audioByte, bitsPerSample, little);
			for (int i = 0; i < audioSample.length; i++) {
				// System.out.println(audioSample[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int[] getSamples(byte[] sampleBytes, int sampleSize, boolean little) {
		int[] samples = null;
		sampleSize = (sampleSize + 7) / 8;
		
		byte[] temp = new byte[sampleSize];
		samples = new int[sampleBytes.length / sampleSize];
		System.out.println("SampleBytes length: " + sampleBytes.length);
		System.out.println("SampleSize: " + sampleSize);
		for (int i = 0; i < samples.length; i++) {
			System.arraycopy(sampleBytes, i * sampleSize, temp, 0, sampleSize);
			ByteBuffer buffer = ByteBuffer.wrap(temp);
			if (little)
				buffer.order(ByteOrder.LITTLE_ENDIAN);
			else
				buffer.order(ByteOrder.BIG_ENDIAN);
			switch (sampleSize) {
			case 1:
				break;
			case 2:
				samples[i] = buffer.getShort();
				break;
			default:
				break;
			}

		}
		return samples;
	}

}
