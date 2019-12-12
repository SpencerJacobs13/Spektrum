import milchreis.imageprocessing.*;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.List;

public class Model {
    private Controller controller;
    private BufferedImage bufferedImage;
    protected Map pixelMap;
    protected int[] colorHex1;
    int totalSize;

    public Model(Controller con, BufferedImage buffImage) {
        this.bufferedImage = buffImage;
        this.controller = con;

        getImageColors(buffImage);
    }

    //extracting the colors of each pixel into a 2D array.
    private void getImageColors(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        totalSize = height * width;
        pixelMap = new HashMap<int[], Integer>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArray = getRGBArray(rgb);

                //filter out the grey spectrum (white/black)
                if (!isGray(rgbArray)) {
                    Integer counter = (Integer) pixelMap.get(rgb);
                    if (counter == null)
                        counter = 0;

                    counter++;
                    pixelMap.put(rgb, counter);
                }
            }
        }//outer

        colorHex1 = getMostCommonColor();
    }//getImageColors

    private int[] getMostCommonColor() {
        List list = new LinkedList(pixelMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRGBArray((Integer) me.getKey());

        return rgb;
    }

    //extracting the RGBArray colors for each pixel. returns an array of ints that represent red, green, and blue
    protected int[] getRGBArray(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
    }

    public int getRGBatPixel(int x, int y) {
        return bufferedImage.getRGB(x, y);
    }

    //we want to ignore grey and black colors - they are not useful for us.
    private static boolean isGray(int[] rgb) {
        int redGreenDifference = rgb[0] - rgb[1];
        int redBlueDifference = rgb[0] - rgb[2];

        // Filter out black, white and grays w 10 pixel tolerance.
        int tolerance = 10;
        if (redGreenDifference > tolerance || redGreenDifference < -tolerance)
            if (redBlueDifference > tolerance || redBlueDifference < -tolerance) {
                return false;
            }
        return true;
    }

    protected Image makeImageGrayscale(Image originalImage) {
        PImage pImage = new PImage(originalImage);
        PImage processed = Grayscale.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImageNegative(Image originalImage) {
        PImage pImage = new PImage(originalImage);
        PImage processed = InvertColors.apply(pImage, true, true, true);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImageThreshold(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Threshold.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImageQuantize(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Quantization.apply(pImage, 3);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImagePixelate(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Pixelation.apply(pImage, 10);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImageEdgeDetect(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = CannyEdgeDetector.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImageSobel(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = SobelEdgeDetector.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    protected Image makeImageDither(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Dithering.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }
}