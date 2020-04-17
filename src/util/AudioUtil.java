package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Administrator
 *
 */
public class AudioUtil {

	/**
	 * 
	 * @param filename
	 *            A path name string of an audio file.
	 * @return
	 */
	private static AudioInputStream getAudioInputStreamFromFile(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("filename is null");
		}

		try {
			// first try to read file from local file system
			File file = new File(filename);
			if (file.exists()) {
				return AudioSystem.getAudioInputStream(file);
			}

			// resource relative to .class file
			InputStream is1 = AudioUtil.class.getResourceAsStream(filename);
			if (is1 != null) {
				return AudioSystem.getAudioInputStream(is1);
			}

			// resource relative to classloader root
			InputStream is2 = AudioUtil.class.getClassLoader().getResourceAsStream(filename);
			if (is2 != null) {
				return AudioSystem.getAudioInputStream(is2);
			}

			// give up
			else {
				throw new IllegalArgumentException("could not read '" + filename + "'");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("could not read '" + filename + "'", e);
		} catch (UnsupportedAudioFileException e) {
			throw new IllegalArgumentException("file of unsupported audio format: '" + filename + "'", e);
		}
	}

	/**
	 * Plays an audio file (in .wav, .mid, or .au format) in a background
	 * thread.
	 *
	 * @param filename
	 *            A pathname string of an audio file
	 * @throws IllegalArgumentException
	 *             if unable to play {@code filename}
	 * @throws IllegalArgumentException
	 *             if {@code filename} is {@code null}
	 */
	public static synchronized void play(final String filename) {
		new Thread(new Runnable() {
			public void run() {
				System.out.println("\r\nPlay the audio '" + filename + "'.");
				AudioInputStream ais = getAudioInputStreamFromFile(filename);
				stream(ais);
			}
		}).start();
	}

	/**
	 * @param ais
	 */
	private static void stream(AudioInputStream ais) {
		SourceDataLine line = null;
		int BUFFER_SIZE = 4096; // 4K buffer

		try {
			AudioFormat audioFormat = ais.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();
			byte[] samples = new byte[BUFFER_SIZE];
			int count = 0;
			while ((count = ais.read(samples, 0, BUFFER_SIZE)) != -1) {
				// 对buffer中的数据进行编辑
				int len = count * 2;
				byte[] edited_samples = new byte[samples.length * 2];
				for (int i = 0; i < samples.length; i += 2) {
					edited_samples[i * 2] = samples[i];
					edited_samples[i * 2 + 2] = samples[i];
					edited_samples[i * 2 + 1] = samples[i + 1];
					edited_samples[i * 2 + 3] = samples[i + 1];
					// if (Math.random() > 0.99F)
					// samples[i] += (byte) (Math.random() * 15);
				}
				int len2 = samples.length / 2;
				byte[] edited_samples2 = new byte[len2];
				for (int i = 0; i < len2; i += 2) {
					edited_samples2[i] = samples[i * 2];
					edited_samples2[i + 1] = samples[i * 2 + 1];
				}
				// 播放samples中的音频
				//sourceDataLineWrite(line, samples);
				sourceDataLineWrite(line, edited_samples);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} finally {
			if (line != null) {
				line.drain();
				line.close();
			}
		}
	}

	public static void sourceDataLineWrite(SourceDataLine line, byte[] samples) {
		line.write(samples, 0, samples.length);
	}

	public static void main(String[] args) {
		AudioUtil.play("dataset\\sample\\soo.wav");
	}
}
