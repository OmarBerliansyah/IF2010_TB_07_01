package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*; // Pastikan ini mengimpor Season, Weather, Location
import com.SpakborHills.entity.Entity.FishableProperties; // Pastikan path ini benar jika nested

public class OBJ_Sardine extends Entity implements Entity.FishableProperties { // Implementasikan interface

    // Field private untuk menyimpan data spesifik Sardine
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; // Field untuk menyimpan kategori ikan

    public OBJ_Sardine(GamePanel gp) {
        super(gp);
        name = "Sardine";
        down1 = setup("objects/Sardine", gp.tileSize, gp.tileSize); // Sesuaikan path gambar jika perlu
        description = "[" + name + "]\nIkan kecil yang sering\nditemukan di laut."; // Deskripsi bisa disesuaikan
        isPickable = true;
        isEdible = true;    // Ikan bisa dimakan
        plusEnergy = 1;     // Menambah 1 energi saat dimakan

        // Inisialisasi data FishableProperties untuk Sardine
        this.category = "Regular"; // Sesuai GDD awal, Sardine adalah Regular
        this.availableSeasons = EnumSet.allOf(Season.class);         // "Any" Season
        this.availableWeathers = EnumSet.allOf(Weather.class);        // "Any" Weather
        this.availableLocations = EnumSet.of(Location.OCEAN);         // Hanya di Ocean
        
        this.availableStartTimes = new ArrayList<>();
        this.availableEndTimes = new ArrayList<>();
        this.availableStartTimes.add(6);  // Dari jam 06:00
        this.availableEndTimes.add(18); // Sampai sebelum jam 18:00 (artinya 06:00 - 17:59)
                                        // Sesuai tabel awal: Sardine, Any, 06.00-18.00, Any, Ocean
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