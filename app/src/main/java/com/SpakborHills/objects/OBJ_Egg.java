package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Egg extends Entity {

    public OBJ_Egg(GamePanel gp){
        super(gp);
        name = "Egg";
        down1 = setup("objects/Egg",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 10;
        salePrice = 5;
        isEdible = true;
        plusEnergy = 3;
    }
}