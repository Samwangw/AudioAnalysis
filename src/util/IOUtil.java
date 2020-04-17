package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class IOUtil {
	public static void writeOut(String filename, String data) {
		if ("" == filename) {

		}
		try {
			File file = new File("output.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.close();

			System.out.println("Done");
		} catch (Exception e) {

		}

	}
}
