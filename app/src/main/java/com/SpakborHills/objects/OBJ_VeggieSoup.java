package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_VeggieSoup extends Entity {
    public int plusEnergy; 

    public OBJ_VeggieSoup(GamePanel gp){
        super(gp);
        name = "VeggieSoup ";
        down1 = setup("objects/VeggieSoup",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
        isPickable = true;
        plusEnergy = 40; 
        buyPrice = 140;
        salePrice = 120;
    }
}