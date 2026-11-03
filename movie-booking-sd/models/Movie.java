public class Movie {
    private String id;
    private String name;
    private String description;
    private String durationinMinutes;

    public Movie(String id, String name, String description, String durationinMinutes){
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationinMinutes = durationinMinutes;
    }
    // getters and setters
    public String getId() { 
        return id;
    }                                                                                                                       

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDurationinMinutes() {
        return durationinMinutes;
    }

    public void setDurationinMinutes(String durationinMinutes) {
        this.durationinMinutes = durationinMinutes;
    }

}