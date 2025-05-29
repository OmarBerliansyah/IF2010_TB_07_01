package com.SpakborHills.entity;

import java.util.ArrayList;
import java.util.List;

import com.SpakborHills.main.GamePanel;

public abstract class NPC extends Entity {
    protected int heartPoints;
    protected List<String> lovedItems;
    protected List<String> likedItems;
    protected List<String> hatedItems;
    protected RelationshipStatus relationshipStatus;
    
    // Statistics for end game
    protected int chattingFrequency;
    protected int giftingFrequency;
    protected int visitingFrequency;
    public boolean dialogueInProgress = false;
    
    public enum RelationshipStatus {
        FRIEND, FIANCE, SPOUSE
    }
    
    public NPC(GamePanel gp, String name) {
        super(gp);
        this.name = name;
        this.heartPoints = 0;
        this.lovedItems = new ArrayList<>();
        this.likedItems = new ArrayList<>();
        this.hatedItems = new ArrayList<>();
        this.relationshipStatus = RelationshipStatus.FRIEND;
        this.chattingFrequency = 0;
        this.giftingFrequency = 0;
        this.visitingFrequency = 0;
        
        initializePreferences();
    }
    
    // Abstract method untuk menginisialisasi preferensi item setiap NPC
    protected abstract void initializePreferences();
    
    // Method untuk chatting dengan NPC
    public void chat() {
        if (dialogueIndex == 0) {
            chattingFrequency++;
            addHeartPoints(10);
            System.out.println(name + " - New conversation! Heart points: " + heartPoints);
        }else{
            System.out.println(name + " - Continuing dialogue " + dialogueIndex + ". Heart points: " + heartPoints);
        }
        
        // Just call speak to show dialogue
        speak();
    }
    
    // Method untuk memberikan gift ke NPC
    public void giveGift(String itemName) {
        giftingFrequency++;
        
        if (lovedItems.contains(itemName)) {
            addHeartPoints(25);
            gp.ui.addMessage("This gift is fabulous! Thank you so much!(+25 heart points)");// LOVED
        } else if (likedItems.contains(itemName)) {
            addHeartPoints(20);
            gp.ui.addMessage("Thank you! I'm feeling a positive energy from this gift (+20 heart points)"); // LIKED
        } else if (hatedItems.contains(itemName)) {
            addHeartPoints(-25);
            gp.ui.addMessage(" Sorry. I don't like this.(-25 heart points)");
        } else {
            // Neutral item - no heart points change
            gp.ui.addMessage(gp.ui.inputPlayerName + " Makasih ya.");
        }
    }
    
    // Method untuk menambah heart points
    public void addHeartPoints(int points) {
        heartPoints = Math.max(0, Math.min(150, heartPoints + points));
    }
    
    // Method untuk proposal
    public boolean propose() {
        if (heartPoints >= 150 && relationshipStatus == RelationshipStatus.FRIEND) {
            relationshipStatus = RelationshipStatus.FIANCE;
            gp.ui.addMessage(name + " accepted your proposal! You are now engaged!");
            return true;
        } else {
            gp.ui.addMessage(name + " rejected your proposal. We need to be closer first.");
            return false;
        }
    }
    
    // Method untuk menikah
    public boolean marry() {
        if (relationshipStatus == RelationshipStatus.FIANCE) {
            relationshipStatus = RelationshipStatus.SPOUSE;
            gp.ui.addMessage("You married to " + name + "! Congratulations!");
            return true;
        }
        return false;
    }
    
    // Method untuk visiting (dipanggil ketika player mengunjungi rumah NPC)
    public void visit() {
        visitingFrequency++;
    }
    @Override
    public void speak(){
        if (dialogue == null || dialogueIndex < 0 || dialogueIndex >= dialogue.length) {
            System.out.println("ERROR: Invalid dialogue state for " + name);
            dialogueIndex = 0;
        }
        // Show current dialogue
        if (dialogue[dialogueIndex] != null) {
            gp.ui.currentDialogue = dialogue[dialogueIndex];
            System.out.println(name + " - Showing dialogue[" + dialogueIndex + "]: " + dialogue[dialogueIndex]);
            
            dialogueIndex++;
            
            // Check if next dialogue exists
            if (dialogueIndex >= dialogue.length || dialogue[dialogueIndex] == null) {
                // We just showed the last dialogue, reset for next conversation
                dialogueIndex = 0;
                System.out.println(name + " - Last dialogue shown! Auto-reset to 0 for next conversation");
            }
        } else {
            // Fallback if current dialogue is null
            System.out.println("ERROR: dialogue[" + dialogueIndex + "] is null for " + name);
            gp.ui.currentDialogue = "...";
            dialogueIndex = 0;
        }


        // Face the player
        switch(gp.player.direction){
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }
    // Getters
    public int getHeartPoints() { 
        return heartPoints; 
    }
    public RelationshipStatus getRelationshipStatus() { 
        return relationshipStatus; 
    }
    public int getChattingFrequency() { 
        return chattingFrequency; 
    }
    public int getGiftingFrequency() { 
        return giftingFrequency; 
    }
    public int getVisitingFrequency() { 
        return visitingFrequency; 
    }
    public List<String> getLovedItems() { 
        return new ArrayList<>(lovedItems); 
    }
    public List<String> getLikedItems() { 
        return new ArrayList<>(likedItems); // ini intinya cuma ngasih liat copy dari list dia
    }
    public List<String> getHatedItems() { 
        return new ArrayList<>(hatedItems); 
    }
}
