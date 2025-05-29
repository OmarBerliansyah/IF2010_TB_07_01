package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Baguette extends Entity {

    public OBJ_Baguette(GamePanel gp){
        super(gp);
        name = "Baguette";
        down1 = setup("objects/Baguette",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 25; 
        buyPrice = 100;
        salePrice = 80;
        isEdible = true;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}