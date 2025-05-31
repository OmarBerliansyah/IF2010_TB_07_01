package com.SpakborHills.entity;

import java.util.Random;

import com.SpakborHills.main.GamePanel;

public class Mayor extends NPC{

    public Mayor(GamePanel gp) {
        super(gp, "Mayor Tedi");
        speed = 1;
        direction = "down";
        getNPCImage();
        setDialogue();
    }

    private void setNPC(){
        name = "Mayor Tedi";

    }
    public void getNPCImage(){
        up1 = setup("NPC/clintbelakang", gp.tileSize, gp.tileSize);
        up2 = setup("NPC/clintbelakangjalan", gp.tileSize, gp.tileSize);
        down1 = setup("NPC/clintdepan", gp.tileSize, gp.tileSize);
        down2 = setup("NPC/clintdepanjalan", gp.tileSize, gp.tileSize);
        left1 = setup("NPC/clintkiri", gp.tileSize, gp.tileSize);
        left2 = setup("NPC/clintkirijalan", gp.tileSize, gp.tileSize);
        right1 = setup("NPC/clintkanan", gp.tileSize, gp.tileSize);
        right2 = setup("NPC/clintkananjalan", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogue[0]="Welcome to Spakbor Hills!\n" +
                      "As mayor, I only appreciate the finest things.\n" +
                      "Legendary items and rare fish are my passion.\n" +
                      "Please, don't insult me with common items.";
    }

    @Override
    public void giveGift(String itemName) {
        giftingFrequency++;
        
        if (lovedItems.contains(itemName)) {
            addHeartPoints(25);
            gp.ui.addMessage(name + ": Magnificent! A truly legendary item! (+25 heart points)");
        } else if (likedItems.contains(itemName)) {
            addHeartPoints(20);
            gp.ui.addMessage(name + ": Ah, a fine specimen indeed! (+20 heart points)");
        } else {
            addHeartPoints(-25);
            gp.ui.addMessage(name + ": This... this is beneath my standards! (-25 heart points)");
        }
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
        lovedItems.add("Legend");
        likedItems.add("Angler");
        likedItems.add("Crimsonfish");
        likedItems.add("Glacierfish");
    }
}
