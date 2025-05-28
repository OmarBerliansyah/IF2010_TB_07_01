package com.SpakborHills.main;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.entity.NPC_1;
import com.SpakborHills.objects.OBJ_AbigailHouse;
import com.SpakborHills.objects.OBJ_CarolineHouse;
import com.SpakborHills.objects.OBJ_DascoHouse;
import com.SpakborHills.objects.OBJ_EmilyHouse;
import com.SpakborHills.objects.OBJ_House;
import com.SpakborHills.objects.OBJ_Keset;
import com.SpakborHills.objects.OBJ_MayorHouse;
import com.SpakborHills.objects.OBJ_PerryHouse;
import com.SpakborHills.objects.OBJ_Pond;
import com.SpakborHills.objects.OBJ_ShippingBin;
import com.SpakborHills.objects.OBJ_Tree;
import com.SpakborHills.objects.OBJ_Tree2;
import com.SpakborHills.objects.OBJ_QueenBed;


public class AssetSetter {
    GamePanel gp;
    Random random;
    // Simpan occupied areas dan house positions per map
    HashMap<Integer, ArrayList<Rectangle>> mapOccupiedAreas;
    HashMap<Integer, int[]> mapHousePositions; // [col, row, houseIndex]
    HashMap<Integer, Boolean> mapInitialized;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        this.random = new Random();
        this.mapOccupiedAreas = new HashMap<>();
        this.mapHousePositions = new HashMap<>();
        this.mapInitialized = new HashMap<>();
        // Initialize hashmaps untuk semua map
        for(int i = 0; i < gp.maxMap; i++) {
            mapOccupiedAreas.put(i, new ArrayList<>());
            mapInitialized.put(i, false);
        }
    }
    
    public void setObject() {
        if(!mapInitialized.get(gp.currentMap)) {
            initializeMap(gp.currentMap);
            mapInitialized.put(gp.currentMap, true);
        }
        
        System.out.println("Objects loaded for map " + gp.currentMap);
    }

    private void initializeMap(int mapIndex) {
        System.out.println("=== INITIALIZING MAP " + mapIndex + " ===");
        // Clear objects untuk map ini
        Entity[] mapObjects = gp.mapObjects[mapIndex];
        for(int i = 0; i < mapObjects.length; i++) {
            mapObjects[i] = null;
        }
        mapOccupiedAreas.get(mapIndex).clear();
        
        // Setup objects berdasarkan map
        switch(mapIndex) {
            case 0: // Farm Map
                initializeFarmMap(mapIndex);
                break;
            case 1: // Ocean Map
                initializeOceanMap(mapIndex);
                break;
            case 2: // House Map
                initializeHouseMap(mapIndex);
                break;
            case 3: // Forest Map
                initializeForestMap(mapIndex);
                break;
            case 4: // NPC Map
                initializeNPCMap(mapIndex);
                break;
            case 5:
                initializeEmilyMap(mapIndex);
                break;
            case 6:
                initializePerryMap(mapIndex);
                break;
            case 7:
                initializeDascoMap(mapIndex);
                break;
            case 8: 
                initializeAbigailMap(mapIndex);
                break;
            case 9:
                initializeMayorMap(mapIndex);
                break;
            case 10:
                initializeCarolineMap(mapIndex);
                break;
            case 11: // Mountain Map
                initializeMountainMap(mapIndex);
                break;
        }
        
        System.out.println("=== MAP " + mapIndex + " INITIALIZED ===");
    }
    
    private void initializeFarmMap(int mapIndex) {
        // Place fixed ponds
        placePondForMap(mapIndex, 28, 25);
        
        // Generate dan simpan house position jika belum ada
        if(!mapHousePositions.containsKey(mapIndex)) {
            int[][] possibleHouseLocations = {
                {14,0}, {21,0}, {2,24}, {16,24}
            };
            int houseIndex = random.nextInt(possibleHouseLocations.length);
            int[] housePos = possibleHouseLocations[houseIndex];
            mapHousePositions.put(mapIndex, new int[]{housePos[0], housePos[1], houseIndex});
            System.out.println("Farm house permanently placed at (" + housePos[0] + "," + housePos[1] + ")");
        }
        
        int[] savedHouseData = mapHousePositions.get(mapIndex);
        int houseCol = savedHouseData[0];
        int houseRow = savedHouseData[1];
        int houseIndex = savedHouseData[2];
        
        placeHouseForMap(mapIndex, houseCol, houseRow);
        placeShippingBinForMap(mapIndex, houseCol + 7, houseRow + 2);
        setTreesForMap(mapIndex);
        setTrees2ForMap(mapIndex, houseIndex);
    }
     private void initializeOceanMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Ocean map initialized");
    }
    
     private void initializeHouseMap(int mapIndex) {
        // Place keset permanently
        int objIndex1 = getNextAvailableObjectIndexForMap(mapIndex);
        if(objIndex1 != -1) {
            gp.mapObjects[mapIndex][objIndex1] = new OBJ_Keset(gp);
            gp.mapObjects[mapIndex][objIndex1].worldX = 11 * gp.tileSize;
            gp.mapObjects[mapIndex][objIndex1].worldY = 22 * gp.tileSize;
        }
        // ini aja di copy ya
        int objIndex2 = getNextAvailableObjectIndexForMap(mapIndex);
        if(objIndex2 != -1) {
            gp.mapObjects[mapIndex][objIndex2] = new OBJ_QueenBed(gp);
            gp.mapObjects[mapIndex][objIndex2].worldX = 5 * gp.tileSize;
            gp.mapObjects[mapIndex][objIndex2].worldY = 5 * gp.tileSize;
        }
    }
    private void initializeForestMap(int mapIndex) {
        // Forest map objects 
        System.out.println("Forest map initialized");
    }

    private void initializeMountainMap(int mapIndex) {
        // Forest map objects 
        System.out.println("Mountain initialized");
    }
    private void initializeNPCMap(int mapIndex) {
        setTreesForNPCMapPermanent(mapIndex);
        setHousesForNPCMapPermanent(mapIndex);
    }
     private void initializeEmilyMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Emily map initialized");
    }
     private void initializePerryMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Perry map initialized");
    }
     private void initializeDascoMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Dasco map initialized");
    }
     private void initializeAbigailMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Abigail map initialized");
    }
     private void initializeMayorMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Mayor map initialized");
    }
     private void initializeCarolineMap(int mapIndex) {
        // Ocean map objects jika ada
        System.out.println("Caroline map initialized");
    }
