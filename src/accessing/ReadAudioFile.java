package accessing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import util.CollectionUtil;;

public class ReadAudioFile {
	/**
	 * Get the byte array from a audio file.
	 * 
	 * @param filename
	 *            A pathname string of an audio file
	 * @return
	 */
	public static byte[] getBytes(String filename) {
		int totalFramesRead = 0;
		File fileIn = new File(filename);
		// somePathName is a pre-existing string whose value was
		// based on a user selection.
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
			if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
				// some audio formats may have unspecified frame size
				// in that case we may read any amount of bytes
				bytesPerFrame = 1;
			}
			byte[] audioBytes = new byte[0];
			// Set an arbitrary buffer size of 1024 frames.
			int numBytes = 1024 * bytesPerFrame;
			byte[] audioBufferBytes = new byte[numBytes];
			try {
				int numBytesRead = 0;
				int numFramesRead = 0;
				// Try to read numBytes bytes from the file.
				while ((numBytesRead = audioInputStream.read(audioBufferBytes)) != -1) {
					// Calculate the number of frames actually read.
					numFramesRead = numBytesRead / bytesPerFrame;
					totalFramesRead += numFramesRead;
					// Here, do something useful with the audio data that's
					// now in the audioBytes array...
					audioBytes = CollectionUtil.mergeByteArrays(audioBytes, audioBufferBytes);
				}
				audioInputStream.close();
				return audioBytes;
			} catch (Exception ex) {
				// Handle the error...
				audioInputStream.close();
				ex.printStackTrace();
				return audioBytes;
			}
		} catch (Exception e) {
			// Handle the error...
			e.printStackTrace();
			return null;
		}
	};

	public static int[] getSignal(String filename, int bitsPerSample) {
		byte[] bs = getBytes(filename);
		ByteBuffer wrap = ByteBuffer.wrap(bs);
		wrap.order(ByteOrder.LITTLE_ENDIAN);
		int sampleSize = (bitsPerSample + 7) / 8;
		if (1 != sampleSize && 2 != sampleSize && 4 != sampleSize && 8 != sampleSize)
			throw new IllegalArgumentException("Unknown bitsPerSample " + bitsPerSample + ".");
		int len = bs.length / sampleSize;
		int[] re = new int[len];
		switch (sampleSize) {
		case 1:
			for (int i = 0; i < len; i++)
				re[i] = (int) wrap.array()[i];
			break;
		case 2:
			for (int i = 0; i < len; i++)
				re[i] = wrap.getShort(i * 2);
			break;
		case 4:
			for (int i = 0; i < len; i++)
				re[i] = wrap.getInt(i * 4);
			break;
		default:
			break;
		}
//
//		for (int i = 0; i < re.length; i++) {
//
//			for (int j = 0; j < 30; j++)
//
//				System.out.print((int) re[i] + " ");
//			System.out.println();
//
//		}
		return re;
	}

	/**
	 * Get the pcm data from a wav file.
	 * 
	 * @param filename
	 *            A pathname string of a wav file.
	 * @return
	 * @throws Exception
	 */
	public static byte[] convertWav2Pcm(String filename) throws Exception {
		try {
			System.out.println("Convert '" + filename + "' into pcm array");
			InputStream wavStream = new FileInputStream(filename);
			// WAV转AudioInputStream
			InputStream bufferedIn = new BufferedInputStream(wavStream);
			AudioInputStream wavAudioStream = AudioSystem.getAudioInputStream(bufferedIn);
			// 将AudioInputStream WAV文件 转换为PCM
			AudioFormat baseFormat = wavAudioStream.getFormat();
			System.out.println("frame rate is " + baseFormat.getFrameRate());
			AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), // 每秒的样本数
					16, // 每个样本中的位数 16或者8，但基本都是16
					baseFormat.getChannels(), // 声道数（单声道 1 个，立体声 2 个）
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false // 指示是否以
																					// big-endian
																					// 字节顺序存储单个样本中的数据（false
																					// 意味着
																					// little-endian）
			);
			AudioInputStream pcmaudioStream = AudioSystem.getAudioInputStream(targetFormat, wavAudioStream);
			// byte[] pcmBytes = IOUtils.toByteArray(pcmaudioStream);
			byte[] pcmBytes = new byte[0];
			// Set an arbitrary buffer size of 1024 frames.
			int bytesPerFrame = pcmaudioStream.getFormat().getFrameSize();
			int numBytes = 1024 * bytesPerFrame;
			byte[] audioBytes = new byte[numBytes];
			int totalFramesRead = 0;
			int numBytesRead = 0;
			int numFramesRead = 0;
			// Try to read numBytes bytes from the file.
			while ((numBytesRead = pcmaudioStream.read(audioBytes)) != -1) {
				// Calculate the number of frames actually read.
				numFramesRead = numBytesRead / bytesPerFrame;
				totalFramesRead += numFramesRead;
				// Here, do something useful with the audio data that's
				// now in the audioBytes array...
				CollectionUtil.mergeByteArrays(pcmBytes, audioBytes);
			}
			pcmaudioStream.close();
			wavAudioStream.close();
			return pcmBytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
