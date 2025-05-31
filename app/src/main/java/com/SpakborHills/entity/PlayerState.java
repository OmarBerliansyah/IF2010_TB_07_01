package com.SpakborHills.entity;

import com.SpakborHills.main.KeyHandler;

public abstract class PlayerState {
    protected Player player;
    
    public PlayerState(Player player) {
        this.player = player;
    }
    
    // Handle input specific to this state
    public abstract void handleInput(KeyHandler keyH);
    
    // Update logic specific to this state
    public abstract void update();
    
   // Get the name of this state for debugging/UI
    public abstract String getStateName();
    
    // Called when entering this state
    public void onEnter() {
        // Default implementation - can be overridden
    }
    
    // Called when exiting this state
    public void onExit() {
        // Default implementation - can be overridden
    }
}