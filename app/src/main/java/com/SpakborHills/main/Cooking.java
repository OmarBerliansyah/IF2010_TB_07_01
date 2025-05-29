package com.SpakborHills.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.SpakborHills.entity.Entity;

public class Cooking {
    private GamePanel gp;
    private Map<String, Recipe> recipes;
    private Map<String, Boolean> unlockedRecipes;
    // DEPOSIT SYSTEM FOR COAL
    private int coalCookingDeposit = 0;
    // Player stats for unlocking recipes
    private int totalFishCaught = 0;
    private boolean hasHarvestedOnce = false;
    private boolean hasHotPepper = false;
    private boolean hasPufferfish = false;
    private boolean hasLegendFish = false;

    public enum FuelType {
        WOOD, COAL, DEPOSIT
    }

    public enum FuelChoice {
        WOOD_ONLY, COAL_ONLY, BOTH_AVAILABLE, NO_FUEL, DEPOSIT_AVAILABLE
    }

    public Cooking(GamePanel gp) {
        this.gp = gp;
        this.recipes = new HashMap<>();
        this.unlockedRecipes = new HashMap<>();
        initializeRecipes();
        initializeUnlockedRecipes();
    }

    private void initializeRecipes() {
        // Recipe 1: Fish n' Chips
        addRecipe("recipe_1", "Fish n' Chips", 
                 Arrays.asList(new Ingredient("Any Fish", 2),new Ingredient("Wheat", 1),new Ingredient("Potato", 1)), 50, 150, 135);
        
        // Recipe 2: Baguette (Default)
        addRecipe("recipe_2", "Baguette", Arrays.asList(new Ingredient("Wheat", 3)), 25, 100, 80);
        
        // Recipe 3: Sashimi
        addRecipe("recipe_3", "Sashimi",Arrays.asList(new Ingredient("Salmon", 3)),70, 300, 275);
        
        // Recipe 4: Fugu
        addRecipe("recipe_4", "Fugu",Arrays.asList(new Ingredient("Pufferfish", 1)),50, -1, 135);
        
        // Recipe 5: Wine (Default)
        addRecipe("recipe_5", "Wine",
                 Arrays.asList(new Ingredient("Grape", 2)), 20, 100, 90);
        
        // Recipe 6: Pumpkin Pie (Default)
        addRecipe("recipe_6", "Pumpkin Pie",
                 Arrays.asList( new Ingredient("Egg", 1), new Ingredient("Wheat", 1), new Ingredient("Pumpkin", 1)), 35, 120, 100);
        
        // Recipe 7: Veggie Soup
        addRecipe("recipe_7", "Veggie Soup", Arrays.asList( new Ingredient("Cauliflower", 1), new Ingredient("Parsnip", 1), new Ingredient("Potato", 1), new Ingredient("Tomato", 1)), 40, 140, 120);
        
        // Recipe 8: Fish Stew
        addRecipe("recipe_8", "Fish Stew", Arrays.asList(new Ingredient("Any Fish", 2),new Ingredient("Hot Pepper", 1),new Ingredient("Cauliflower", 2)), 70, 280, 260);
        
        // Recipe 9: Spakbor Salad (Default)
        addRecipe("recipe_9", "Spakbor Salad", Arrays.asList(new Ingredient("Melon", 1), new Ingredient("Cranberry", 1), new Ingredient("Blueberry", 1), new Ingredient("Tomato", 1)), 70, -1, 250);
        
        // Recipe 10: Fish Sandwich
        addRecipe("recipe_10", "Fish Sandwich", Arrays.asList(new Ingredient("Any Fish", 1), new Ingredient("Wheat", 2), new Ingredient("Tomato", 1), new Ingredient("Hot Pepper", 1)), 50, 200, 180);
        
        // Recipe 11: The Legends of Spakbor
        addRecipe("recipe_11", "The Legends of Spakbor", Arrays.asList(new Ingredient("Legend", 1), new Ingredient("Potato", 2), new Ingredient("Parsnip", 1), new Ingredient("Tomato", 1), new Ingredient("Eggplant", 1)), 100, -1, 2000);
    }

