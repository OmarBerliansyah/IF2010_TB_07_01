package com.SpakborHills.main; // Package declaration for the main class

import java.awt.event.KeyListener; // Import the KeyListener interface for handling keyboard events
import java.awt.event.KeyEvent; // Import the KeyEvent class for key even

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, pausePressed, characterPressed; // Movement flags
    //DEBUG
    boolean checkDrawTime;
    public KeyHandler(GamePanel gp){

        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this example
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Get the key code of the pressed key

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            if(gp.ui.titleScreenState == 0){
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2){
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.ui.titleScreenState = 1;
                    }
                    if(gp.ui.commandNum == 1){
                        //implement nanti
                    }
                    if(gp.ui.commandNum == 2){
                        System.exit(0);
                    }
                }
                if(code == KeyEvent.VK_T){
                    if(checkDrawTime == false){
                        checkDrawTime = true;
                    }
                    else if(checkDrawTime == true){
                        checkDrawTime = false;
                    }
                }
            }
            else if(gp.ui.titleScreenState == 1){
//                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
//                    gp.ui.commandNum--;

//                    if(gp.ui.commandNum < 0){
//                        gp.ui.commandNum = 2;
//                    }
//                }
//                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
//                    gp.ui.commandNum++;
//                    if(gp.ui.commandNum > 2){
//                        gp.ui.commandNum = 0;
//                    }
//                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
//                    if(gp.ui.commandNum == 1){
//                        //implement nanti
//                    }
//                    if(gp.ui.commandNum == 2){
//                        System.exit(0);
//                    }
                }
            }
        }

        //PLAY STATE
        if(gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true; // Set the upPressed flag to true
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true; // Set the downPressed flag to true
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true; // Set the leftPressed flag to true
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true; // Set the rightPressed flag to true
            }
            if (code == KeyEvent.VK_P && !pausePressed) {
                gp.gameState = gp.pauseState;
                pausePressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_C && !characterPressed) {
                gp.gameState = gp.characterState;
                characterPressed = true;
            }
        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P && !pausePressed) {
                gp.gameState = gp.playState;
                pausePressed = true;
            }
        }

        //DIALOGUE STATE
        if (gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }

        //CHARACTER STATE
        if (gp.gameState == gp.characterState){
            if(code == KeyEvent.VK_C && !characterPressed){
                gp.gameState = gp.playState;
                characterPressed = true;
            }
            if(code == KeyEvent.VK_W){
                if(gp.ui.slotRow != 0){
                    gp.ui.slotRow--;
                }
            }
            if(code == KeyEvent.VK_A){
                if(gp.ui.slotCol != 0){
                    gp.ui.slotCol--;
                }
            }
            if(code == KeyEvent.VK_S){
                if(gp.ui.slotRow != 3){
                    gp.ui.slotRow++;
                }
            }
            if(code == KeyEvent.VK_D){
                if(gp.ui.slotCol != 4){
                    gp.ui.slotCol++;
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_P){
            pausePressed = false;
        }
        if(code == KeyEvent.VK_C){
            characterPressed = false;
        }
    }
}