package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_Carp extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_Carp(GamePanel gp){
        super(gp);
        name = "Carp";
        down1 = setup("objects/Carp",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nJenis Common Fish hanya\ndapat ditangkap di\nMountain Lake, Pond.";
        isPickable = true;
        availableSeasons = EnumSet.allOf(Season.class);
        availableWeathers = EnumSet.allOf(Weather.class); 
        availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE, Location.POND);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(0);
        availableEndTimes.add(24);
        category = "Common"; 
    }
}