    private void initializeUnlockedRecipes() {
        // Default recipes
        unlockedRecipes.put("recipe_2", true); // Baguette
        unlockedRecipes.put("recipe_5", true); // Wine
        unlockedRecipes.put("recipe_6", true); // Pumpkin Pie
        unlockedRecipes.put("recipe_9", true); // Spakbor Salad
        
        // Locked recipes
        unlockedRecipes.put("recipe_1", false); // Fish n' Chips
        unlockedRecipes.put("recipe_3", false); // Sashimi - Fish 10 times
        unlockedRecipes.put("recipe_4", false); // Fugu - Catch pufferfish
        unlockedRecipes.put("recipe_7", false); // Veggie Soup - Harvest once
        unlockedRecipes.put("recipe_8", false); // Fish Stew - Get hot pepper
        unlockedRecipes.put("recipe_10", false); // Fish Sandwich
        unlockedRecipes.put("recipe_11", false); // Legends of Spakbor - Catch Legend
    }

    private void addRecipe(String id, String name, List<Ingredient> ingredients, int energy, int buyPrice, int sellPrice) {
        recipes.put(id, new Recipe(id, name, ingredients, energy, buyPrice, sellPrice));
    }

    public boolean canCook(String recipeId) {
        // Check if inside house
        if (gp.currentMap != 2) { 
            return false;
        }
        
        // Check if recipe is unlocked
        if (!unlockedRecipes.getOrDefault(recipeId, false)) {
            return false;
        }     
        
        Recipe recipe = recipes.get(recipeId);
        if (recipe == null) return false;
        
        // Check energy (10 energy cost for cooking)
        if (gp.player.energy < 10) return false;
        
        // Check ingredients
        for (Ingredient ingredient : recipe.ingredients) {
            if (!hasEnoughItems(ingredient.itemName, ingredient.quantity)) {
                return false;
            }
        }
        
        // Check fuel (Wood or Coal)
         // Check fuel (Wood, Coal, or Deposit)
        if (getFuelChoice() == FuelChoice.NO_FUEL) { return false; }
        return true;
    }

    public FuelChoice getFuelChoice() {
        boolean hasWood = hasEnoughItems("Wood", 1);
        boolean hasCoal = hasEnoughItems("Coal", 1);
        boolean hasDeposit = coalCookingDeposit > 0;
        
        if (hasDeposit) {
            return FuelChoice.DEPOSIT_AVAILABLE;
        } else if (hasCoal && hasWood) {
            return FuelChoice.BOTH_AVAILABLE;
        } else if (hasCoal) {
            return FuelChoice.COAL_ONLY;
        } else if (hasWood) {
            return FuelChoice.WOOD_ONLY;
        } else {
            return FuelChoice.NO_FUEL;
        }
    }

    // COOKING METHOD USING FACTORY
    public boolean cookRecipe(String recipeId, FuelType fuelType) {
        if (!canCook(recipeId)) {
            gp.ui.addMessage("Cannot cook this recipe!");
            return false;
        }
        
        Recipe recipe = recipes.get(recipeId);
        
        // Consume ingredients
        for (Ingredient ingredient : recipe.ingredients) {
            removeItems(ingredient.itemName, ingredient.quantity);
        }
        
        // Handle fuel consumption and deposits
        if (fuelType == FuelType.DEPOSIT) {
            // Use deposit (no fuel consumed)
            coalCookingDeposit--;
            gp.ui.addMessage(" Using coal deposit! Remaining: " + coalCookingDeposit);
        } else if (fuelType == FuelType.WOOD) {
            // Normal wood cooking
            removeItems("Wood", 1);
            gp.ui.addMessage(" Wood consumed for cooking!");
        } else if (fuelType == FuelType.COAL) {
            // Coal cooking - consume coal and add deposit
            removeItems("Coal", 1);
            coalCookingDeposit += 1; // Add 1 free cooking
            gp.ui.addMessage("Coal consumed! Added 1 cooking deposit!");
            gp.ui.addMessage("Coal deposit: " + coalCookingDeposit + " free cooks available!");
        }
        
        // Consume energy
        gp.player.energy -= 10;
        
        // ADVANCE TIME BY 1 HOUR
        int currentHour = gp.eManager.getHour();
        int currentMinute = gp.eManager.getMinute();
        int newHour = (currentHour + 1) % 24;
        gp.eManager.setTime(newHour, currentMinute);
        
        // CREATE COOKED ITEM USING FACTORY
        Entity cookedItem = ItemFactory.createCookedItem(recipe.id, gp);
        if (cookedItem != null) {
            gp.player.inventory.addItem(cookedItem);
        }
        
        // Success messages
        gp.ui.addMessage("Cooked " + recipe.name + "! (-10 energy, +1 hour)");
        gp.playSE(1);
        
        return true;
    }

