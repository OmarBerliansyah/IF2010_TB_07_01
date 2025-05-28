package com.SpakborHills.entity;

import java.util.Random;

import com.SpakborHills.main.GamePanel;

public class Dasco extends NPC{

    public Dasco(GamePanel gp) {
        super(gp, "Dasco");
        speed = 1;
        direction = "Dasco";
        getNPCImage();
        setDialogue();
    }

    private void setNPC(){
        name = "Dasco";

    }
    public void getNPCImage(){
        up1 = setup("NPC/alexbelakang", gp.tileSize, gp.tileSize);
        up2 = setup("NPC/alexbelakangjalan", gp.tileSize, gp.tileSize);
        down1 = setup("NPC/alexdepan", gp.tileSize, gp.tileSize);
        down2 = setup("NPC/alexdepanjalan", gp.tileSize, gp.tileSize);
        left1 = setup("NPC/alexkiri", gp.tileSize, gp.tileSize);
        left2 = setup("NPC/alexkirijalan", gp.tileSize, gp.tileSize);
        right1 = setup("NPC/alexkanan", gp.tileSize, gp.tileSize);
        right2 = setup("NPC/alexkananjalan", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
         dialogue[0] = "Welcome to my house! I'm Dasco.";
        dialogue[1] = "I appreciate only the finest cuisine.";
        dialogue[2] = "Luxury dining is my passion!";
        dialogue[3] = "Don't insult me with raw ingredients.";
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
        // Dasco loves luxury cooked food
        lovedItems.add("The Legends of Spakbor");
        lovedItems.add("Cooked Pig's Head");
        lovedItems.add("Wine");
        lovedItems.add("Fugu");
        lovedItems.add("Spakbor Salad");
        
        // Dasco likes prepared dishes
        likedItems.add("Fish Sandwich");
        likedItems.add("Fish Stew");
        likedItems.add("Baguette");
        likedItems.add("Fish n' Chips");
        
        // Dasco hates raw ingredients (considers them insulting)
        hatedItems.add("Legend");
        hatedItems.add("Grape");
        hatedItems.add("Cauliflower");
        hatedItems.add("Wheat");
        hatedItems.add("Pufferfish");
        hatedItems.add("Salmon");
    }
}
