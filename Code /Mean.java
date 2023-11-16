import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mean {

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg"));

            // Define the size of the mean filter kernel (e.g., 3x3)
            int kernelSize = 3;

            // Perform mean filtering
            BufferedImage outputImage = applyMeanFilter(inputImage, kernelSize);

            // Save the output image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Mean.jpg");
            ImageIO.write(outputImage, "jpg", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage applyMeanFilter(BufferedImage inputImage, int kernelSize) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Apply mean filter to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compute the mean value in the neighborhood defined by the kernel
                int sumRed = 0, sumGreen = 0, sumBlue = 0;
                int count = 0;

                for (int ky = -kernelSize / 2; ky <= kernelSize / 2; ky++) {
                    for (int kx = -kernelSize / 2; kx <= kernelSize / 2; kx++) {
                        int nx = x + kx;
                        int ny = y + ky;

                        // Check if the neighbor is within the image bounds
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            int rgb = inputImage.getRGB(nx, ny);
                            sumRed += (rgb >> 16) & 0xFF;
                            sumGreen += (rgb >> 8) & 0xFF;
                            sumBlue += rgb & 0xFF;
                            count++;
                        }
                    }
                }

                // Calculate the mean values
                int meanRed = sumRed / count;
                int meanGreen = sumGreen / count;
                int meanBlue = sumBlue / count;

                // Combine the mean values into the output pixel
                int meanRGB = (meanRed << 16) | (meanGreen << 8) | meanBlue;
                outputImage.setRGB(x, y, meanRGB);
            }
        }

        return outputImage;
    }
}
