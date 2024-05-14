import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//            // path image
//            URL imageU
//            BufferedImage image = loadImage("D:\\Clion\\IntelliJ\\Image-to-Ascii\\Season-two_shane_walsh.png");
//
//            if (image == null){
//                System.out.println("No image found");
//                return;
//            }
//
//            //convert the image to ascii art
//        String asciiArt = convertToAscii(image);
//            System.out.println(asciiArt);


        BufferedImage image = null;
        int ImageScale = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter image URL: ");
            String ImageURLString = scanner.nextLine();
            System.out.println("Enter image scale: ");
            ImageScale = scanner.nextInt();
            URL imageUrl = new URL(ImageURLString);
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String asciiArt = convertToAscii(image, ImageScale);
        System.out.println(asciiArt);

    }

    //load img
    private static BufferedImage loadImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image " + imagePath);
            e.printStackTrace();
        }
        return image;
    }

    private static String convertToAscii(BufferedImage image, int scale) {
        // Define ASCII characters to represent different brightness levels
        String asciiChars = "@%#*+=-:. ";

        StringBuilder asciiArt = new StringBuilder();

        // Get the scaled dimensions
        int scaledWidth = image.getWidth() / scale;
        int scaledHeight = image.getHeight() / scale;

        // Iterate through each pixel of the scaled image
        for (int y = 0; y < scaledHeight; y++) {
            for (int x = 0; x < scaledWidth; x++) {
                // Calculate the average brightness of the scaled block of pixels
                int totalBrightness = 0;
                for (int dy = 0; dy < scale; dy++) {
                    for (int dx = 0; dx < scale; dx++) {
                        int pixelX = x * scale + dx;
                        int pixelY = y * scale + dy;
                        int rgb = image.getRGB(pixelX, pixelY);
                        int red = (rgb >> 16) & 0xFF;
                        int green = (rgb >> 8) & 0xFF;
                        int blue = rgb & 0xFF;
                        totalBrightness += (red + green + blue) / 3;
                    }
                }
                int blockBrightness = totalBrightness / (scale * scale);

                // Map brightness to ASCII character
                int index = (int) (blockBrightness / 255.0 * (asciiChars.length() - 1));
                char asciiChar = asciiChars.charAt(index);

                // Append the ASCII character to the ASCII art string
                asciiArt.append(asciiChar);
            }
            // Add a newline character after each row of ASCII characters
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }

}