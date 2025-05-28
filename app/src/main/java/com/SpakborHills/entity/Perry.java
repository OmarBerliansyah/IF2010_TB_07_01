package com.SpakborHills.entity;

import java.util.Arrays;
import java.util.Random;

import com.SpakborHills.main.GamePanel;


public class Perry extends NPC {
    
    public Perry(GamePanel gp) {
        super(gp, "Perry");
        speed = 1;
        direction = "down";
        getNPCImage();
        setDialogue();
    }
    
    
    @Override
    protected void initializePreferences() {
        // Perry loves berries
        lovedItems.add("Cranberry");
        lovedItems.add("Blueberry");
        
        // Perry likes wine
        likedItems.add("Wine");
        
        // Perry hates all fish
        hatedItems.addAll(Arrays.asList(
            "Bullhead", "Carp", "Chub", "Largemouth Bass", "Rainbow Trout",
            "Sturgeon", "Midnight Carp", "Flounder", "Halibut", "Octopus",
            "Pufferfish", "Sardine", "Super Cucumber", "Catfish", "Salmon",
            "Angler", "Crimsonfish", "Glacierfish", "Legend"
        ));
    }
    
    public void getNPCImage(){
        up1 = setup("NPC/perrybelakang", gp.tileSize, gp.tileSize);
        up2 = setup("NPC/perrybelakangjalan", gp.tileSize, gp.tileSize);
        down1 = setup("NPC/perrydepan", gp.tileSize, gp.tileSize);
        down2 = setup("NPC/perrydepanjalan", gp.tileSize, gp.tileSize);
        left1 = setup("NPC/perrykiri", gp.tileSize, gp.tileSize);
        left2 = setup("NPC/perrykirijalan", gp.tileSize, gp.tileSize);
        right1 = setup("NPC/perrykanan", gp.tileSize, gp.tileSize);
        right2 = setup("NPC/perrykananjalan", gp.tileSize, gp.tileSize);
    }
    
    protected void setDialogue() {
        dialogue[0] = "Oh, hello... I'm Perry.";
        dialogue[1] = "I just published my first book!";
        dialogue[2] = "I moved here for peace and quiet to write.";
        dialogue[3] = "I love berries, but please... no fish.";
    }
    
    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            
            if (i <= 25) direction = "up";
            else if (i <= 50) direction = "down";
            else if (i <= 75) direction = "left";
            else direction = "right";
            
            actionLockCounter = 0;
        }
    }
}