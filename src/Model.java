import java.awt.image.BufferedImage;
import java.util.*;

public class Model {
    Controller controller;
    BufferedImage image;

    public Model(BufferedImage image){
        this.image = image;

        getImageColors(image);
    }

    //extracting the colors of each pixel into a 2D array.
    public void getImageColors(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();

        Map pixelMap = new HashMap();
        for(int i = 0; i < width; i ++){
            for(int j = 0; j < height; j++){
                int rgb = image.getRGB(i, j);
                int[] rgbArray = getRGBArray(rgb);

                //filter out the grey spectrum (white/black)
                if(isGray(rgbArray)){
                    Integer counter = (Integer) pixelMap.get(rgb);
                    if(counter == null)
                        counter = 0;
                    pixelMap.put(rgb, counter);
                }
            }
        }//outer

        String colourHex = getMostCommonColour(pixelMap);
        System.out.println(colourHex);

    }//getImageColors

    private String getMostCommonColour(Map map){
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map.Entry me = (Map.Entry)list.get(list.size()-1);
        int[] rgb = getRGBArray((Integer)me.getKey());

        return "#" + Integer.toHexString(rgb[0]) + " " + Integer.toHexString(rgb[1]) + " " + Integer.toHexString(rgb[2]);
    }

    //extracting the RGBArray colors for each pixel. returns an array of ints that
    private int[] getRGBArray(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red,green,blue};
    }

    //we want to ignore grey and black colors - they are not useful for us.
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


}
