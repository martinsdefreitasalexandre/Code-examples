import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Alexdkk (Alexandre Martins de Freitas), 23/10/1994, PT
 * 
 * The following algorithm reduces the pallete size to colorTarget (example 255)
 * Reduces max colour to any amount you prefer
 * Equalizes in a way that does not loss quality, 
 * Example main at the bottom which can be switched to any image
**/
public class ReducePixels {

	public static void define(int[][] pixels, int xLength, int yLength, int colourCount, int colourTarget) {
		while (colourCount > colourTarget) {
			for (int x = 1; x < xLength - 1; x++) {
				for (int y = 1; y < yLength - 1; y++) {
					int pixel = pixels[x][y];
					int offPixel = pixels[x+1][y];
					//right
					if (pixel != offPixel) {
						pixels[x+1][y] = convert(pixel, offPixel);
						if (pixel == pixels[x+1][y]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}	
					}
					//up
					offPixel = pixels[x][y+1];
					if (pixel != offPixel) {
						pixels[x][y+1] = convert(pixel, offPixel);
						if (pixel == pixels[x][y+1]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
					}
					//right up
					offPixel = pixels[x+1][y+1];
					if (pixel != offPixel) {
						pixels[x+1][y+1] = convert(pixel, offPixel);
						if (pixel == pixels[x+1][y+1]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
					}	
					//left
					offPixel = pixels[x-1][y];
					if (pixel != offPixel) {
						pixels[x-1][y] = convert(pixel, offPixel);
						if (pixel == pixels[x-1][y]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
						
					}
					//bottom
					offPixel = pixels[x][y-1];
					if (pixel != offPixel) {
						pixels[x][y-1] = convert(pixel, offPixel);
						if (pixel == pixels[x][y-1]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
					}
					//left bottom
					offPixel = pixels[x-1][y-1];
					if (pixel != offPixel) {
						pixels[x-1][y-1] = convert(pixel, offPixel);
						if (pixel == pixels[x-1][y-1]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
					}
					//right bottom 
					offPixel = pixels[x+1][y-1];
					if (pixel != offPixel) {
						pixels[x+1][y-1] = convert(pixel, offPixel);
						if (pixel == pixels[x+1][y-1]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
					}	
					//right up 
					offPixel = pixels[x-1][y+1];
					if (pixel != offPixel) {
						pixels[x-1][y-1] = convert(pixel, offPixel);
						if (pixel == pixels[x-1][y+1]) {
							colourCount--;
							if (colourCount == colourTarget)
								return;
						}
					}	
				}
				
				
			}
		}
	}
	
	private static int convert(int pixel1, int pixel2) {
		
		int r1 = pixel1 & 0xff;
		int g1 = (pixel1 >> 8) & 0xff;
		int b1 = (pixel1 >> 16) & 0xff;
		int a1 = (pixel1 >> 14) & 0xff;
		
		int r2 = pixel2 & 0xff;
		int g2 = (pixel2 >> 8) & 0xff;
		int b2 = (pixel2 >> 16) & 0xff;
		int a2 = (pixel2 >> 14) & 0xff;
		
		

		if(r2 > r1)
			r2--;
		else if (r2 < r1)
			r2++;
		
		if (g2 > g1)
			g2--;
		else if (g2 < g1)
			g2++;
		
		if (b2 > b1)
			b2--;
		else if (b2 < b1)
			b2++;
		
		if (a2 > a1)
			a2--;
		else if (a2 < a1)
			a2++;
		


			
		return r2 + (g2 << 8) + (b2 << 16) + (a2 << 24);
		
	}
	
	/**
	 * Example test
	 */
	public static void main(String[] args) {
		int xLength = 100;
		int yLength = 100;
		int colourTarget = 255;
		int[][] pixels = new int[xLength][yLength];
		
		//test code:
		for (int x = 0; x < xLength; x++)
			for (int y = 0; y < yLength; y++)
				pixels[x][y] = x * xLength + y; 
		//
		
		ArrayList<Integer> pallete = new ArrayList<Integer>();
		while (true) {
			pallete.clear();
			for (int x = 0; x < pixels.length; x++) {
				for (int y = 0; y < pixels[x].length; y++) {
					int colour = pixels[x][y];
					if (!pallete.contains(colour)) {
						pallete.add(colour);
					}
				}
			}
			
			int colourCount = pallete.size();
			if (colourCount <= colourTarget) {
				System.out.println(colourCount+", "+pallete);
				break;
			}
			define(pixels, xLength, yLength, colourCount, colourTarget);
		}
	}
	
}
