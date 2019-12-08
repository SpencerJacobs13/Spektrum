import milchreis.imageprocessing.*;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.List;

public class Model {
    Controller controller;
    protected BufferedImage bufferedImage;
    protected Map pixelMap;
    protected List list; //this is the sorted list of pixels (by count)
    protected int[] colorHex1;
    protected Image image;
    int totalSize;
    int mapSize;
    protected int value; //the slider on the main screen changes this value


    public Model(Controller con, BufferedImage buffImage) {
        this.bufferedImage = buffImage;
        this.controller = con;
        this.image = controller.testImage;

        getImageColors(buffImage);
    }

    //extracting the colors of each pixel into a 2D array.
    public void getImageColors(BufferedImage image) {
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

        colorHex1 = getMostCommonColor(pixelMap);
    }//getImageColors

    private int[] getMostCommonColor(Map map) {
        list = new LinkedList(pixelMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        mapSize = pixelMap.entrySet().size();

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
        int rgb;
        rgb = bufferedImage.getRGB(x, y);
        return rgb;
    }

    //we want to ignore grey and black colors - they are not useful for us.
    private static boolean isGray(int[] rgb) {
        int rgDiff = rgb[0] - rgb[1];
        int rbDiff = rgb[0] - rgb[2];

        // Filter out black, white and grays w 10 pixel tolerance.
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance)
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        return true;
    }

    public Image makeImageGrayscale(Image originalImage) {
        PImage pImage = new PImage(originalImage);
        PImage processed = Grayscale.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImageNegative(Image originalImage) {
        PImage pImage = new PImage(originalImage);
        PImage processed = InvertColors.apply(pImage, true, true, true);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImageThreshold(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Threshold.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImageQuantize(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Quantization.apply(pImage, 3);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImagePixelate(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Pixelation.apply(pImage, 10);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImageEdgeDetect(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = CannyEdgeDetector.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImageSobel(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = SobelEdgeDetector.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }

    public Image makeImageDither(Image originalImage){
        PImage pImage = new PImage(originalImage);
        PImage processed = Dithering.apply(pImage);
        originalImage = processed.getImage();

        return originalImage;
    }
}