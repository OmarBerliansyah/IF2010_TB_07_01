package com.SpakborHills.main; // Package declaration for the main class

import java.awt.event.KeyEvent; // Import the KeyListener interface for handling keyboard events
import java.awt.event.KeyListener; // Import the KeyEvent class for key even
import com.SpakborHills.entity.Entity;

import java.awt.Color;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, pausePressed, characterPressed, useToolPressed, giftPressed, proposePressed, marryPressed, escPressed; // Movement flags
    //DEBUG
    boolean checkDrawTime;

    public boolean backspacePressedFishing = false;
    public char lastTypeCharFishing = '\0';
    public boolean newCharTypedFishing = false;

    public KeyHandler(GamePanel gp){

        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
         // INPUT TEXT HANDLING untuk title screen
        if (gp.gameState == gp.titleState && gp.ui.titleScreenState == 1) {

            // Only allow letters, numbers, and spaces
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || 
                (c >= '0' && c <= '9') || c == ' ') {
                gp.ui.addCharacterToInput(c);
            }
        }

        else if (gp.gameState == gp.fishingMinigameState){
            if (Character.isDigit(c)){
                lastTypeCharFishing = c;
                newCharTypedFishing = true; // Set the flag to indicate a new character was typed
            }
        }
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
                    checkDrawTime = !checkDrawTime; 
                }
            }
            else if(gp.ui.titleScreenState == 1){
                // INPUT SCREEN HANDLING
                if (code == KeyEvent.VK_UP) {
                    gp.ui.isTyping = false; // Stop typing when navigating
                    gp.ui.inputState--;
                    if(gp.ui.inputState < 0){
                        gp.ui.inputState = 3; // Wrap to last option
                    }
                }if (code == KeyEvent.VK_DOWN) {
                    gp.ui.isTyping = false; // Stop typing when navigating
                    gp.ui.inputState++;
                    if(gp.ui.inputState > 3){
                        gp.ui.inputState = 0; // Wrap to first option
                    }
                }if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.inputState == 0) {
                        // Start typing name
                        gp.ui.isTyping = true;
                    } else if(gp.ui.inputState == 1) {
                        // Start typing farm name
                        gp.ui.isTyping = true;
                    } else if(gp.ui.inputState == 2) {
                        // Toggle gender
                        gp.ui.toggleGender();
                    } else if(gp.ui.inputState == 3) {
                        // Start game
                        gp.ui.startGame();
                    }
                }if(code == KeyEvent.VK_DELETE || code == KeyEvent.VK_BACK_SPACE) {
                    gp.ui.removeLastCharacter();
                }  
                if(code == KeyEvent.VK_G && !gp.ui.isTyping) {
                    // Shortcut untuk toggle gender
                    gp.ui.toggleGender();
                }
                if(code == KeyEvent.VK_ESCAPE) {
                    // Cancel typing or go back to main menu
                    if(gp.ui.isTyping) {
                        gp.ui.isTyping = false;
                    } else {
                        gp.ui.titleScreenState = 0; // Go back to main menu
                        gp.ui.inputState = 0;
                        gp.ui.isTyping = false;
                    }
                }

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
                    if(gp.ui.inputState == 0) {
                        gp.ui.isTyping = true;
                    } else if(gp.ui.inputState == 1) {
                        gp.ui.isTyping = true;
                    } else if(gp.ui.inputState == 2) {
                        gp.ui.toggleGender();
                    } else if(gp.ui.inputState == 3) {
                        gp.ui.startGame();
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
        else if(gp.gameState == gp.playState){
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
            if (code == KeyEvent.VK_SPACE && !useToolPressed) {
               useToolPressed = true; // Set the useToolPressed flag to true
            }
            // NEW NPC INTERACTION KEYS
            if (code == KeyEvent.VK_G && !giftPressed) {
                giftPressed = true;
            }
            if (code == KeyEvent.VK_R && !proposePressed) { // R for pRopose
                proposePressed = true;
            }
            if (code == KeyEvent.VK_M && !marryPressed) {
                marryPressed = true;
            }
            //Cheat for End Game
            //Cheat Money
            if (code == KeyEvent.VK_1) {
                int inputGold = 17208;
                gp.player.cheatMoney(inputGold);
                gp.ui.addMessage("Total Income: " + gp.player.totalIncome);

            }
            //Cheat Marry 
            if (code == KeyEvent.VK_2) {
                gp.player.forceMarry("Abigail");
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
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if(gp.ui.slotRow != 0){
                    gp.ui.slotRow--;
                    gp.playSE(2);
                }
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                if(gp.ui.slotCol != 0){
                    gp.ui.slotCol--;
                    gp.playSE(2);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if(gp.ui.slotRow != 3){
                    gp.ui.slotRow++;
                    gp.playSE(2);
                }
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                if(gp.ui.slotCol != 4){
                    gp.ui.slotCol++;
                    gp.playSE(2);
                }
            }
        }

        if (gp.gameState == gp.shippingBinState) {
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
            }
            if (code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }
        // cooking state
        if (gp.gameState == gp.cookingState) {
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
            }
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        if (gp.gameState == gp.endGameTriggerState){
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }

        if (gp.gameState == gp.endGameState){
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true; // Set the escPressed flag to true
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true; // Set the enterPressed flag to true
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
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            useToolPressed = false; // Reset the useToolPressed flag when the space key is released
        }
        if (code == KeyEvent.VK_G) {
            giftPressed = false;
        }
        if (code == KeyEvent.VK_R) {
            proposePressed = false;
        }
        if (code == KeyEvent.VK_M) {
            marryPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }
        if (gp.gameState == gp.characterState) {
            if (code == KeyEvent.VK_ENTER) {
                int selectedIndex = gp.ui.getItemIndexOnSLot();
                if (selectedIndex >= 0 && selectedIndex < gp.player.inventory.getInventory().size()) {
                    if (gp.player.getEquippedItem() == gp.player.inventory.getInventory().get(selectedIndex).item) {
                        gp.player.unEquipItem();
                    } else {
                        gp.player.equipItem(selectedIndex);
                    }
                }
            }
            if (code == KeyEvent.VK_E) {
                gp.player.eating(); // biar semua logika ada di 1 tempat
            }

            /*if (code == KeyEvent.VK_E) {
                Entity equippedItem = gp.player.getEquippedItem();
                if (equippedItem != null && equippedItem.isEdible) {
                    gp.player.eating();
                } else if (equippedItem != null && !equippedItem.isEdible) {
                    gp.ui.addMessage("This equipped item cannot be eaten!");
                } else {
                    gp.ui.addMessage("No item equipped to eat!");
                }
            }*/
        }
    }
}