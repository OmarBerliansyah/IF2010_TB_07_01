package com.SpakborHills.data;

public class ItemDefinition {
    public String id;
    public String name;
    public String category;
    public int sellPrice;
    public int buyPrice;
    public String description;
    public boolean isSellable;
    public boolean isBuyable;
    public int energyValue;
    public int stackSize;
    
    public ItemDefinition(String id, String name, String category, int sellPrice, int buyPrice,  String description, boolean isSellable, boolean isBuyable, int energyValue, int stackSize) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.description = description;
        this.isSellable = isSellable;
        this.isBuyable = isBuyable;
        this.energyValue = energyValue;
        this.stackSize = stackSize;
    }
}
