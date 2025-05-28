package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_PumpkinPie extends Entity {
    public int plusEnergy; 

    public OBJ_PumpkinPie(GamePanel gp){
        super(gp);
        name = "PumpkinPie ";
        down1 = setup("objects/PumpkinPie",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 35; 
        buyPrice = 120;
        salePrice = 100;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}