package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Coal extends Entity {

    public OBJ_Coal(GamePanel gp){
        super(gp);
        name = "Coal";
        down1 = setup("objects/Coal", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nbuat masak yh"; 
        isPickable = false;
        buyPrice = 60; 
        salePrice = 30;
    }
}
