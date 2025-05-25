package com.SpakborHills.main;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.SpakborHills.entity.NPC_1;
import com.SpakborHills.objects.OBJ_House;
import com.SpakborHills.objects.OBJ_Keset;
import com.SpakborHills.objects.OBJ_Pond;
import com.SpakborHills.objects.OBJ_ShippingBin;
import com.SpakborHills.objects.OBJ_Tree;
import com.SpakborHills.objects.OBJ_Tree2;


public class AssetSetter {
    GamePanel gp;
    Random random;
    ArrayList<Rectangle> occupiedAreas;
    int houseCol, houseRow;
    private boolean houseAlreadyPlaced = false;
    private int savedHouseCol, savedHouseRow;
    int houseIndex = 0;

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
                int[][] possibleHouseLocations = {
                {10,0}, {21,0}, {2,24}, {16,24}
            };
            houseIndex = random.nextInt(possibleHouseLocations.length); // houseIndex harusnya field di class
            this.houseCol = possibleHouseLocations[houseIndex][0];
            this.houseRow = possibleHouseLocations[houseIndex][1];
            savedHouseCol = this.houseCol;
            savedHouseRow = this.houseRow;
            houseAlreadyPlaced = true; // Tandai bahwa rumah sudah ditempatkan
            System.out.println("Initial house placement at (" + this.houseCol + "," + this.houseRow + ")");
            } else {
                houseCol = savedHouseCol;
                houseRow = savedHouseRow;
                System.out.println("Restoring house at saved position (" + this.houseCol + "," + this.houseRow + ")");
            }
            placeHouse(this.houseCol, this.houseRow);
            int binCol = this.houseCol + 6 + 1;
            int binRow = this.houseRow + 2;
            placeShippingBin(binCol, binRow); 
            setTreesFromMap();
            setTrees2FromMap();
        }else if (gp.currentMap == 1){
            //map 1 objects
        }
        else if (gp.currentMap == 2){
            int objIndex = getNextAvailableObjectIndex();
            gp.obj[objIndex] = new OBJ_Keset(gp);
            gp.obj[objIndex].worldX = 11 * gp.tileSize;
            gp.obj[objIndex].worldY = 22 * gp.tileSize;//map 2 objects
        }
    }
    
    public void placeFixedPonds() {
        int col = 26, row = 25;
        placePond(col, row);
        occupiedAreas.add(new Rectangle(col, row, 3, 4));
    }
    
    private void placeHouse(int col, int row) {
        int objIndex = getNextAvailableObjectIndex();
        if(objIndex != -1) {
            gp.obj[objIndex] = new OBJ_House(gp);
            gp.obj[objIndex].worldX = col * gp.tileSize;
            gp.obj[objIndex].worldY = row * gp.tileSize;
            occupiedAreas.add(new Rectangle(col, row, 6, 6)); 
        }
    }
    
    private void placeShippingBin(int col, int row) {
        // Create shipping bin object
        int objIndex = getNextAvailableObjectIndex();
        if(objIndex != -1) {
            gp.obj[objIndex] = new OBJ_ShippingBin(gp);
            gp.obj[objIndex].worldX = col * gp.tileSize;
            gp.obj[objIndex].worldY = row * gp.tileSize;
            occupiedAreas.add(new Rectangle(col, row, 3, 2));
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
            {3,0},   
            {5,0},  
            {7,0},  
            {9,0}, 
            {1,3},   
            {3,3}, 
            {1,5},  
            {3,5}, 
            {1,7},  
            {3,7},  
            {11,0}, 
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

    public void setTrees2FromMap() { // different tree photo
        int[][] treePositions2;
        treePositions2 = switch (houseIndex+1) {
            case 1 -> new int[][]{
                {30,0}, {28,0}, {26,0}, {24,0},
                {30,3}, {30,5}, {30,7}, {30,9}
            };
            case 2 -> new int[][]{
                 {13,0}, {15,0}, {17,0},
                {7,3}, {5,3}, {9,3}, {21,0}
            };
            case 3 -> new int[][]{
                {30,0}, {28,0}, {26,0}, {24,0},
                {30,3}, {30,5}, {30,7}, {30,9}
            };
            default -> new int[][]{
                {30,3}, {30,5}, {30,7}, {30,9}
            };
        };
        
        for(int i = 0; i < treePositions2.length; i++) {
            int col = treePositions2[i][0];
            int row = treePositions2[i][1];
            
            int objIndex = getNextAvailableObjectIndex();
            if(objIndex != -1) {
                gp.obj[objIndex] = new OBJ_Tree2(gp);
                gp.obj[objIndex].worldX = col * gp.tileSize;
                gp.obj[objIndex].worldY = row * gp.tileSize;
                occupiedAreas.add(new Rectangle(col, row, 2, 3)); 
                
                System.out.println("Pohon2 " + (i+1) + " ditempatkan di (" + col + "," + row + ")");
            } else {
                System.out.println("Gagal menempatkan pohon2 #" + (i+1) + " - tidak ada slot");
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