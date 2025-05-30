package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.*;
import com.SpakborHills.main.GamePanel;

public class OBJ_ParsnipSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_ParsnipSeeds(GamePanel gp){
        super(gp);
        name = "Parsnip Seeds";
        down1 = setup("objects/ParsnipSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nspring."; 
        isPickable = true;
        daysToHarvest = 1;
        buyPrice = 20;
        salePrice = 10;
        availableSeasons = EnumSet.of(Season.SPRING); 
        isEdible = false;
        type = EntityType.SEED;
    }

    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return availableSeasons;
    }
}