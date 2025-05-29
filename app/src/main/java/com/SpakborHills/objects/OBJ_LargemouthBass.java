package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_LargemouthBass extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_LargemouthBass(GamePanel gp){
        super(gp);
        name = "LargemouthBass";
        down1 = setup("objects/LargemouthBass",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Regular Fish.";
        isPickable = true;
        availableSeasons = EnumSet.allOf(Season.class);
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