# Spakbor Hills

## IF2010_TB1_K4_07

**Anggota Kelompok:**

1.  Muhammad Omar Berliansyah (18223055)
2.  Nadia Apsarini Baizal (18221065)
3.  Catherine Alicia N (18223069)
4.  Aliya Harta Ary Utama (18223081)
5.  Noeriza Aqila Wibawa (18221095)

## Deskripsi Game

Spakbor Hills adalah game simulasi bertani berbasis Command Line Interface (CLI) yang dikembangkan untuk IF2010 Pemrograman Berorientasi Objek STI. Dalam game ini, pemain berperan sebagai seorang agen yang diberi misi khusus oleh O.W.C.A untuk membantu Dr. Asep Spakbor, seorang mantan ilmuwan jahat, menjadi petani sukses. Keberhasilan misi ini krusial untuk menyelamatkan dunia dari krisis ekonomi. Pemain akan mengelola sebuah pertanian, berinteraksi dengan warga desa, dan melakukan berbagai aktivitas untuk memastikan kelangsungan hidup dan kesuksesan Dr. Asep Spakbor sebagai petani.

## Alur Permainan

1.  **Setup Awal:**
    *   Inisiasi Environment (musim, cuaca, waktu).
    *   Inisiasi Village Map dan Farm Map dengan objek-objek dasar (House, Pond, Shipping Bin) serta lahan yang dapat ditanami.
    *   Inisiasi inventaris awal Player dengan item-item dasar (Seeds, Hoe, Watering Can, Pickaxe, Fishing Rod).
    *   Inisiasi Player dengan modal awal dan atribut lainnya.
    *   Game siap dimainkan setelah inisiasi objek utama selesai.

2.  **Gameplay Inti:**
    *   Pemain berinteraksi dengan lingkungan dan objek (lahan, Pond, Shipping Bin, House, NPC, dll.) melalui command line input.
    *   Melakukan berbagai aksi bertani seperti mengolah lahan (`Tilling`), menanam (`Planting`), menyiram (`Watering`), dan memanen (`Harvesting`).
    *   Mengelola energi Player dengan makan (`Eating`) atau tidur (`Sleeping`).
    *   Berinteraksi dengan NPC desa, membangun hubungan melalui `Chatting` dan `Gifting`, serta potensi `Proposing` dan `Marry`.
    *   Melakukan aktivitas tambahan seperti `Fishing` dan `Cooking`.
    *   Mengelola inventaris (`Open Inventory`).
    *   Menjual hasil panen atau item lain melalui `Shipping Bin` untuk mendapatkan `Gold`.
    *   Mengunjungi lokasi lain di luar Farm melalui `Visiting`.

3.  **Sistem Ekonomi:**
    *   Perolehan `Gold` melalui penjualan item di `Shipping Bin`.
    *   Pengelolaan item melalui `Inventory`.
    *   Pembelian item (misalnya seeds) dari `Store` (bagian dari World Map yang dapat di-`Visiting`).
    *   (Potensial Bonus: Implementasi `Free Market` dengan harga yang dinamis).
    *   (Potensial Bonus: Implementasi `Gambling` di Casino).

4.  **End Game:**
    *   Permainan tidak memiliki akhir yang tetap (infinite gameplay).
    *   Terdapat *milestone* tertentu (memiliki sejumlah `Gold` dan menikah).
    *   Ketika *milestone* tercapai, game akan menampilkan `End Game Statistics` yang memvalidasi dan merangkum progress pemain (total income/expenditure, average season income/expenditure, total days played, NPC relationships, crops harvested, fish caught).

## Struktur Proyek (Rencana Awal, tentatif)

Tugas_Besar_IF2010_K4_07
├── README.md
├── pom.xml 
└── src
├── main
│ ├── java
│ │ ├── controller
│ │ ├── model
│ │ └── view
│ └── resources 
└── test 
└── java
└── com
└── spakborhills
└── spakborhills


## Struktur Package dan Class (Sesuai Class Diagram)

