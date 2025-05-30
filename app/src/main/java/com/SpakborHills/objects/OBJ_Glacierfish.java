package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*; // Pastikan ini mengimpor Season, Weather, Location
import com.SpakborHills.entity.Entity.FishableProperties; // Pastikan path ini benar jika nested

public class OBJ_Glacierfish extends Entity implements Entity.FishableProperties { // Implementasikan interface

    // Field private untuk menyimpan data spesifik Glacierfish
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; // Field untuk menyimpan kategori ikan

    public OBJ_Glacierfish(GamePanel gp) {
        super(gp);
        name = "Glacierfish";
        down1 = setup("objects/Glacierfish", gp.tileSize, gp.tileSize); // Sesuaikan path gambar jika perlu
        description = "[" + name + "]\nIkan legendaris yang hidup\ndi perairan dingin membeku."; // Deskripsi bisa disesuaikan
        isPickable = true;
        isEdible = true;    // Ikan bisa dimakan
        plusEnergy = 1;     // Menambah 1 energi saat dimakan

        // Inisialisasi data FishableProperties untuk Glacierfish
        this.category = "Legendary";
        this.availableSeasons = EnumSet.of(Season.WINTER);            // Hanya Winter
        this.availableWeathers = EnumSet.allOf(Weather.class);        // "Any" Weather
        this.availableLocations = EnumSet.of(Location.FOREST_RIVER);  // Hanya di Forest River
        
        this.availableStartTimes = new ArrayList<>();
        this.availableEndTimes = new ArrayList<>();
        this.availableStartTimes.add(8);  // Dari jam 08:00
        this.availableEndTimes.add(20); // Sampai sebelum jam 20:00 (artinya 08:00 - 19:59)
                                        // Sesuai tabel awal: Glacierfish, Winter, 08.00-20.00, Any, Forest River
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