    private boolean hasFuel() {
        return hasEnoughItems("Coal", 1) || hasEnoughItems("Wood", 1);
    }

    private boolean hasEnoughItems(String itemName, int requiredQuantity) {
        if (itemName.equals("Any Fish")) {
            return getTotalFishCount() >= requiredQuantity;
        }
        
        int totalCount = 0;
        for (Inventory.InventoryItem invItem : gp.player.inventory.getInventory()) {
            if (invItem.item.name.equals(itemName)) {
                totalCount += invItem.count;
                if (totalCount >= requiredQuantity) return true;
            }
        }
        return false;
    }

    private int getTotalFishCount() {
        String[] fishTypes = {"Salmon", "Catfish", "Sardine", "Pufferfish", "Legend", 
                             "Angler", "Crimsonfish", "Glacierfish", "BullHead", "Carp", 
                             "Chub", "LargemouthBass", "RainbowTrout", "Sturgeon", 
                             "MidnightCarp", "Flounder", "Halibut", "Octopus", "SuperCucumber"};
        
        int totalFish = 0;
        for (String fishType : fishTypes) {
            for (Inventory.InventoryItem invItem : gp.player.inventory.getInventory()) {
                if (invItem.item.name.equals(fishType)) {
                    totalFish += invItem.count;
                }
            }
        }
        return totalFish;
    }

    private void removeItems(String itemName, int quantityToRemove) {
        if (itemName.equals("Any Fish")) {
            removeFishItems(quantityToRemove);
            return;
        }
        
        List<Inventory.InventoryItem> inventory = gp.player.inventory.getInventory();
        for (int i = 0; i < inventory.size() && quantityToRemove > 0; i++) {
            Inventory.InventoryItem invItem = inventory.get(i);
            if (invItem.item.name.equals(itemName)) {
                if (invItem.count <= quantityToRemove) {
                    quantityToRemove -= invItem.count;
                    inventory.remove(i);
                    i--;
                } else {
                    invItem.count -= quantityToRemove;
                    quantityToRemove = 0;
                }
            }
        }
    }

    private void removeFishItems(int quantityToRemove) {
        String[] fishTypes = {"Salmon", "Catfish", "Sardine", "Pufferfish", "Legend", 
                             "Angler", "Crimsonfish", "Glacierfish", "BullHead", "Carp", 
                             "Chub", "LargemouthBass", "RainbowTrout", "Sturgeon", 
                             "MidnightCarp", "Flounder", "Halibut", "Octopus", "SuperCucumber"};
        
        List<Inventory.InventoryItem> inventory = gp.player.inventory.getInventory();
        
        for (String fishType : fishTypes) {
            if (quantityToRemove <= 0) break;
            
            for (int i = 0; i < inventory.size() && quantityToRemove > 0; i++) {
                Inventory.InventoryItem invItem = inventory.get(i);
                if (invItem.item.name.equals(fishType)) {
                    if (invItem.count <= quantityToRemove) {
                        quantityToRemove -= invItem.count;
                        inventory.remove(i);
                        i--;
                    } else {
                        invItem.count -= quantityToRemove;
                        quantityToRemove = 0;
                    }
                }
            }
        }
    }

