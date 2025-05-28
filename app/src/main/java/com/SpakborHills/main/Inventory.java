package com.SpakborHills.main;

import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.Player;
import com.SpakborHills.objects.OBJ_BullHead;
import com.SpakborHills.objects.OBJ_CookedPigHead;
import com.SpakborHills.objects.OBJ_FishStew;
import com.SpakborHills.objects.OBJ_FishingRod;
import com.SpakborHills.objects.OBJ_Hoe;
import com.SpakborHills.objects.OBJ_Parsnip;
import com.SpakborHills.objects.OBJ_ParsnipSeeds;
import com.SpakborHills.objects.OBJ_Pickaxe;
import com.SpakborHills.objects.OBJ_WateringCan;

public class Inventory {
    public ArrayList<Inventory.InventoryItem> inventory = new ArrayList<>();    
    private final int maxInventorySize = 20;
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
        inventory.add(new InventoryItem(new OBJ_Hoe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_WateringCan(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Pickaxe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishingRod(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Parsnip(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishStew(gp), 1));
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