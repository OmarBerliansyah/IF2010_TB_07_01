package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Wheat extends Entity {

    public OBJ_Wheat(GamePanel gp){
        super(gp);
        name = "Wheat";
        down1 = setup("objects/Wheat",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 50;
        salePrice = 30;
        cropCount = 3; 
        isEdible = true;
        plusEnergy = 3;
    }

    @Override
    public Entity copy() {
        return new OBJ_Wheat(gp);
    }
}