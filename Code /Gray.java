import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gray {
    public static void main(String args[]) throws IOException {
        BufferedImage img = null;
        File f = null;

        // Read the input image
        try {
            f = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/wall.jpg");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }

        // Get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();

        // Get the pixel data as an array
        int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);

        // Convert to grayscale
        for (int i = 0; i < pixels.length; i++) {
            int p = pixels[i];

            int a = (p >> 24) & 0xff;
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;

            // Calculate the average of RGB values
            int avg = (r + g + b) / 3;

            // Replace RGB value with the grayscale value
            p = (a << 24) | (avg << 16) | (avg << 8) | avg;

            pixels[i] = p;
        }

        // Set the modified pixel data back to the image
        img.setRGB(0, 0, width, height, pixels, 0, width);

        // Write the grayscale image
        try {
            f = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Gray.jpg");
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
