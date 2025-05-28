package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_SpakborSalad extends Entity {
    public int plusEnergy; 

    public OBJ_SpakborSalad(GamePanel gp){
        super(gp);
        name = "SpakborSalad";
        down1 = setup("objects/SpakborSalad",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 70; 
        salePrice = 250;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}