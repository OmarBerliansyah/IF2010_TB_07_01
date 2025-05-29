package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_Chub extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_Chub(GamePanel gp){
        super(gp);
        name = "Chub";
        down1 = setup("objects/Chub",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nJenis Common Fish hanya\ndapat ditangkap di Forest\nRiver,Mountain Lake.";
        isPickable = true;
        availableSeasons = EnumSet.allOf(Season.class);
        availableWeathers = EnumSet.allOf(Weather.class); 
        availableLocations = EnumSet.of(Location.FOREST_RIVER, Location.MOUNTAIN_LAKE);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(0);
        availableEndTimes.add(24);
        category = "Common"; 
        isEdible = true;
        plusEnergy = 1;
    }
}