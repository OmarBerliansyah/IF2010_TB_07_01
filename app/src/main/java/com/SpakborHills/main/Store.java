package com.SpakborHills.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.SpakborHills.data.ItemDefinition;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.entity.Player;
import com.SpakborHills.entity.Entity;

public class Store {
    private GamePanel gp;
    private List<ItemDefinition> storeItems;
    private int selectedItemIndex = 0;
    private int scrollOffset = 0;
    private final int ITEMS_PER_ROW = 3;
    private final int VISIBLE_ROWS = 3;
    private final int ITEMS_PER_PAGE = ITEMS_PER_ROW * VISIBLE_ROWS;
    
    private final int STORE_WIDTH = 600;
    private final int STORE_HEIGHT = 500;
    private final int ITEM_SLOT_SIZE = 80;
    private final int DESCRIPTION_HEIGHT = 120;
    
    public Store(GamePanel gp) {
        this.gp = gp;
        loadStoreItems();
    }
    
    private void loadStoreItems() {
        storeItems = new ArrayList<>();
        for (ItemDefinition item : gp.itemManager.getAllDefinitions()) {
            if (item.isBuyable) {
                storeItems.add(item);
            }
        }
    }
    
    public void update() {
        if (gp.keyH.upPressed) {
            if (selectedItemIndex >= ITEMS_PER_ROW) {
                selectedItemIndex -= ITEMS_PER_ROW;
            }
            gp.keyH.upPressed = false;
        }
        
        if (gp.keyH.downPressed) {
            if (selectedItemIndex + ITEMS_PER_ROW < storeItems.size()) {
                selectedItemIndex += ITEMS_PER_ROW;
            }
            gp.keyH.downPressed = false;
        }
        
        if (gp.keyH.leftPressed) {
            if (selectedItemIndex % ITEMS_PER_ROW > 0) {
                selectedItemIndex--;
            }
            gp.keyH.leftPressed = false;
        }
        
        if (gp.keyH.rightPressed) {
            if (selectedItemIndex % ITEMS_PER_ROW < ITEMS_PER_ROW - 1 && 
                selectedItemIndex + 1 < storeItems.size()) {
                selectedItemIndex++;
            }
            gp.keyH.rightPressed = false;
        }
        
        updateScrollOffset();
        
        if (gp.keyH.enterPressed) {
            purchaseSelectedItem();
            gp.keyH.enterPressed = false;
        }
    
        if (gp.keyH.escPressed) {
            gp.gameState = gp.playState;
            gp.keyH.escPressed = false;
        }
    }
    
    private void updateScrollOffset() {
        int selectedRow = selectedItemIndex / ITEMS_PER_ROW;
        int visibleStartRow = scrollOffset / ITEMS_PER_ROW;
        int visibleEndRow = visibleStartRow + VISIBLE_ROWS - 1;
        
        if (selectedRow < visibleStartRow) {
            scrollOffset = selectedRow * ITEMS_PER_ROW;
        } else if (selectedRow > visibleEndRow) {
            scrollOffset = (selectedRow - VISIBLE_ROWS + 1) * ITEMS_PER_ROW;
        }
    }
    
