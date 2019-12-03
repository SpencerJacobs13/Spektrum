import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Model {
    Controller controller;
    protected BufferedImage image;
    protected Map pixelMap;
    protected List list; //this is the sorted list of pixels (by count)
    protected int[] colorHex1;
    int totalSize;
    int mapSize;


    public Model(BufferedImage image) {
        this.image = image;

        //allPixels = new int[image.getWidth()][image.getHeight()];
        getImageColors(image);
    }

    //extracting the colors of each pixel into a 2D array.
    public void getImageColors(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        totalSize = height * width;
        System.out.println("Height of model BI: " + image.getHeight());
        System.out.println("Width of model BI: " + image.getWidth());

        //colorListTest = new LinkedList<Pixel>();
        pixelMap = new HashMap<int[], Integer>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                //allPixels[i][j] = rgb;
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
        System.out.println("Unique colors: " + pixelMap.entrySet().size());

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
        rgb = image.getRGB(x, y);
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

    public BufferedImage makeImageGrayscale(BufferedImage originalImage) {
        BufferedImage returnImage = originalImage;
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = returnImage.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate average
                int avg = (r + g + b) / 3; //the average of every pixel makes it the grayscale equivalent

                //replace RGB value with avg
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                returnImage.setRGB(i, j, p);
            }
        }
        return returnImage;
    }

    public BufferedImage makeImageNegative(BufferedImage originalImage) {
        BufferedImage returnImage = originalImage;

        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = returnImage.getRGB(i, j);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                //subtract RGB from 255 to get negative of every value
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                returnImage.setRGB(i, j, p);
            }
        }
        return returnImage;
    }
}