*   **Package `model`**: Berisi class-class yang merepresentasikan data dan entitas dalam game.
    *   `Player`: Merepresentasikan pemain dengan atribut `name`, `gender`, `energy`, `FarmName`, `partner`, `gold`, `inventory`, `location`.
    *   `Farm`: Merepresentasikan dunia permainan dengan atribut `name`, `player`, `farmMap`, `time`, `day`, `season`, `weather`.
    *   `FarmMaps`: Merepresentasikan peta Farm dengan atribut `tile` (array 2D `Tile`), `width`, `height`.
    *   `WorldMaps`: Merepresentasikan peta dunia di luar Farm. (Diagram tidak memberikan detail atribut, mungkin hanya representasi lokasi).
    *   `Coordinate`: Merepresentasikan posisi (x, y).
    *   `Tile`: Merepresentasikan petak di peta dengan atribut `tileType` (`Tillable`, `Planted`, `Tilled`), `isWatered`, `plantedItem`.
    *   `DeployedObject`: Kelas abstrak untuk objek yang ditempatkan di map (House, Pond, Shipping Bin). Memiliki atribut `coordinate`.
        *   `House`: Merepresentasikan rumah pemain, mewarisi dari `DeployedObject`. Memiliki atribut `coordinate`.
        *   `Pond`: Merepresentasikan kolam, mewarisi dari `DeployedObject`. Memiliki atribut `coordinate`.
        *   `ShippingBin`: Merepresentasikan kotak pengiriman untuk menjual item, mewarisi dari `DeployedObject`. Memiliki atribut `maxSlots`, `items`, `totalValue`, `isProcessedToday`, `coordinate`.
    *   `NPC`: Merepresentasikan non-player character dengan atribut `name`, `currHeartPoints`, `maxHeartPoints`, `lovedItems`, `likedItems`, `hatedItems`, `relationshipStatus`.
    *   `Item`: Kelas abstrak untuk item dengan atribut `name`, `kategori`.
        *   `Seeds`: Mewarisi dari `Item`. Memiliki atribut `seasonSeeds`, `daysToHarvestSeeds`, `buyPriceSeeds`.
        *   `Crops`: Mewarisi dari `Item`. Memiliki atribut `buyPriceCrop`, `sellPriceCrop`.
        *   `Fish`: Mewarisi dari `Item`. Memiliki atribut `rarity`, `minSeason`, `maxSeason`, `availWeather`, `availTime`, `availLocation`. Memiliki enum `FishRarity` (`Legend`, `Common`, `Regular`).
        *   `Foods`: Mewarisi dari `Item`. Memiliki atribut `energyFood`, `buyPriceFood`, `sellPriceFood`.
        *   `Misc`: Mewarisi dari `Item`. Memiliki atribut `buyPrice`, `sellPrice`.
        *   `Equipment`: Mewarisi dari `Item`.
    *   `Inventory`: Mengelola item yang dimiliki pemain. Diagram menunjukkan implementasi menggunakan `Map<Item, Integer> items`.
    *   `Recipe`: Resep untuk memasak dengan atribut `itemID`, `name`, `ingredient` (List<Item>), `waysToUnlock`, `resultItem`.
    *   `State`: Merepresentasikan status game saat ini dengan atribut `currentTime`, `phase`, `season`, `dayCount`, `currentWeather`, `rainyDays`.
    *   `Furniture` (Abstract Class - Bonus): Kelas abstrak untuk furnitur di House dengan atribut `ID`, `Name`, `Description`, `Width`, `Height`.
        *   `Bed`: Mewarisi dari `Furniture`. Memiliki atribut `type`, `maxPeople`.
        *   `TV`: Mewarisi dari `Furniture`.
        *   `Stove`: Mewarisi dari `Furniture`. Memiliki atribut `fuel`.
    *   `Gold`: Merepresentasikan mata uang game dengan atribut `amount`.
    *   `Store`: Merepresentasikan toko untuk membeli item dengan atribut `storeItems` (Map<Item, Integer>).

*   **Package `service`**: Berisi logika bisnis dan operasional game. 

*   **Package `view`**: Berisi komponen untuk antarmuka pengguna (CLI atau GUI).
    *   `GameMenuView`: Tampilan menu utama game.
    *   `InventoryView`: Tampilan inventaris.
    *   `StateView`: Tampilan status game (Player info, friendship status).
    *   `ShippingBinView`: Tampilan Shipping Bin.
    *   `FarmMapsView`: Tampilan Farm Map.
    *   `WorldMapsView`: Tampilan World Map (Ocean, Forest, Mountain, Store, NPC Maps).
    *   `GoldView`: Tampilan jumlah Gold.
    *   `StoreView`: Tampilan Store.
    *   `BedView`: Tampilan interaksi dengan Bed.
    *   `TVView`: Tampilan interaksi dengan TV.
    *   `StoveView`: Tampilan interaksi dengan Stove.
    *   `NPCView`: Tampilan interaksi dengan NPC.
    *   `PlayerView`: Tampilan informasi Player.