    private void purchaseSelectedItem() {
        if (selectedItemIndex >= 0 && selectedItemIndex < storeItems.size()) {
            ItemDefinition selectedItem = storeItems.get(selectedItemIndex);
            
            if (gp.player.gold >= selectedItem.buyPrice) {
                try {
                    String className = "com.SpakborHills.objects.OBJ_" + selectedItem.name.replace(" ", "").replace("'", "");
                    Class<?> itemClass = Class.forName(className);
                    com.SpakborHills.entity.Entity itemEntity = (com.SpakborHills.entity.Entity) itemClass.getDeclaredConstructor(GamePanel.class).newInstance(gp);
                    
                    boolean added = gp.player.inventory.addItemBool(itemEntity);
                    
                    if (added) {
                        gp.player.gold -= selectedItem.buyPrice;
                        gp.player.totalExpenditure += selectedItem.buyPrice;
                        gp.ui.addMessage("Purchased " + selectedItem.name + " for " + selectedItem.buyPrice + "g!");
                    }
                     else {
                        gp.ui.addMessage("Inventory is full!");
                    }
                } 
                catch (Exception e) {
                    gp.ui.addMessage("Error purchasing item: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                gp.ui.addMessage("Not enough gold! Need " + selectedItem.buyPrice + "g");
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        int storeX = (gp.screenWidth - STORE_WIDTH) / 2;
        int storeY = (gp.screenHeight - STORE_HEIGHT) / 2;
        
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(storeX, storeY, STORE_WIDTH, STORE_HEIGHT, 20, 20);
        
        g2.setColor(new Color(139, 69, 19));
        g2.fillRoundRect(storeX + 5, storeY + 5, STORE_WIDTH - 10, STORE_HEIGHT - 10, 15, 15);
        
        g2.setColor(new Color(222, 184, 135));
        g2.fillRoundRect(storeX + 10, storeY + 10, STORE_WIDTH - 20, STORE_HEIGHT - 20, 10, 10);
        
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String title = "Emily's Store";
        FontMetrics fm = g2.getFontMetrics();
        int titleX = storeX + (STORE_WIDTH - fm.stringWidth(title)) / 2;
        g2.drawString(title, titleX, storeY + 40);
        
        drawItemGrid(g2, storeX, storeY);
        
        drawDescriptionBox(g2, storeX, storeY);
        
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.YELLOW);
        g2.drawString("Gold: " + gp.player.gold + "g", storeX + 20, storeY + STORE_HEIGHT - 20);
        
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("Arrow Keys: Navigate | Enter: Buy | ESC: Exit", 
                     storeX + 20, storeY + STORE_HEIGHT - 40);
    }
    
    private void drawItemGrid(Graphics2D g2, int storeX, int storeY) {
        int gridStartX = storeX + 30;
        int gridStartY = storeY + 60;
        
        for (int i = 0; i < ITEMS_PER_PAGE && (scrollOffset + i) < storeItems.size(); i++) {
            int itemIndex = scrollOffset + i;
            ItemDefinition item = storeItems.get(itemIndex);
            
            int row = i / ITEMS_PER_ROW;
            int col = i % ITEMS_PER_ROW;
            
            int slotX = gridStartX + col * (ITEM_SLOT_SIZE + 10);
            int slotY = gridStartY + row * (ITEM_SLOT_SIZE + 10);
            
            // Draw slot background
            if (itemIndex == selectedItemIndex) {
                g2.setColor(new Color(255, 255, 0, 100));
                g2.fillRoundRect(slotX - 2, slotY - 2, ITEM_SLOT_SIZE + 4, ITEM_SLOT_SIZE + 4, 8, 8);
            }
            
            g2.setColor(new Color(101, 67, 33));
            g2.fillRoundRect(slotX, slotY, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE, 5, 5);
            
            g2.setColor(new Color(160, 120, 80));
            g2.fillRoundRect(slotX + 2, slotY + 2, ITEM_SLOT_SIZE - 4, ITEM_SLOT_SIZE - 4, 3, 3);
            
            // Draw item image
            BufferedImage itemImage = getItemImage(item.name);
            if (itemImage != null) {
                int imageSize = ITEM_SLOT_SIZE - 10;
                g2.drawImage(itemImage, slotX + 5, slotY + 5, imageSize, imageSize, null);
            }
            
            // Draw price
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            String priceText = item.buyPrice + "g";
            FontMetrics fm = g2.getFontMetrics();
            int priceX = slotX + (ITEM_SLOT_SIZE - fm.stringWidth(priceText)) / 2;
            g2.drawString(priceText, priceX, slotY + ITEM_SLOT_SIZE - 5);
        }
    }
    
    private void drawDescriptionBox(Graphics2D g2, int storeX, int storeY) {
        int descX = storeX + 30;
        int descY = storeY + STORE_HEIGHT - DESCRIPTION_HEIGHT - 80;
        int descWidth = STORE_WIDTH - 60;
        
        // Background
        g2.setColor(new Color(101, 67, 33));
        g2.fillRoundRect(descX, descY, descWidth, DESCRIPTION_HEIGHT, 10, 10);
        
        g2.setColor(new Color(160, 120, 80));
        g2.fillRoundRect(descX + 3, descY + 3, descWidth - 6, DESCRIPTION_HEIGHT - 6, 7, 7);
        
        // Description text
        if (selectedItemIndex >= 0 && selectedItemIndex < storeItems.size()) {
            ItemDefinition selectedItem = storeItems.get(selectedItemIndex);
            
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(selectedItem.name, descX + 10, descY + 25);
            
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            
            // Wrap description text
            String[] words = selectedItem.description.split(" ");
            StringBuilder line = new StringBuilder();
            int lineY = descY + 45;
            int maxWidth = descWidth - 20;
            
            for (String word : words) {
                String testLine = line.length() == 0 ? word : line + " " + word;
                FontMetrics fm = g2.getFontMetrics();
                
                if (fm.stringWidth(testLine) > maxWidth && line.length() > 0) {
                    g2.drawString(line.toString(), descX + 10, lineY);
                    line = new StringBuilder(word);
                    lineY += 15;
                } else {
                    line = new StringBuilder(testLine);
                }
            }
            
            if (line.length() > 0) {
                g2.drawString(line.toString(), descX + 10, lineY);
            }
            
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.setColor(new Color(0, 100, 0));
            g2.drawString("Price: " + selectedItem.buyPrice + "g", descX + 10, descY + DESCRIPTION_HEIGHT - 25);
            
            if (selectedItem.energyValue > 0) {
                g2.setColor(new Color(0, 0, 150));
                g2.drawString("Energy: +" + selectedItem.energyValue, descX + 150, descY + DESCRIPTION_HEIGHT - 25);
            }
        }
    }
    
    private BufferedImage getItemImage(String itemName) {
        try {
            String className = "com.SpakborHills.objects.OBJ_" + itemName.replace(" ", "").replace("'", "");
            Class<?> itemClass = Class.forName(className);
            Entity itemEntity = (com.SpakborHills.entity.Entity) itemClass.getDeclaredConstructor(GamePanel.class).newInstance(gp);
            return itemEntity.down1;
        } 
        catch (Exception e) {
            return null;
        }
    }
}
