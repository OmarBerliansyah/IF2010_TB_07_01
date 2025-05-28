package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Ring extends Entity {
    public int plusEnergy; 

    public OBJ_Ring(GamePanel gp){
        super(gp);
        name = "Ring";
        down1 = setup("objects/Ring",gp.tileSize, gp.tileSize);
        isPickable = false;
        description = "[" + name + "]\nUntuk Melamar dan Menikah "; 
    }
}