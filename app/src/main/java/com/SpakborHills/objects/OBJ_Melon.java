package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Melon extends Entity {

    public OBJ_Melon(GamePanel gp){
        super(gp);
        name = "Melon";
        down1 = setup("objects/Melon",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        salePrice = 250;
        buyPrice = -1;
        cropCount = 1; 
        isEdible = true;
        plusEnergy = 3;
    }
}