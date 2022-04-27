package engine.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    public static BufferedImage loadImage(File file){
        try {
            return toCompatibleImage(ImageIO.read(file));
        } catch (IOException e){
            System.out.println(e);
            System.out.println("Image load could not happen:" + file);
        }
        return null;
    }

    public static BufferedImage toCompatibleImage(final Image img) {
        //Code from https://stackoverflow.com/questions/31325742/java-awt-graphics-graphics-drawimage-is-too-slow-what-is-wrong
        //Reason performance
        /*
         * if image is already compatible and optimized for current system settings, simply return it
         */
        BufferedImage image = (BufferedImage) img;

        if (image.getColorModel().equals(GFX_CONFIG.getColorModel())) {
            return image;
        }

        // image is not optimized, so create a new image that is
        final BufferedImage new_image = GFX_CONFIG.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        final Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }
}
