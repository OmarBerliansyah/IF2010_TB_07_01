package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Hoe extends Entity {

    public OBJ_Hoe(GamePanel gp){
        super(gp);
        name = "Hoe";
        down1 = setup("objects/Hoe",gp.tileSize, gp.tileSize );
        description = "[" + name + "]\nDigunakan untuk \nmengolah tanah untuk \nkeperluan pertanian."; 
        isPickable = true;
    }
}