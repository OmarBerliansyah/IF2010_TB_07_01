package com.SpakborHills.data;

import com.SpakborHills.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemManager {
    private final Map<String, ItemDefinition> itemDefinitions = new HashMap<>();
    private GamePanel gp;

    public ItemManager(GamePanel gp) {
        this.gp = gp;
        loadItemDefinitions("/data/game_items.csv");
    }

    private void loadItemDefinitions(String csvFilePath) {
        try (InputStream is = getClass().getResourceAsStream(csvFilePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            br.readLine(); // Skip header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Handles commas within quotes
                if (values.length < 7) {
                    System.err.println("ItemManager: Skipping malformed line in " + csvFilePath + ": " + line);
                    continue;
                }

                String id = values[0].trim().replaceAll("\"", "");
                String name = values[1].trim().replaceAll("\"", "");
                String category = values[2].trim().replaceAll("\"", "");
                int sellPrice = Integer.parseInt(values[3].trim().replaceAll("\"", ""));
                int buyPrice = Integer.parseInt(values[4].trim().replaceAll("\"", ""));
                String description = values[5].trim().replaceAll("\"", "");
                String attributesString = values[6].trim().replaceAll("\"", "");
                BufferedImage itemDefImage = null;

                try {
                    // Path for UI icons, distinct from entity world sprites
                    String imagePath = "/ui_icons/items/" + id + ".png"; // e.g., /resources/ui_icons/items/001.png
                    InputStream imageStream = getClass().getResourceAsStream(imagePath);
                    if (imageStream != null) {
                        BufferedImage originalImage = ImageIO.read(imageStream);
                        // Typically icons are a fixed size, e.g., 32x32 or 48x48, not necessarily gp.tileSize
                        // itemDefImage = UtilityTool.scaleImage(originalImage, 48, 48); // Example: scale to 48x48
                        itemDefImage = originalImage; // Or use as is if already correctly sized
                        imageStream.close();
                    }
                } catch (Exception e) {
                    // It's okay if an icon isn't found, can fallback to entity sprite or blank
                    // System.err.println("ItemManager: UI icon not found for item ID: " + id + " - " + e.getMessage());
                }

                ItemDefinition definition = new ItemDefinition(id, name, category, sellPrice, buyPrice, description, attributesString, itemDefImage);
                itemDefinitions.put(id, definition);
            }
            System.out.println("ItemManager: Loaded " + itemDefinitions.size() + " item definitions.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ItemManager: Critical error loading item definitions from " + csvFilePath + ". Ensure file exists and format is correct.");
        }
    }

    public ItemDefinition getDefinition(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return itemDefinitions.get(id);
    }

    public String getItem(String name){
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (Map.Entry<String, ItemDefinition> entry : itemDefinitions.entrySet()) {
            if (entry.getValue().name.equals(name)) {
                    return entry.getKey();
                }
            }

        return null; 
    }

    public ItemDefinition getDefinitionByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (ItemDefinition def : itemDefinitions.values()) {
                if (def.name.equals(name)) {
                    return def;
                }
            }
        return null;
    }
}