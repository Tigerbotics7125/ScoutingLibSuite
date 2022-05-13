package cubscout.utilities;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Util {
  
  public static Color getAverageColor(BufferedImage image) {
    int red, green, blue;
    int redAvg = 0, greenAvg = 0, blueAvg = 0;

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        // convert rgb to ints
        int rgb = image.getRGB(j, i);
        red = (rgb >> 16) & 0xFF;
        green = (rgb >> 8) & 0xFF;
        blue = rgb & 0xFF;

        redAvg = redAvg + red / 2;
        greenAvg = greenAvg + green / 2;
        blueAvg = blueAvg + blue / 2;
      }
    }


    return new Color(redAvg, greenAvg, blueAvg);
  }
}
