package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Sashimi extends Entity {
    public int plusEnergy; 

    public OBJ_Sashimi(GamePanel gp){
        super(gp);
        name = "Sashimi";
        down1 = setup("objects/Sashimi",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 70; 
        buyPrice = 300;
        salePrice = 275;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}