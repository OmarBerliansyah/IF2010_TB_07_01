package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*; // Pastikan ini mengimpor Season, Weather, Location
import com.SpakborHills.entity.Entity.FishableProperties; // Pastikan path ini benar jika nested

public class OBJ_Halibut extends Entity implements Entity.FishableProperties { // Implementasikan interface

    // Field private untuk menyimpan data spesifik Halibut
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; // Field untuk menyimpan kategori ikan

    public OBJ_Halibut(GamePanel gp) {
        super(gp);
        name = "Halibut";
        down1 = setup("objects/Halibut", gp.tileSize, gp.tileSize); // Sesuaikan path gambar jika perlu
        description = "[" + name + "]\nIkan laut datar berukuran besar."; // Deskripsi bisa disesuaikan
        isPickable = true;
        isEdible = true;    // Ikan bisa dimakan
        plusEnergy = 1;     // Menambah 1 energi saat dimakan

        // Inisialisasi data FishableProperties untuk Halibut
        this.category = "Regular";
        this.availableSeasons = EnumSet.allOf(Season.class);         // "Any" Season
        this.availableWeathers = EnumSet.allOf(Weather.class);        // "Any" Weather
        this.availableLocations = EnumSet.of(Location.OCEAN);         // Hanya di Ocean
        
        this.availableStartTimes = new ArrayList<>();
        this.availableEndTimes = new ArrayList<>();
        // Waktu penangkapan Halibut memiliki dua slot:
        // Slot 1: 06:00 - 11:00 (artinya 06:00 - 10:59)
        this.availableStartTimes.add(6);  
        this.availableEndTimes.add(11); 
        // Slot 2: 19:00 - 02:00 (artinya 19:00 - 01:59 keesokan hari)
        this.availableStartTimes.add(19); 
        this.availableEndTimes.add(2);   // Ini adalah waktu yang melewati tengah malam
                                        // Logika di canCatchThisFish() sudah menghandle ini.
    }

    // Implementasi method dari FishableProperties
    @Override
    public String getFishName() {
        return this.name;
    }

    @Override
    public String getFishCategory() {
        return this.category;
    }

    @Override
    public EnumSet<Season> getAvailableSeasons() {
        return this.availableSeasons;
    }

    @Override
    public EnumSet<Weather> getAvailableWeathers() {
        return this.availableWeathers;
    }

    @Override
    public List<Integer> getAvailableStartTimes() {
        return this.availableStartTimes;
    }

    @Override
    public List<Integer> getAvailableEndTimes() {
        return this.availableEndTimes;
    }

    @Override
    public EnumSet<Location> getAvailableLocations() {
        return this.availableLocations;
    }
}