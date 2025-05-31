package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_MelonSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_MelonSeeds(GamePanel gp){
        super(gp);
        name = "Melon Seeds";
        down1 = setup("objects/MelonSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 4; 
        this.harvestProduct = new OBJ_Melon(gp);
        cropCount = 1;
        buyPrice = 80; 
        salePrice = 40;
        isEdible = false;
        availableSeasons = EnumSet.of(Season.SUMMER);
        type = EntityType.SEED;
    }

    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    @Override
    public Entity copy() {
        return new OBJ_MelonSeeds(gp);
    }
}