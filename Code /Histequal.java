import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Histequal {

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg"));

            // Perform histogram equalization
            BufferedImage outputImage = equalizeHistogram(inputImage);

            // Save the output image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Hist.jpg");
            ImageIO.write(outputImage, "jpg", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage equalizeHistogram(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Calculate the histogram of the input image
        int[] histogram = new int[256];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                histogram[gray]++;
            }
        }

        // Calculate the cumulative distribution function (CDF) of the histogram
        int[] cdf = new int[256];
        cdf[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + histogram[i];
        }

        // Normalize the CDF to the range [0, 255]
        int cdfMin = cdf[0];
        for (int i = 0; i < 256; i++) {
            cdf[i] = (int) (255.0 * (cdf[i] - cdfMin) / (width * height - cdfMin));
        }

        // Apply histogram equalization to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                int equalizedGray = cdf[gray];
                int equalizedRGB = (equalizedGray << 16) | (equalizedGray << 8) | equalizedGray;
                outputImage.setRGB(x, y, equalizedRGB);
            }
        }

        return outputImage;
    }
}
