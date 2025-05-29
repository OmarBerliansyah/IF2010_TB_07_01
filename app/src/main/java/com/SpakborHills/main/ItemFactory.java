package com.SpakborHills.main;

import java.util.HashMap;
import java.util.Map;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.objects.OBJ_Baguette;
import com.SpakborHills.objects.OBJ_FishSandwich;
import com.SpakborHills.objects.OBJ_FishStew;
import com.SpakborHills.objects.OBJ_Fugu;
import com.SpakborHills.objects.OBJ_PumpkinPie;
import com.SpakborHills.objects.OBJ_Sashimi;
import com.SpakborHills.objects.OBJ_SpakborSalad;
import com.SpakborHills.objects.OBJ_TheLegendsOfSpakbor;
import com.SpakborHills.objects.OBJ_VeggieSoup;
import com.SpakborHills.objects.OBJ_Wine;

public class ItemFactory {
    private static final Map<String, ItemCreator> itemCreators = new HashMap<>();
    
    // Functional interface for item creation
    @FunctionalInterface
    private interface ItemCreator {
        Entity create(GamePanel gp);
    }
    
    // Static initialization block - register all items
    static {
        // Cooked Food Items
        //itemCreators.put("recipe_1", OBJ_FishNChips::new);
        itemCreators.put("recipe_2", OBJ_Baguette::new);
        itemCreators.put("recipe_3", OBJ_Sashimi::new);
        itemCreators.put("recipe_4", OBJ_Fugu::new);
        itemCreators.put("recipe_5", OBJ_Wine::new);
        itemCreators.put("recipe_6", OBJ_PumpkinPie::new);
        itemCreators.put("recipe_7", OBJ_VeggieSoup::new);
        itemCreators.put("recipe_8", OBJ_FishStew::new);
        itemCreators.put("recipe_9", OBJ_SpakborSalad::new);
        itemCreators.put("recipe_10", OBJ_FishSandwich::new);
        itemCreators.put("recipe_11", OBJ_TheLegendsOfSpakbor::new);
    }
    
    /**
     * Create item by recipe ID
     */
    public static Entity createCookedItem(String recipeId, GamePanel gp) {
         ItemCreator creator = itemCreators.get(recipeId);
        if (creator != null) {
            try {
                return creator.create(gp);
            } catch (Exception e) {
                System.err.println("ItemFactory: Failed to create item for recipe " + recipeId + ": " + e.getMessage());
                // You might still want to return null here if creation itself fails
                return null;
            }
        }
        System.err.println("ItemFactory: Unknown recipe ID: " + recipeId + ". No item will be created.");
        return null; 
    }
    
    /**
     * Create item by name (for general use)
     */
    public static Entity createItemByName(String itemName, GamePanel gp) {
        ItemCreator creator = itemCreators.get(itemName);
        if (creator != null) {
            try {
                return creator.create(gp);
            } catch (Exception e) {
                System.err.println("ItemFactory: Failed to create item " + itemName + ": " + e.getMessage());
            }
        }
        
        System.err.println("ItemFactory: Unknown item name: " + itemName);
        return null;
    }
    
    /**
     * Register new item creator (for mods/extensions)
     */
    public static void registerItem(String identifier, ItemCreator creator) {
        itemCreators.put(identifier, creator);
        System.out.println("ItemFactory: Registered new item: " + identifier);
    }
    
    /**
     * Check if item exists in factory
     */
    public static boolean hasItem(String identifier) {
        return itemCreators.containsKey(identifier);
    }
    
    /**
     * Get all registered item identifiers
     */
    public static String[] getAllItemIdentifiers() {
        return itemCreators.keySet().toArray(new String[0]);
    }
}