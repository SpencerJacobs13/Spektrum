import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

public class Model {
    Controller controller;
    BufferedImage image;
    protected String colorHex;
    private List colorList;
    protected Map pixelMap;
    protected List list; //this is the sorted list of pixels
    protected Set colorSet;

    public Model(BufferedImage image){
        this.image = image;

        getImageColors(image);
    }

    //extracting the colors of each pixel into a 2D array.
    public void getImageColors(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();

        //colorListTest = new LinkedList<Pixel>();
        pixelMap = new HashMap<>();
        for(int i = 0; i < width; i ++){
            for(int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArray = getRGBArray(rgb);

                //filter out the grey spectrum (white/black)
                if (!isGray(rgbArray)) {
                    Integer counter = (Integer) pixelMap.get(rgb);
                    if (counter == null)
                        counter = 0;

                    counter++;
                    pixelMap.put(rgb, counter);
                    //new Pixel based on the red, green, and blue values of the rgbArray
                    //colorListTest.add(new Pixel(rgbArray[0], rgbArray[1], rgbArray[2]));
                }
            }
        }//outer

        colorHex = getMostCommonColour(pixelMap);
        //System.out.println(colorHex);

        //once we build the Hash Map, we want to get unique values from that map.
        //the list was sorted in getMostCommonColor, now we want to create a hash set with
        //that sorted list so that we can only see unique values.
        colorSet = new LinkedHashSet();
        colorSet.addAll(list);

        //this is a list of the Hash Set KEYS, not values.
        colorList = new LinkedList<>(colorSet);
        System.out.println();
        System.out.println();
        System.out.println(colorList.get(colorList.size() - 1));
        System.out.println();
        System.out.println();

        //getting the final 3 colors in the list of colors.
        for(int i = 1; i < 4; i++) {
            Map.Entry colorEntry = (Map.Entry) colorList.get(colorList.size() - i);
            int[] rgbThing = getRGBArray((Integer) colorEntry.getKey());
            System.out.println(Integer.toHexString(rgbThing[0]) + Integer.toHexString(rgbThing[1]) + Integer.toHexString(rgbThing[0]));
            System.out.println();
        }

        //int[] rgbThing = getRGBArray(rgb);

        //int[] rgbTest = getRGBArray((Integer) colorList.get(colorList.size() - 1));
        //Object rgbThing = colorList.get(colorList.size() - 1);

        //String rgb = getRGBArray((Integer) colorList.get(colorList.size() - 1)).toString();
        //int[] rgbTest2 = getRGBArray(colorSet.size() - 2);

        //String color2 = Integer.toHexString(rgbTest[0]) + Integer.toHexString(rgbTest[1]) + Integer.toHexString(rgbTest[2]);
        //String color3 = Integer.toHexString(rgbTest2[0]) + Integer.toHexString(rgbTest2[1]) + Integer.toHexString(rgbTest2[2]);
        //System.out.println("color2: " + color2/* + " color3: " + color3*/);

        //delete the color that has been used already
    }//getImageColors

    private String getMostCommonColour(Map map){
        list = new LinkedList(pixelMap.entrySet());
//      colorList = new LinkedList(list);

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        System.out.println("Map size is: " + pixelMap.size());

        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRGBArray((Integer)me.getKey());

        return Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]);
    }

    //extracting the RGBArray colors for each pixel. returns an array of ints that represent red, green, and blue
    private int[] getRGBArray(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
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
