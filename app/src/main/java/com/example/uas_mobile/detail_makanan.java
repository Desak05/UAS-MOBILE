package com.example.uas_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class detail_makanan extends AppCompatActivity {

    private ImageView imgMakanan;
    private TextView txtNamaMakanan, txtDeskripsi, txtHarga, txtJumlah, txtTotalHarga;
    private Button btnKurang, btnTambah, btnPesan;

    private int jumlah = 1;
    private int hargaSatuan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);



        // Hubungkan dengan komponen layout
        imgMakanan = findViewById(R.id.imgMakanan);
        txtNamaMakanan = findViewById(R.id.txtNamaMakanan);
        txtDeskripsi = findViewById(R.id.txtDeskripsi);
        txtHarga = findViewById(R.id.txtHarga);
        txtJumlah = findViewById(R.id.txtJumlah);
        txtTotalHarga = findViewById(R.id.txtTotalHarga);
        btnKurang = findViewById(R.id.btnKurang);
        btnTambah = findViewById(R.id.btnTambah);
        btnPesan = findViewById(R.id.btnPesan);

        // Ambil data dari Intent
        String nama = getIntent().getStringExtra("nama");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        String gambar = getIntent().getStringExtra("gambar");
        String harga = getIntent().getStringExtra("harga");

        // Tampilkan data
        txtNamaMakanan.setText(nama);
        txtDeskripsi.setText(deskripsi);
        txtHarga.setText("Harga: Rp " + harga);

        try {
            hargaSatuan = Integer.parseInt(harga);
        } catch (Exception e) {
            hargaSatuan = 0;
        }

        // Load gambar dari URL menggunakan Glide
        Glide.with(this).load(gambar).into(imgMakanan);

        // Hitung total harga awal
        hitungTotal();

        // Tombol Tambah
        btnTambah.setOnClickListener(v -> {
            jumlah++;
            txtJumlah.setText(String.valueOf(jumlah));
            hitungTotal();
        });

        // Tombol Kurang
        btnKurang.setOnClickListener(v -> {
            if (jumlah > 1) {
                jumlah--;
                txtJumlah.setText(String.valueOf(jumlah));
                hitungTotal();
            }
        });

        // Tombol Pesan Sekarang
        btnPesan.setOnClickListener(v -> {
            // Tambahkan ke keranjang atau lanjutkan ke checkout
            Toast.makeText(this, "Pesanan ditambahkan: " + jumlah + " x " + nama, Toast.LENGTH_SHORT).show();

            // (Opsional) kirim ke halaman keranjang atau proses
            // Intent intent = new Intent(this, CheckoutActivity.class);
            // startActivity(intent);
        });
    }

    // Fungsi hitung total harga
    private void hitungTotal() {
        int total = hargaSatuan * jumlah;
        txtTotalHarga.setText("Total: Rp " + total);

    }
}