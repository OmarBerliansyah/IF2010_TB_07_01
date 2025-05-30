package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_EggplantSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_EggplantSeeds(GamePanel gp){
        super(gp);
        name = "Eggplant Seeds";
        down1 = setup("objects/EggplantSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 5;
        this.harvestProduct = new OBJ_Eggplant(gp);
        cropCount = 1; 
        buyPrice = 100; 
        salePrice = 50;
        availableSeasons = EnumSet.of(Season.FALL);
        type = EntityType.SEED;
    }

    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    @Override
    public Entity copy() {
        return new OBJ_EggplantSeeds(gp);
    }
}