package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_Sturgeon extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_Sturgeon(GamePanel gp){
        super(gp);
        name = "Sturgeon";
        down1 = setup("objects/Sturgeon",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Regular Fish.";
        isPickable = true;
        availableSeasons = EnumSet.of(Season.SUMMER, Season.WINTER);
        availableWeathers = EnumSet.allOf(Weather.class); 
        availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(6);
        availableEndTimes.add(18);
        category = "Regular";
        isEdible = true;
        plusEnergy = 1;
    }
}