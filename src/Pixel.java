public class Pixel implements Comparable {
    int red;
    int green;
    int blue;

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String getHexOfPixel(){
        String redString = Integer.toString(red);
        String greenString = Integer.toString(green);
        String blueString = Integer.toString(blue);

        String hex = String.format("#%02x%02x%02x", red, green, blue);

        return hex;
    }

    protected int[] getRGB(){
        return new int[]{this.red, this.green, this.blue};
    }

    @Override
    public String toString() {
        return "#" + "" + red + " " + green + " " + blue;
    }


    @Override
    public int compareTo(Object o) {


        return 0;
    }
}
