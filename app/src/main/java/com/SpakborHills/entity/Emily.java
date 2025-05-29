package com.SpakborHills.entity;

import java.util.Random;

import com.SpakborHills.main.GamePanel;

public class Emily extends NPC{

    public Emily(GamePanel gp) {
        super(gp, "Emily");
        speed = 1;
        direction = "down";
        getNPCImage();
        setDialogue();
    }

    private void setNPC(){
        name = "Emily";

    }
    public void getNPCImage(){
        up1 = setup("NPC/emilybelakang", gp.tileSize, gp.tileSize);
        up2 = setup("NPC/emilybelakangjalan", gp.tileSize, gp.tileSize);
        down1 = setup("NPC/emilydepan", gp.tileSize, gp.tileSize);
        down2 = setup("NPC/emilydepanjalan", gp.tileSize, gp.tileSize);
        left1 = setup("NPC/emilykiri", gp.tileSize, gp.tileSize);
        left2 = setup("NPC/emilykirijalan", gp.tileSize, gp.tileSize);
        right1 = setup("NPC/emilykanan", gp.tileSize, gp.tileSize);
        right2 = setup("NPC/emilykananjalan", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogue[0]="Hello, " + gp.player.name + ".\n" + // Menggunakan gp.player.name untuk konsistensi setelah game dimulai
                      "Welcome to this store. \nMy name is Emily. \nYou can ask anything to me, \nand also you can find me just in this store/house.\n" +
                      "I hope you can enjoy your life here,\nand interact as many as you can. \nNow, you can go to your house first.\n" +
                      "Bye-bye and See you!!!";
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
        // Emily loves all seeds
        lovedItems.add("Parsnip Seeds");
        lovedItems.add("Cauliflower Seeds");
        lovedItems.add("Potato Seeds");
        lovedItems.add("Wheat Seeds");
        lovedItems.add("Blueberry Seeds");
        lovedItems.add("Tomato Seeds");
        lovedItems.add("Hot Pepper Seeds");
        lovedItems.add("Melon Seeds");
        lovedItems.add("Cranberry Seeds");
        lovedItems.add("Pumpkin Seeds");
        lovedItems.add("Grape Seeds");
        
        likedItems.add("Catfish");
        likedItems.add("Salmon");
        likedItems.add("Sardine");
        
        hatedItems.add("Coal");
        hatedItems.add("Wood");
    }
}
