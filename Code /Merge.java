import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Merge {

    public static void main(String[] args) {
        try {
            // Load the foreground and background images
            BufferedImage foregroundImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/Tiger.png"));
            BufferedImage backgroundImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/bg.jpg"));

            // Create a new BufferedImage with the same dimensions as the background image
            BufferedImage mergedImage = new BufferedImage(
                    backgroundImage.getWidth(),
                    backgroundImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            // Create a Graphics2D object to draw on the merged image
            Graphics2D g2d = mergedImage.createGraphics();

            // Draw the background image onto the merged image
            g2d.drawImage(backgroundImage, 0, 0, null);

            // Set the composite mode to combine the foreground with the background
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
            g2d.setComposite(alphaComposite);

            // Draw the foreground image onto the merged image
            g2d.drawImage(foregroundImage, 0, 0, null);

            // Dispose of the Graphics2D object
            g2d.dispose();

            // Save the merged image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Merge.png");
            ImageIO.write(mergedImage, "png", output);

            System.out.println("Images merged and saved as mergedImage.png.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
