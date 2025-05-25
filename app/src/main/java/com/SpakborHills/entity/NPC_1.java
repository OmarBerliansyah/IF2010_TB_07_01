package com.SpakborHills.entity;

import com.SpakborHills.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_1 extends Entity{

    public NPC_1(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        getNPC1Image();
        setDialogue();
    }

    public void getNPC1Image(){
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
        dialogue[0]="Hello, Aliya";
        dialogue[1]="Welcome to this Farm. \n My name is Rino, and I'm your guide. \n You can ask anything to me, \n and also you can find me just in this field";
        dialogue[2]="I hope you can enjoy your life here,\n and interact as many as you can. \n Now, you can go to your house first.";
        dialogue[3]="Bye-bye and See you!!!";
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
}
