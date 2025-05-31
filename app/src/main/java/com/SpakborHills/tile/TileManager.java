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
    public SoilTile[][][] soilMap;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[150];
        mapCols[0] = gp.maxWorldCol;
        mapRows[0] = gp.maxWorldRow;
        mapCols[1] = 24;
        mapRows[1] = 24;
        mapCols[2] = 24; 
        mapRows[2] = 24; 
        mapCols[3] = 17;
        mapRows[3] = 12;
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
        mapCols[11] = 16;
        mapRows[11] = 12;
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
        loadMap("maps/MountainLake.txt", 11);

        initSoilMap(gp.maxMap, mapCols[0], mapRows[0]); // Inisialisasi soilMap
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
        setup(14, "LautTerang", true, TileType.WATER);
        setup(15, "SandDefault", false, TileType.NONE);
        setup(16, "SandPlant", true, TileType.NONE);
        setup(17, "LautGelap", true, TileType.WATER);
        setup(18, "LautTerTerang", true, TileType.WATER);
        setup(19, "SandPijakan", false, TileType.NONE);
        setup(20, "SandStone", false, TileType.NONE);
        setup(21, "PinggirLautTengah", false, TileType.WATER);
        setup(22, "PinggirLautTengahBawah", false, TileType.WATER);
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
        setup(33, "Water", true, TileType.WATER);
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
        setup(46, "rumahatas", true, TileType.NONE);
        setup(47, "rumahbawah", true, TileType.NONE);
        setup(48, "rumahkanan", true, TileType.NONE);
        setup(49, "rumahkiri", true, TileType.NONE);
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
        setup(65, "LautTerangGelap", true, TileType.WATER);
        setup(66, "kakiDermaga", true, TileType.WATER);
        setup(67, "tileDermaga", false, TileType.NONE);
        setup(68, "DermagaTerang", true, TileType.WATER);
        setup(69, "DermagaGelap", true, TileType.WATER);
        setup(70, "DermagaTerangKanan", true, TileType.WATER);
        setup(71, "LautTerangTerang", true, TileType.WATER);
        setup(72, "LautPasir", true, TileType.WATER);
        setup(73, "RumputLautTengah", true, TileType.NONE);
        setup(74, "RumputLautBawah", true, TileType.NONE);
        setup(75, "RumputLautKanan", true, TileType.NONE);
        setup(76, "RumputLautKiri", true, TileType.NONE);
        setup(77, "RumputLautEdgeKiri", true, TileType.NONE);
        setup(78, "RumputLautEdgeKanan", true, TileType.NONE);
        setup(79, "PerahuKiri", true, TileType.NONE);
        setup(80, "PerahuKanan", true, TileType.NONE);
        setup(81, "SandRumput", false, TileType.NONE);
        setup(82, "Batuantengah", true, TileType.NONE);
        setup(83, "BatuLake", true, TileType.NONE);
        setup(84, "Batuanbawah", false, TileType.NONE);
        setup(85, "BatuanKanan", true, TileType.NONE);
        setup(86, "BatuanEdge", true, TileType.NONE);
        setup(87, "GrassLake", false, TileType.NONE);
        setup(88, "GrassLakePinggirBatu", true, TileType.NONE);
        setup(89, "GrassTanahPinggir", false, TileType.NONE);
        setup(90, "GrassTanahPinggirTengah", false, TileType.NONE);
        setup(91, "GrassBatuAtas", true, TileType.NONE);
        setup(92, "SoilGelap", false, TileType.NONE);
        setup(93, "LakeTile", true, TileType.WATER);
        setup(94, "GrassMepetBatu", false, TileType.NONE);
        setup(95, "BatuTanahBawah", false, TileType.NONE);
        setup(96, "GrassBatuAtas", true, TileType.NONE);
        setup(97, "BatuGrassAtasEdge", false, TileType.NONE);
        setup(98, "BatuMiring", true, TileType.NONE);
        setup(99, "RumputMiring", true, TileType.NONE);
        setup(100, "BatuTanahBawahMiring", false, TileType.NONE);
        setup(101, "GoaKiA", true, TileType.NONE);
        setup(102, "GoaKiB", false, TileType.NONE);
        setup(103, "GoaKaA", true, TileType.NONE);
        setup(104, "GoaKaB", false, TileType.NONE);
        setup(105, "RumputTanahBawah", false, TileType.NONE);
        setup(106, "RumputEdge", false, TileType.NONE);
        setup(107, "RumputUjung", false, TileType.NONE);
        setup(108, "BatuLake", false, TileType.WATER);
        setup(109, "JemKiri", false, TileType.NONE);
        setup(110, "JemTengah", false, TileType.NONE);
        setup(111, "JemKanan", false, TileType.NONE);
        setup(112, "LakeSandKiri", true, TileType.WATER);
        setup(113, "LakeSandKanan", false, TileType.WATER);
        setup(114, "LakeSandBawah", false, TileType.WATER);
        setup(115, "BatuLake2", false, TileType.NONE);
        setup(116, "RiverLandPinggir", false, TileType.NONE);
        setup(117, "RumputTanahKiri", false, TileType.NONE);
        setup(118, "RumputTanahEdgeBawah", false, TileType.NONE);
        setup(119, "RumputTanahEdgeAtas", false, TileType.NONE);
        setup(120, "RumputTanahBawahBGT", false, TileType.NONE);
        setup(121, "RumputTanahAtas", false, TileType.NONE);
        setup(122, "TanahGelap", true, TileType.NONE);
        setup(123, "JembatanAtas", false, TileType.NONE);
        setup(124, "JembatanTengah", false, TileType.NONE);
        setup(125, "JembatanBawah", true, TileType.NONE);
        setup(126, "BatuTanahGelapBawah", false, TileType.NONE);
        setup(127, "BatuJembatanTanah", false, TileType.NONE);
        setup(128, "UjungJembatanBawah", false, TileType.NONE);
        setup(129, "UjungJurangBawah", false, TileType.NONE);
        setup(130, "UjungJurangAtas", false, TileType.NONE);
        setup(131, "UjungJembatanAtas", false, TileType.NONE);
        setup(132, "UjungPantai", false, TileType.NONE);
        setup(133, "GrassCollision", true, TileType.NONE);
        setup(134, "rumahbawah", false, TileType.NONE);
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
            is.close();
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
            int currentMapCols = mapCols[map];
            int currentMapRows = mapRows[map];

            while(row < currentMapRows){
                String line = br.readLine();
                if (line == null) {
                break;
                }

                String numbers[] = line.split(" ");
                if (numbers.length < currentMapCols) {
                     System.err.println("ERROR TILEMANAGER: Map file " + mapFile + " pada baris " + row + " memiliki " + numbers.length + " kolom, diharapkan " + currentMapCols);
                }

                for (col = 0; col < currentMapCols; col++) {
                    if (col < numbers.length) {
                        try {
                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[map][col][row] = num;
                        } catch (NumberFormatException e) {
                            System.err.println("ERROR TILEMANAGER: Invalid number " + numbers[col] + " di map " + mapFile + " posisi (" + col + "," + row + ")");
                            mapTileNum[map][col][row] = 0; // Default ke tile 0 jika ada error
                        }
                    } else {
                        // Jika baris lebih pendek dari yang diharapkan, isi dengan tile default
                        mapTileNum[map][col][row] = 0; 
                    }
                }
                row++;
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initSoilMap(int mapCount, int cols, int rows) {
        System.out.println("=== INITIALIZING SOIL MAP ===");
        System.out.println("Map count: " + mapCount + ", Cols: " + cols + ", Rows: " + rows);
        
        if (soilMap == null) {
            soilMap = new SoilTile[mapCount][cols][rows];
            System.out.println("Created new soilMap array");
        }

        for (int m = 0; m < mapCount; m++) {
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    if (soilMap[m][c][r] == null) {
                        soilMap[m][c][r] = new SoilTile();
                    }
                }
            }
        }
        System.out.println("SoilMap initialization completed");
    }


        public void draw(Graphics2D g2, int cameraX, int cameraY) { // Tambahkan parameter cameraX, cameraY
        int worldCol = 0;
        int worldRow = 0;
        int currentMapIndex = gp.currentMap; // Ambil currentMap dari GamePanel

        // Gunakan dimensi spesifik dari peta saat ini
        int maxMapCol = mapCols[currentMapIndex];
        int maxMapRow = mapRows[currentMapIndex];

        while (worldCol < maxMapCol && worldRow < maxMapRow) {
            int tileNum = -1;
            // Pencegahan OutOfBounds jika mapTileNum tidak cukup besar untuk maxMapCol/maxMapRow
            // Meskipun seharusnya sudah dihandle oleh loadMap dan dimensi mapCols/mapRows
            if (worldCol < mapTileNum[currentMapIndex].length && worldRow < mapTileNum[currentMapIndex][worldCol].length) {
                 tileNum = mapTileNum[currentMapIndex][worldCol][worldRow];
            } else {
                // Seharusnya tidak terjadi jika mapCols/mapRows dan loadMap sudah benar
                System.err.println("Warning: Akses di luar batas mapTileNum untuk peta " + currentMapIndex + " di (" + worldCol + "," + worldRow + ")");
                worldCol++;
                if (worldCol >= maxMapCol) {
                    worldCol = 0;
                    worldRow++;
                }
                continue;
            }


            // Pastikan tileNum valid dan tile serta image-nya ada
            if (tileNum < 0 || tileNum >= tile.length || tile[tileNum] == null || tile[tileNum].image == null) {
                // System.err.println("Warning: Tile index " + tileNum + " tidak valid atau gambar null di ("+worldCol+","+worldRow+")");
                worldCol++;
                if (worldCol >= maxMapCol) {
                    worldCol = 0;
                    worldRow++;
                }
                continue; // Lanjut ke tile berikutnya
            }

            int tileWorldX = worldCol * gp.tileSize;
            int tileWorldY = worldRow * gp.tileSize;

            // Hitung posisi layar relatif terhadap KAMERA YANG SUDAH DI-CLAMP
            int screenX = tileWorldX - cameraX;
            int screenY = tileWorldY - cameraY;

            // Culling: Hanya gambar tile yang terlihat di layar
            if (screenX + gp.tileSize > 0 &&   // Tepi kanan tile > tepi kiri layar
                screenX < gp.screenWidth &&    // Tepi kiri tile < tepi kanan layar
                screenY + gp.tileSize > 0 &&   // Tepi bawah tile > tepi atas layar
                screenY < gp.screenHeight) {   // Tepi atas tile < tepi bawah layar
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;
            if (worldCol >= maxMapCol) { // Gunakan maxMapCol yang spesifik untuk peta ini
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

