package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Wood extends Entity {

    public OBJ_Wood(GamePanel gp){
        super(gp);
        name = "Wood";
        down1 = setup("objects/Wood", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIni kayu booossss"; 
        isPickable = false;
        buyPrice = 60; 
        salePrice = 30;
        isEdible = false;
    }
}
