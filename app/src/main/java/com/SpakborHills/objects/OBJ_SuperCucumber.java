package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_SuperCucumber extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_SuperCucumber(GamePanel gp){
        super(gp);
        name = "SuperCucumber";
        down1 = setup("objects/SuperCucumber",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Regular Fish.";
        isPickable = true;
        availableSeasons = EnumSet.of(Season.SUMMER, Season.FALL, Season.WINTER);
        availableWeathers = EnumSet.allOf(Weather.class); 
        availableLocations = EnumSet.of(Location.OCEAN);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(18);
        availableEndTimes.add(2);
        category = "Regular"; 
        isEdible = true;
        plusEnergy = 1;
    }
}