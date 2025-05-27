package com.SpakborHills.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.SpakborHills.main.GamePanel;
import com.SpakborHills.main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    public int mapCols[] = new int[20];
    public int mapRows[] = new int[20];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[100];
        mapCols[0] = gp.maxWorldCol;
        mapRows[0] = gp.maxWorldRow;
        mapCols[1] = 24;
        mapRows[1] = 24;
        mapCols[2] = 24; 
        mapRows[2] = 24; 
        mapCols[3] = gp.maxWorldCol;
        mapRows[3] = gp.maxWorldRow;
        mapCols[4] = gp.maxWorldCol;
        mapRows[4] = gp.maxWorldRow;
        mapCols[5] = 24;
        mapRows[5] = 24;
        mapCols[6] = 24;
        mapRows[6] = 24;
        mapCols[7] = 24;
        mapRows[7] = 24;
        mapCols[8] = 24;
        mapRows[8] = 24;
        mapCols[9] = 24;
        mapRows[9] = 24;
        mapCols[10] = 24;
        mapRows[10] = 24;
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("maps/WorldMap.txt",0);
        loadMap("maps/OceanMap.txt", 1);
        loadMap("maps/HouseMap.txt", 2);
        loadMap("maps/ForestMap.txt", 3);
        loadMap("maps/npcMap.txt", 4);
        loadMap("maps/EmilyMap.txt", 5);// emily house
        loadMap("maps/PerryMap.txt", 6);// perry house
        loadMap("maps/DascoMap.txt", 7);// dasco house
        loadMap("maps/AbigailMap.txt", 8);// abigail house
        loadMap("maps/MayorMap.txt", 9);//mayor house
        loadMap("maps/CarolineMap.txt", 10);// caroline house
        
    }

    public void getTileImage() {
        setup(0, "Soil", false, TileType.TILLABLE);
        setup(1, "Grass", false, TileType.NONE);
        setup(2, "GrassFench", true, TileType.NONE); // Assuming "Fench" means Fence and is a collision object
        setup(3, "GrassFenchBA", true, TileType.NONE);
        setup(4, "GrassFenchBB", true, TileType.NONE);
        setup(5, "GrassFenchBT", true, TileType.NONE);
        setup(6, "SoilStone", false, TileType.NONE); // Or true if it's a collision object
        setup(7, "Bushes", true, TileType.NONE); // Or false depending on behavior
        setup(8, "HoedSoil", false, TileType.TILLED);
        setup(9, "Planted", false, TileType.PLANTED);
        setup(10, "PlantedWatered", false, TileType.PLANTED); // Visually different, but same type for interaction
        setup(11, "GrassFenchBBK", true, TileType.NONE);
        setup(12, "GrassFenchBAK", true, TileType.NONE);
        setup(13, "GrassFenchBTK", true, TileType.NONE);
        setup(14, "LautTerang", true, TileType.NONE);
        setup(15, "SandDefault", false, TileType.NONE);
        setup(16, "SandPlant", true, TileType.NONE);
        setup(17, "LautGelap", true, TileType.NONE);
        setup(18, "LautTerTerang", true, TileType.NONE);
        setup(19, "SandPijakan", false, TileType.NONE);
        setup(20, "SandStone", false, TileType.NONE);
        setup(21, "PinggirLautTengah", false, TileType.NONE);
        setup(22, "PinggirLautTengahBawah", false, TileType.NONE);
        setup(23, "SandTengah", false, TileType.NONE);
        setup(24, "SandBawahTengah", false, TileType.NONE);
        setup(25, "Starfish", false, TileType.NONE);
        setup(26, "PinggirPantaiTengah", false, TileType.NONE);
        setup(27, "PinggirPantaiTengahNext", false, TileType.NONE);
        setup(28, "HouseWall", true, TileType.NONE);
        setup(29, "HouseTiles", false, TileType.NONE);
        setup(30, "GrassFlower", false, TileType.NONE);
        setup(31, "GrassCorn", true, TileType.NONE);
        setup(32, "GrassFlower2", false, TileType.NONE);
        setup(33, "Water", true, TileType.NONE);
        setup(34, "GrassStone", false, TileType.NONE);
        setup(35, "GrassSoilTengah2", false, TileType.NONE);
        setup(36, "GrassSoilAtas", false, TileType.NONE);
        setup(37, "GrassSoilTengahKiri", false, TileType.NONE);
        setup(38, "GrassSoilKAEdge", false, TileType.NONE);
        setup(39, "GrassSoilKBEdge", false, TileType.NONE);
        setup(40, "GrassSoilBawah", false, TileType.NONE);
        setup(41, "GressSoilKaBEdge", false, TileType.NONE);
        setup(42, "GressSoilKaAEdge", false, TileType.NONE);
        setup(43, "SoilKeinjek", false, TileType.TILLABLE);
        setup(44, "rumahataskanan", false, TileType.NONE);
        setup(45, "rumahataskiri", false, TileType.NONE);
        setup(46, "rumahatas", false, TileType.NONE);
        setup(47, "rumahbawah", false, TileType.NONE);
        setup(48, "rumahkanan", false, TileType.NONE);
        setup(49, "rumahkiri", false, TileType.NONE);
        setup(50, "rumahbawahkiri", false, TileType.NONE);
        setup(51, "rumahbawahkanan", false, TileType.NONE);
        setup(52, "rumahtengah", false, TileType.NONE);
        setup(53, "SoilBATU", false, TileType.NONE);
        setup(54, "GressSoilKaBEdge2", false, TileType.NONE);
        setup(55, "GressSoilKaAEdge2", false, TileType.NONE);
        setup(56, "GressSoilKaAEdge3", false, TileType.NONE);
        setup(57, "GrassSoilTengahKiri2", false, TileType.NONE);
        setup(58, "GrassSoilMasukKanan", false, TileType.NONE);
        setup(59, "SoilStone2", false, TileType.NONE);
        setup(60, "SoilRumput", false, TileType.NONE);
        setup(61, "GrassSoilKAEdge2", false, TileType.NONE);
        setup(62, "GrassSoilTengahKiri4", false, TileType.NONE);
        setup(63, "GrassSoilKAEdge3", false, TileType.NONE);
        setup(64, "GrassSoilKAEdge4", false, TileType.NONE);
        setup(65, "LautTerangGelap", true, TileType.NONE);
        setup(66, "kakiDermaga", true, TileType.NONE);
        setup(67, "tileDermaga", false, TileType.NONE);
        setup(68, "DermagaTerang", true, TileType.NONE);
        setup(69, "DermagaGelap", true, TileType.NONE);
        setup(70, "DermagaTerangKanan", true, TileType.NONE);
        setup(71, "LautTerangTerang", true, TileType.NONE);
        setup(72, "LautPasir", false, TileType.NONE);
        setup(73, "RumputLautTengah", true, TileType.NONE);
        setup(74, "RumputLautBawah", true, TileType.NONE);
        setup(75, "RumputLautKanan", true, TileType.NONE);
        setup(76, "RumputLautKiri", true, TileType.NONE);
        setup(77, "RumputLautEdgeKiri", true, TileType.NONE);
        setup(78, "RumputLautEdgeKanan", true, TileType.NONE);

    }


    public void setup(int index, String imageName, boolean collision, TileType type){
        UtilityTool uTool = new UtilityTool();
        String imagePath = "tile/" + imageName + ".png"; // Path yang sudah diperbaiki

        try{

            InputStream is = getClass().getClassLoader().getResourceAsStream(imagePath);
            if (is == null) {
                System.out.println("ERROR: Tidak menemukan file: " + imagePath);
            }
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(is);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            tile[index].tileType = type;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFile, int map){
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream(mapFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            int maxCol = mapCols[map];
            int maxRow = mapRows[map];

            while(col < maxCol && row < maxRow){
                String line = br.readLine();
                if (line == null) {
                break;
                }
                while(col < maxCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col >= maxCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;
        int currentMap = gp.currentMap;
        int maxCol = mapCols[currentMap];
        int maxRow = mapRows[currentMap];

        while(worldCol < maxCol && worldRow < maxRow){

            int tileNum = mapTileNum[currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;
            if(worldCol == maxCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

