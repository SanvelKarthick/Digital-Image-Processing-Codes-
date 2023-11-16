// Java program to demonstrate read and write of image 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Read {
    public static void main(String args[])
            throws IOException {

        int width = 963;

        int height = 640;

        BufferedImage image = null;

        try {
            File input_file = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/sk.jpg");

            image = new BufferedImage(
                    width, height, BufferedImage.TYPE_INT_ARGB);

            // Reading input file
            image = ImageIO.read(input_file);

            System.out.println("Reading complete.");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        // WRITE IMAGE
        try {
            // Output file path
            File output_file = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Read.jpg");

            // Writing to file taking type and path as
            ImageIO.write(image, "png", output_file);

            System.out.println("Writing complete.");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    } // main() ends here
} // class ends here
