package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_TheLegendsOfSpakbor extends Entity {
    public int plusEnergy; 

    public OBJ_TheLegendsOfSpakbor(GamePanel gp){
        super(gp);
        name = "TheLegendsOfSpakbor ";
        down1 = setup("objects/TheLegendsOfSpakbor",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
        isPickable = true;
        plusEnergy = 100; 
        salePrice = 2000;
    }
}