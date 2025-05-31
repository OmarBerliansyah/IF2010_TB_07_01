package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Grape extends Entity {

    public OBJ_Grape(GamePanel gp){
        super(gp);
        name = "Grape";
        down1 = setup("objects/Grape",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 100;
        salePrice = 10;
        cropCount = 20; 
        isEdible = true;
        plusEnergy = 3;
    }

    @Override
    public Entity copy() {
        return new OBJ_Grape(gp);
    }
}