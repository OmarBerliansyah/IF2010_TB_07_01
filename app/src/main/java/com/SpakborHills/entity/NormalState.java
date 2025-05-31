package com.SpakborHills.entity;

import com.SpakborHills.main.KeyHandler;

public class NormalState extends PlayerState {
    
    public NormalState(Player player) {
        super(player);
    }
    
    @Override
    public void handleInput(KeyHandler keyH) {
        
    }
    
    @Override
    public void update() {

    }
    
    @Override
    public String getStateName() {
        return "Normal";
    }
}