package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_Catfish extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_Catfish(GamePanel gp){
        super(gp);
        name = "Catfish";
        down1 = setup("objects/Catfish",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Regular Fish.";
        isPickable = true;
        availableSeasons = EnumSet.of(Season.SPRING, Season.SUMMER, Season.FALL);
        availableWeathers = EnumSet.of(Weather.RAINY); 
        availableLocations = EnumSet.of(Location.FOREST_RIVER, Location.POND);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(6);
        availableEndTimes.add(22);
        category = "Regular"; 
        isEdible = true;
        plusEnergy = 1;
    }
}