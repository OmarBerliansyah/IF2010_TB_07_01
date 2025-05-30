package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Eggplant extends Entity {

    public OBJ_Eggplant(GamePanel gp){
        super(gp);
        name = "Eggplant";
        down1 = setup("objects/Eggplant",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = -1;
        salePrice = 600;
        cropCount = 1; 
        isEdible = true;
        plusEnergy = 3;
    }

    @Override
    public Entity copy() {
        return new OBJ_Eggplant(gp);
    }
}