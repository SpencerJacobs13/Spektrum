import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Model {
    Controller controller;
    protected BufferedImage image;
    private List colorList;
    protected Map pixelMap;
    protected List list; //this is the sorted list of pixels (by count)
    protected Set colorSet;
    protected String[] finalFiveColors;
    protected String colorHex1;
    protected String colorHex2;
    protected String colorHex3;
    protected String colorHex4;
    protected String colorHex5;
    protected int[][] allPixels;
    int totalSize;
    int mapSize;


    public Model(BufferedImage image){
        this.image = image;


        allPixels = new int[image.getWidth()][image.getHeight()];
        getImageColors(image);
    }

    //extracting the colors of each pixel into a 2D array.
    public void getImageColors(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();
        totalSize = height * width;

        //colorListTest = new LinkedList<Pixel>();
        pixelMap = new HashMap<int[], Integer>();
        for(int i = 0; i < width; i ++){
            for(int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                allPixels[i][j] = rgb;
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
        System.out.println(colorHex1);

    }//getImageColors

    private String getMostCommonColor(Map map){
        list = new LinkedList(pixelMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        mapSize = pixelMap.entrySet().size();

        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRGBArray((Integer)me.getKey());
        System.out.println();
        System.out.println("red: " + rgb[0] + " green: " + rgb[1] + " blue: " + rgb[2]);

        return Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]);
    }

    //extracting the RGBArray colors for each pixel. returns an array of ints that represent red, green, and blue
    protected int[] getRGBArray(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
    }

    public int getRGBatPixel(int x, int y){
        int rgb;
        rgb = image.getRGB(x, y);
        return rgb;
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














































//    private void setColorHex(){
//
//        System.out.println("color 1: " + colorHex1);
//
//        //color 2
//        Map.Entry colorEntry = (Map.Entry) colorList.get(colorList.size() - 1);
//        int[] rgbThing = getRGBArray((Integer) colorEntry.getKey());
//        String rgbString = Integer.toHexString(rgbThing[0]) + Integer.toHexString(rgbThing[1]) + Integer.toHexString(rgbThing[0]);
//        colorHex2 = rgbString;
//        System.out.println("color 2: " + colorHex2);
//
//        //color 3
//        colorEntry = (Map.Entry) colorList.get(colorList.size() - 2);
//        rgbThing = getRGBArray((Integer) colorEntry.getKey());
//        rgbString = Integer.toHexString(rgbThing[0]) + Integer.toHexString(rgbThing[1]) + Integer.toHexString(rgbThing[0]);
//        colorHex3 = rgbString;
//        System.out.println("color 3: " + colorHex3);
//
//        //color 4
//        colorEntry = (Map.Entry) colorList.get(colorList.size() - 3);
//        rgbThing = getRGBArray((Integer) colorEntry.getKey());
//        rgbString = Integer.toHexString(rgbThing[0]) + Integer.toHexString(rgbThing[1]) + Integer.toHexString(rgbThing[0]);
//        //colorHex4 = setColorHexWithZero(rgbString);
//        colorHex4 = rgbString;
//        System.out.println("color 4: " + colorHex4);
//
//        //color 5
//        colorEntry = (Map.Entry) colorList.get(colorList.size() - 4);
//        rgbThing = getRGBArray((Integer) colorEntry.getKey());
//        rgbString = Integer.toHexString(rgbThing[0]) + Integer.toHexString(rgbThing[1]) + Integer.toHexString(rgbThing[0]);
//        //colorHex5 = setColorHexWithZero(rgbString);
//        colorHex5 = rgbString;
//        System.out.println("color 5: " + colorHex5);
//    }
//
//    private String setColorHexWithZero(String rgbString){
//        String newHexString = "";
//        String zero = "0";
//
//        for(int i = 0; i < rgbString.length(); i++){
//            newHexString += rgbString.charAt(i);
//            if(rgbString.charAt(i) == '0'){
//                newHexString += zero;
//            }
//        }
//        return "#" + newHexString;
//    }

}
