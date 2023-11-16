import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GrayNeg {
    public static void main(String[] args) {
        try {
            // Load the grayscale image with the corrected file path
            BufferedImage image = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Gray.jpg"));

            // Get the image dimensions
            int width = image.getWidth();
            int height = image.getHeight();

            // Iterate through each pixel and calculate the negative
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xff;
                    int gray = (pixel >> 16) & 0xff; // Assuming the image is grayscale
                    int negativeGray = 255 - gray;
                    int negativePixel = (alpha << 24) | (negativeGray << 16) | (negativeGray << 8) | negativeGray;
                    image.setRGB(x, y, negativePixel);
                }
            }

            // Save the negative image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/GrayNeg.jpg");
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
