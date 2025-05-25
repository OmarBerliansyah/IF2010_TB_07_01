package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_ParsnipSeeds extends Entity {

    public OBJ_ParsnipSeeds(GamePanel gp){
        super(gp);
        name = "Parsnip Seeds";
        down1 = setup("objects/ParsnipSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nini Parsnip Seeds"; 
    }
}