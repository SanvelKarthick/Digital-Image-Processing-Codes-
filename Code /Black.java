import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Black {
    public static void main(String[] args) throws IOException {
        BufferedImage img = null;
        File inputFile = null;
        File outputFile = null;

        // Read the input image
        try {
            inputFile = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg");
            img = ImageIO.read(inputFile);
        } catch (IOException e) {
            System.out.println(e);
        }

        // Get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();

        // Create a new BufferedImage for the black and white image
        BufferedImage bwImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Iterate through each pixel to convert to black and white
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = img.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Calculate grayscale value using the luminance method (weighted average)
                int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);

                // Set the grayscale value for the pixel in the new image
                bwImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }

        // Write the black and white image
        try {
            outputFile = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Black.jpg");
            ImageIO.write(bwImage, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
