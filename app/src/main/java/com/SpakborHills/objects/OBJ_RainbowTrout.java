package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*; // Pastikan ini mengimpor Season, Weather, Location
import com.SpakborHills.entity.Entity.FishableProperties; // Pastikan path ini benar jika nested

public class OBJ_RainbowTrout extends Entity implements Entity.FishableProperties { // Implementasikan interface

    // Field private untuk menyimpan data spesifik Rainbow Trout
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; // Field untuk menyimpan kategori ikan

    public OBJ_RainbowTrout(GamePanel gp) {
        super(gp);
        name = "Rainbow Trout"; // Spasi agar lebih mudah dibaca
        down1 = setup("objects/RainbowTrout", gp.tileSize, gp.tileSize); // Sesuaikan path gambar jika perlu
        description = "[" + name + "]\nIkan sungai dengan warna\npelangi yang khas."; // Deskripsi bisa disesuaikan
        isPickable = true;
        isEdible = true;    // Ikan bisa dimakan
        plusEnergy = 1;     // Menambah 1 energi saat dimakan

        // Inisialisasi data FishableProperties untuk Rainbow Trout
        this.category = "Regular";
        this.availableSeasons = EnumSet.of(Season.SUMMER);
        this.availableWeathers = EnumSet.of(Weather.SUNNY);
        this.availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE, Location.FOREST_RIVER);
        this.availableStartTimes = new ArrayList<>();
        this.availableEndTimes = new ArrayList<>();
        this.availableStartTimes.add(6);  // Dari jam 06:00
        this.availableEndTimes.add(18); // Sampai sebelum jam 18:00 (artinya 06:00 - 17:59)
                                        // Sesuai tabel awal: Rainbow Trout, Summer, 06.00-18.00, Sunny, Forest River, Mountain Lake
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