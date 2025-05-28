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

import javax.imageio.ImageIO;

import com.SpakborHills.entity.NPC;

public class UI {
    GamePanel gp;
    KeyHandler keyH;
    Graphics2D g2;
    
    Font menuFont;
    Font selectorFont; 
    Font inputFont;
    Font confirmationFont;

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
    public int inputState = 0; // 0 = name, 1 = farm name, 2 = gender, 3 = done
    public boolean isTyping = false;
    public boolean showingSleepConfirmDialog = false;
    public int sleepConfirmCommandNum = 0; // 0 = Yes, 1 = No
    public boolean forceBlackScreenActive = false;


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
                confirmationFont = baseFont.deriveFont(Font.PLAIN, 32F);                                                  // Kamu bisa sesuaikan 60f ini (misal 56f, 64f)

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
            return; // Skip drawing anything else if black screen is forced
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
            else{
                drawDialogueScreen();
            }
            if (isInNPCHouse()) {
                drawNPCInteractionInfo();
            }
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }
    }
    private boolean isInNPCHouse() {
        return gp.currentMap >= 5 && gp.currentMap <= 10;
    }
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
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
        int windowWidth = gp.tileSize * 9;    // Sesuaikan lebar
        int windowHeight = gp.tileSize * 4;   // Sesuaikan tinggi
        int x = gp.screenWidth / 2 - windowWidth / 2;
        int y = gp.screenHeight / 2 - windowHeight / 2;
        drawSubWindow(x, y, windowWidth, windowHeight);
        // 4. Gambar teks pertanyaan
        g2.setFont(confirmationFont); // Gunakan font yang sudah disiapkan
        g2.setColor(Color.white);
        String question = "Want to sleep for the night?";
        int qx = getXforCenteredTextInWindow(question, x, windowWidth, g2, confirmationFont);
        int qy = y + gp.tileSize + (gp.tileSize/2); // Posisi Y pertanyaan

        g2.drawString(question, qx, qy);

        // 5. Gambar opsi "Yes" dan "No"
        g2.setFont(confirmationFont.deriveFont(Font.BOLD, 36F)); // Sedikit lebih besar untuk opsi

        String yesText = "Yes";
        String noText = "No";

        // Posisi untuk "Yes" (kiri)
        int yesX = x + windowWidth / 4 - getHalfTextWidth(yesText, g2, g2.getFont()) ;
        int optionY = qy + gp.tileSize + (gp.tileSize/2) ;

        // Posisi untuk "No" (kanan)
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
            int startY = gp.tileSize * 3;
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

            // START GAME BUTTON
            g2.setColor(inputState == 3 ? Color.YELLOW : Color.WHITE);
            String startText = "START GAME";
            int startX = getXforCenteredText(startText);
            g2.drawString(startText, startX, startY + lineSpacing * 3);
            if(inputState == 3){
                g2.drawString(">", startX - gp.tileSize, startY + lineSpacing * 3);
            }

            // INSTRUCTIONS
            g2.setFont(g2.getFont().deriveFont(18F));
            g2.setColor(Color.LIGHT_GRAY);
            String instruction = "Use UP/DOWN to navigate, ENTER to select, Type to input text";
            int instrX = getXforCenteredText(instruction);
            g2.drawString(instruction, instrX, gp.screenHeight - gp.tileSize);
        }
    }
    public void addCharacterToInput(char c) {
        if (!isTyping) return;
        
        if (inputState == 0) { // Player name
            if (inputPlayerName.length() < 12) { // Max 12 characters
                inputPlayerName += c;
            }
        } else if (inputState == 1) { // Farm name
            if (inputFarmName.length() < 15) { // Max 15 characters
                inputFarmName += c;
            }
        }
    }
    public void removeLastCharacter() {
        if (!isTyping) return;
        
        if (inputState == 0 && inputPlayerName.length() > 0) {
            inputPlayerName = inputPlayerName.substring(0, inputPlayerName.length() - 1);
        } else if (inputState == 1 && inputFarmName.length() > 0) {
            inputFarmName = inputFarmName.substring(0, inputFarmName.length() - 1);
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
        }
        
        // Apply ke player
        gp.player.name = inputPlayerName;
        gp.player.farmName = inputFarmName;
        gp.player.gender = inputGender;

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
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;
        final int maxValueWidth = frameWidth - 180;

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
                    houseNPC.getRelationshipStatus() == NPC.RelationshipStatus.SINGLE) {
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

}
