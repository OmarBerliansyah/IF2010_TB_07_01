package com.SpakborHills.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {
    private Map<String, ItemDefinition> itemDefinitions;
    
    public ItemManager() {
        itemDefinitions = new HashMap<>();
        loadItemDefinitions();
    }
    
    private void loadItemDefinitions() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("data/game_items.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 10) {
                    String id = parts[0];
                    String name = parts[1];
                    String category = parts[2];
                    int sellPrice = Integer.parseInt(parts[3]);
                    int buyPrice = Integer.parseInt(parts[4]);
                    String description = parts[5].replace("\"", "");
                    boolean isSellable = Boolean.parseBoolean(parts[6]);
                    boolean isBuyable = Boolean.parseBoolean(parts[7]);
                    int energyValue = Integer.parseInt(parts[8]);
                    int stackSize = Integer.parseInt(parts[9]);
                    
                    ItemDefinition item = new ItemDefinition(id, name, category, sellPrice, buyPrice, description, isSellable, isBuyable, energyValue, stackSize);
                    itemDefinitions.put(name, item);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ItemDefinition getDefinitionByName(String name) {
        return itemDefinitions.get(name);
    }
    
    public List<ItemDefinition> getAllDefinitions() {
        return new ArrayList<>(itemDefinitions.values());
    }
}