    // RECIPE UNLOCKING METHODS
    public void onFishCaught(String fishType) {
        totalFishCaught++;
        
        // Unlock Sashimi after catching 10 fish
        if (totalFishCaught >= 10) {
            unlockRecipe("recipe_3", "Sashimi");
        }
        
        // Unlock Fugu after catching Pufferfish
        if (fishType.equals("Pufferfish")) {
            hasPufferfish = true;
            unlockRecipe("recipe_4", "Fugu");
        }
        
        // Unlock Legends of Spakbor after catching Legend
        if (fishType.equals("Legend")) {
            hasLegendFish = true;
            unlockRecipe("recipe_11", "The Legends of Spakbor");
        }
        
        checkIngredientBasedUnlocks();
    }
    
    public void onFirstHarvest() {
        if (!hasHarvestedOnce) {
            hasHarvestedOnce = true;
            unlockRecipe("recipe_7", "Veggie Soup");
        }
    }
    
    public void onHotPepperObtained() {
        if (!hasHotPepper) {
            hasHotPepper = true;
            unlockRecipe("recipe_8", "Fish Stew");
        }
    }

    public void checkIngredientBasedUnlocks() {
        // Fish n' Chips - unlock when player has fish, wheat, and potato
        if (!unlockedRecipes.getOrDefault("recipe_1", false)) {
            if (getTotalFishCount() >= 2 && hasEnoughItems("Wheat", 1) && hasEnoughItems("Potato", 1)) {
                unlockRecipe("recipe_1", "Fish n' Chips");
            }
        }
        
        // Fish Sandwich - unlock when player has fish, wheat, tomato, and hot pepper
        if (!unlockedRecipes.getOrDefault("recipe_10", false)) {
            if (getTotalFishCount() >= 1 && hasEnoughItems("Wheat", 2) && 
                hasEnoughItems("Tomato", 1) && hasEnoughItems("Hot Pepper", 1)) {
                unlockRecipe("recipe_10", "Fish Sandwich");
            }
        }
    }
    
    public void onItemObtained(String itemName) {
        if (itemName.equals("Hot Pepper")) {
            onHotPepperObtained();
        }
        
        checkIngredientBasedUnlocks();
    }
    
    private void unlockRecipe(String recipeId, String recipeName) {
        if (!unlockedRecipes.getOrDefault(recipeId, false)) {
            unlockedRecipes.put(recipeId, true);
            gp.ui.addMessage("🎉 New recipe unlocked: " + recipeName + "!");
        }
    }
    
    // GETTERS
    public List<Recipe> getUnlockedRecipes() {
        List<Recipe> unlocked = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : unlockedRecipes.entrySet()) {
            if (entry.getValue()) {
                Recipe recipe = recipes.get(entry.getKey());
                if (recipe != null) {
                    unlocked.add(recipe);
                }
            }
        }
        return unlocked;
    }
    
    public String getFuelStatus() {
        int coalCount = 0;
        int woodCount = 0;
        
        for (Inventory.InventoryItem invItem : gp.player.inventory.getInventory()) {
            if (invItem.item.name.equals("Coal")) {
                coalCount += invItem.count;
            } else if (invItem.item.name.equals("Wood")) {
                woodCount += invItem.count;
            }
        }
        
        String status = "Fuel: ";
        if (coalCount > 0) status += "Coal x" + coalCount + " (batch: 2 recipes) ";
        if (woodCount > 0) status += "Wood x" + woodCount + " (single recipe) ";
        
        if (coalCount == 0 && woodCount == 0) {
            status += "None available";
        }
        return status;
    }
    
    public Recipe getRecipe(String recipeId) {
        return recipes.get(recipeId);
    }
    
    // INNER CLASSES
    public static class Recipe {
        public String id;
        public String name;
        public List<Ingredient> ingredients;
        public int energyRestore;
        public int buyPrice;
        public int sellPrice;
        
        public Recipe(String id, String name, List<Ingredient> ingredients, 
                     int energyRestore, int buyPrice, int sellPrice) {
            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.energyRestore = energyRestore;
            this.buyPrice = buyPrice;
            this.sellPrice = sellPrice;
        }
    }
    
    public static class Ingredient {
        public String itemName;
        public int quantity;
        
        public Ingredient(String itemName, int quantity) {
            this.itemName = itemName;
            this.quantity = quantity;
        }
    }
}