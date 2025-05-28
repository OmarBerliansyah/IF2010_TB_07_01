package com.SpakborHills.environment;

public enum Location {
    MOUNTAIN_LAKE("Mountain Lake"),
    OCEAN("Ocean"),
    FOREST_RIVER("Forest River"),
    POND("Pond");

    private String locationName;

    Location(String locationName) {
        this.locationName = locationName;
    }

    public String getNamaLokasi() {
        return locationName;
    }
}