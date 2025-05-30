﻿
# **Spakbor Hills - Tugas Besar Pemrograman Berorientasi Objek IF2010 Kelompok 7**
Repository: [https://github.com/OmarBerliansyah/Tugas_Besar_IF2010_K4_07.git](https://github.com/OmarBerliansyah/Tugas_Besar_IF2010_K4_07.git)


## Deskripsi
_______

"That Time I Became a Farming Game Dev to Save the World"

**Spakbor Hills** adalah permainan simulasi pertanian di mana pemain akan mengelola sebuah peternakan, berinteraksi dengan berbagai Non-Player Character (NPC), menanam berbagai jenis tanaman, memancing, memasak, dan menjalani kehidupan di sebuah desa yang damai. Tujuan utama permainan ini adalah mencapai salah satu dari *milestone* yang ditentukan, seperti mengumpulkan sejumlah gold atau menikah

## Contributors:
_______

| **No** | **Nama** | **NIM** |
| :----- | :--------------------------- | :------- |
| 1      | Muhammad Omar Berliansyah    | 18223055 |
| 2      | Nadia Apsarini Baizal        | 18221065 |
| 3      | Catherine Alicia N           | 18223069 |
| 4      | Aliya Harta Ary Utama        | 18223081 |
| 5      | Noeriza Aqila Wibawa         | 18221095 |

**Asisten:** Nazhif Haidar Putra Wibowo


## Features

### Technical Features:
* **Graphical User Interface (GUI):** Permainan menggunakan Java Swing untuk tampilan visual dan interaksi
* **Keyboard Input:** Kontrol pemain dan interaksi game didukung melalui keyboard (WASD/Arrow keys untuk pergerakan, tombol aksi)
* **Object-Oriented Principles & Design Patterns:** Implementasi inheritance, abstract class/interface, polymorphism, generics, exceptions, concurrency dan design pattern factory, a , b
* **Sound System:** Musik latar dan efek suara
* **Data-Driven Items:** Definisi item dimuat dari file eksternal 

### Game Features:
* **Player Customization & Progression:**
    * Pemain dapat menentukan nama, nama peternakan, item favorit dan jenis kelamin di awal permainan
    * **Energy System:** Aksi pemain mengkonsumsi energi, yang dapat dipulihkan dengan makan atau tidur
    * **Gold & Inventory System:** Mengelola mata uang (gold) untuk jual beli dan menyimpan item dalam inventory
    * **Diverse Items:** Meliputi Tools, Seeds, Crops, Fish, Cooked Food, Equipment, dan Miscellaneous items
    * **Shipping Bin:** Tempat untuk menjual hasil panen dan item lainnya
* **Farming Simulation:**
    * **Core Mechanics:** Tilling, Planting, Watering, Recover Land dan Harvesting
    * **Crop Growth Cycle:** Tanaman membutuhkan waktu tertentu untuk tumbuh dan dipanen
* **NPC Interaction & Relationships:**
    * **Dialogue, Gifting, Marriage:** Berinteraksi, memberi hadiah, dan membangun hubungan hingga pernikahan dengan NPC
    * **Relationship System:** Heart points dan status hubungan (friend, fiance, spouse)
* **World Maps:**
    * **Multiple Maps & Exploration:** Farm Map, World Map dengan berbagai lokasi seperti Ocean, Forest River, Mountain Lake, dan rumah NPC
    * **Map Transitions:** Pindah antar map melalui titik-titik tertentu
    * **Time, Season, and Weather System:** Waktu berjalan dengan siklus siang/malam, empat musim yang mempengaruhi tanaman/ikan, dan cuaca (cerah/hujan)
* **Activities & Minigames:**
    * **Cooking:** Memasak berbagai resep makanan
    * **Fishing:** Memancing ikan di berbagai lokasi dengan mekanisme RNG
    * **Sleeping & Watching TV:** Mengistirahatkan pemain dan mengetahui cuaca melalui TV
* **Game Progression:**
    * **End Game Statistics:** Menampilkan statistik pencapaian pemain setelah mencapai milestone tertentu
    * **Furniture (Bonus):** Beberapa item furnitur seperti tempat tidur dan TV telah diimplementasikan


## Tech Needs

**IDE:**
* Visual Studio Code (dengan Java Extension Pack direkomendasikan)
* IntelliJ IDEA
* Eclipse IDE for Java Developers

**Java Development Kit (JDK):**
* JDK Version 11 atau lebih baru.

**Build Tool:**
* Gradle (sudah terintegrasi dalam proyek ini)

  ## Booklet & Log Act:
  on Tugas_Besar_IF2010_K4_07/docs directory


## How to Run

1.  **Clone the Project Repository:**
    Pastikan Anda memiliki Git terinstal.
    ```bash
    git clone https://github.com/OmarBerliansyah/Tugas_Besar_IF2010_K4_07.git
    ```

2.  **Navigate to the Project Directory:**
    ```bash
    cd Tugas_Besar_IF2010_K4_07
    ```
    *(Nama folder akan sesuai dengan nama repository)*

3.  **Open the Project in VS Code:**
    * Pastikan Anda telah menginstal [Visual Studio Code](https://code.visualstudio.com/) dan [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) dari Microsoft.
    * Buka VS Code, lalu pilih `File > Open Folder...` dan pilih direktori proyek `Tugas_Besar_IF2010_K4_07` yang telah Anda clone.
    * VS Code akan secara otomatis mendeteksi proyek Gradle. Biarkan proses sinkronisasi selesai.

4.  **Compile and Run the Program using Gradle:**
    Proyek ini menggunakan Gradle Wrapper, sehingga Anda tidak perlu menginstal Gradle secara manual.

    * **For macOS/Linux:**
        Buka terminal terintegrasi di VS Code (`Terminal > New Terminal`) atau terminal sistem di direktori proyek, lalu jalankan:
        ```bash
       chmod +x ./gradlew  
       ./gradlew run        
        ```

    * **For Windows:**
        Buka Command Prompt, PowerShell, atau terminal terintegrasi di VS Code di direktori proyek, lalu jalankan:
        ```bash
        gradlew.bat run
        ```
    Perintah `run` pada Gradle akan mengkompilasi kode sumber dan menjalankan kelas `Main` (`com.SpakborHills.main.Main`) yang telah dikonfigurasi dalam file `build.gradle`.

Selamat Bertani di Spakbor Hills! 🚜🌾it)
_______
