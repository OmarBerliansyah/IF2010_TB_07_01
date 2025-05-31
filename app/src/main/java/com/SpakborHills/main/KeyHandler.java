package com.SpakborHills.main; // Package declaration for the main class

import java.awt.event.KeyEvent; // Import the KeyListener interface for handling keyboard events
import java.awt.event.KeyListener; // Import the KeyEvent class for key even

import com.SpakborHills.entity.NPC;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, pausePressed, characterPressed, useToolPressed, giftPressed, proposePressed, marryPressed, escPressed, pageUpPressed, pageDownPressed,npcInfoPressed ; // Movement flags
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
                    gp.ui.commandNum = 2; // Sekarang ada 3 menu: NEW GAME(0), OTHERS(1), QUIT(2)
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){ // Ubah dari 2 ke 2 (range 0-2)
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    // NEW GAME
                    gp.ui.titleScreenState = 1;
                }
                else if(gp.ui.commandNum == 1){
                    // OTHERS - Masuk ke menu others
                    gp.ui.titleScreenState = 2;
                    gp.ui.othersCommandNum = 0; // Reset selection
                }
                else if(gp.ui.commandNum == 2){
                    // QUIT
                    System.exit(0);
                }
            }
            if(code == KeyEvent.VK_T){
                checkDrawTime = !checkDrawTime; 
            }
        }
        // TAMBAHKAN LOGIC UNTUK titleScreenState == 2 (OTHERS MENU)
        else if(gp.ui.titleScreenState == 2){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.othersCommandNum--;
                if(gp.ui.othersCommandNum < 0){
                    gp.ui.othersCommandNum = 2; // HELP(0), CREDITS(1), BACK(2)
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.othersCommandNum++;
                if(gp.ui.othersCommandNum > 2){
                    gp.ui.othersCommandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.othersCommandNum == 0){
                    // HELP
                    gp.ui.titleScreenState = 3;
                }
                else if(gp.ui.othersCommandNum == 1){
                    // CREDITS
                    gp.ui.titleScreenState = 4;
                }
                else if(gp.ui.othersCommandNum == 2){
                    // BACK to main menu
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ESCAPE){
                // ESC to go back to main menu
                gp.ui.titleScreenState = 0;
                gp.ui.commandNum = 0;
            }
        }
        // TAMBAHKAN LOGIC UNTUK titleScreenState == 3 (HELP SCREEN)
        else if(gp.ui.titleScreenState == 3){
            if(code == KeyEvent.VK_ESCAPE){
                // Go back to others menu
                gp.ui.titleScreenState = 2;
            }
        }
        // TAMBAHKAN LOGIC UNTUK titleScreenState == 4 (CREDITS SCREEN)
        else if(gp.ui.titleScreenState == 4){
            if(code == KeyEvent.VK_ESCAPE){
                // Go back to others menu
                gp.ui.titleScreenState = 2;
            }
        }
            else if(gp.ui.titleScreenState == 1){
                // INPUT SCREEN HANDLING
                if (code == KeyEvent.VK_UP) {
                    gp.ui.isTyping = false; // Stop typing when navigating
                    gp.ui.inputState--;
                    if(gp.ui.inputState < 0){
                        gp.ui.inputState = 4; // Wrap to last option
                    }
                }if (code == KeyEvent.VK_DOWN) {
                    gp.ui.isTyping = false; // Stop typing when navigating
                    gp.ui.inputState++;
                    if(gp.ui.inputState > 4){
                        gp.ui.inputState = 0; // Wrap to first option
                    }
                }if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.inputState == 0) {
                        gp.ui.isTyping = true;// Start typing name
                    } else if(gp.ui.inputState == 1) {
                        gp.ui.isTyping = true; // Start typing farm name
                    } else if(gp.ui.inputState == 2) {
                        gp.ui.toggleGender(); // Toggle gender
                    } else if (gp.ui.inputState == 3) {
                        gp.ui.isTyping = true;
                    }else if(gp.ui.inputState == 4) { 
                        gp.ui.startGame();// Start game
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
                    } else if (gp.ui.inputState == 3) {
                        gp.ui.isTyping = true;
                    }else if(gp.ui.inputState == 4) { 
                        gp.ui.startGame();// Start game
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
                if (code == KeyEvent.VK_H && !npcInfoPressed) {
                    npcInfoPressed = true;
                    if (gp.currentMap >= 5 && gp.currentMap <= 10) {
                        if (gp.ui.showingNPCInfo) {
                            gp.ui.closeNPCInfo();
                        } else {
                            for (int i = 0; i < gp.NPC.length; i++) {
                                if (gp.NPC[i] instanceof NPC) {
                                    gp.ui.showNPCInfo(i);
                                    break;
                                }
                            }
                        }
                    } 
            }
            //Cheat for End Game
            //Cheat Money
            // if (code == KeyEvent.VK_1) {
            //     int inputGold = 17208;
            //     gp.player.cheatMoney(inputGold);
            //     gp.ui.addMessage("Total Income: " + gp.player.totalIncome);

            // }
            // //Cheat Marry 
            // if (code == KeyEvent.VK_2) {
            //     gp.player.forceMarry("Abigail");
            // }
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
                if(gp.ui.showingWatchTV){
                    gp.ui.showingWatchTV = false;
                    gp.eHandler.canTouchEvent = false;
                    gp.gameState = gp.playState; // Exit dialogue state
                    gp.eManager.addMinutesToTime(15); // Add 30 minutes for watching TV
                    gp.ui.addMessage("You watched TV for 15 minutes.");
                }
                else{
                    gp.gameState = gp.playState; // Exit dialogue state
                }
            }
        }

        //CHARACTER STATE
        if (gp.gameState == gp.characterState){
            if(code == KeyEvent.VK_C && !characterPressed){
                gp.gameState = gp.playState;
                characterPressed = true;
            }
            
            // Handle scrolling with Page Up/Down or Shift+Up/Down
            if(code == KeyEvent.VK_PAGE_UP || (code == KeyEvent.VK_UP && e.isShiftDown())){
                pageUpPressed = true;
                gp.ui.scrollInventory(true); // Scroll up
                gp.playSE(2);
                return; // Don't process normal navigation
            }
            if(code == KeyEvent.VK_PAGE_DOWN || (code == KeyEvent.VK_DOWN && e.isShiftDown())){
                pageDownPressed = true;
                gp.ui.scrollInventory(false); // Scroll down  
                gp.playSE(2);
                return; // Don't process normal navigation
            }
            
            // Normal navigation (only if not scrolling)
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if(gp.ui.slotRow != 0){
                    gp.ui.slotRow--;
                    gp.playSE(2);
                } else {
                    // At top row, try to scroll up
                    if (gp.ui.inventoryScrollOffset > 0) {
                        gp.ui.scrollInventory(true);
                        gp.ui.slotRow = 3; // Move to bottom row of new view
                        gp.playSE(2);
                    }
                }
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                if(gp.ui.slotCol != 0){
                    gp.ui.slotCol--;
                    gp.playSE(2);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                // Check if we can move down in current view
                int totalItems = gp.player.inventory.getInventory().size();
                int currentAbsoluteIndex = gp.ui.getItemIndexOnSLot();
                int nextRowIndex = currentAbsoluteIndex + 5; // Next row
                
                if(gp.ui.slotRow != 3 && nextRowIndex < totalItems && 
                nextRowIndex < gp.ui.inventoryScrollOffset + gp.ui.maxVisibleSlots){
                    gp.ui.slotRow++;
                    gp.playSE(2);
                } else if (nextRowIndex < totalItems) {
                    // At bottom row but more items exist, scroll down
                    gp.ui.scrollInventory(false);
                    gp.ui.slotRow = 0; // Move to top row of new view
                    gp.playSE(2);
                }
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                int totalItems = gp.player.inventory.getInventory().size();
                int currentAbsoluteIndex = gp.ui.getItemIndexOnSLot();
                
                if(gp.ui.slotCol != 4 && currentAbsoluteIndex + 1 < totalItems){
                    gp.ui.slotCol++;
                    gp.playSE(2);
                }
            }
            

            if(code == KeyEvent.VK_C && !characterPressed){
                gp.gameState = gp.playState;
                characterPressed = true;
            }
            
            if(code == KeyEvent.VK_0 || code == KeyEvent.VK_NUMPAD0) {
                gp.gameState = gp.statsState;
                gp.ui.interfaceScroll = 0; 
                return; 
            }

            // Ensure cursor stays within bounds after any movement
            gp.ui.ensureCursorInBounds();
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

        // FISHING MINIGAME STATE
        if (gp.gameState == gp.fishingMinigameState) {
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                useToolPressed = true;
            }
            if (code == KeyEvent.VK_BACK_SPACE) {
                backspacePressedFishing = true;
            }
        }


        if (gp.gameState == gp.endGameTriggerState){
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
            }
        }

        if (gp.gameState == gp.endGameState){
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true; 
            }
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                upPressed = true; 
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                downPressed = true; 
            }
        }

        if (gp.gameState == gp.shoppingState){
            if (code == KeyEvent.VK_ESCAPE) {
                    escPressed = true;
                    gp.gameState = gp.playState;
                    gp.eHandler.canTouchEvent = true;
                    return;
            }
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                upPressed = true; 
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                downPressed = true; 
            }
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                leftPressed = true; 
            }
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true; 
            }

        }

        if (gp.gameState == gp.statsState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.characterState;
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
        if (code == KeyEvent.VK_PAGE_UP) {
            pageUpPressed = false;
        }
        if (code == KeyEvent.VK_PAGE_DOWN) {
            pageDownPressed = false;
        }
        if (code == KeyEvent.VK_H) {
            npcInfoPressed = false;
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
        if (gp.gameState == gp.fishingMinigameState) {
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = false;
            }
            if (code == KeyEvent.VK_SPACE) {
                useToolPressed = false;
            }
            if (code == KeyEvent.VK_BACK_SPACE) {
                backspacePressedFishing = false;
            }
        }
    }
}