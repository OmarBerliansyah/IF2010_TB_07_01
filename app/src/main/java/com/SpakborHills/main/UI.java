package com.SpakborHills.main;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.SpakborHills.data.ItemDefinition;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.NPC;
import com.SpakborHills.entity.ShippingBinItem;

public class UI {
    GamePanel gp;
    KeyHandler keyH;
    Graphics2D g2;
    
    Font menuFont;
    Font selectorFont; 
    Font inputFont;
    Font confirmationFont;
    Font characterScreenFont;
    Font enviFont;

    public boolean messageOn = false;
    public ArrayList<String> message = new ArrayList<>(); 
    public ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public BufferedImage titleScreenImage;
    public BufferedImage spakborHillsLogo;
    public int slotCol = 0;
    public int slotRow = 0;
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 : the first screen, 1: the second screen
    // INPUT SYSTEM
    public String inputPlayerName = "";
    public String inputFarmName = "";
    public String inputGender = "Male"; // Default Male
    public String inputFavoriteItem = "";
    public int inputState = 0; // 0 = name, 1 = farm name, 2 = gender, 3 = favorite item, 4 = done
    public boolean isTyping = false;
    public boolean showingSleepConfirmDialog = false;
    public int sleepConfirmCommandNum = 0; // 0 = Yes, 1 = No
    public boolean forceBlackScreenActive = false;
    public boolean showingShippingBinInterface = false;
    public int shippingBinSelectedIndex = 0;
    public int shippingBinQuantity = 1;
    public boolean showShippingBinConfirmDialog = false;
    public boolean confirmAdd = false;
    public int shippingBinConfirmCommandNum = 0;
    public Inventory.InventoryItem pendingAddItem = null;
    public ShippingBinItem pendingTakeBackItem = null;
    public boolean showingCookingInterface = false;
    public int cookingSelectedIndex = 0;
    public boolean coalBatchMode = false;
    public String coalBatchFirstRecipe = null;
    public boolean showingFuelSelectionDialog = false;
    public int fuelSelectionIndex = 0;
    public String pendingRecipeId = null;
    public boolean showingWatchTV = false;

