

package com.marek.nextplayer.Core;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Utils {
	
	public static final int ALPHA = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;

    public static final int HUE = 0;
    public static final int SATURATION = 1;
    public static final int BRIGHTNESS = 2;

    public static final int TRANSPARENT = 0;
    
    public static int safeLongToInt(long l) {
	    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
	        throw new IllegalArgumentException
	            (l + " cannot be cast to int without changing its value.");
	    }
	    return (int) l;
	}
    
    private static int getNewPixelRGB(int replacement, int destRGB) {
        float[] destHSB = getHSBArray(destRGB);
        float[] replHSB = getHSBArray(replacement);

        int rgbnew = Color.HSBtoRGB(replHSB[HUE], replHSB[SATURATION], destHSB[BRIGHTNESS]);
        return rgbnew;
    }

    private static boolean matches(int maskRGB, int destRGB) {
        float[] hsbMask = getHSBArray(maskRGB);
        float[] hsbDest = getHSBArray(destRGB);

        if (hsbMask[HUE] == hsbDest[HUE]
                && hsbMask[SATURATION] == hsbDest[SATURATION]
                && getRGBArray(destRGB)[ALPHA] != TRANSPARENT) {

            return true;
        }
        return false;
    }

    private static int[] getRGBArray(int rgb) {
        return new int[] { (rgb >> 24) & 0xff, (rgb >> 16) & 0xff,
                (rgb >> 8) & 0xff, rgb & 0xff };
    }

    private static float[] getHSBArray(int rgb) {
        int[] rgbArr = getRGBArray(rgb);
        return Color.RGBtoHSB(rgbArr[RED], rgbArr[GREEN], rgbArr[BLUE], null);
    }
    
    public static Color getImageColor(BufferedImage image, int x, int y) {
	    return new Color(image.getRGB(x, y));
	}
	
	public static Color getContrastColor(Color color) {
		int d = 0;
		
	    double a = 1 - ( 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue())/255;

	    if (a < 0.5)
	       d = 0;
	    else
	       d = 255;

	    return new Color(d, d, d);
	}

}