// Method untuk menempatkan objek di map tertentu
    private void placeHouseForMap(int mapIndex, int col, int row) {
        int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
        if(objIndex != -1) {
            gp.mapObjects[mapIndex][objIndex] = new OBJ_House(gp);
            gp.mapObjects[mapIndex][objIndex].worldX = col * gp.tileSize;
            gp.mapObjects[mapIndex][objIndex].worldY = row * gp.tileSize;
            mapOccupiedAreas.get(mapIndex).add(new Rectangle(col, row, 6, 6));
        }
    }
     private void placeShippingBinForMap(int mapIndex, int col, int row) {
        int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
        if(objIndex != -1) {
            gp.mapObjects[mapIndex][objIndex] = new OBJ_ShippingBin(gp);
            gp.mapObjects[mapIndex][objIndex].worldX = col * gp.tileSize;
            gp.mapObjects[mapIndex][objIndex].worldY = row * gp.tileSize;
            mapOccupiedAreas.get(mapIndex).add(new Rectangle(col, row, 3, 2));
        }
    }
    private void placePondForMap(int mapIndex, int col, int row) {
        int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
        if(objIndex != -1) {
            gp.mapObjects[mapIndex][objIndex] = new OBJ_Pond(gp);
            gp.mapObjects[mapIndex][objIndex].worldX = col * gp.tileSize;
            gp.mapObjects[mapIndex][objIndex].worldY = row * gp.tileSize;
            mapOccupiedAreas.get(mapIndex).add(new Rectangle(col, row, 3, 4));
        }
    }
     private int getNextAvailableObjectIndexForMap(int mapIndex) {
        Entity[] mapObjects = gp.mapObjects[mapIndex];
        for(int i = 0; i < mapObjects.length; i++) {
            if(mapObjects[i] == null) {
                return i;
            }
        }
        return -1;
    }
    private void setTreesForMap(int mapIndex) {
        int[][] treePositions = {
            {1,0}, {3,0}, {5,0}, {7,0}, {9,0}, 
            {1,3}, {3,3}, {1,5}, {3,5}, {1,7}, {3,7}, {11,0}
        };
        
        for(int i = 0; i < treePositions.length; i++) {
            int col = treePositions[i][0];
            int row = treePositions[i][1];
            
            int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
            if(objIndex != -1) {
                gp.mapObjects[mapIndex][objIndex] = new OBJ_Tree(gp);
                gp.mapObjects[mapIndex][objIndex].worldX = col * gp.tileSize;
                gp.mapObjects[mapIndex][objIndex].worldY = row * gp.tileSize;
                mapOccupiedAreas.get(mapIndex).add(new Rectangle(col, row, 2, 3));
            }
        }
    }
    
     private void setTrees2ForMap(int mapIndex, int houseIndex) {
        int[][] treePositions2;
        treePositions2 = switch (houseIndex+1) {
            case 1 -> new int[][]{
                {22,0}, {24,0},
                {31,3}, {30,5}, {30,7}, {30,9}
            };
            case 2 -> new int[][]{
                 {13,0}, {15,0}, {17,0},
                {7,3}, {5,3}, {9,3}, {11,3}
            };
            case 3 -> new int[][]{
                {22,0}, {24,0},
                {30,3}, {30,5}, {30,7}, {30,9}
            };
            default -> new int[][]{
                {30,3}, {30,5}, {30,7}, {30,9}
            };
        };
        
        for(int i = 0; i < treePositions2.length; i++) {
            int col = treePositions2[i][0];
            int row = treePositions2[i][1];
            
            int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
            if(objIndex != -1) {
                gp.mapObjects[mapIndex][objIndex] = new OBJ_Tree2(gp);
                gp.mapObjects[mapIndex][objIndex].worldX = col * gp.tileSize;
                gp.mapObjects[mapIndex][objIndex].worldY = row * gp.tileSize;
                mapOccupiedAreas.get(mapIndex).add(new Rectangle(col, row, 2, 3));
            }
        }
    }

    private void setTreesForNPCMapPermanent(int mapIndex) {
        int[][] treePositions = {
            {28, 1}, {28, 4}, {28, 8}, {1, 8}, {3, 8}, {5, 8},
            {12, 8}, {14, 8}, {16, 8}, {28, 10}, {28, 13}, 
            {28, 22}, {28, 25}, {28, 28}
        };
        
        for(int i = 0; i < treePositions.length; i++) {
            int col = treePositions[i][0];
            int row = treePositions[i][1];
            
            int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
            if(objIndex != -1) {
                gp.mapObjects[mapIndex][objIndex] = new OBJ_Tree(gp);
                gp.mapObjects[mapIndex][objIndex].worldX = col * gp.tileSize;
                gp.mapObjects[mapIndex][objIndex].worldY = row * gp.tileSize;
            }
        }
    }
     private void setHousesForNPCMapPermanent(int mapIndex) {
        // Mayor House
    int objIndex = getNextAvailableObjectIndexForMap(mapIndex);
    if (objIndex != -1) {
        gp.mapObjects[mapIndex][objIndex] = new OBJ_MayorHouse(gp);
        gp.mapObjects[mapIndex][objIndex].worldX = 1 * gp.tileSize;
        gp.mapObjects[mapIndex][objIndex].worldY = 1 * gp.tileSize;
    }
    
    // Perry House
    objIndex = getNextAvailableObjectIndexForMap(mapIndex);
    if (objIndex != -1) {
        gp.mapObjects[mapIndex][objIndex] = new OBJ_PerryHouse(gp);
        gp.mapObjects[mapIndex][objIndex].worldX = 1 * gp.tileSize;
        gp.mapObjects[mapIndex][objIndex].worldY = 22 * gp.tileSize;
    }
    
    // Abigail House
    objIndex = getNextAvailableObjectIndexForMap(mapIndex);
    if (objIndex != -1) {
        gp.mapObjects[mapIndex][objIndex] = new OBJ_AbigailHouse(gp);
        gp.mapObjects[mapIndex][objIndex].worldX = 12 * gp.tileSize;
        gp.mapObjects[mapIndex][objIndex].worldY = 12 * gp.tileSize;
    }
    
    // Caroline House
    objIndex = getNextAvailableObjectIndexForMap(mapIndex);
    if (objIndex != -1) {
        gp.mapObjects[mapIndex][objIndex] = new OBJ_CarolineHouse(gp);
        gp.mapObjects[mapIndex][objIndex].worldX = 1 * gp.tileSize;
        gp.mapObjects[mapIndex][objIndex].worldY = 12 * gp.tileSize;
    }
    
    // Emily House
    objIndex = getNextAvailableObjectIndexForMap(mapIndex);
    if (objIndex != -1) {
        gp.mapObjects[mapIndex][objIndex] = new OBJ_EmilyHouse(gp);
        gp.mapObjects[mapIndex][objIndex].worldX = 12 * gp.tileSize;
        gp.mapObjects[mapIndex][objIndex].worldY = 1 * gp.tileSize;
    }
    
    // Dasco House
    objIndex = getNextAvailableObjectIndexForMap(mapIndex);
    if (objIndex != -1) {
        gp.mapObjects[mapIndex][objIndex] = new OBJ_DascoHouse(gp);
        gp.mapObjects[mapIndex][objIndex].worldX = 12 * gp.tileSize;
        gp.mapObjects[mapIndex][objIndex].worldY = 22 * gp.tileSize;
    }
    }

    public int getDoorCol() {
        if(mapHousePositions.containsKey(0)) {
            return mapHousePositions.get(0)[0] + 2;
        }
        return 16; // default misal error yak
    }
    
    public int getDoorRow() {
        if(mapHousePositions.containsKey(0)) {
            return mapHousePositions.get(0)[1] + 6;
        }
        return 24; // default
    }

     public void setNPC(){
        gp.NPC[0] = new NPC_1(gp);
        gp.NPC[0].worldX = gp.tileSize*1;
        gp.NPC[0].worldY = gp.tileSize*21;

        gp.NPC[1] = new NPC_1(gp);
        gp.NPC[1].worldX = gp.tileSize*10;
        gp.NPC[1].worldY = gp.tileSize*21;

        gp.NPC[2] = new NPC_1(gp);
        gp.NPC[2].worldX = gp.tileSize*8;
        gp.NPC[2].worldY = gp.tileSize*21;
    }

}