package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_RainbowTrout extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_RainbowTrout(GamePanel gp){
        super(gp);
        name = "RainbowTrout";
        down1 = setup("objects/RainbowTrout",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Regular Fish.";
        isPickable = true;
        availableSeasons = EnumSet.of(Season.SUMMER);
        availableWeathers = EnumSet.of(Weather.SUNNY); 
        availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE, Location.FOREST_RIVER);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(6);
        availableEndTimes.add(18);
        category = "Regular";
    }
}