package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_WheatSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_WheatSeeds(GamePanel gp){
        super(gp);
        name = "Wheat Seeds";
        down1 = setup("objects/WheatSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nspring dan fall."; 
        isPickable = true;
        daysToHarvest = 1; 
        this.harvestProduct = new OBJ_Wheat(gp);
        cropCount = 3;
        buyPrice = 60; 
        salePrice = 30;
        isEdible = false;
        availableSeasons = EnumSet.of(Season.SPRING, Season.FALL);
        type = EntityType.SEED;
    }
    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    @Override
    public Entity copy() {
        return new OBJ_WheatSeeds(gp);
    }
}