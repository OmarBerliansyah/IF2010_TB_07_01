package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*; // Pastikan ini mengimpor Season, Weather, Location
import com.SpakborHills.entity.Entity.FishableProperties; // Pastikan path ini benar jika nested

public class OBJ_Catfish extends Entity implements Entity.FishableProperties { // Implementasikan interface

    // Field private untuk menyimpan data spesifik Catfish
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; // Field untuk menyimpan kategori ikan

    public OBJ_Catfish(GamePanel gp) {
        super(gp);
        name = "Catfish";
        down1 = setup("objects/Catfish", gp.tileSize, gp.tileSize); // Sesuaikan path gambar jika perlu
        description = "[" + name + "]\nIkan air tawar yang\nditemukan saat hujan."; // Deskripsi bisa disesuaikan
        isPickable = true;
        isEdible = true;    // Ikan bisa dimakan
        plusEnergy = 1;     // Menambah 1 energi saat dimakan

        // Inisialisasi data FishableProperties untuk Catfish
        this.category = "Regular";
        this.availableSeasons = EnumSet.of(Season.SPRING, Season.SUMMER, Season.FALL);
        this.availableWeathers = EnumSet.of(Weather.RAINY); // Hanya saat hujan
        this.availableLocations = EnumSet.of(Location.FOREST_RIVER, Location.FARM);
        
        this.availableStartTimes = new ArrayList<>();
        this.availableEndTimes = new ArrayList<>();
        this.availableStartTimes.add(6);  // Dari jam 06:00
        this.availableEndTimes.add(22); // Sampai sebelum jam 22:00 (artinya 06:00 - 21:59)
                                        // Sesuai tabel awal: Catfish, Spring Summer Fall, 06.00-22.00, Rainy, Forest River, Pond
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