package com.SpakborHills.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import com.SpakborHills.data.ItemManager;
import com.SpakborHills.entity.Abigail;
import com.SpakborHills.entity.Caroline;
import com.SpakborHills.entity.Dasco;
import com.SpakborHills.entity.Emily;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.Mayor;
import com.SpakborHills.entity.NPC;
import com.SpakborHills.entity.Perry;
import com.SpakborHills.entity.Player;
import com.SpakborHills.environment.EnvironmentManager;
import com.SpakborHills.objects.OBJ_Angler;
import com.SpakborHills.objects.OBJ_BullHead;
import com.SpakborHills.objects.OBJ_Carp;
import com.SpakborHills.objects.OBJ_Catfish;
import com.SpakborHills.objects.OBJ_Chub;
import com.SpakborHills.objects.OBJ_Crimsonfish;
import com.SpakborHills.objects.OBJ_Flounder;
import com.SpakborHills.objects.OBJ_Glacierfish;
import com.SpakborHills.objects.OBJ_Halibut;
import com.SpakborHills.objects.OBJ_LargemouthBass;
import com.SpakborHills.objects.OBJ_Legend;
import com.SpakborHills.objects.OBJ_MidnightCarp;
import com.SpakborHills.objects.OBJ_Octopus;
import com.SpakborHills.objects.OBJ_Pufferfish;
import com.SpakborHills.objects.OBJ_RainbowTrout;
import com.SpakborHills.objects.OBJ_Salmon;
import com.SpakborHills.objects.OBJ_Sardine;
import com.SpakborHills.objects.OBJ_Sturgeon;
import com.SpakborHills.objects.OBJ_SuperCucumber;
import com.SpakborHills.tile.TileManager;
import com.SpakborHills.entity.PlayerDayObserver;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 32; // Ukuran maksimum global, TileManager akan menggunakan ukuran spesifik per peta
    public final int maxWorldRow = 32; // Ukuran maksimum global
    public final int maxMap = 20;
    public int currentMap = 0;

    // FULL SCREEN (tidak relevan langsung dengan clamping kamera, tapi ada di kode Anda)
    // int screenWidth2 = screenWidth; // Anda bisa hapus jika tidak digunakan
    // int screenHeight2 = screenHeight; // Anda bisa hapus jika tidak digunakan
    // Graphics2D g2; // Deklarasi g2 di sini tidak perlu, karena didapat dari parameter paintComponent

    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this); // 'this' sudah benar
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound(); // Pastikan kelas Sound ada dan berfungsi
    Sound se = new Sound();    // Pastikan kelas Sound ada dan berfungsi
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this, keyH);
    public EventHandler eHandler = new EventHandler(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    public Cooking cooking;
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH); // Create an instance of the Player class, passing the GamePanel and KeyHandler as parameters
    public Entity mapObjects[][] = new Entity[maxMap][100];
    public Entity NPC[] = new Entity[10];
    private static HashMap<String, NPC> NPCs = new HashMap<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    public ItemManager itemManager = new ItemManager();
    ArrayList<Entity> allFishPrototypes = new ArrayList<>();
    public Store store;



    //GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int shippingBinState = 5;
    public final int cookingState = 6;
    public final int fishingMinigameState = 7;
    public final int endGameTriggerState = 8; 
    public final int endGameState = 9;
    public final int shoppingState = 10;
    public final int statsState = 11;

    public String fishingInputBuffer = "";

    // Variabel untuk menyimpan posisi kamera yang sudah dibatasi (clamped)
    public int clampedCameraX;
    public int clampedCameraY;

    public GamePanel() {
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        this.setBackground(java.awt.Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public Entity[] getCurrentMapObjects() {
        return mapObjects[currentMap];
    }

    public Entity[] getMapObjects(int mapIndex){
        if (mapIndex >= 0 && mapIndex < maxMap) {
            return mapObjects[mapIndex];
        }
        return null;
    }

    public void setUpGame() {
        initializeAllMapObjects();
        aSetter.setObject(); // Pastikan ini mengisi mapObjects[currentMap] dengan benar
        aSetter.setNPC();    // Mengisi array NPC
        playMusic(0);
        eManager.setup();
        setupDayObservers();
        cooking = new Cooking(this);
        gameState = titleState;
        initializeFishPrototypes();
        store = new Store(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void sleep(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // Gunakan double untuk presisi
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // ke milidetik
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeAllMapObjects() {
        for (int map = 0; map < maxMap; map++) {
            for (int i = 0; i < mapObjects[map].length; i++) {
                mapObjects[map][i] = null;
            }
        }
    }

    public static HashMap<String, NPC> getNPCs() {
        return NPCs;
    }

     public static NPC getOrCreateNPC(String npcName, GamePanel gp) {
        if (!NPCs.containsKey(npcName)) {
            // Create NPC only if doesn't exist
            switch (npcName) {
                case "Abigail":
                    NPCs.put(npcName, new Abigail(gp));
                    break;
                case "Caroline":
                    NPCs.put(npcName, new Caroline(gp));
                    break;
                case "Dasco":
                    NPCs.put(npcName, new Dasco(gp));
                    break;
                case "Emily":
                    NPCs.put(npcName, new Emily(gp));
                    break;
                case "Mayor":
                    NPCs.put(npcName, new Mayor(gp));
                    break;
                case "Perry":
                    NPCs.put(npcName, new Perry(gp));
                    break;
            }
            System.out.println("CREATED NEW NPC: " + npcName);
        } else {
            System.out.println("REUSING EXISTING NPC: " + npcName + " (Heart points: " + 
                             NPCs.get(npcName).getHeartPoints() + ")");
        }
        return NPCs.get(npcName);
    }

    public void setupDayObservers() {
        // Create only player observer
        PlayerDayObserver playerObserver = new PlayerDayObserver(player);
        
        // Register observer with EnvironmentManager
        eManager.addDayObserver(playerObserver);
        
        System.out.println("Player day observer setup completed");
    }




    public void update() {
        if (ui.showingSleepConfirmDialog) {
            ui.processSleepConfirmationInput();
        }
        // // Update game logic here
        if(gameState == playState){
            if(!ui.showingSleepConfirmDialog) {
                player.update(); // Update the player entity 
            }
            //NPC
            for(int i = 0; i < NPC.length ; i++){
                if(NPC[i] != null){
                    NPC[i].update();
                }
            }
            Entity[] currentObjects = getCurrentMapObjects();
            for (Entity currentObject : currentObjects) {
                if (currentObject != null) {
                    // Jika objek punya update sendiri, panggil di sini.
                    // Beberapa objek mungkin statis dan tidak perlu update().
                    // currentObject.update(); // Aktifkan jika objek Anda punya logika update
                }
            }
            eManager.update();
            if(ui.showingSleepConfirmDialog ) {
                ui.processSleepConfirmationInput();                                             // Pastikan player.update() di atas tidak memproses gerakan jika dialog aktif
            }
            if(ui.showingNPCInfo) {
                ui.processNPCInfoInput();
            }
        }
        else if (gameState == pauseState){
            
        }
        else if(gameState == dialogueState){
            if(ui.showingSleepConfirmDialog){
                ui.processSleepConfirmationInput();
            }
            else if(ui.showingWatchTV){
                // if (keyH.enterPressed) {
                //     ui.showingWatchTV = false;
                //     keyH.enterPressed = false;
                //     eHandler.canTouchEvent = true; 
                //     gameState = playState;
                    
                // }
            }
            else {
                ui.processDialogueScrollingInput();
            }
        }
        else if (gameState == shippingBinState) {
            if (ui.showShippingBinConfirmDialog) {
                ui.processShippingBinConfirmInput();
            } 
            else {
                ui.processShippingBinInput();
            }
        }
        else if (gameState == cookingState) {
            ui.processCookingInput();
        }

        else if (gameState == fishingMinigameState) {
            ui.processFishingMinigameInput();
        }

        else if (gameState == endGameTriggerState) {
            ui.processEndGameTriggerInput();
        }

        else if (gameState == endGameState) {
            ui.processEndGameStatsInput();
        }

        else if (gameState == shoppingState) {
            store.update();
        }

        else if (gameState == statsState) {
            ui.processStatsInput();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if (keyH.checkDrawTime) { // Asumsi checkDrawTime adalah boolean di KeyHandler
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);
        } 
        else { // Untuk playState, pauseState, dialogueState, characterState
            // --- LOGIKA KAMERA YANG DIPERBAIKI ---
            // player.screenX dan player.screenY adalah posisi target pemain di layar (misal, tengah)
            int targetCameraX = player.worldX - player.screenX;
            int targetCameraY = player.worldY - player.screenY;

            // Dimensi peta saat ini dalam piksel
            // Mengakses field dari GamePanel ini secara langsung (tanpa "gp.")
            int currentMapActualCols = this.tileM.mapCols[this.currentMap];
            int currentMapActualRows = this.tileM.mapRows[this.currentMap];
            int currentMapWorldWidth = currentMapActualCols * this.tileSize;
            int currentMapWorldHeight = currentMapActualRows * this.tileSize;

            // Clamp Camera X
            if (currentMapWorldWidth > 0 && currentMapWorldWidth <= this.screenWidth) {
                // Jika peta lebih sempit atau sama dengan layar, kamera bisa di 0 (rata kiri)
                // atau di tengah: (currentMapWorldWidth - this.screenWidth) / 2; (akan <= 0)
                // Biarkan logika Math.max(0, ...) di bawah yang menangani ini agar konsisten.
                // Untuk kasus ini, clampedCameraX akan menjadi 0 karena
                // (currentMapWorldWidth - this.screenWidth) akan <= 0,
                // sehingga min(targetCameraX, nilai_negatif_atau_0) akan menghasilkan nilai_negatif_atau_0,
                // dan max(0, nilai_negatif_atau_0) akan menjadi 0.
                this.clampedCameraX = 0; // Simplifikasi untuk peta lebih kecil dari layar
            } else if (currentMapWorldWidth > 0) { // Peta lebih lebar dari layar
                this.clampedCameraX = Math.max(0, Math.min(targetCameraX, currentMapWorldWidth - this.screenWidth));
            } else { // Peta tidak punya lebar (seharusnya tidak terjadi untuk peta valid)
                this.clampedCameraX = 0;
            }

            // Clamp Camera Y
            if (currentMapWorldHeight > 0 && currentMapWorldHeight <= this.screenHeight) {
                this.clampedCameraY = 0; // Simplifikasi untuk peta lebih kecil dari layar
            } else if (currentMapWorldHeight > 0) { // Peta lebih tinggi dari layar
                this.clampedCameraY = Math.max(0, Math.min(targetCameraY, currentMapWorldHeight - this.screenHeight));
            } else { // Peta tidak punya tinggi
                this.clampedCameraY = 0;
            }
            // ------------------------------------

            // TILE
            tileM.draw(g2, this.clampedCameraX, this.clampedCameraY);

            // ADD ENTITY TO LIST (untuk sorting Z-order/Y-order)
            entityList.clear(); // Kosongkan dulu untuk frame ini
            entityList.add(player);

            for (Entity npcEntity : NPC) { // Ganti nama variabel agar tidak bentrok
                if (npcEntity != null) {
                    entityList.add(npcEntity);
                }
            }
            Entity[] currentMapObjArray = getCurrentMapObjects();
            for (Entity obj : currentMapObjArray) {
                if (obj != null) {
                    entityList.add(obj);
                }
            }

            // SORT entity berdasarkan worldY untuk urutan penggambaran yang benar
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // DRAW ENTITIES (Player, NPC, Objects)
            // Metode draw pada setiap entitas akan menggunakan gp.clampedCameraX/Y
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            // entityList.clear(); // Sudah di atas sebelum add

            // ENVIRONMENT
            eManager.draw(g2); // Jika eManager perlu camera, ia harus akses gp.clampedCameraX/Y

            // UI (digambar terakhir agar di atas segalanya)
            ui.draw(g2);

            // DEBUG Draw Time
            if (keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white); 
                g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
                g2.drawString("Draw Time: " + passed + " ns", 10, screenHeight - 10);
                // System.out.println("Draw Time: "+passed); // Hindari print di game loop jika memungkinkan
        // g2.dispose(); 
                g2.setColor(Color.white);
                g2.drawString("Draw Time: "+passed, 10, 400);
                System.out.println("Draw Time: "+passed);

        }

            //UI
            ui.draw(g2);

            if(gameState == shippingBinState) {
                ui.drawShippingBinInterface(g2);
            }
            if (gameState == cookingState) {
                if (ui.showingFuelSelectionDialog) {
                    ui.drawFuelSelectionDialog(g2);
                } else {
                    ui.drawCookingInterface(g2);
                }
            }

            if (gameState == endGameTriggerState) {
                    ui.drawEndGameTriggerInterface(g2);
                } 
            }

            if (gameState == endGameState) {
                ui.drawEndGameStatsInterface(g2);
            }

            if (gameState == statsState){
                ui.drawEndGameStatsInterface2(g2);
            }

            else if (gameState == shoppingState) {
                store.draw(g2);
            }

            g2.dispose();
        }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public List<Entity> getAllFishPrototypes(){
        if (this.allFishPrototypes == null){
            System.out.println("Warning: allFishPrototypes is null, initializing it now.");
            initializeFishPrototypes();
        }
        return this.allFishPrototypes;
    }

    public void initializeFishPrototypes(){
        allFishPrototypes = new ArrayList<>();

        // Common Fish
        allFishPrototypes.add(new OBJ_BullHead(this));
        allFishPrototypes.add(new OBJ_Carp(this));
        allFishPrototypes.add(new OBJ_Chub(this));

        // Regular Fish
        allFishPrototypes.add(new OBJ_LargemouthBass(this));
        allFishPrototypes.add(new OBJ_RainbowTrout(this));
        allFishPrototypes.add(new OBJ_Sturgeon(this));
        allFishPrototypes.add(new OBJ_MidnightCarp(this));
        allFishPrototypes.add(new OBJ_Flounder(this));
        allFishPrototypes.add(new OBJ_Halibut(this));
        allFishPrototypes.add(new OBJ_Octopus(this));
        allFishPrototypes.add(new OBJ_Pufferfish(this));
        allFishPrototypes.add(new OBJ_Sardine(this));
        allFishPrototypes.add(new OBJ_SuperCucumber(this));
        allFishPrototypes.add(new OBJ_Catfish(this));
        allFishPrototypes.add(new OBJ_Salmon(this));

        // Legendary Fish
        allFishPrototypes.add(new OBJ_Angler(this));
        allFishPrototypes.add(new OBJ_Crimsonfish(this));
        allFishPrototypes.add(new OBJ_Glacierfish(this));
        allFishPrototypes.add(new OBJ_Legend(this));

        System.out.println("Total fish prototypes initialized: " + allFishPrototypes.size());
    }
}