package com.SpakborHills.tile;

public class SoilTile{
    public boolean isTilled;
    public boolean isSeedPlanted;
    public String seedType;
    public int plantedDay;
    public int wateringCount = 0;
    public int lastWateredDay = -1; 

    // New fields for daily watering tracking
    public int dailyWateringCount = 0;
    public int lastWateringDay = -1;
    public boolean adequatelyWatered = false;
}