package processing.frame;

public class Cepstrum {
	
	public static double cepstrum(double[] frame){
		if (frame.length < 3) {
			throw new IllegalArgumentException(
					"Frame does not has enough samples.(number of samples is " + frame.length + ")");
		}
		double cep = 0;
		return cep;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
