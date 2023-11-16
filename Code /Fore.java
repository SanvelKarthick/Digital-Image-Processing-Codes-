import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Fore {
    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Merge.png"));
            BufferedImage backgroundModel = ImageIO.read(new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Input/bg.jpg"));
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int[][] originalMatrix = imageToMatrix(originalImage, width, height);
            int[][] backgroundMatrix = imageToMatrix(backgroundModel, width, height);
            int[][] differenceMatrix = computeDifference(originalMatrix, backgroundMatrix, width, height);
            int threshold = 25;
            int[][] binaryMask = thresholdMatrix(differenceMatrix, width, height, threshold);
            BufferedImage foregroundImage = createForegroundImage(originalImage, binaryMask, width, height);

            // Save the output foreground image
            File output = new File(
                    "/Users/karthikeyans/Library/Mobile Documents/com~apple~CloudDocs/Mac/Sem 3/DIP LAB/Output/Fore.png");
            ImageIO.write(foregroundImage, "png", output);
            System.out.println("Foreground image saved as outputForegroundImage.png.");

            // Display the foreground image
            showImageInFrame(foregroundImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] imageToMatrix(BufferedImage image, int width, int height) {
        int[][] matrix = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int gray = (red + green + blue) / 3;
                matrix[y][x] = gray;
            }
        }
        return matrix;
    }

    private static int[][] computeDifference(int[][] matrix1, int[][] matrix2, int width, int height) {
        int[][] difference = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                difference[y][x] = Math.abs(matrix1[y][x] - matrix2[y][x]);
            }
        }
        return difference;
    }

    private static int[][] thresholdMatrix(int[][] matrix, int width, int height, int threshold) {
        int[][] binaryMask = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                binaryMask[y][x] = (matrix[y][x] > threshold) ? 255 : 0;
            }
        }
        return binaryMask;
    }

    private static BufferedImage createForegroundImage(BufferedImage originalImage, int[][] binaryMask, int width,
            int height) {
        BufferedImage foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                int alpha = binaryMask[y][x];
                int newRgb = (alpha << 24) | (rgb & 0x00FFFFFF);
                foregroundImage.setRGB(x, y, newRgb);
            }
        }
        return foregroundImage;
    }

    private static void showImageInFrame(BufferedImage image) {
        JFrame frame = new JFrame("Foreground image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = screenSize.width - 100;
        int maxHeight = screenSize.height - 100;
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
        JLabel label = new JLabel(icon);
        frame.getContentPane().add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
