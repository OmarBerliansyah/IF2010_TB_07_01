package com.SpakborHills.main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame(); // Create a new JFrame object
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Buat tombol close
        window.setResizable(false);
        window.setTitle("My 2D Game"); // Set the title of the window

        GamePanel gamePanel = new GamePanel(); // Create a new instance of the GamePanel class
        window.add(gamePanel);

        window.pack(); // Pack the components within the window

        window.setLocationRelativeTo(null); // Center the window on the screen
        window.setVisible(true); // Make the window visible

        gamePanel.setUpGame();
        gamePanel.startGameThread(); // Start the game thread
    }
}