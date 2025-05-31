package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;
import com.SpakborHills.entity.Entity.FishableProperties;


public class OBJ_BullHead extends Entity implements Entity.FishableProperties {
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; //Common, Regular, Legendary

    public OBJ_BullHead(GamePanel gp){
        super(gp);
        name = "BullHead";
        down1 = setup("objects/BullHead",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nJenis Common Fish hanya\ndapat ditangkap di\nMountain Lake.";
        isPickable = true;

        this.availableSeasons = EnumSet.allOf(Season.class);
        this.availableWeathers = EnumSet.allOf(Weather.class); 
        this.availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE);
        this.availableStartTimes = new ArrayList<>();
        this.availableEndTimes = new ArrayList<>();
        this.availableStartTimes.add(0);
        this.availableEndTimes.add(24);
        this.category = "Common"; 
        isEdible = true;
        plusEnergy = 1;
    }
    // Masih di dalam kelas OBJ_BullHead.java

@Override
public String getFishName() {
    return this.name; // Mengambil dari field 'name' di kelas Entity
}

@Override
public String getFishCategory() {
    return this.category;
}

@Override
public EnumSet<Season> getAvailableSeasons() {
    return this.availableSeasons;
}

@Override
public EnumSet<Weather> getAvailableWeathers() { // <-- IMPLEMENTASI METHOD INI
    return this.availableWeathers;
}

@Override
public List<Integer> getAvailableStartTimes() {
    return this.availableStartTimes;
}

@Override
public List<Integer> getAvailableEndTimes() {
    return this.availableEndTimes;
}

@Override
public EnumSet<Location> getAvailableLocations() {
    return this.availableLocations;
}
}