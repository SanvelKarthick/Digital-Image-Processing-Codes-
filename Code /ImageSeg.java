import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSeg {

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg"));

            // Perform image segmentation
            BufferedImage segmentedImage = segmentROI(inputImage);

            // Save the segmented image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/ImageSeg.jpg");
            ImageIO.write(segmentedImage, "jpg", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage segmentROI(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage for the segmented image
        BufferedImage segmentedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Define the threshold value (adjust as needed)
        int threshold = 128;

        // Apply segmentation to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);

                // Extract the grayscale value (assuming the image is in RGB format)
                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));

                // Check if the pixel value is above the threshold
                if (gray > threshold) {
                    // Set the pixel to white (255, 255, 255)
                    segmentedImage.setRGB(x, y, (255 << 16) | (255 << 8) | 255);
                } else {
                    // Set the pixel to black (0, 0, 0)
                    segmentedImage.setRGB(x, y, 0);
                }
            }
        }

        return segmentedImage;
    }
}
