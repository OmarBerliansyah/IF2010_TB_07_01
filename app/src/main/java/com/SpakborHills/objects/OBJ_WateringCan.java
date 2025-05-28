package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_WateringCan extends Entity {

    public OBJ_WateringCan(GamePanel gp){
        super(gp);
        name = "Watering Can";
        down1 = setup("objects/wateringcan", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nAlat yang digunakan \nuntuk menyiram tanaman."; 
        isPickable = true;
    }
}