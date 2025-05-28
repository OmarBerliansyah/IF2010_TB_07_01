package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_HotPepper extends Entity {

    public OBJ_HotPepper(GamePanel gp){
        super(gp);
        name = "HotPepper";
        down1 = setup("objects/HotPepper",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        salePrice = 40;
        cropCount = 1; 
    }
}