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
        // Essential Tools
        inventory.add(new InventoryItem(new OBJ_Hoe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_WateringCan(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Pickaxe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishingRod(gp), 1));
        
        // Seeds
        inventory.add(new InventoryItem(new OBJ_ParsnipSeeds(gp), 15));
        inventory.add(new InventoryItem(new OBJ_CauliflowerSeeds(gp), 5));
        inventory.add(new InventoryItem(new OBJ_PotatoSeeds(gp), 5));
        
        // Crops/Produce
        inventory.add(new InventoryItem(new OBJ_Parsnip(gp), 2));
        inventory.add(new InventoryItem(new OBJ_Cauliflower(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Potato(gp), 3));

        
        // Basic Resources
        inventory.add(new InventoryItem(new OBJ_Wood(gp), 20));

        
        // Fish (examples)
        inventory.add(new InventoryItem(new OBJ_Sardine(gp), 2));
        inventory.add(new InventoryItem(new OBJ_Carp(gp), 1));
        
        // Special Items
        inventory.add(new InventoryItem(new OBJ_Ring(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Bed(gp), 100));
        inventory.add(new InventoryItem(new OBJ_BullHead(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Angler(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Blueberry(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishStew(gp), 1));
        inventory.add(new InventoryItem(new OBJ_EggplantSeeds(gp), 1));
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

    public boolean addItemBool(Entity item) {
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
            return true;
        } else {
            gp.ui.addMessage("You cannot carry any more!");
            return false;
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