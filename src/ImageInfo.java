public class ImageInfo {
    int id;
    String name;
    String path;
    int allPixels;
    int uniqueColors;

    public ImageInfo(String name, String path, int allPixels, int uniqueColors) {
        this.name = name;
        this.path = path;
        this.allPixels = allPixels;
        this.uniqueColors = uniqueColors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getAllPixels() {
        return allPixels;
    }

    public void setAllPixels(int allPixels) {
        this.allPixels = allPixels;
    }

    public int getUniqueColors() {
        return uniqueColors;
    }

    public void setUniqueColors(int uniqueColors) {
        this.uniqueColors = uniqueColors;
    }
}