    public UI(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        InputStream is = null; // Untuk font

        try {
            // Path ke file font kamu di dalam folder resources
            String fontPath = "fonts/SDV.ttf"; 
            is = getClass().getClassLoader().getResourceAsStream(fontPath);
        
            if (is != null) {
                Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
                
                menuFont = baseFont.deriveFont(Font.BOLD, 58f); // Ukuran font menu
                selectorFont = baseFont.deriveFont(Font.BOLD, 60f); // Ukuran font untuk selector ">", buat lebih besar
                inputFont = baseFont.deriveFont(Font.PLAIN, 50f);
                confirmationFont = baseFont.deriveFont(Font.PLAIN, 32F);  
                characterScreenFont =  baseFont.deriveFont(Font.PLAIN, 28F);                                                 // Kamu bisa sesuaikan 60f ini (misal 56f, 64f)
                enviFont = baseFont.deriveFont(Font.PLAIN, 25f);
                System.out.println("Font kustom '" + fontPath + "' berhasil dimuat.");
            } else {
                System.err.println("ERROR UI: File font kustom '" + fontPath + "' TIDAK DITEMUKAN!");
                throw new IOException("File font tidak ditemukan: " + fontPath); 
            }
        } catch (IOException | FontFormatException e) { 
            e.printStackTrace();
            System.err.println("ERROR UI: Gagal memuat atau memproses font kustom 'fonts/SDV.ttf'. Menggunakan font default.");
            menuFont = new Font(Font.SANS_SERIF, Font.BOLD, 48); // Fallback untuk menuFont
            selectorFont = new Font(Font.SANS_SERIF, Font.BOLD, 60); // Fallback untuk selectorFont
            inputFont = new Font(Font.SANS_SERIF, Font.PLAIN, 42); // Fallback untuk inputFont
            confirmationFont = new Font(Font.SANS_SERIF, Font.PLAIN, 36);
            characterScreenFont =  new Font(Font.SANS_SERIF, Font.PLAIN, 16);
            enviFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        }

        // --- PEMUATAN GAMBAR DIPINDAHKAN KE KONSTRUKTOR ---
        try {
            // Memuat background title screen
            InputStream tsIs = getClass().getClassLoader().getResourceAsStream("title/Title.jpg");
            if (tsIs != null) {
                titleScreenImage = ImageIO.read(tsIs);
                tsIs.close(); // Selalu tutup InputStream setelah digunakan
            } else {
                System.err.println("ERROR UI: File background 'title/Title.jpg' tidak ditemukan!");
            }

            // Memuat logo
            InputStream logoIs = getClass().getClassLoader().getResourceAsStream("title/SpakborHillsLogo.png");
            if (logoIs != null) {
                spakborHillsLogo = ImageIO.read(logoIs);
                logoIs.close(); // Selalu tutup InputStream
            } else {
                System.err.println("ERROR UI: File logo 'title/SpakborHillsLogo.png' tidak ditemukan!");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR UI: Gagal memuat gambar untuk title screen.");
            titleScreenImage = null;
            spakborHillsLogo = null;
        }
    }

    public void showMessage(String text){
        message.clear(); 
        message.add(text);
        messageCounter.clear();
        messageCounter.add(0);
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        if(forceBlackScreenActive){
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            return; 
        }

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // TITLE SCREEN
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            drawMessage();
            if (isInNPCHouse()) {
                drawNPCInteractionInfo();
            }
            if(showingSleepConfirmDialog){
                drawSleepConfirmationDialog(g2);
            }
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            if(showingSleepConfirmDialog){
                drawSleepConfirmationDialog(g2);
            }
            else if(showingWatchTV){
                drawTVScreen();
            }
            if (isInNPCHouse()) {
                drawNPCInteractionInfo();
                if (currentDialogue != null && !currentDialogue.isEmpty()) {
                        drawDialogueScreen(); 
                }
            }
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }
        if (gp.gameState == gp.shippingBinState) {
            drawShippingBinInterface(g2);
        }
        if (gp.gameState == gp.cookingState) {
            if (showingFuelSelectionDialog) {
                drawFuelSelectionDialog(g2);
            } else {
                drawCookingInterface(g2);
            }
        }

        drawMessage();
        
        if (gp.gameState == gp.fishingMinigameState){
            drawFishingMinigameUI();
        }

    }
    private boolean isInNPCHouse() {
        return gp.currentMap >= 5 && gp.currentMap <= 10;
    }
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    public Font getenviFont(){
        return enviFont;
    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        int eatingMessageX = gp.tileSize; 
        int eatingMessageY = gp.screenHeight - gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = message.size() - 1; i >= 0; i--) {
            if (message.get(i) != null) {
                if (message.get(i).startsWith("Ate ") || message.get(i).equals("No item selected to eat!") || message.get(i).equals("Its not edible!")) {
                g2.setColor(new Color(0, 0, 0, 150)); 
                g2.fillRoundRect(eatingMessageX - 10, eatingMessageY - 20, gp.tileSize * 8, 30, 10, 10); 

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), eatingMessageX, eatingMessageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                eatingMessageY += 35; 

                if (messageCounter.get(i) > 120) {
                    message.remove(i);
                    messageCounter.remove(i);
                    i--;
                }

            } else {
                g2.setFont(g2.getFont()); 
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);

                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
            }
        }
    }

    public void setForceBlackScreen(boolean active) {
        this.forceBlackScreenActive = active;
    }

    public void showSleepConfirmationDialog(){
        gp.keyH.enterPressed = false; // Reset enter key state
        this.showingSleepConfirmDialog = true;
        this.sleepConfirmCommandNum = 0; // Reset command number to 0 (Yes)
    }

    public void closeSleepConfirmationDialog(){
        this.showingSleepConfirmDialog = false;
        gp.eHandler.canTouchEvent = true;
        if (gp.gameState == gp.dialogueState) { 
            boolean otherDialogueStillActive = false;
            if (!otherDialogueStillActive) {
                gp.gameState = gp.playState;
            }
        }
    }

    public void drawSleepConfirmationDialog(Graphics2D g2) {
        if (!showingSleepConfirmDialog) return;
        int windowWidth = gp.tileSize * 9;  
        int windowHeight = gp.tileSize * 4;  
        int x = gp.screenWidth / 2 - windowWidth / 2;
        int y = gp.screenHeight / 2 - windowHeight / 2;
        drawSubWindow(x, y, windowWidth, windowHeight);
        
        g2.setFont(confirmationFont); 
        g2.setColor(Color.white);
        String question = "Want to sleep for the night?";
        int qx = getXforCenteredTextInWindow(question, x, windowWidth, g2, confirmationFont);
        int qy = y + gp.tileSize + (gp.tileSize/2); 

        g2.drawString(question, qx, qy);
        g2.setFont(confirmationFont.deriveFont(Font.BOLD, 36F));

        String yesText = "Yes";
        String noText = "No";

        int yesX = x + windowWidth / 4 - getHalfTextWidth(yesText, g2, g2.getFont()) ;
        int optionY = qy + gp.tileSize + (gp.tileSize/2) ;

        int noX = x + (windowWidth / 4 * 3) - getHalfTextWidth(noText, g2, g2.getFont());


        // Gambar "Yes" dengan selector jika terpilih
        if (sleepConfirmCommandNum == 0) {
            g2.setColor(Color.YELLOW); // Warna highlight
            g2.drawString(">", yesX - gp.tileSize / 2, optionY);
        } else {
            g2.setColor(Color.white);
        }
        g2.drawString(yesText, yesX, optionY);

        // Gambar "No" dengan selector jika terpilih
        if (sleepConfirmCommandNum == 1) {
            g2.setColor(Color.YELLOW); // Warna highlight
            g2.drawString(">", noX - gp.tileSize / 2, optionY);
        } else {
            g2.setColor(Color.white);
        }
        g2.drawString(noText, noX, optionY);
    }

     // Helper untuk mendapatkan posisi X agar teks terpusat di dalam area window
    private int getXforCenteredTextInWindow(String text, int windowX, int windowWidth, Graphics2D g2, Font font) {
        FontMetrics fm = g2.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        return windowX + (windowWidth - textWidth) / 2;
    }

    // Helper untuk mendapatkan setengah lebar teks (untuk centering manual jika diperlukan)
    private int getHalfTextWidth(String text, Graphics2D g2, Font font) {
        FontMetrics fm = g2.getFontMetrics(font);
        return fm.stringWidth(text) / 2;
    }


    public void processSleepConfirmationInput() {
        if (!showingSleepConfirmDialog) return; // Hanya proses jika dialog aktif

        if (gp.keyH.leftPressed || (gp.keyH.upPressed && sleepConfirmCommandNum == 1) ) { // Pindah ke kiri (atau atas dari No ke Yes)
            sleepConfirmCommandNum = 0; // Pilih Yes
            gp.keyH.leftPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.enterPressed = false;
        } else if (gp.keyH.rightPressed || (gp.keyH.downPressed && sleepConfirmCommandNum == 0) ) { // Pindah ke kanan (atau bawah dari Yes ke No)
            sleepConfirmCommandNum = 1; // Pilih No
            gp.keyH.rightPressed = false;
            gp.keyH.downPressed = false;
            gp.keyH.enterPressed = false;
        }

        if (gp.keyH.enterPressed) {
            if (sleepConfirmCommandNum == 0) { // Jika "Yes" dipilih
                closeSleepConfirmationDialog(); // Tutup dialog dulu
                gp.player.sleeping();
                gp.eHandler.canTouchEvent = false; // Nonaktifkan input sementara
            } else { // Jika "No" dipilih
                closeSleepConfirmationDialog();
                gp.eHandler.canTouchEvent = false; // Nonaktifkan input sementara
                // gp.player.canMove = true; // Pastikan player bisa gerak lagi jika di-disable
                // gp.canTouchEvent = true; // Aktifkan kembali jika ini yang kamu gunakan
            }
            gp.keyH.enterPressed = false; // Konsumsi input enter
        }
    }

    public void showShippingBinInterface() {
        showingShippingBinInterface = true;
        shippingBinSelectedIndex = 0;
        shippingBinQuantity = 1;
    }

    public void closeShippingBinInterface() {
        showingShippingBinInterface = false;
        gp.gameState = gp.playState;
    }

    public void drawShippingBinInterface(Graphics2D g2) {
        if (!showingShippingBinInterface) return;

        int windowWidth = gp.tileSize * 12;
        int windowHeight = gp.tileSize * 10;
        int x = gp.screenWidth / 2 - windowWidth / 2;
        int y = gp.screenHeight / 2 - windowHeight / 2;
        
        drawSubWindow(x, y, windowWidth, windowHeight);
        
        // Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        g2.setColor(Color.WHITE);
        String title = "Shipping Bin";
        int titleX = getXforCenteredTextInWindow(title, x, windowWidth, g2, g2.getFont());
        g2.drawString(title, titleX, y + 40);
        
        // Instructions
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        g2.setColor(Color.LIGHT_GRAY);
        String instruction = "Select items to sell (ENTER to add/take back, ESC to close)";
        int instrX = getXforCenteredTextInWindow(instruction, x, windowWidth, g2, g2.getFont());
        g2.drawString(instruction, instrX, y + 65);
        
        g2.setColor(Color.CYAN);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
        g2.drawString("Inventory Items:", x + 20, y + 100);
        
        List<Inventory.InventoryItem> sellableItems = gp.player.getSellableItems();
        int inventoryStartY = y + 120;
        int itemHeight = 25;
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        
        if (sellableItems.isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.drawString("No sellable items", x + 40, inventoryStartY);
        } else {
            for (int i = 0; i < sellableItems.size(); i++) {
                Inventory.InventoryItem item = sellableItems.get(i);
                ItemDefinition itemDef = gp.itemManager.getDefinitionByName(item.item.name);
                int itemY = inventoryStartY + (i * itemHeight);
                
                if (shippingBinSelectedIndex == i) {
                    g2.setColor(new Color(255, 255, 0, 100));
                    g2.fillRect(x + 10, itemY - 15, windowWidth - 20, itemHeight - 2);
                    g2.setColor(Color.YELLOW);
                    g2.drawString(">", x + 15, itemY);
                } else {
                    g2.setColor(Color.WHITE);
                }
                
                String itemInfo = item.item.name + " x" + item.count;
                if (itemDef != null) {
                    itemInfo += " (" + itemDef.sellPrice + "g each)";
                }
                g2.drawString(itemInfo, x + 35, itemY);
            }
        }
        
        int binSectionY = inventoryStartY + (sellableItems.size() * itemHeight) + 40;
        g2.setColor(Color.ORANGE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
        g2.drawString("In Shipping Bin:", x + 20, binSectionY);
        
        List<ShippingBinItem> binItems = gp.player.getShippingBinItems();
        int binStartY = binSectionY + 20;
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        
        if (binItems.isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.drawString("Empty", x + 40, binStartY);
        } else {
            int totalValue = 0;
            for (int i = 0; i < binItems.size(); i++) {
                ShippingBinItem binItem = binItems.get(i);
                int itemY = binStartY + (i * itemHeight);
                int selectionIndex = sellableItems.size() + i;
                
                if (shippingBinSelectedIndex == selectionIndex) {
                    g2.setColor(new Color(255, 255, 0, 100));
                    g2.fillRect(x + 10, itemY - 15, windowWidth - 20, itemHeight - 2);
                    g2.setColor(Color.YELLOW);
                    g2.drawString(">", x + 15, itemY);
                } else {
                    g2.setColor(Color.WHITE);
                }
                
                int itemValue = binItem.quantity * binItem.sellPrice;
                String binInfo = binItem.itemName + " x" + binItem.quantity + " (" + itemValue + "g)";
                g2.drawString(binInfo, x + 35, itemY);
                totalValue += itemValue;
            }
            
            g2.setColor(Color.YELLOW);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
            int totalY = binStartY + (binItems.size() * itemHeight) + 20;
            g2.drawString("Total Value: " + totalValue + "g", x + 20, totalY);
        }

        if (showShippingBinConfirmDialog) {
            drawShippingBinConfirmDialog(g2);
        }
    }


    public void drawShippingBinConfirmDialog(Graphics2D g2) {
        int windowWidth = gp.tileSize * 7;
        int windowHeight = gp.tileSize * 3;
        int x = gp.screenWidth / 2 - windowWidth / 2;
        int y = gp.screenHeight / 2 - windowHeight / 2;
        drawSubWindow(x, y, windowWidth, windowHeight);

        g2.setFont(confirmationFont);
        g2.setColor(Color.white);

        String question;
        if (confirmAdd) {
            question = "Add " + (pendingAddItem != null ? pendingAddItem.item.name : "") + " to shipping bin?";
        } else {
            question = "Take back " + (pendingTakeBackItem != null ? pendingTakeBackItem.itemName : "") + " from bin?";
        }
        int qx = getXforCenteredTextInWindow(question, x, windowWidth, g2, confirmationFont);
        int qy = y + gp.tileSize + (gp.tileSize / 2);
        g2.drawString(question, qx, qy);
        
        g2.setFont(confirmationFont.deriveFont(Font.BOLD, 36F));
        String yesText = "Yes";
        String noText = "No";
        int yesX = x + windowWidth / 4 - getHalfTextWidth(yesText, g2, g2.getFont());
        int optionY = qy + gp.tileSize / 2;
        int noX = x + (windowWidth / 4 * 3) - getHalfTextWidth(noText, g2, g2.getFont());
        
        if (shippingBinConfirmCommandNum == 0) {
            g2.setColor(Color.YELLOW);
            g2.drawString(">", yesX - gp.tileSize / 2, optionY);
        } else {
            g2.setColor(Color.white);
        }
        g2.drawString(yesText, yesX, optionY);
        
        if (shippingBinConfirmCommandNum == 1) {
            g2.setColor(Color.YELLOW);
            g2.drawString(">", noX - gp.tileSize / 2, optionY);
        } else {
            g2.setColor(Color.white);
        }
        g2.drawString(noText, noX, optionY);
    }

    public void processShippingBinInput() {
        if (!showingShippingBinInterface) return;
        if (showShippingBinConfirmDialog) return;
        
        List<Inventory.InventoryItem> sellableItems = gp.player.getSellableItems();
        List<ShippingBinItem> binItems = gp.player.getShippingBinItems();
        int totalOptions = sellableItems.size() + binItems.size();
        
        if (totalOptions == 0) {
            if (gp.keyH.enterPressed || gp.keyH.escPressed) {
                closeShippingBinInterface();
                gp.keyH.enterPressed = false;
                gp.keyH.escPressed = false;
            }
            return;
        }
        
        if (gp.keyH.upPressed) {
            shippingBinSelectedIndex--;
            if (shippingBinSelectedIndex < 0) {
                shippingBinSelectedIndex = totalOptions - 1;
            }
            gp.keyH.upPressed = false;
        }
        if (gp.keyH.downPressed) {
            shippingBinSelectedIndex++;
            if (shippingBinSelectedIndex >= totalOptions) {
                shippingBinSelectedIndex = 0;
            }
            gp.keyH.downPressed = false;
        }
        
        if (gp.keyH.enterPressed) {
            if (shippingBinSelectedIndex < sellableItems.size()) {
                pendingAddItem = sellableItems.get(shippingBinSelectedIndex);
                confirmAdd = true;
            } else {
                int binIndex = shippingBinSelectedIndex - sellableItems.size();
                if (binIndex < binItems.size()) {
                    pendingTakeBackItem = binItems.get(binIndex);
                    confirmAdd = false;
                }
            }
            showShippingBinConfirmDialog = true;
            shippingBinConfirmCommandNum = 0;
            gp.keyH.enterPressed = false;
        }
        
        if (gp.keyH.escPressed) {
            closeShippingBinInterface();
            gp.keyH.escPressed = false;
        }
    }


    public void processShippingBinConfirmInput() {
        if (!showShippingBinConfirmDialog) return;
        
        if (gp.keyH.leftPressed) {
            shippingBinConfirmCommandNum = 0; 
            gp.keyH.leftPressed = false;
        }
        if (gp.keyH.rightPressed) {
            shippingBinConfirmCommandNum = 1;
            gp.keyH.rightPressed = false;
        }
        if (gp.keyH.upPressed || gp.keyH.downPressed) {
            shippingBinConfirmCommandNum = (shippingBinConfirmCommandNum == 0 ? 1 : 0);
            gp.keyH.upPressed = false;
            gp.keyH.downPressed = false;
        }
        
        if (gp.keyH.enterPressed) {
            if (shippingBinConfirmCommandNum == 0) { 
                if (confirmAdd && pendingAddItem != null) {
                    gp.player.addToShippingBin(pendingAddItem.item.name, 1);
                } 
                else if (!confirmAdd && pendingTakeBackItem != null) {
                    gp.player.takeBackFromShippingBin(pendingTakeBackItem.itemName, 1);
                }
            }
            showShippingBinConfirmDialog = false;
            pendingAddItem = null;
            pendingTakeBackItem = null;
            gp.keyH.enterPressed = false;
        }
        if (gp.keyH.escPressed) {
            showShippingBinConfirmDialog = false;
            pendingAddItem = null;
            pendingTakeBackItem = null;
            gp.keyH.escPressed = false;
        }
    }

    public void drawTVScreen(){
        // Gambar layar TV
        int x = gp.tileSize * 3;
        int y = gp.tileSize * 2;
        int width = gp.tileSize * 10;
        int height = gp.tileSize * 7;

        drawSubWindow(x, y, width, height);

        // Gambar teks di dalam layar TV
        g2.setColor(Color.white);
        g2.setFont(confirmationFont.deriveFont(Font.BOLD, 36F));
        FontMetrics fm = g2.getFontMetrics(); 
        int lineHeight = fm.getHeight()+8;

        int currentMinute = gp.eManager.getMinute();
        int currentHour = gp.eManager.getHour();
        String timeString = String.format("Waktu : %02d : %02d", currentHour, currentMinute);

        String weatherString = "Cuaca : " + gp.eManager.getWeatherName();
        String seasonString = "Musim : " + gp.eManager.getSeasonName();

        String programLine1 = "Selamat Datang di TV Spakbor!";
        String programLine2 = "Informasi Hari Ini";

        int numberOfLine = 5;
        int totalTextHeight = lineHeight * numberOfLine;

        int currentTextY = y + (height - totalTextHeight) / 2 + fm.getAscent();
        int textX_Program1 = x + (width - fm.stringWidth(programLine1)) / 2; // Tengah horizontal
        g2.drawString(programLine1, textX_Program1, currentTextY);
        currentTextY += lineHeight;

        int textX_Program2 = x + (width - fm.stringWidth(programLine2)) / 2;
        g2.drawString(programLine2, textX_Program2, currentTextY);
        currentTextY += lineHeight + 5;

        int textX_Time = x + (width - fm.stringWidth(timeString)) / 2;
        g2.drawString(timeString, textX_Time, currentTextY);
        currentTextY += lineHeight;

        int textX_Weather = x + (width - fm.stringWidth(weatherString)) / 2;
        g2.drawString(weatherString, textX_Weather, currentTextY);
        currentTextY += lineHeight;

        int textX_Season = x + (width - fm.stringWidth(seasonString)) / 2;
        g2.drawString(seasonString, textX_Season, currentTextY);

        String closeMessage = "[Tekan Enter untuk Kembali]";
        Font closeFont = confirmationFont.deriveFont(Font.PLAIN, 18F); // Font lebih kecil
        g2.setFont(closeFont);
        FontMetrics fmClose = g2.getFontMetrics();
        int closeX = x + (width - fmClose.stringWidth(closeMessage)) / 2;
        // Posisikan di bawah, sedikit di atas batas bawah subWindow
        int closeY = y + height - (gp.tileSize / 3) - fmClose.getDescent(); 
        g2.drawString(closeMessage, closeX, closeY);

        // gp.ui.showingWatchTV = false;
        // gp.eHandler.canTouchEvent = true;
    }

    public void drawTitleScreen(){
        if (titleScreenState == 0) {
            // 1. GAMBAR BACKGROUND
            if (titleScreenImage != null) {
                g2.drawImage(titleScreenImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
            } else {
                // Fallback jika background gagal dimuat
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
                g2.setColor(Color.WHITE);
                g2.drawString("Background Error", getXforCenteredText("Background Error"), gp.screenHeight / 2 - 20);
            }

            // 2. GAMBAR LOGO DI ATAS BACKGROUND
            if (spakborHillsLogo != null) {
                int originalLogoWidth = spakborHillsLogo.getWidth();
                int originalLogoHeight = spakborHillsLogo.getHeight();

                double scaleFactor = 0.90;

                int drawLogoWidth = (int)(originalLogoWidth * scaleFactor);
                int drawLogoHeight = (int)(originalLogoHeight * scaleFactor);

                int x_logo = (gp.screenWidth - drawLogoWidth) / 2; // Tengah horizontal
                int y_logo = gp.tileSize * 1; // Posisi Y dari atas (misal 1x tileSize, sesuaikan!)

                g2.drawImage(spakborHillsLogo, x_logo, y_logo, drawLogoWidth, drawLogoHeight, null);
            } else {
                // Fallback jika logo gagal dimuat
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F)); // Font sementara untuk pesan error
                String logoErrorText = "LOGO ERROR";
                int x_logo_error = getXforCenteredText(logoErrorText);
                int y_logo_error = gp.tileSize * 3; // Posisi teks error
                
                g2.setColor(Color.BLACK); // Shadow untuk teks error
                g2.drawString(logoErrorText, x_logo_error + 4, y_logo_error + 4);
                g2.setColor(Color.RED); // Teks error
                g2.drawString(logoErrorText, x_logo_error, y_logo_error);
            }

            // 3. GAMBAR MENU DI BAWAH LOGO
            g2.setFont(menuFont);
            String text;
            int x_menu;

            int y_logo_height_for_menu_calc = 0;
            int y_logo_top_position = gp.tileSize * 1; 
            if (spakborHillsLogo != null) {
                double scaleFactor = 0.75; // Pastikan ini sama dengan scaleFactor saat menggambar logo
                y_logo_height_for_menu_calc = (int)(spakborHillsLogo.getHeight() * scaleFactor);
            }
            int y_logo_bottom = (spakborHillsLogo != null) ? (y_logo_top_position + y_logo_height_for_menu_calc) : (gp.tileSize * 5);
            
            // --- MEMPERBESAR SPACING MENU ---
            // int y_menu_start = y_logo_bottom + gp.tileSize * 1; // Spacing sebelumnya
            int y_menu_start = y_logo_bottom + gp.tileSize * 2; // Diperbesar jadi 2 tileSize (atau sesuaikan: 1.5, 2.5, dll.)

            int current_menu_y = y_menu_start;

            // MENU ITEM: NEW GAME
            text = "NEW GAME";
            x_menu = getXforCenteredText(text);

            g2.setColor(Color.black); // Shadow
            g2.drawString(text, x_menu + 3, current_menu_y + 3);
            g2.setColor(Color.white); // Main text
            g2.drawString(text, x_menu, current_menu_y);
            if (commandNum == 0) {
                Font originalFont = g2.getFont();
                g2.setFont(selectorFont);
                FontMetrics fmSelector = g2.getFontMetrics();
                int selectorWidth = fmSelector.stringWidth(">");
                int selector_x_offset = selectorWidth + (gp.tileSize / 3); // Jarak selector dari teks menu
                g2.drawString(">", x_menu - selector_x_offset, current_menu_y);
                g2.setFont(originalFont);       // Kembalikan ke font menu
           
            }
            current_menu_y += gp.tileSize * 2.0;

            // MENU ITEM: QUIT
            text = "QUIT";
            x_menu = getXforCenteredText(text);

            g2.setColor(Color.black); // Shadow
            g2.drawString(text, x_menu + 3, current_menu_y + 3);
            g2.setColor(Color.white); // Main text
            g2.drawString(text, x_menu, current_menu_y);
            if (commandNum == 1) { // commandNum untuk QUIT sekarang 1
                Font originalFont = g2.getFont(); 
                g2.setFont(selectorFont);
                FontMetrics fmSelector = g2.getFontMetrics();
                int selectorWidth = fmSelector.stringWidth(">");
                int selector_x_offset = selectorWidth + (gp.tileSize / 3);
                g2.drawString(">", x_menu - selector_x_offset, current_menu_y);
                g2.setFont(originalFont);
            }

        }
        else if(titleScreenState == 1){
            //CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(inputFont);
           
            // BACKGROUND
            if (titleScreenImage != null) {
                g2.drawImage(titleScreenImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
            } else {
                // Fallback jika background gagal dimuat
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
                g2.setColor(Color.WHITE);
                g2.drawString("Background Error", getXforCenteredText("Background Error"), gp.screenHeight / 2 - 20);
            }
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            int centerX = gp.screenWidth / 2;
            int startY =  gp.tileSize * 2;
            int lineSpacing = gp.tileSize * 2;

            // PLAYER NAME INPUT
            String nameText = "Your Name: " + inputPlayerName;
            if (inputState == 0 && isTyping) {
                nameText += "_"; // Cursor blink effect
            }
            g2.setColor(inputState == 0 ? Color.YELLOW : Color.WHITE);
            int nameX = getXforCenteredText(nameText);
            g2.drawString(nameText, nameX, startY);

            // FARM NAME INPUT  
            String farmText = "Farm Name: " + inputFarmName;
            if (inputState == 1 && isTyping) {
                farmText += "_";
            }
            g2.setColor(inputState == 1 ? Color.YELLOW : Color.WHITE);
            int farmX = getXforCenteredText(farmText);
            g2.drawString(farmText, farmX, startY + lineSpacing);

            // GENDER SELECTION
            String genderText = "Gender: " + inputGender + " (Press G to change)";
            g2.setColor(inputState == 2 ? Color.YELLOW : Color.WHITE);
            int genderX = getXforCenteredText(genderText);
            g2.drawString(genderText, genderX, startY + lineSpacing * 2);
            
            // INPUT SELECTION
            String favoriteText = "Favorite Item: " + inputFavoriteItem;
            if (inputState == 3 && isTyping) {
                favoriteText += "_";
            }
            g2.setColor(inputState == 3 ? Color.YELLOW : Color.WHITE);
            int favoriteX = getXforCenteredText(favoriteText);
            g2.drawString(favoriteText, favoriteX, startY + lineSpacing * 3);

            // START GAME BUTTON
            g2.setColor(inputState == 4 ? Color.YELLOW : Color.WHITE);
            String startText = "START GAME";
            int startX = getXforCenteredText(startText);
            g2.drawString(startText, startX, startY + lineSpacing * 4);
            if(inputState == 4){
                g2.drawString(">", startX - gp.tileSize, startY + lineSpacing * 4);
            }

            // INSTRUCTIONS
            g2.setFont(g2.getFont().deriveFont(16F));
            g2.setColor(Color.LIGHT_GRAY);
            String instruction = "Use UP/DOWN to navigate, ENTER to select, Type to input text";
            int instrX = getXforCenteredText(instruction);
            g2.drawString(instruction, instrX, gp.screenHeight - gp.tileSize);
        }
    }
    public void addCharacterToInput(char c) {
        if (!isTyping) return;
        
        if (inputState == 0) { // Player name
            if (inputPlayerName.length() < 10) { // Max 10 characters
                inputPlayerName += c;
            }
        } else if (inputState == 1) { // Farm name
            if (inputFarmName.length() < 10) { // Max 10 characters
                inputFarmName += c;
            }
        }else if (inputState == 3) { // Favorite item
            if (inputFavoriteItem.length() < 15) { // Max 15 characters, ntar ke cut juga si sama truncate text jadi yang ditampilin cuma 10
                inputFavoriteItem += c;
            }
        }
    }
    public void removeLastCharacter() {
        if (!isTyping) return;
        
        if (inputState == 0 && inputPlayerName.length() > 0) {
            inputPlayerName = inputPlayerName.substring(0, inputPlayerName.length() - 1);
        } else if (inputState == 1 && inputFarmName.length() > 0) {
            inputFarmName = inputFarmName.substring(0, inputFarmName.length() - 1);
        } else if (inputState == 3 && inputFavoriteItem.length() > 0) { 
            inputFavoriteItem = inputFavoriteItem.substring(0, inputFavoriteItem.length() - 1);
        }
    }
    public void toggleGender() {
        if (inputGender.equals("Male")) {
            inputGender = "Female";
        } else {
            inputGender = "Male";  
        }
    }
    public void startGame() {
        // Set default values jika kosong
        if (inputPlayerName.isEmpty()) {
            inputPlayerName = "Player";
        }
        if (inputFarmName.isEmpty()) {
            inputFarmName = "My Farm";
        } if (inputFavoriteItem.isEmpty()) {
            inputFavoriteItem = "Parsnip"; 
        }
        
        // Apply ke player
        gp.player.name = inputPlayerName;
        gp.player.farmName = inputFarmName;
        gp.player.gender = inputGender;
        gp.player.favoriteItem = inputFavoriteItem;

        // Reset title screen state
        titleScreenState = 0;
        inputState = 0;
        isTyping = false;
        
        // Start game
        gp.player.updateLocation();
        gp.gameState = gp.playState;
    }

    public void drawDialogueScreen(){
        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*5;
        int width = gp.screenWidth - (gp.tileSize *4);
        int height = gp.tileSize *5;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25,25);
    }
    public void drawPauseScreen(){
        System.out.println("Drawing");
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length /2;
        return x;
    }
    public int getXforRightAlignedText(String text, int padding ){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = padding-length;
        return x;
    }
    private String truncateText(String text, int maxWidth) {
        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        String truncated = text;

        while (metrics.stringWidth(truncated) > maxWidth && truncated.length() > 0) {
            truncated = truncated.substring(0, truncated.length() - 1);
        }

        return truncated;
    }
    public void drawCharacterScreen(){
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        final int padding = frameX + frameWidth - 20;

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(characterScreenFont);

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;
        final int maxValueWidth = frameWidth - 180;
        g2.drawString("Player Info", textX+87, textY);
        textY += 50;
        //NAMES
        g2.drawString("Name: ", textX, textY);
        g2.drawString(truncateText(gp.player.name, maxValueWidth), getXforRightAlignedText(truncateText(gp.player.name, maxValueWidth), padding), textY);
        textY += lineHeight;
        g2.drawString("Farm Name: " ,textX, textY);
        g2.drawString(truncateText(gp.player.farmName, maxValueWidth), getXforRightAlignedText(truncateText(gp.player.farmName, maxValueWidth), padding), textY);
        textY += lineHeight;
        g2.drawString("Energy : ", textX, textY);
        g2.drawString(String.valueOf(gp.player.energy), getXforRightAlignedText(String.valueOf(gp.player.energy), padding), textY);
        textY += lineHeight;
        g2.drawString("Gold : ", textX, textY);
        g2.drawString(String.valueOf(gp.player.gold), getXforRightAlignedText(String.valueOf(gp.player.gold), padding), textY);
        textY += lineHeight;
        g2.drawString("Gender : ", textX, textY);
        g2.drawString(truncateText(gp.player.gender, maxValueWidth), getXforRightAlignedText(truncateText(gp.player.gender, maxValueWidth), padding), textY);
        textY += lineHeight;
        g2.drawString("Location : ", textX, textY);
        g2.drawString(truncateText(gp.player.getCurrentLocation(), maxValueWidth), getXforRightAlignedText(truncateText(gp.player.getCurrentLocation(), maxValueWidth), padding), textY);
        textY += lineHeight;
        g2.drawString("Partner : ", textX, textY);
        if (gp.player.partner != null) {
            g2.drawString(truncateText(gp.player.partner.name, maxValueWidth), getXforRightAlignedText(truncateText(gp.player.partner.name, maxValueWidth), padding), textY);
        } else {
            g2.drawString("None", getXforRightAlignedText("None", padding), textY);
        }
        textY += lineHeight;
        g2.drawString("Favorite : ", textX, textY);
        if (gp.player.favoriteItem == null || gp.player.favoriteItem.trim().isEmpty()) {
            gp.player.favoriteItem = "None";
        }
        g2.drawString(truncateText(gp.player.favoriteItem, maxValueWidth), getXforRightAlignedText(truncateText(gp.player.favoriteItem, maxValueWidth), padding), textY);
        //masih atur-atur ye . disini 
    }
    
    public void drawInventory(){
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // DRAW PLAYER'S ITEMS
        for(int i = 0; i < gp.player.inventory.getInventory().size(); i++){
            g2.drawImage(gp.player.inventory.getInventory().get(i).item.down1, slotX, slotY, null);

            // EQUIP CURSOR 
            if (gp.player.getEquippedItem() == gp.player.inventory.getInventory().get(i).item) {
                g2.setColor(new Color(240, 190, 90, 200));
                g2.fillRoundRect(slotX + 2, slotY + 2, gp.tileSize - 4, gp.tileSize - 4, 8, 8);
            }
            g2.drawImage(gp.player.inventory.getInventory().get(i).item.down1, slotX, slotY, null);

            // nambahin jumlah item di pojok kanan bawah
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            g2.setColor(Color.WHITE);
            String itemCount = String.valueOf(gp.player.inventory.getInventory().get(i).count);            
            int textX = slotX + gp.tileSize - g2.getFontMetrics().stringWidth(itemCount) - 2;
            int textY = slotY + gp.tileSize - 2;
            g2.drawString(itemCount, textX, textY);
            
            slotX += slotSize; 

            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart; 
                slotY += slotSize; 
            }
        }
        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight; 
        int dFrameWidth = frameWidth; 
        int dFrameHeight = gp.tileSize*3; 

        // DRAW DESCRIPTION TEXT 
        int textX = dFrameX + 20; 
        int textY = dFrameY + gp.tileSize; 
        g2.setFont(g2.getFont().deriveFont(28F)); 

        int itemIndex = getItemIndexOnSLot();

        if(itemIndex < gp.player.inventory.getInventory().size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); 
            for(String line : gp.player.inventory.getInventory().get(itemIndex).item.description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10,10);
    }

    public int getItemIndexOnSLot(){
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    public void drawNPCInteractionInfo() {
        // Always show NPC info when in NPC house (maps 5-10)
        if (!isInNPCHouse()) {
            return; // Exit if not in NPC house
        }
        
        // Find the NPC in current house
        NPC houseNPC = null;
        for (int i = 0; i < gp.NPC.length; i++) {
            if (gp.NPC[i] instanceof NPC) {
                houseNPC = (NPC) gp.NPC[i];
                break; // Found the NPC in this house
            }
        }
        
        if (houseNPC == null) {
            return; // No NPC found in this house
        }
        
        // Check if player is nearby for distance-based actions
        int npcIndex = gp.cChecker.checkEntity(gp.player, gp.NPC);
        boolean isNearby = (npcIndex != 999);
        
        g2.setFont(g2.getFont().deriveFont(16F));
        int x = 20; // Kiri atas
        int y = 50; // Sedikit dari atas
        
        // Background
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(x - 10, y - 20, 260, 220, 10, 10);
        
        // NPC Info Header
        g2.setColor(Color.YELLOW);
        g2.drawString("=== " + houseNPC.name + " ===", x, y);
        y += 25;
        
        // Current Stats (Always Updated)
        g2.setColor(Color.WHITE);
        g2.drawString("Heart Points: " + houseNPC.getHeartPoints() + "/150", x, y);
        y += 20;
        g2.drawString("Status: " + houseNPC.getRelationshipStatus(), x, y);
        y += 20;
        g2.drawString("Chat Count: " + houseNPC.getChattingFrequency(), x, y);
        y += 20;
        g2.drawString("Gift Count: " + houseNPC.getGiftingFrequency(), x, y);
        y += 25;
        
        // Available Actions Header
        g2.setColor(Color.CYAN);
        g2.drawString("ACTIONS:", x, y);
        y += 20;
        
        if (isNearby) {
            // Show available actions when nearby
            g2.setColor(Color.WHITE);
            
            // Chat action
            if (gp.player.energy >= 10) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.RED);
            }
            g2.drawString("ENTER: Chat (+10♥, -10⚡)", x, y);
            y += 15;
            
            // Gift action
            if (gp.player.equippedItem != null) {
                String giftEffect = getGiftEffect(houseNPC, gp.player.equippedItem.name);
                g2.setColor(Color.WHITE);
                g2.drawString("G: Gift " + gp.player.equippedItem.name, x, y);
                y += 12;
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawString("   " + giftEffect, x, y);
                y += 15;
            } else {
                g2.setColor(Color.GRAY);
                g2.drawString("G: No item equipped", x, y);
                y += 15;
            }
            
            // Propose action
            if (gp.player.hasProposalRing()) {
                if (houseNPC.getHeartPoints() >= 150 && 
                    houseNPC.getRelationshipStatus() == NPC.RelationshipStatus.FRIEND) {
                    g2.setColor(Color.GREEN);
                    g2.drawString("R: Propose (Ready!)", x, y);
                } else {
                    g2.setColor(Color.YELLOW);
                    g2.drawString("R: Propose (Need 150♥)", x, y);
                }
            } else {
                g2.setColor(Color.GRAY);
                g2.drawString("R: Propose (Need ring)", x, y);
            }
            y += 15;
            
            // Marry action
            if (houseNPC.getRelationshipStatus() == NPC.RelationshipStatus.FIANCE) {
                g2.setColor(Color.PINK);
                g2.drawString("M: Marry your fiance!", x, y);
            } else {
                g2.setColor(Color.GRAY);
                g2.drawString("M: Marry (Need fiance)", x, y);
            }
        } else {
            // Show "Get closer" message when not nearby
            g2.setColor(Color.GRAY);
            g2.drawString("Get closer to interact!", x, y);
        }
    }
    private String getGiftEffect(NPC npc, String itemName) {
        if (npc.getLovedItems().contains(itemName)) {
            return "(+25♥, -5⚡) LOVES IT!";
        } else if (npc.getLikedItems().contains(itemName)) {
            return "(+20♥, -5⚡) Likes it";
        } else if (npc.getHatedItems().contains(itemName)) {
            return "(-25♥, -5⚡) HATES IT!";
        } else {
            return "(+0♥, -5⚡) Neutral";
        }
    }
    public void showCookingInterface() {
        showingCookingInterface = true;
        cookingSelectedIndex = 0;
    }
    
    public void closeCookingInterface() {
        showingCookingInterface = false;
        showingFuelSelectionDialog = false;
        gp.gameState = gp.playState;
    }
    public void drawCookingInterface(Graphics2D g2) {
        if (!showingCookingInterface) return;
        
        int windowWidth = gp.tileSize * 14;
        int windowHeight = gp.tileSize * 10;
        int x = gp.screenWidth / 2 - windowWidth / 2;
        int y = gp.screenHeight / 2 - windowHeight / 2;
        
        drawSubWindow(x, y, windowWidth, windowHeight);
        
        // === TITLE ===
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        g2.setColor(Color.WHITE);
        String title = "KITCHEN";
        int titleX = getXforCenteredTextInWindow(title, x, windowWidth, g2, g2.getFont());
        g2.drawString(title, titleX, y + 40);
        
        // === FUEL STATUS ===
        if (!coalBatchMode) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
            g2.setColor(Color.CYAN);
            String fuelStatus = gp.cooking.getFuelStatus();
            g2.drawString("⛽ " + fuelStatus, x + 20, y + 70);
        }
        // === COOKING INSTRUCTIONS ===
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14F));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("💡 Coal gives +1 cooking deposit | Wood is single use | Deposits are free", x + 20, y + 90);
    
        // === RECIPE LIST ===
        List<Cooking.Recipe> recipes = gp.cooking.getUnlockedRecipes();
        
        if (recipes.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            g2.setColor(Color.GRAY);
            String noRecipes = "No recipes unlocked yet!";
            int noRecipesX = getXforCenteredTextInWindow(noRecipes, x, windowWidth, g2, g2.getFont());
            g2.drawString(noRecipes, noRecipesX, y + windowHeight / 2);
            return;
        }
        
        int listStartY = y + 120;
        int itemHeight = 45;
        int maxVisibleItems = 6;
        
        for (int i = 0; i < Math.min(recipes.size(), maxVisibleItems); i++) {
            Cooking.Recipe recipe = recipes.get(i);
            int itemY = listStartY + (i * itemHeight);
            
            // === SELECTION HIGHLIGHT ===
            if (i == cookingSelectedIndex) {
                g2.setColor(new Color(255, 215, 0, 120));
                g2.fillRect(x + 10, itemY - 20, windowWidth - 20, itemHeight - 2);
                g2.setColor(Color.YELLOW);
                g2.drawString("▶", x + 15, itemY);
            }
            
            // === RECIPE INFO ===
            boolean canCook = gp.cooking.canCook(recipe.id);
            g2.setColor(canCook ? Color.WHITE : Color.GRAY);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
            
            String recipeInfo = recipe.name + " (⚡+" + recipe.energyRestore + ")";
            g2.drawString(recipeInfo, x + 40, itemY);
            
            // === INGREDIENTS INFO ===
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14F));
            g2.setColor(canCook ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            
            StringBuilder ingredients = new StringBuilder("📦 Needs: ");
            for (int j = 0; j < recipe.ingredients.size(); j++) {
                Cooking.Ingredient ing = recipe.ingredients.get(j);
                ingredients.append(ing.itemName).append(" x").append(ing.quantity);
                if (j < recipe.ingredients.size() - 1) ingredients.append(", ");
            }
            g2.drawString(ingredients.toString(), x + 40, itemY + 18);
        }
        
        // === INSTRUCTIONS ===
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        g2.setColor(Color.LIGHT_GRAY);
        String instructions = "ENTER: Cook Recipe | ESC: Close | ↑↓: Navigate";
        g2.drawString(instructions, x + 20, y + windowHeight - 30);
    }
    
    public void drawFuelSelectionDialog(Graphics2D g2) {
        int windowWidth = gp.tileSize * 10;
        int windowHeight = gp.tileSize * 7;
        int x = gp.screenWidth / 2 - windowWidth / 2;
        int y = gp.screenHeight / 2 - windowHeight / 2;
        
        drawSubWindow(x, y, windowWidth, windowHeight);
        
        // === QUESTION ===
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        g2.setColor(Color.WHITE);
        String question = "Choose Fuel Type:";
        int qx = getXforCenteredTextInWindow(question, x, windowWidth, g2, g2.getFont());
        g2.drawString(question, qx, y + gp.tileSize);
        
        // === FUEL OPTIONS ===
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        
        String[] fuelOptions = {"🪵 Wood (Single Use)", "⚫ Coal (+1 Deposit)"};
        String[] fuelDescriptions = {"Cook once then consume", "Cook once + get 1 free cook"};
        
        int optionY = y + gp.tileSize + 40;
        int optionSpacing = 60;
        
        for (int i = 0; i < fuelOptions.length; i++) {
            int currentY = optionY + (i * optionSpacing);
            
            // Check if fuel is available
            boolean hasThisFuel = (i == 0) ? hasEnoughItems("Wood", 1) : hasEnoughItems("Coal", 1);
            
            if (!hasThisFuel) {
                g2.setColor(Color.DARK_GRAY);
            } else if (fuelSelectionIndex == i) {
                g2.setColor(Color.YELLOW);
                g2.drawString("▶", x + 20, currentY);
            } else {
                g2.setColor(Color.WHITE);
            }
            
            g2.drawString(fuelOptions[i], x + 50, currentY);
            
            // Description
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
            g2.setColor(hasThisFuel ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            g2.drawString(fuelDescriptions[i], x + 50, currentY + 20);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        }
        
        // === INSTRUCTIONS ===
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("ENTER: Select | ESC: Cancel", x + 20, y + windowHeight - 20);
    }

    
    public void processCookingInput() {
        if (showingFuelSelectionDialog) {
            processFuelSelectionInput();
            return;
        }
        
        if (!showingCookingInterface) return;
        
        List<Cooking.Recipe> recipes = gp.cooking.getUnlockedRecipes();
        
        if (recipes.isEmpty()) {
            if (gp.keyH.enterPressed || gp.keyH.escPressed) {
                closeCookingInterface();
                gp.keyH.enterPressed = false;
                gp.keyH.escPressed = false;
            }
            return;
        }
        
        // NAVIGATION
        if (gp.keyH.upPressed) {
            cookingSelectedIndex--;
            if (cookingSelectedIndex < 0) {
                cookingSelectedIndex = recipes.size() - 1;
            }
            gp.keyH.upPressed = false;
        }
        
        if (gp.keyH.downPressed) {
            cookingSelectedIndex++;
            if (cookingSelectedIndex >= recipes.size()) {
                cookingSelectedIndex = 0;
            }
            gp.keyH.downPressed = false;
        }
        
        // SELECTION
        if (gp.keyH.enterPressed) {
            if (cookingSelectedIndex < recipes.size()) {
                Cooking.Recipe selectedRecipe = recipes.get(cookingSelectedIndex);
                
                // Check fuel choice and handle cooking
                Cooking.FuelChoice fuelChoice = gp.cooking.getFuelChoice();
                
                if (fuelChoice == Cooking.FuelChoice.DEPOSIT_AVAILABLE) {
                    // Use deposit directly
                    boolean success = gp.cooking.cookRecipe(selectedRecipe.id, Cooking.FuelType.DEPOSIT);
                    if (success) closeCookingInterface();
                } else if (fuelChoice == Cooking.FuelChoice.BOTH_AVAILABLE) {
                    // Show fuel selection dialog
                    showFuelSelectionDialog(selectedRecipe.id);
                } else if (fuelChoice == Cooking.FuelChoice.WOOD_ONLY) {
                    boolean success = gp.cooking.cookRecipe(selectedRecipe.id, Cooking.FuelType.WOOD);
                    if (success) closeCookingInterface();
                } else if (fuelChoice == Cooking.FuelChoice.COAL_ONLY) {
                    boolean success = gp.cooking.cookRecipe(selectedRecipe.id, Cooking.FuelType.COAL);
                    if (success) closeCookingInterface();
                } else {
                    gp.ui.addMessage("No fuel available!");
                }
            }
            gp.keyH.enterPressed = false;
        }
        
        // ESCAPE
        if (gp.keyH.escPressed) {
            closeCookingInterface();
            gp.keyH.escPressed = false;
        }
    }
    
    public void showFuelSelectionDialog(String recipeId) {
        showingFuelSelectionDialog = true;
        pendingRecipeId = recipeId;
        fuelSelectionIndex = 0;
    }
    
    public void processFuelSelectionInput() {
        if (gp.keyH.upPressed) {
        fuelSelectionIndex--;
        if (fuelSelectionIndex < 0) fuelSelectionIndex = 1;
        gp.keyH.upPressed = false;
    }
    
        if (gp.keyH.downPressed) {
            fuelSelectionIndex++;
            if (fuelSelectionIndex > 1) fuelSelectionIndex = 0;
            gp.keyH.downPressed = false;
        }
    
        if (gp.keyH.enterPressed) {
            if (fuelSelectionIndex == 0) {
                // Wood selected
                if (hasEnoughItems("Wood", 1)) {
                    boolean success = gp.cooking.cookRecipe(pendingRecipeId, Cooking.FuelType.WOOD);
                    if (success) {
                        showingFuelSelectionDialog = false;
                        closeCookingInterface();
                    }
                } else {
                    gp.ui.addMessage("Not enough wood!");
                }
            } else {
                // Coal selected
                if (hasEnoughItems("Coal", 1)) {
                    boolean success = gp.cooking.cookRecipe(pendingRecipeId, Cooking.FuelType.COAL);
                    if (success) {
                        showingFuelSelectionDialog = false;
                        closeCookingInterface();
                    }
                } else {
                    gp.ui.addMessage("Not enough coal!");
                }
            }
            gp.keyH.enterPressed = false;
        }
        if (gp.keyH.escPressed) {
            showingFuelSelectionDialog = false;
            gp.keyH.escPressed = false;
        }
    }
    
    private boolean hasEnoughItems(String itemName, int quantity) {
        int count = 0;
        for (Inventory.InventoryItem item : gp.player.inventory.getInventory()) {
            if (item.item.name.equals(itemName)) {
                count += item.count;
                if (count >= quantity) return true;
            }
        }
        return false;
    }

    public void processFishingMinigameInput(){
        System.out.println("DEBUG: UI.processFishingMinigameInput() - AWAL. Input buffer: " + gp.fishingInputBuffer);

        // kondisi awal
        if (!gp.player.inFishingMinigame){
            return;
        }

        //  no 1
        if (gp.keyH.newCharTypedFishing){
            int maxInputLength = 3;

            if (gp.player.fishBeingFishedProto != null && gp.player.fishingRangeString != null){
                String range = gp.player.fishingRangeString;
                if (range.endsWith("10")){
                    maxInputLength = 2;
                }

                if (gp.fishingInputBuffer.length() < maxInputLength) {
                    gp.fishingInputBuffer += gp.keyH.lastTypeCharFishing;
                }
                gp.keyH.lastTypeCharFishing = '\0'; // Reset after processing
            }
        }

        gp.keyH.newCharTypedFishing = false;

        // no 2
        if (gp.keyH.backspacePressedFishing){
            if (gp.fishingInputBuffer.length() > 0) {
                gp.fishingInputBuffer = gp.fishingInputBuffer.substring(0, gp.fishingInputBuffer.length() - 1);
            }
            gp.keyH.backspacePressedFishing = false; // Reset after processing
        }

        //no 3
        if (gp.keyH.enterPressed){
            if (!gp.fishingInputBuffer.isEmpty()){
                try {
                    int guessNumber = Integer.parseInt(gp.fishingInputBuffer);
                    gp.player.processPlayerGuess(guessNumber);
                } catch (NumberFormatException nfe){
                    addMessage("Invalid input! Please enter a number.");
                }
                gp.fishingInputBuffer = ""; // Reset input buffer after processing
            } else  {

            }
            gp.keyH.enterPressed = false; // Reset after processing
        }

        // no 4
        if(gp.keyH.escPressed){
            if(gp.player.inFishingMinigame){
                gp.player.endFishingMinigame(false);
                addMessage("Fishing cancelled");
            }
            gp.keyH.escPressed = false;
        }
    }
         

    public void drawFishingMinigameUI(){
        if (!gp.player.inFishingMinigame && gp.gameState != gp.fishingMinigameState){
            return; 
        }

        int windowX = gp.tileSize *3;
        int windowY = gp.tileSize * 2;
        int windowWidth = gp.screenWidth - (gp.tileSize * 6);
        int windowHeight = gp.tileSize * 8;

        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        Font baseFontForMinigame = (inputFont != null) ? inputFont.deriveFont(36f) : new Font("Arial", Font.BOLD, 28); // Contoh ukuran
        if (confirmationFont == null) { // Fallback jika font utama belum ada
            confirmationFont = new Font("Arial", Font.PLAIN, 24);
        }
        g2.setFont(baseFontForMinigame);
        g2.setColor(Color.white);

        int textPaddingX = gp.tileSize/2 ;
        int textX = windowX + textPaddingX;
        int textY = windowY + gp.tileSize;
        int lineHeight = 50;

        if (gp.player.fishBeingFishedProto != null && gp.player.fishBeingFishedProto instanceof Entity.FishableProperties){
            Entity.FishableProperties fishProps = (Entity.FishableProperties) gp.player.fishBeingFishedProto;
            String fishInfo = "On the line: " + fishProps.getFishName() + " ( " + fishProps.getFishCategory() + " )";
            g2.drawString(fishInfo, textX, textY);
            textY += lineHeight;
        } else if (gp.player.inFishingMinigame){
            g2.drawString("Determining fish...", textX, textY);
            textY += lineHeight;
        }

        if (gp.player.inFishingMinigame){
            String guessPromptText = "Guess (" + gp.player.fishingRangeString + "):";
            FontMetrics fm = g2.getFontMetrics();
            int promptWidth = fm.stringWidth(guessPromptText);
            g2.drawString(guessPromptText, textX, textY);

            int inputFieldX = textX + promptWidth + 5;
            int inputFieldY = textY - fm.getAscent() - 5; // Adjust Y to align with text

            int maxDigits = 3;

            if (gp.player.fishingRangeString != null){
                if (gp.player.fishingRangeString.endsWith("10")){
                    maxDigits = 2; // Adjust for range ending with "10"
                }

                int inputFieldWidth = (fm.charWidth('0') * maxDigits) + (gp.tileSize / 2); // Width for 3 digits + padding
                int inputFieldHeight = fm.getHeight() + 10; // Height for input field

                g2.setColor(new Color(80, 80, 80, 200));
                g2.fillRect(inputFieldX, inputFieldY, inputFieldWidth, inputFieldHeight);

                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(2)); // Border lebih tipis
                g2.drawRect(inputFieldX, inputFieldY, inputFieldWidth, inputFieldHeight);
                g2.setStroke(new BasicStroke(1));

                g2.setColor(Color.white);
                String currentInputText = gp.fishingInputBuffer;

                if (System.currentTimeMillis() / 500 % 2 == 0 && gp.fishingInputBuffer.length() < maxDigits) {
                    currentInputText += "_";
                }

                g2.drawString(currentInputText, inputFieldX + 5, textY);

                textY += lineHeight;

                
                int triesLeft = gp.player.maxFishingTries - gp.player.currentFishingTry;
                g2.drawString("Tries left: " + triesLeft + "/" + gp.player.maxFishingTries, textX, textY);
                textY += lineHeight;

                if (gp.player.fishingGuessHint != null && !gp.player.fishingGuessHint.isEmpty()) {
                g2.setColor(Color.YELLOW); // Warna berbeda untuk petunjuk
                g2.drawString(gp.player.fishingGuessHint, textX, textY);
                g2.setColor(Color.white); // Kembalikan ke warna default
                textY += lineHeight;
            }

            textY += lineHeight ;

            Font instructionFont = (confirmationFont != null) ? confirmationFont.deriveFont(28F) : new Font("Arial", Font.PLAIN, 18);
            g2.setFont(instructionFont);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("Enter: Submit Guess", textX, textY);
            textY += 25;
            g2.drawString("Backspace: Delete Character", textX, textY);
            textY += 25;

            } else if (gp.gameState == gp.fishingMinigameState) {
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRoundRect(100, 100, 400, 200, 35, 35);

                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                g2.drawString("Fishing Minigame!", 150, 160);
                g2.setFont(g2.getFont().deriveFont(24F));
                g2.drawString("Press ENTER to reel in!", 150, 210);
            }
        }
    }

}
