package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Blueberry extends Entity {

    public OBJ_Blueberry(GamePanel gp){
        super(gp);
        name = "Blueberry";
        down1 = setup("objects/Blueberry",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 150;
        salePrice = 40;
        isEdible = true; 
        cropCount = 3; 
        plusEnergy = 3;
    }

    @Override
    public Entity copy() {
        return new OBJ_Blueberry(gp);
    }
}