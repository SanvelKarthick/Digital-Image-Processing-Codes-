import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class SepRGB {
    public static void main(String args[]) throws IOException {
        BufferedImage image = null;
        File file = null;
        try {
            file = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/car.jpg");
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = ScreenSize.width - 100;
        int maxHeight = ScreenSize.height - 100;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int newWidth = imageWidth;
        int newHeight = imageHeight;
        if (imageWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) ((double) newWidth / imageWidth * imageHeight);
        }
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) ((double) newHeight / imageHeight * imageWidth);
        }
        ImageIcon icon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        JPanel imagePanel = new JPanel(new GridLayout(1, 4));
        JLabel originalLabel = new JLabel(icon);
        imagePanel.add(originalLabel);

        BufferedImage redImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                p = (a << 24) | (r << 16) | (0 << 8) | 0;
                redImage.setRGB(x, y, p);
            }
        }
        ImageIcon redIcon = new ImageIcon(redImage);
        JLabel redLabel = new JLabel(redIcon);
        imagePanel.add(redLabel);

        BufferedImage greenImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int g = (p >> 8) & 0xff;
                p = (a << 24) | (0 << 16) | (g << 8) | 0;
                greenImage.setRGB(x, y, p);
            }
        }
        ImageIcon greenIcon = new ImageIcon(greenImage);
        JLabel greenLabel = new JLabel(greenIcon);
        imagePanel.add(greenLabel);

        BufferedImage blueImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int b = p & 0xff;
                p = (a << 24) | (0 << 16) | (0 << 8) | b;
                blueImage.setRGB(x, y, p);
            }
        }
        ImageIcon blueIcon = new ImageIcon(blueImage);
        JLabel blueLabel = new JLabel(blueIcon);
        imagePanel.add(blueLabel);

        JFrame frame = new JFrame("Original,Red,Green and Blue Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(imagePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Save the red, green, and blue images
        File redFile = new File(
                "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/RGB/Red.jpg");
        File greenFile = new File(
                "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/RGB/Green.jpg");
        File blueFile = new File(
                "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/RGB/Blue.jpg");

        try {
            ImageIO.write(redImage, "png", redFile);
            ImageIO.write(greenImage, "png", greenFile);
            ImageIO.write(blueImage, "png", blueFile);
            System.out.println("Red, Green, and Blue channels saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
