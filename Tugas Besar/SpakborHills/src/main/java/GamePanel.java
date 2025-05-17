package main.java;

import java.awt.*;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import objects.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGGS

    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2; // Scale the tile size by 2 (basically zooming the character)

    public final int tileSize = originalTileSize * scale; // 64x64 tile
    public final int maxScreenCol = 16; // 16 tiles across the screen
    public final int maxScreenRow = 12; // 12 tiles down the screen
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels wide
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels tall

    // WORLD SEtTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager((this));
    public KeyHandler keyH = new KeyHandler(this); // Create an instance of the KeyHandler class to handle keyboard input
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread; // Thread for the game loop

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH); // Create an instance of the Player class, passing the GamePanel and KeyHandler as parameters
    public SuperObject obj[] = new SuperObject[10];
    public Entity NPC[] = new Entity[10];

    //GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public GamePanel(){
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight)); // Set the preferred size of the panel
        this.setBackground(java.awt.Color.black); // Set the background color to black
        this.setDoubleBuffered(true); // Enable double buffering for smoother graphics
        this.addKeyListener(keyH); // Add key listener for handling keyboard input
        this.setFocusable(true); // Make the panel focusable to receive key events
    }

    public void setUpGame(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the thread
    }

    @Override
    public void run() {
        // Game loop

        double drawInterval = 1000000000/FPS; //0.01666 second
        double nextDrawTime = System.nanoTime() + drawInterval; // Calculate the next draw time

        while(gameThread != null) { // While the game thread is running
            // long currentTime = System.nanoTime(); // Get the current time in nanoseconds
            // System.out.println("current Time :"+currentTime); // Print a message to the console

            update(); // Update the game logic
            repaint(); // Repaint the screen with the updated graphics

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // Calculate the remaining time until the next draw
                remainingTime = remainingTime / 1000000; // Convert remaining time to milliseconds

                if(remainingTime < 0) { // If the remaining time is negative, set it to 0
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime); // Sleep for the remaining time in milliseconds

                nextDrawTime += drawInterval; // Update the next draw time
            }
            catch (InterruptedException e) {
                e.printStackTrace(); // Print the stack trace if an exception occurs
            }
        }

        // 1 UPDATE : update information such as character position
        // 2 DRAW : draw the screen with the updated information
        // 3 REPAINT : repaint the screen with the new graphics
    }

    // public void run(){

    //     double drawInternal = 1000000000/FPS; // 0.01666 second
    //     double delta = 0; // Time difference between frames
    //     long lastTime = System.nanoTime(); // Get the current time in nanoseconds
    //     long currentTime; // Variable to store the current time
    //     long timer = 0;
    //     int drawCount = 0;

    //     while(gameThread != null){
    //         currentTime = System.nanoTime(); // Get the current time in nanoseconds
    //         delta += (currentTime - lastTime) / drawInternal; // Calculate the time difference
    //         timer += (currentTime - lastTime); // Update the timer
    //         lastTime = currentTime; // Update the last time

    //         if(delta >= 1) { // If the time difference is greater than or equal to 1
    //             update(); // Update the game logic
    //             repaint();
    //             delta--; // Decrease the delta by 1
    //             drawCount++;
    //         }

    //         if(timer >= 1000000000) {
    //             System.out.println("FPS:"+drawCount); // Print the FPS to the console
    //             drawCount = 0; // Reset the draw count
    //             timer = 0;
    //         }
    //     }
    // }

    public void update() {
        // // Update game logic here
        if(gameState == playState){
            player.update(); // Update the player entity
            //NPC
            for(int i = 0; i < NPC.length ; i++){
                if(NPC[i] != null){
                    NPC[i].update();
                }
            }
        }
        if (gameState == pauseState){
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to paint the component
        // Draw the game graphics here
        Graphics2D g2 = (Graphics2D) g; // Cast the Graphics object to Graphics2D for better control

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        // OTHERS
        else{
            //TILE
            tileM.draw(g2);
            //OBJECT
            for(int i = 0; i< obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }
            //NPC
            for(int i= 0; i < NPC.length; i++){
                if(NPC[i] != null){
                    NPC[i].draw(g2);
                }
            }
            //PLAYER
            player.draw(g2); // Draw the player entity

            //UI
            ui.draw(g2);
            g2.dispose();
        }
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}