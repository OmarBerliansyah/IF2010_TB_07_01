package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_PumpkinSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_PumpkinSeeds(GamePanel gp){
        super(gp);
        name = "Pumpkin Seeds";
        down1 = setup("objects/PumpkinSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 7;
        this.harvestProduct = new OBJ_Pumpkin(gp);
        cropCount = 1; 
        buyPrice = 150; 
        salePrice = 75;
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
        return new OBJ_PumpkinSeeds(gp);
    }
}