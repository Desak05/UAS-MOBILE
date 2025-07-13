package com.example.uas_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uas_mobile.adapter.RiwayatAdapter;
import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ModelRiwayat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPesanan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RiwayatAdapter riwayatAdapter;
    private Button btnTambahMenu, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pesanan);

        recyclerView = findViewById(R.id.recyclerRiwayat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnTambahMenu = findViewById(R.id.btnTambahMenu);
        btnCheckout = findViewById(R.id.btnCheckout);

        btnTambahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatPesanan.this, MenuMakanan.class);
                startActivity(intent);
                finish();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arahkan ke halaman Checkout.java
                Intent intent = new Intent(RiwayatPesanan.this, Checkout.class);
                startActivity(intent);
            }
        });


        getRiwayatData(); // Panggil saat pertama kali
    }

    private void getRiwayatData() {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        int idUser = preferences.getInt("id_user", -1);

        if (idUser == -1) {
            Toast.makeText(this, "User tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<ModelRiwayat>> call = apiService.getRiwayat(idUser); // ✅ Gunakan POST dengan id_user

        call.enqueue(new Callback<List<ModelRiwayat>>() {
            @Override
            public void onResponse(Call<List<ModelRiwayat>> call, Response<List<ModelRiwayat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    riwayatAdapter = new RiwayatAdapter(RiwayatPesanan.this, response.body());
                    recyclerView.setAdapter(riwayatAdapter);
                } else {
                    Toast.makeText(RiwayatPesanan.this, "Gagal memuat riwayat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelRiwayat>> call, Throwable t) {
                Toast.makeText(RiwayatPesanan.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            getRiwayatData(); // Refresh data
        }
    }

    private void showStrukDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Struk Pembayaran");

        String isiStruk = "Terima kasih telah memesan!\n\nPesanan Anda telah dibayar.\n\nTanggal: "
                + java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

        builder.setMessage(isiStruk);
        builder.setPositiveButton("Tutup", null);
        builder.show();
    }
}
