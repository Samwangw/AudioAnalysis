import models.DTW;
import models.EPD;

public class Start {

	public static void main(String[] args) {
		System.out.println("Main processing start...");
		try {
			DTW.main(args);
			// testEDP();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("\r\nProcess end.");
		}
	}
}
