package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_FishingRod extends Entity {

    public OBJ_FishingRod(GamePanel gp){
        super(gp);
        name = "Fishing Rod";
        down1 = setup("objects/fishingrod", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDigunakan untuk \nmemancing."; 
        isPickable = true;
        isEdible = false;
    }
}