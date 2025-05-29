package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.environment.Season;
import com.SpakborHills.main.GamePanel;

public class OBJ_PotatoSeeds extends Entity {
    public EnumSet<Season> availableSeasons;

    public OBJ_PotatoSeeds(GamePanel gp){
        super(gp);
        name = "Potato Seeds";
        down1 = setup("objects/PotatoSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nspring."; 
        isPickable = true;
        daysToHarvest = 3; 
        buyPrice = 50; 
        salePrice = 25;
        isEdible = false;
        availableSeasons = EnumSet.of(Season.SPRING);
    }
}