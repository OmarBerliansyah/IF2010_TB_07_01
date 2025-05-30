package com.SpakborHills.objects;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.environment.*;
import com.SpakborHills.entity.Entity.FishableProperties; // Pastikan path ini benar jika nested

public class OBJ_Carp extends Entity implements Entity.FishableProperties { // Implementasikan interface

    // Field private untuk menyimpan data spesifik Carp
    private EnumSet<Season> availableSeasons;
    private EnumSet<Weather> availableWeathers;
    private List<Integer> availableStartTimes;
    private List<Integer> availableEndTimes;
    private EnumSet<Location> availableLocations;
    private String category; // Field untuk menyimpan kategori ikan

    public OBJ_Carp(GamePanel gp) {
        super(gp);
        name = "Carp";
        down1 = setup("objects/Carp", gp.tileSize, gp.tileSize); // Sesuaikan path gambar jika perlu
        description = "[" + name + "]\nJenis Common Fish dapat\nditangkap di Mountain Lake\ndan Pond.";
        isPickable = true;
        isEdible = true;    // Ikan bisa dimakan
        plusEnergy = 1;     // Menambah 1 energi saat dimakan

        // Inisialisasi data FishableProperties untuk Carp
        this.category = "Common";
        this.availableSeasons = EnumSet.allOf(Season.class);         // "Any" Season
        this.availableWeathers = EnumSet.allOf(Weather.class);        // "Any" Weather
        this.availableLocations = EnumSet.of(Location.MOUNTAIN_LAKE, Location.POND); // Lokasi spesifik
        this.availableStartTimes = new ArrayList<>();               // "Any" Time
        this.availableEndTimes = new ArrayList<>();                 // "Any" Time
        // Jika Anda ingin eksplisit 0-24 untuk "Any Time"
        // this.availableStartTimes.add(0);
        // this.availableEndTimes.add(24);
    }

    // Implementasi method dari FishableProperties
    @Override
    public String getFishName() {
        return this.name; // Mengambil dari field 'name' di kelas Entity
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