*   **Package `controller`**: Menghubungkan view dan model, menangani input pengguna.
    *   `GameMenuController`: Mengontrol menu utama.
    *   `MainGameController`: Mengontrol alur utama permainan.
    *   `InventoryController`: Mengontrol interaksi dengan inventaris.
    *   `StateController`: Mengontrol status game dan alur waktu.
    *   `ShippingBinController`: Mengontrol interaksi dengan Shipping Bin.
    *   `FarmMapsController`: Mengontrol interaksi dengan Farm Map.
    *   `WorldMapsController`: Mengontrol interaksi dengan World Map.
    *   `PlayerController`: Mengontrol aksi pemain.
    *   `GoldController`: Mengontrol jumlah Gold.
    *   `StoreController`: Mengontrol interaksi dengan Store.
    *   `BedController`: Mengontrol interaksi dengan Bed.
    *   `TVController`: Mengontrol interaksi dengan TV.
    *   `StoveController`: Mengontrol interaksi dengan Stove.
    *   `NPCController`: Mengontrol interaksi dengan NPC.
    *   `FishingController`: Mengontrol aktivitas Fishing.
    *   `CookingController`: Mengontrol aktivitas Cooking.

## Fitur Utama (Sesuai Spesifikasi Game Bertani)

*   Sistem waktu, musim, dan cuaca yang berjalan paralel dengan waktu nyata.
*   Pengelolaan energi pemain dengan konsekuensi jika habis saat bekerja atau belum tidur hingga pukul 02.00.
*   Berbagai aksi pemain terkait pertanian (mengolah lahan, menanam, menyiram, memanen).
*   Sistem inventaris untuk menyimpan berbagai jenis item (Seeds, Fish, Crops, Food, Equipment, Misc).
*   Sistem ekonomi dengan `Gold` sebagai mata uang dan `Shipping Bin` untuk penjualan.
*   Interaksi dengan NPC yang memiliki atribut `heartPoints`, `lovedItems`, `likedItems`, dan `hatedItems`, serta status hubungan (`single`, `fiance`, `spouse`).
*   Aktivitas `Fishing` dengan berbagai jenis ikan dan mekanik menebak angka.
*   Aktivitas `Cooking` menggunakan resep dan bahan bakar.
*   *Milestone* End Game dengan tampilan statistik komprehensif.
*   Menu Game untuk navigasi dan informasi pemain.
*   (Implementasi bonus fitur seperti Furnitures, GUI, Keyboard and Mouse Input, Free Market, Gambling, Save and Load, NPC Easter Egg akan memberikan poin tambahan).

## Teknologi yang Digunakan (Rancangan awal)

*   **Java**: Bahasa pemrograman utama.
*   **SQLite**: Database ringan untuk penyimpanan data permainan (profil bengkel, inventaris, dll.).
*   **JavaFX (Opsional - Bonus GUI)**: Framework untuk membangun antarmuka pengguna grafis.
*   **Maven/Gradle**: Build automation tool untuk manajemen build dan package.
*   **Pustaka Logger(Opsional)**: Seperti SLF4J, untuk memudahkan observasi dan debugging.
*   **Random Number Generator**: Diperlukan untuk aktivitas Fishing.

## Konsep OOP yang Digunakan

*   **Inheritance:** Contoh: Berbagai jenis `Item` (Seed, Fish, Crop, Food, Equipment, Misc) mewarisi dari class `Item`. `House`, `Pond`, `ShippingBin` mewarisi dari `DeployedObject`. `Bed`, `TV`, `Stove` mewarisi dari `Furniture`.
*   **Abstract Class / Interface:** Contoh: `Item` dan `DeployedObject` adalah abstract class. `Furniture` adalah abstract class. Mungkin ada interface untuk tipe Tile.
*   **Polymorphism:** Penggunaan method yang sama pada objek dari class yang berbeda (misalnya method untuk interaksi dengan berbagai jenis `DeployedObject`).
*   **Generics:** Penggunaan tipe parameter pada class atau method (misalnya class `Inventory` yang menggunakan `Map<Item, Integer>`).
*   **Exceptions:** Penanganan kesalahan (misalnya saat terjadi error pada aksi pemain).
*   **Concurrency:** Implementasi mekanisme konkurensi (threading) yang terlihat pada `StateController` untuk mengelola waktu (`timer`).

