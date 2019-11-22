import java.util.Map;

public class Centroid {
    private Map<int[], Integer> coordinates;

    public Centroid(Map<int[], Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<int[], Integer> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Map<int[], Integer> coordinates) {
        this.coordinates = coordinates;
    }
}