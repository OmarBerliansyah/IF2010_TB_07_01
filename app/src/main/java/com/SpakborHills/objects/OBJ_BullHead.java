package com.SpakborHills.objects;

import java.util.EnumSet;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;

public class OBJ_BullHead extends Entity {

    public EnumSet<Season> availableSeasons;
    public EnumSet<Weather> availableWeathers;
    public int availableStartTime;
    public int availableEndTime;
    public String availableLocation;

    public OBJ_BullHead(GamePanel gp){
        super(gp);
        name = "Bullhead";
        down1 = setup("objects/Bullhead",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat ditangkap pada\nsemua musim, waktu, \ndan cuaca apapun."; 
        isPickable = true;
        availableSeasons = EnumSet.allOf(Season.class);
        availableStartTime = 0;
        availableEndTime = 24;
        availableWeathers = EnumSet.of(Weather.SUNNY, Weather.RAINY); 
    }
}