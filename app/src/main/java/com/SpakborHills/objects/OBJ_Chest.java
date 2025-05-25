package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gp){
        super(gp);
        name = "Chest";
        down1 = setup("objects/Chest", gp.tileSize, gp.tileSize);
        collision = true;
        isPickable = false;
    }
}
