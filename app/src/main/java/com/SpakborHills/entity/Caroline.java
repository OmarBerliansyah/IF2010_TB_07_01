package com.SpakborHills.entity;

import java.util.Random;

import com.SpakborHills.main.GamePanel;

public class Caroline extends NPC{

    public Caroline(GamePanel gp) {
        super(gp, "Caroline");
        speed = 1;
        direction = "down";
        getNPCImage();
        setDialogue();
    }

    private void setNPC(){
        name = "Caroline";

    }
    public void getNPCImage(){
        up1 = setup("NPC/carbelakang", gp.tileSize, gp.tileSize);
        up2 = setup("NPC/carbelakangjalan", gp.tileSize, gp.tileSize);
        down1 = setup("NPC/cardepan", gp.tileSize, gp.tileSize);
        down2 = setup("NPC/cardepanjalan", gp.tileSize, gp.tileSize);
        left1 = setup("NPC/carkiri", gp.tileSize, gp.tileSize);
        left2 = setup("NPC/carkirijalan", gp.tileSize, gp.tileSize);
        right1 = setup("NPC/carkanan", gp.tileSize, gp.tileSize);
        right2 = setup("NPC/carkananjalan", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogue[0] = "Hello! I'm Caroline, the local carpenter.";
        dialogue[1] = "I love working with wood and recycling materials.";
        dialogue[2] = "Creating art from basic materials is my passion!";
        dialogue[3] = "I prefer mild foods... spicy things aren't for me.";
    }

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; //pick up a number from 1

            if(i<= 25){
                direction = "up";
            }
            if(i>25 && i <=50){
                direction = "down";
            }
            if(i>50 && i <=75){
                direction = "left";
            }
            if(i>75 && i <=100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak() {
        super.speak();
    }

    @Override
    protected void initializePreferences() {
         // Caroline loves crafting materials
        lovedItems.add("Firewood");
        lovedItems.add("Coal");
        
        // Caroline likes simple foods
        likedItems.add("Potato");
        likedItems.add("Wheat");
        
        // Caroline hates spicy food
        hatedItems.add("Hot Pepper");
    }
}
