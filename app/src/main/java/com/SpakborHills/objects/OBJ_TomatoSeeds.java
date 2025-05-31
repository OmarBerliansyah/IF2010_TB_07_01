package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_TomatoSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_TomatoSeeds(GamePanel gp){
        super(gp);
        name = "Tomato Seeds";
        down1 = setup("objects/TomatoSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 3;
        this.harvestProduct = new OBJ_Tomato(gp);
        cropCount = 1;  
        buyPrice = 50; 
        salePrice = 25;
        isEdible = false;
        availableSeasons = EnumSet.of(Season.SUMMER); 
        availableSeasons = EnumSet.of(Season.SUMMER);
        type = EntityType.SEED; 
    }

    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    @Override
    public Entity copy() {
        return new OBJ_TomatoSeeds(gp);
    }
}