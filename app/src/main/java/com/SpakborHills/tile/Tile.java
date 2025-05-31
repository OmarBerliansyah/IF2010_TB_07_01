package com.SpakborHills.tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    public TileType tileType = TileType.NONE; // Default to NONE
}
