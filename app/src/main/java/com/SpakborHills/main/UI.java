package com.SpakborHills.main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class UI {
    GamePanel gp;
    KeyHandler keyH;
    Graphics2D g2;
    
    Font menuFont;
    Font selectorFont; 

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
                                                                  // Kamu bisa sesuaikan 60f ini (misal 56f, 64f)

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
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // TITLE SCREEN
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            drawMessage();
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }
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
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Insert Your Name"; //BUTUH INPUT DARI USER BISA GA YA
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Insert Farm Name"; //BUTUH INPUT DARI USER BISA GA YA
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text,x,y);

            text = "OKE"; //BUTUH INPUT DARI USER BISA GA YA
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text,x,y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
    }
    public void drawDialogueScreen(){
        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*2;
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
    public void drawCharacterScreen(){
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        //NAMES
        //masih atur-atur ye
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
        for(int i = 0; i < gp.player.inventory.size(); i++){
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

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

        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); 
            for(String line : gp.player.inventory.get(itemIndex).description.split("\n")){
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
}
