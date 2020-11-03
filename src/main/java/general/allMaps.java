package general;

public enum allMaps {
    dust2 ("de_dust2"),
    inferno ("de_inferno"),
    train ("de_train"),
    vertigo ("de_vertigo"),
    mirage ("de_mirage"),
    nuke ("de_nuke"),
    overpass ("de_overpass");

    private final String title;

    allMaps(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
