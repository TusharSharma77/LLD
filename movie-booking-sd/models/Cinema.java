import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String name;
    private String id;
    private String city;
    private List<Screen> screens;

    public Cinema(String name, String id, String city) {
        this.name = name;
        this.id = id;
        this.city = city;
        this.screens = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }

    public void addScreen(Screen screen) {
        this.screens.add(screen);
    }
}
