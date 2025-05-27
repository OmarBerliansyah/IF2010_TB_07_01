package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public OBJ_Pickaxe(GamePanel gp){
        super(gp);
        name = "Pickaxe";
        down1 = setup("objects/pickaxe", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\ndigunakan untuk \nrecover land."; 
        isPickable = true;
    }
}