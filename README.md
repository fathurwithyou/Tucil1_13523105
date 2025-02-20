# IQ Puzzle Solver
![Java 11](https://img.shields.io/badge/Java-11-blue?logo=java&logoColor=white)


IQ Puzzle Solver adalah program yang dirancang untuk menyelesaikan puzzle IQ menggunakan algoritma brute-force. Program ini mencoba menempatkan potongan-potongan puzzle ke dalam sebuah papan (board) dengan cara:
- Mencari sel kosong (sel dengan nilai 0)
- Mencoba menempatkan potongan (dengan berbagai varian bentuk) pada posisi tersebut
- Melakukan pencarian secara bruteforce dengan stack.

Solusi yang ditemukan akan ditampilkan di konsol dengan representasi warna berdasarkan label potongan, serta disimpan sebagai file gambar.

## Struktur Proyek
```
.
├── bin
├── doc
├── src
│   ├── datatypes
│   │   ├── Pair.java
│   │   └── Tuple5.java
│   └── models
│       ├── GenerateVariants.java
│       ├── IQPuzzleSolver.java
│       └── ImageGenerator.java
├── test
│   ├── bad1.txt
│   └── input1.txt
├── LICENSE (opsional)
├── Main.java
├── README.md
└── run.bat

```

## Cara Menjalankan Program

### 1. Kompilasi
Pastikan JDK telah terinstall. Dari direktori root proyek, kompilasi dengan
```bash
javac -d bin Main.java
```

### 2. Menjalankan
Dari direktori `bin`, jalankan program dengan perintah:
```bash
java -cp bin Main
```

### 3. Cara Mudah
Gunakan perintah berikut untuk windows.
```
./run.bat
```

## Input dan Output

### Input
Program menerima input melalui objek `Tuple5` yang memuat:
- **Dimensi Board:** Nilai `N` (baris) dan `M` (kolom)
- **Jumlah Potongan:** Nilai `P`
- **Board:** Array dua dimensi (`int[][]`)
- **List Potongan:** `List<Pair<Character, List<List<List<Integer>>>>>` yang berisi label potongan dan varian bentuk (representasi matriks 0/1)

### Output
- **Konsol:** Menampilkan board dengan penempatan potongan, dengan warna berbeda untuk tiap potongan.
- **Gambar:** Solusi disimpan sebagai file gambar menggunakan kelas `ImageGenerator`.
- **TXT File:** Solusi ditulis sebagai file txt menggunakan kelas `FileProcessing`.
---
## Author
| Nama | NIM | Kelas |
|------|---|---|
| Muhammad Fathur Rizky | 13523105 | K02 |  