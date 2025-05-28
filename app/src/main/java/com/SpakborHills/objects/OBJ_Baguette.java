package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Baguette extends Entity {
    public int plusEnergy; 

    public OBJ_Baguette(GamePanel gp){
        super(gp);
        name = "Baguette ";
        down1 = setup("objects/Baguette",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak" + plusEnergy; 
        isPickable = true;
        plusEnergy = 25; 
        buyPrice = 100;
        salePrice = 80;
    }
}