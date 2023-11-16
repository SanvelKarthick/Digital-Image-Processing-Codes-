import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sharp2 {

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg"));

            // Perform image sharpening
            BufferedImage outputImage = sharpenImage(inputImage);

            // Save the output image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Sharp2.jpg");
            ImageIO.write(outputImage, "jpg", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage sharpenImage(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Define a Laplacian kernel (3x3)
        int[][] laplacianKernel = {
                { 0, -1, 0 },
                { -1, 4, -1 },
                { 0, -1, 0 }
        };

        // Apply convolution to each pixel
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int red = 0, green = 0, blue = 0;

                // Convolution operation
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int rgb = inputImage.getRGB(x + kx, y + ky);
                        int kernelValue = laplacianKernel[ky + 1][kx + 1];

                        // Extract red, green, and blue components
                        red += ((rgb >> 16) & 0xFF) * kernelValue;
                        green += ((rgb >> 8) & 0xFF) * kernelValue;
                        blue += (rgb & 0xFF) * kernelValue;
                    }
                }

                // Ensure the values are within the valid range [0, 255]
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                // Combine the sharpened components into the output pixel
                int sharpenedRGB = (red << 16) | (green << 8) | blue;
                outputImage.setRGB(x, y, sharpenedRGB);
            }
        }

        return outputImage;
    }
}
