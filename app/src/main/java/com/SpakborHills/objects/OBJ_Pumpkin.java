package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Pumpkin extends Entity {

    public OBJ_Pumpkin(GamePanel gp){
        super(gp);
        name = "Pumpkin";
        down1 = setup("objects/Pumpkin",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 300;
        salePrice = 250;
        cropCount = 1; 
        isEdible = true;
        plusEnergy = 3;
    }

    @Override
    public Entity copy() {
        return new OBJ_Pumpkin(gp);
    }
}