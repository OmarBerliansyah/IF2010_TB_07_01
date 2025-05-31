package com.SpakborHills.entity;

import com.SpakborHills.main.KeyHandler;

public class WatchingTVState extends PlayerState {
    
    public WatchingTVState(Player player) {
        super(player);
    }
    
    @Override
    public void onEnter() {
        // Start watching TV
        player.gp.gameState = player.gp.dialogueState;
        player.gp.ui.showingWatchTV = true;
        
        // Consume energy and time
        player.energy -= 5;
        player.gp.eManager.addMinutesToTime(15);
        player.gp.eHandler.canTouchEvent = false;
        
        System.out.println("Started watching TV - showing TV interface");
    }
    
    @Override
    public void handleInput(KeyHandler keyH) {
        // During watching TV state, disable all normal player actions
        keyH.upPressed = false;
        keyH.downPressed = false;
        keyH.leftPressed = false;
        keyH.rightPressed = false;
        keyH.useToolPressed = false;
        keyH.giftPressed = false;
        keyH.proposePressed = false;
        keyH.marryPressed = false;
        
        // Handle ENTER to exit TV watching
        if (keyH.enterPressed) {
            // Exit TV watching
            player.gp.ui.showingWatchTV = false;
            player.gp.eHandler.canTouchEvent = false; 
            player.gp.gameState = player.gp.playState;
            player.changeState(new NormalState(player));
            keyH.enterPressed = false;
            player.gp.ui.addMessage("You finished watching TV for 15 minutes.");
        }
    }
    
    @Override
    public void update() {
        // Check if TV watching is closed
        if (!player.gp.ui.showingWatchTV) {
            // TV watching ended, return to normal state
            player.changeState(new NormalState(player));
            player.gp.eHandler.canTouchEvent = false;
            System.out.println("TV watching ended, returning to normal state");
        }
    }
    
    @Override
    public void onExit() {
        System.out.println("Exiting watching TV state");
        // Ensure UI state is clean
        player.gp.ui.showingWatchTV = false;
        player.gp.eHandler.canTouchEvent = true;
        player.gp.gameState = player.gp.playState;
    }
    
    @Override
    public String getStateName() {
        return "Watching TV";
    }
}