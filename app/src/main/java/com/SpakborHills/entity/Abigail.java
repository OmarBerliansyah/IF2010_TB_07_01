package com.SpakborHills.entity;

import java.util.Random;

import com.SpakborHills.main.GamePanel;

public class Abigail extends NPC{

    public Abigail(GamePanel gp) {
        super(gp, "Abigail");
        speed = 1;
        direction = "down";
        getNPCImage();
        setDialogue();
    }

    private void setNPC(){
        name = "Abigail";

    }
    public void getNPCImage(){
        up1 = setup("NPC/abibelakang", gp.tileSize, gp.tileSize);
        up2 = setup("NPC/abibelakangjalan", gp.tileSize, gp.tileSize);
        down1 = setup("NPC/abidepan", gp.tileSize, gp.tileSize);
        down2 = setup("NPC/abidepanjalan", gp.tileSize, gp.tileSize);
        left1 = setup("NPC/abikiri", gp.tileSize, gp.tileSize);
        left2 = setup("NPC/abikirijalan", gp.tileSize, gp.tileSize);
        right1 = setup("NPC/abikanan", gp.tileSize, gp.tileSize);
        right2 = setup("NPC/abikananjalan", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogue[0] = "Hey there! I'm Abigail!";
        dialogue[1] = "I love exploring and going on adventures!";
        dialogue[2] = "Fruits give me energy for my expeditions!";
        dialogue[3] = "Vegetables? Ugh, no thanks!";
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
         // Abigail loves fruits for energy
        lovedItems.add("Blueberry");
        lovedItems.add("Melon");
        lovedItems.add("Pumpkin");
        lovedItems.add("Grape");
        lovedItems.add("Cranberry");
        
        // Abigail likes some prepared foods
        likedItems.add("Baguette");
        likedItems.add("Pumpkin Pie");
        likedItems.add("Wine");
        
        // Abigail hates vegetables
        hatedItems.add("Hot Pepper");
        hatedItems.add("Cauliflower");
        hatedItems.add("Parsnip");
        hatedItems.add("Wheat");
    }
}
