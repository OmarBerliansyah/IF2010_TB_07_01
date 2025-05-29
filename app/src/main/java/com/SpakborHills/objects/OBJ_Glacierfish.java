package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_Glacierfish extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_Glacierfish(GamePanel gp){
        super(gp);
        name = "Glacierfish";
        down1 = setup("objects/Glacierfish",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Legendary Fish.";
        isPickable = true;
        availableSeasons = EnumSet.of(Season.WINTER);
        availableWeathers = EnumSet.allOf(Weather.class); 
        availableLocations = EnumSet.of(Location.FOREST_RIVER);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(8);
        availableEndTimes.add(20);
        category = "Legendary"; 
        isEdible = true;
        plusEnergy = 1;
    }
}