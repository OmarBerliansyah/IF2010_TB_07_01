package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp){
        super(gp);
        name = "Door";
        down1 = setup("objects/Door",  gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
        isEdible = false;
    }
}
