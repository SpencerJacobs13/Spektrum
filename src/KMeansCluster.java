import sun.plugin.dom.css.RGBColor;

import java.awt.image.BufferedImage;
import java.util.Map;

public class KMeansCluster {
    int[] RGBOfPixel;
    protected Map<int[], Integer> pixelMap;

    public KMeansCluster(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();

        for(int i = 0; i < width; i ++){
            for(int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArray = getRGBArray(rgb);

                //filter out the grey spectrum (white/black)
                if (!isGray(rgbArray)) {
                    int counter = pixelMap.get(rgb);

                    counter++;
                    pixelMap.put(rgbArray, counter);
                    //new Pixel based on the red, green, and blue values of the rgbArray
                }
            }
        }//outer
    }

    private int[] getRGBArray(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
    }

    private static boolean isGray(int[] rgb){
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

    public double calculateEuclideanDistance(Map<int[], Integer> f1, Map<int[], Integer> f2){
        double sum = 0;
        for( int[] key : f1.keySet()){
            double v1 = f1.get(key);
            double v2 = f2.get(key);

            if(v1 != 0 && v2 != 0){
                sum += Math.pow(v1 - v2, 2);
            }
        }
        System.out.println("Euclid Distance: " + Math.sqrt(sum));
        return Math.sqrt(sum);
    }
}
