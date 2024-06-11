package utils;


public class SimplexNoiseTest {

	public static void main(String[] args) {
		for(int x = 0; x < 100; x++) {
			for(int y = 0; y < 100; y++) {
				double val = SimplexNoise.noise(x/ 10.0, y / 10.0);
				System.out.printf("%.2f ", val);
			}
			System.out.println();
		}
	}
	
}
