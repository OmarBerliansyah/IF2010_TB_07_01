package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Cauliflower extends Entity {

    public OBJ_Cauliflower(GamePanel gp){
        super(gp);
        name = "Cauliflower";
        down1 = setup("objects/Cauliflower",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat dijual, di-gift, atau \ndimasak sesuai dengan \nkeperluan resep masakan."; 
        isPickable = true;
        buyPrice = 200;
        salePrice = 150;
        cropCount = 1; 
        isEdible = true;
        plusEnergy = 3;
    }

    @Override
    public Entity copy(){
        return new OBJ_Cauliflower(gp);
    }
}