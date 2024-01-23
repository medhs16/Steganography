import java.awt.Color;
public class Steganography {

	public Steganography() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Picture beach = new Picture ("beach.jpg");
		beach.explore();
		
		Picture copy = testClearLow(beach);
		copy.explore();
		
		Picture beach2 = new Picture ("beach.jpg");
		beach2.explore();
		
		Picture copy2 = testSetLow(beach2, Color.PINK);
		copy2.explore();
		
		Picture copy3 = revealPicture(copy2);
		copy3.explore(); 



	}
	/** 
	* Clear the lower (rightmost) two bits in a pixel. 
	*/ 
	public static void clearLow(Pixel p ) {
		p.setRed(removeRightmost(p.getRed()));
		p.setBlue(removeRightmost(p.getBlue()));
		p.setGreen(removeRightmost(p.getGreen()));
	}
	public static int removeRightmost (int num) {
		int answer = 0;
        int place = 0;
        int binNum = 0;
        int i = 1;
        while (num > 0) {
            place = num%2;
            binNum+=place*i;
            i*=10;
            num/=2;
        }
        binNum/=100;
        binNum*=100;
        int power = 1;
        while (binNum > 0) {
            place = binNum%10;
            answer+=place*power;
            power*=2;
            binNum/=10;
        }
        return answer;
	}
	public static Picture testClearLow(Picture p) {
		Pixel[][] list = p.getPixels2D();
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list[0].length; j++) {
				clearLow(list[i][j]);
			}
		}
		return p;
	}
	/**
	* Set the lower 2 bits in a pixel to the highest 2 bits in c
	*/
	public static void setLow (Pixel p, Color c) {
		p.setRed(p.getRed()/4*4 + c.getRed()/64);
		p.setBlue(p.getBlue()/4*4 + c.getBlue()/64);
		p.setGreen(p.getGreen()/4*4 + c.getGreen()/64);
	}
	public static Picture testSetLow(Picture p, Color c) {
		Pixel[][] list = p.getPixels2D();
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list[0].length; j++) {
				setLow(list[i][j], c);
			}
		}
		return p;
	}
	/**
	 * Sets the highest two bits of each pixel’s colors
	 * to the lowest two bits of each pixel’s color o s
	*/
	public static Picture revealPicture(Picture hidden) {
		Picture copy = new Picture(hidden);
		Pixel[][] pixels = copy.getPixels2D();
		Pixel[][] source = hidden.getPixels2D();
		for (int r = 0; r < pixels.length; r++) {
			for (int c = 0; c < pixels[0].length; c++) {	
				Color col = source[r][c].getColor();
				setLow(source[r][c],col);
			}
		}
		return copy; 
	

}
}
