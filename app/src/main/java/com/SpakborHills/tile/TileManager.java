package com.SpakborHills.tile;

import com.SpakborHills.main.GamePanel;
import com.SpakborHills.main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[15];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("maps/WorldMap.txt");
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
    }

    public void setup(int index, String imageName, boolean collision, TileType type){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/"+imageName+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFile){
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream(mapFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

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


            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

