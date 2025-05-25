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
        try{
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abibelakang.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abibelakangjalan.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abidepan.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abidepanjalan.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abikiri.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abikirijalan.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abikanan.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/abikananjalan.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
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
