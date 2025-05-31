package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_CranberrySeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_CranberrySeeds(GamePanel gp){
        super(gp);
        name = "Cranberry Seeds";
        down1 = setup("objects/CranberrySeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 2; 
        this.harvestProduct = new OBJ_Cranberry(gp);
        cropCount = 10;
        buyPrice = 100; 
        salePrice = 50;
        isEdible = false;
        availableSeasons = EnumSet.of(Season.FALL);
        type = EntityType.SEED;
    }

    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    @Override
    public Entity copy() {
        return new OBJ_CranberrySeeds(gp);
    }
}