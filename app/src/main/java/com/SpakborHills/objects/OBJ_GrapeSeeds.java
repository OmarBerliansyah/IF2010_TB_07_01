package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_GrapeSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_GrapeSeeds(GamePanel gp){
        super(gp);
        name = "Grape Seeds";
        down1 = setup("objects/GrapeSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 3; 
        buyPrice = 60; 
        salePrice = 30;
        availableSeasons = EnumSet.of(Season.FALL);
    }
}