package com.SpakborHills.main;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.SpakborHills.entity.NPC_1;
import com.SpakborHills.objects.OBJ_House;
import com.SpakborHills.objects.OBJ_Pond;
import com.SpakborHills.objects.OBJ_ShippingBin;
import com.SpakborHills.objects.OBJ_Tree;
import com.SpakborHills.objects.OBJ_Wood;


public class AssetSetter {
    GamePanel gp;
    Random random;
    ArrayList<Rectangle> occupiedAreas;
    int houseCol, houseRow;
    private boolean houseAlreadyPlaced = false;
    private int savedHouseCol, savedHouseRow;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        this.random = new Random();
        this.occupiedAreas = new ArrayList<>();
    }
    
    public void setObject() {
        occupiedAreas.clear();
        for(int i = 0; i < gp.obj.length; i++) {
        gp.obj[i] = null;
        }
        if (gp.currentMap == 0) {
            placeFixedPonds();
            if (!houseAlreadyPlaced) {
                placeHousesAndShippingBins(); 
                houseAlreadyPlaced = true;
                savedHouseCol = houseCol;
                savedHouseRow = houseRow;
            } else {
                houseCol = savedHouseCol;
                houseRow = savedHouseRow;
                placeHouseAtSavedLocation();
            }
            setTreesFromMap();
        }

        int i = 0; 
        gp.obj[i] = new OBJ_Wood(gp); 
        gp.obj[i].worldX = gp.tileSize*25; 
        gp.obj[i].worldY = gp.tileSize*23;
        i++; 
        gp.obj[i] = new OBJ_Wood(gp); 
        gp.obj[i].worldX = gp.tileSize*21; 
        gp.obj[i].worldY = gp.tileSize*19;
        i++; 
    }
    
    public void placeHousesAndShippingBins() {
        //kandidat lokasi
        int[][] possibleHouseLocations = {
            {5, 19},   // opsi 1: column 8, row 18
            {8,23},  // opsi 2: column 15, row 22  
            {21, 23}   // opsi 3: column 25, row 12
        };
        
        // randomisasi lokasi rumah
        int chosenIndex = random.nextInt(possibleHouseLocations.length);
        this.houseCol = possibleHouseLocations[chosenIndex][0];
        this.houseRow = possibleHouseLocations[chosenIndex][1];

        
        if(isValidHouseArea(houseCol, houseRow)) {
            int binCol = houseCol + 6 + 1; 
            int binRow = houseRow + 2; 
            if(isValidShippingBinArea(binCol, binRow)) {
                placeHouse(houseCol, houseRow);
                occupiedAreas.add(new Rectangle(houseCol, houseRow, 6, 6));
                placeShippingBin(binCol, binRow);
                occupiedAreas.add(new Rectangle(binCol, binRow, 3, 2));
                
                System.out.println("Placed house at position " + (chosenIndex + 1) + " (" + houseCol + "," + houseRow + 
                                 ") with shipping bin at (" + binCol + "," + binRow + ")");
            } else {
                System.out.println("Warning: Shipping bin cannot be placed for house at position " + (chosenIndex + 1));
            }
        } else {
            System.out.println("Warning: House cannot be placed at chosen position " + (chosenIndex + 1));
        }
    }
    public void placeHouseAtSavedLocation() {
        if(isValidHouseArea(savedHouseCol, savedHouseRow)) {
            placeHouse(savedHouseCol, savedHouseRow);
            occupiedAreas.add(new Rectangle(savedHouseCol, savedHouseRow, 6, 6));
        } else {
            System.out.println("Warning: House cannot be placed at saved position (" + savedHouseCol + "," + savedHouseRow + ")");
        }
    }
    
    public void placeFixedPonds() {
        int col = 20, row = 10;
        placePond(col, row);
        occupiedAreas.add(new Rectangle(col, row, 4, 3));
    }
    
    private boolean isValidHouseArea(int col, int row) {
        for(int r = row; r < row + 6; r++) {
            for(int c = col; c < col + 6; c++) {
                if(r >= gp.maxWorldRow || c >= gp.maxWorldCol) return false;
                if(gp.tileM.mapTileNum[gp.currentMap][c][r] != 1) return false; // Not grass
                if(isAreaOccupied(c, r, 1, 1)) return false; // Already occupied
            }
        }
        return true;
    }
    
    private boolean isValidShippingBinArea(int col, int row) {
        if(col + 3 > gp.maxWorldCol || row + 2 > gp.maxWorldRow) return false;
        for(int r = row; r < row + 2; r++) {
            for(int c = col; c < col + 3; c++) {
                if(gp.tileM.mapTileNum[gp.currentMap][c][r] != 1) return false; 
                if(isAreaOccupied(c, r, 1, 1)) return false; 
            }
        }
        return true;
    }
    private boolean isAreaOccupied(int col, int row, int width, int height) {
        Rectangle checkArea = new Rectangle(col, row, width, height);
        for(Rectangle occupied : occupiedAreas) {
            if(checkArea.intersects(occupied)) {
                return true;
            }
        }
        return false;
    }
    
    private void placeHouse(int col, int row) {
        int objIndex = getNextAvailableObjectIndex();
        if(objIndex != -1) {
            gp.obj[objIndex] = new OBJ_House(gp);
            gp.obj[objIndex].worldX = col * gp.tileSize;
            gp.obj[objIndex].worldY = row * gp.tileSize;
        }
    }
    
    private void placeShippingBin(int col, int row) {
        // Create shipping bin object
        int objIndex = getNextAvailableObjectIndex();
        if(objIndex != -1) {
            gp.obj[objIndex] = new OBJ_ShippingBin(gp);
            gp.obj[objIndex].worldX = col * gp.tileSize;
            gp.obj[objIndex].worldY = row * gp.tileSize;
        }
    }
    
    private void placePond(int col, int row) {
        // Create pond object
        int objIndex = getNextAvailableObjectIndex();
        if(objIndex != -1) {
            gp.obj[objIndex] = new OBJ_Pond(gp);
            gp.obj[objIndex].worldX = col * gp.tileSize;
            gp.obj[objIndex].worldY = row * gp.tileSize;
        }
    }
    
    private int getNextAvailableObjectIndex() {
        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public void setTreesFromMap() {
        int[][] treePositions = {
            {1,0},  
            {6,0},   
            {14,0},  
            {22,0},  
            {30,0}, 
            {0,3},   
            {29,3}, 
            {0,16},  
            {30,16}, 
            {1,18},  
            {20,28},  
        };
        for(int i = 0; i < treePositions.length; i++) {
            int col = treePositions[i][0];
            int row = treePositions[i][1];
            
            int objIndex = getNextAvailableObjectIndex();
            if(objIndex != -1) {
                gp.obj[objIndex] = new OBJ_Tree(gp);
                gp.obj[objIndex].worldX = col * gp.tileSize;
                gp.obj[objIndex].worldY = row * gp.tileSize;
                occupiedAreas.add(new Rectangle(col, row, 2, 3)); 
                
                System.out.println("Pohon " + (i+1) + " ditempatkan di (" + col + "," + row + ")");
            } else {
                System.out.println("Gagal menempatkan pohon #" + (i+1) + " - tidak ada slot");
            }
        }
        
    }
    
    public int getDoorCol() {
        return houseCol + 2;
    }
    
    public int getDoorRow() {
        return houseRow + 6; 
    }

    public void setNPC(){
        gp.NPC[0] = new NPC_1(gp);
        gp.NPC[0].worldX = gp.tileSize*21;
        gp.NPC[0].worldY = gp.tileSize*21;

        gp.NPC[1] = new NPC_1(gp);
        gp.NPC[1].worldX = gp.tileSize*11;
        gp.NPC[1].worldY = gp.tileSize*21;

        gp.NPC[2] = new NPC_1(gp);
        gp.NPC[2].worldX = gp.tileSize*9;
        gp.NPC[2].worldY = gp.tileSize*21;
    }

}