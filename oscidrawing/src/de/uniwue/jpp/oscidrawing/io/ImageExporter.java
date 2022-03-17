package de.uniwue.jpp.oscidrawing.io;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageExporter {
    public static boolean writeToPNG(String pathWithoutSuffix, BufferedImage img) {

        try {
            File f = new File(pathWithoutSuffix + ".png");

            ImageIO.write(img, "PNG", f);
        }catch (IOException e){
            return false;
        }

        return true;

    }
}
