package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.EntityType;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_HotPepperSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_HotPepperSeeds(GamePanel gp){
        super(gp);
        name = "HotPepper Seeds";
        down1 = setup("objects/HotPepperSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 1; 
        this.harvestProduct = new OBJ_HotPepper(gp);
        cropCount = 1;
        buyPrice = 40; 
        salePrice = 20;
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
        return new OBJ_HotPepperSeeds(gp);
    }
}