package com.SpakborHills.entity;


public class ShippingBinItem {
    public String itemId;
    public String itemName;
    public int quantity;
    public int sellPrice;
    
    public ShippingBinItem(String itemId, String itemName, int quantity, int sellPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
    }
}


