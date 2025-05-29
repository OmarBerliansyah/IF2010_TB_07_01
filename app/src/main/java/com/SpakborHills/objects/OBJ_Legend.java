package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_Legend extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public List<Integer> availableStartTimes;
    public List<Integer> availableEndTimes;
    public EnumSet<Location> availableLocations;
    public String category; //Common, Regular, Legendary

    public OBJ_Legend(GamePanel gp){
        super(gp);
        name = "Legend";
        down1 = setup("objects/Legend",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nMemiliki jenis Legendary Fish.";
        isPickable = true;
        availableSeasons = EnumSet.of(Season.SPRING);
        availableWeathers = EnumSet.of(Weather.RAINY); 
        availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE);
        availableStartTimes = new ArrayList<>();
        availableEndTimes = new ArrayList<>();
        availableStartTimes.add(8);
        availableEndTimes.add(20);
        category = "Legendary"; 
        isEdible = true;
        plusEnergy = 1;
    }
}