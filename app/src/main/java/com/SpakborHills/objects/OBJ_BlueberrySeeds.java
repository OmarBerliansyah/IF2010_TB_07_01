package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_BlueberrySeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_BlueberrySeeds(GamePanel gp){
        super(gp);
        name = "Blueberry Seeds";
        down1 = setup("objects/BlueberrySeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 7; 
        this.harvestProduct = new OBJ_Blueberry(gp);
        cropCount = 3; 
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
        return new OBJ_BlueberrySeeds(gp);
    }
}