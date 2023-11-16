import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Median {

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg"));

            // Define the size of the median filter kernel (e.g., 3x3)
            int kernelSize = 3;

            // Perform median filtering
            BufferedImage outputImage = applyMedianFilter(inputImage, kernelSize);

            // Save the output image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Median.jpg");
            ImageIO.write(outputImage, "jpg", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage applyMedianFilter(BufferedImage inputImage, int kernelSize) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Apply median filter to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Create an array to store pixel values in the neighborhood defined by the
                // kernel
                int[] valuesRed = new int[kernelSize * kernelSize];
                int[] valuesGreen = new int[kernelSize * kernelSize];
                int[] valuesBlue = new int[kernelSize * kernelSize];
                int count = 0;

                for (int ky = -kernelSize / 2; ky <= kernelSize / 2; ky++) {
                    for (int kx = -kernelSize / 2; kx <= kernelSize / 2; kx++) {
                        int nx = x + kx;
                        int ny = y + ky;

                        // Check if the neighbor is within the image bounds
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            int rgb = inputImage.getRGB(nx, ny);
                            valuesRed[count] = (rgb >> 16) & 0xFF;
                            valuesGreen[count] = (rgb >> 8) & 0xFF;
                            valuesBlue[count] = rgb & 0xFF;
                            count++;
                        }
                    }
                }

                // Sort the arrays to find the median values
                Arrays.sort(valuesRed, 0, count);
                Arrays.sort(valuesGreen, 0, count);
                Arrays.sort(valuesBlue, 0, count);

                // Get the median values
                int medianRed = valuesRed[count / 2];
                int medianGreen = valuesGreen[count / 2];
                int medianBlue = valuesBlue[count / 2];

                // Combine the median values into the output pixel
                int medianRGB = (medianRed << 16) | (medianGreen << 8) | medianBlue;
                outputImage.setRGB(x, y, medianRGB);
            }
        }

        return outputImage;
    }
}
