package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Potato extends Entity {

    public OBJ_Potato(GamePanel gp){
        super(gp);
        name = "Potato";
        down1 = setup("objects/Potato",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = -1;
        salePrice = 80;
        cropCount = 1; 
        isEdible = true;
        plusEnergy = 3;
    }

    @Override
    public Entity copy() {
        return new OBJ_Potato(gp);
    }
}