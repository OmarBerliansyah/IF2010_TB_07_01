package com.SpakborHills.environment;

public enum Location {
    MOUNTAIN_LAKE("Mountain Lake"),
    OCEAN("Ocean"),
    FOREST_RIVER("Forest River"),
    POND("Pond"),
    FARM("Farm"),
    HOUSE("House"),
    NPCMAP("Npc Map"),
    EMILY_MAP ("Emily Map"),
    PERRY_MAP ("Perry Map"),
    DASCO_MAP ("Dasco Map"),
    ABIGAIL_MAP ("Abigail Map"),
    MAYOR_MAP ("Mayor Map"),
    CAROLINE_MAP("Caroline Map");

    private String locationName;

    Location(String locationName) {
        this.locationName = locationName;
    }

    public String getNamaLokasi() {
        return locationName;
    }
}