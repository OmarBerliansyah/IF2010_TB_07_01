package com.SpakborHills.data;

import java.awt.image.BufferedImage;

public class ItemDefinition {
    public final String id;
    public final String name;
    public final String category;
    public final int sellPrice;
    public final int buyPrice;
    public final String description;
    public BufferedImage uiImage; 

    public final boolean isPlantable;
    public final String harvestId;
    public final int maxStackSize;
    public final boolean isEdible;
    public final int energyRestored;
    public final int healthRestored;
    public final boolean isSellable;
    public final boolean isTool;
    public final boolean isEquippable;
    public final int capacity;
    public final boolean isFurniture;
    public final String furnitureType; 
    public final boolean isInteractableObject;
    public final boolean isCookingStation;
    public final boolean isQuestItem;
    public final String itemType; // Seed, Crop, Tool, Fish, CookedDish, Material etc.

    public ItemDefinition(String id, String name, String category, int sellPrice, int buyPrice, String description, String attributesString, BufferedImage uiImage) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.description = description;
        this.uiImage = uiImage;

        boolean tempIsPlantable = false;
        String tempHarvestId = null;
        int tempMaxStackSize = 1;
        boolean tempIsEdible = false;
        int tempEnergyRestored = 0;
        int tempHealthRestored = 0;
        boolean tempIsSellable = false;
        boolean tempIsTool = false;
        boolean tempIsEquippable = false;
        int tempCapacity = 0;
        boolean tempIsFurniture = false;
        String tempFurnitureType = null;
        boolean tempIsInteractableObject = false;
        boolean tempIsCookingStation = false;
        boolean tempIsQuestItem = false;
        String tempItemType = category;

        if (attributesString != null && !attributesString.isEmpty()) {
            String[] attributes = attributesString.split(";");
            for (String attr : attributes) {
                String[] parts = attr.split(":");
                String key = parts[0].trim().toUpperCase();
                String value = parts.length > 1 ? parts[1].trim() : null;

                switch (key) {
                    case "PLANTABLE": tempIsPlantable = true; break;
                    case "HARVEST_ID": tempHarvestId = value; break;
                    case "STACKABLE": if (value != null) tempMaxStackSize = Integer.parseInt(value); break;
                    case "EDIBLE": tempIsEdible = true; break;
                    case "ENERGY": if (value != null) tempEnergyRestored = Integer.parseInt(value); break;
                    case "HEALTH": if (value != null) tempHealthRestored = Integer.parseInt(value); break;
                    case "SELLABLE": tempIsSellable = true; break;
                    case "TOOL": tempIsTool = true; break;
                    case "EQUIPPABLE": tempIsEquippable = true; break;
                    case "CAPACITY": if (value != null) tempCapacity = Integer.parseInt(value); break;
                    case "FURNITURE": tempIsFurniture = true; break;
                    case "INTERACTABLE": tempIsInteractableObject = true; break;
                    case "COOKING_STATION": tempIsCookingStation = true; break;
                    case "FURNITURE_TYPE": tempFurnitureType = value; break;
                    case "QUEST_ITEM": tempIsQuestItem = true; break;
                    case "TYPE": if (value != null) tempItemType = value; break;
                }
            }
        }
        this.isPlantable = tempIsPlantable;
        this.harvestId = tempHarvestId;
        this.maxStackSize = tempMaxStackSize;
        this.isEdible = tempIsEdible;
        this.energyRestored = tempEnergyRestored;
        this.healthRestored = tempHealthRestored;
        this.isSellable = tempIsSellable;
        this.isTool = tempIsTool;
        this.isEquippable = tempIsEquippable;
        this.capacity = tempCapacity;
        this.isFurniture = tempIsFurniture;
        this.furnitureType = tempFurnitureType;
        this.isInteractableObject = tempIsInteractableObject;
        this.isCookingStation = tempIsCookingStation;
        this.isQuestItem = tempIsQuestItem;
        this.itemType = tempItemType;
    }
}