package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Cranberry extends Entity {

    public OBJ_Cranberry(GamePanel gp){
        super(gp);
        name = "Cranberry";
        down1 = setup("objects/Cranberry",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        salePrice = 25;
        buyPrice = -1;
        cropCount = 10; 
    }
}