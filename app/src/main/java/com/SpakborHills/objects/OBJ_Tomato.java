package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Tomato extends Entity {

    public OBJ_Tomato(GamePanel gp){
        super(gp);
        name = "Tomato";
        down1 = setup("objects/Tomato",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 90;
        salePrice = 60;
        cropCount = 1; 
        isEdible = true;
        plusEnergy = 3;
    }
}