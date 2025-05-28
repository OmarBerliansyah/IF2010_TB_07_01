package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Parsnip extends Entity {

    public OBJ_Parsnip(GamePanel gp){
        super(gp);
        name = "Parsnip";
        down1 = setup("objects/Parsnip",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 50;
        salePrice = 35;
        cropCount = 1; 
    }
}