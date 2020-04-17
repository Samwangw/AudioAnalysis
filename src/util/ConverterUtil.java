package util;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class ConverterUtil {
	/**
	 * Convert a map3 file to wav.
	 * 
	 * @param A pathname string of a MP3 File
	 * @param A pathname string of a Wav File
	 * @throws JavaLayerException
	 */
	public static void convertMP32MAV(String MP3File, String WavFile) throws JavaLayerException {
		// MP3 to WAV
		new Converter().convert(MP3File, WavFile);

	}
	
	public static void main(String[] args) {
		// ConverterUtil.convertMP32MAV("dataset\\sample\\4.wma",
		// "dataset\\sample\\4.wav");
	}
}
