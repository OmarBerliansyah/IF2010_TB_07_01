package com.SpakborHills.main;

import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.Player;
import com.SpakborHills.objects.*;

public class Inventory {
    public ArrayList<Inventory.InventoryItem> inventory = new ArrayList<>();    
    private final int maxInventorySize = 9999;
    private Entity equippedItem;
    private GamePanel gp;
    private Player player; 

    public Inventory(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        setItems();
    }

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    public Entity getEquippedItem() {
        return equippedItem;
    }

    public void setItems() {
        inventory.add(new InventoryItem(new OBJ_ParsnipSeeds(gp), 15));
        // inventory.add(new InventoryItem(new OBJ_Cauliflower(gp), 180)); //Debug
        inventory.add(new InventoryItem(new OBJ_Hoe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_WateringCan(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Pickaxe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishingRod(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Parsnip(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishStew(gp), 1));
        inventory.add(new InventoryItem (new OBJ_Wood(gp),2));
        inventory.add(new InventoryItem (new OBJ_Grape(gp),6));
        inventory.add(new InventoryItem(new OBJ_Coal(gp),1));
        inventory.add(new InventoryItem(new OBJ_Ring(gp),1));
        inventory.add(new InventoryItem(new OBJ_BlueberrySeeds(gp),2));
        inventory.add(new InventoryItem(new OBJ_LargemouthBass(gp), 6));
        inventory.add(new InventoryItem(new OBJ_RainbowTrout(gp), 4));
        inventory.add(new InventoryItem(new OBJ_Sturgeon(gp), 2));
        inventory.add(new InventoryItem(new OBJ_MidnightCarp(gp), 3));
        inventory.add(new InventoryItem(new OBJ_Flounder(gp), 5));
        inventory.add(new InventoryItem(new OBJ_Halibut(gp), 3));
        inventory.add(new InventoryItem(new OBJ_Octopus(gp), 2));
        inventory.add(new InventoryItem(new OBJ_Pufferfish(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Sardine(gp), 15));
        inventory.add(new InventoryItem(new OBJ_SuperCucumber(gp), 2));
        inventory.add(new InventoryItem(new OBJ_Catfish(gp), 4));
        inventory.add(new InventoryItem(new OBJ_Salmon(gp), 6));
        inventory.add(new InventoryItem(new OBJ_SpakborSalad(gp), 1));
        inventory.add(new InventoryItem(new OBJ_TheLegendsOfSpakbor(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Fugu(gp), 1));
        inventory.add(new InventoryItem(new OBJ_CookedPigHead(gp), 1));
                

    }

    public void addItem(Entity item) {
        if (inventory.size() < maxInventorySize) {
            boolean itemAlreadyInInventory = false;
            for (InventoryItem invItem : inventory) {
                if (invItem.item.name.equals(item.name)) {
                    invItem.count++;
                    itemAlreadyInInventory = true;
                    break;
                }
            }
            if (!itemAlreadyInInventory) {
                inventory.add(new InventoryItem(item, 1));
            }
            gp.playSE(1);
            gp.ui.addMessage("Got a " + item.name + "!");
        } else {
            gp.ui.addMessage("You cannot carry any more!");
        }
    }

    public void equipItem(int inventoryIndex) {
        if (inventoryIndex >= 0 && inventoryIndex < inventory.size()) {
            equippedItem = inventory.get(inventoryIndex).item;
        }
    }

    public void unEquipItem() {
        equippedItem = null;
    }

    public static class InventoryItem {
        public Entity item;
        public int count;

        public InventoryItem(Entity item, int count) {
            this.item = item;
            this.count = count;
        }
    }
}