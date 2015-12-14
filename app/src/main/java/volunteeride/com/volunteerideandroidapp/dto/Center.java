package volunteeride.com.volunteerideandroidapp.dto;



import java.util.List;

/**
 * Created by ayazlakdawala on 12/13/15.
 */
public class Center extends BaseDTOObject{

    private String name;
    private Location location;
    private List<Location> pickUpLocations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Location> getPickUpLocations() {
        return pickUpLocations;
    }

    public void setPickUpLocations(List<Location> pickUpLocations) {
        this.pickUpLocations = pickUpLocations;
    }

    @Override
    public String toString() {
        return "Center name ='" + name + '\'' + "\n" +
                location + "\n" +
                "Pick Up Locations = " + pickUpLocations +
                '}';
    }
}
