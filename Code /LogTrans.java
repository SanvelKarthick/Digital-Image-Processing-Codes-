import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LogTrans {

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg"));

            // Perform log transformation
            BufferedImage outputImage = applyLogTransformation(inputImage);

            // Save the output image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Log.jpg");
            ImageIO.write(outputImage, "jpg", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage applyLogTransformation(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage for the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Apply log transformation to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);

                // Extract the red, green, and blue components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Apply log transformation to each component
                red = (int) (Math.log(1 + red) / Math.log(256) * 255);
                green = (int) (Math.log(1 + green) / Math.log(256) * 255);
                blue = (int) (Math.log(1 + blue) / Math.log(256) * 255);

                // Combine the transformed components
                int transformedRGB = (red << 16) | (green << 8) | blue;

                // Set the pixel value in the output image
                outputImage.setRGB(x, y, transformedRGB);
            }
        }

        return outputImage;
    }
}
