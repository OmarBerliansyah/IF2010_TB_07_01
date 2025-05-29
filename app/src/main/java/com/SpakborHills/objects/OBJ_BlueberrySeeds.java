package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
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
        buyPrice = 80; 
        salePrice = 40;
        availableSeasons = EnumSet.of(Season.SUMMER);
    }
}