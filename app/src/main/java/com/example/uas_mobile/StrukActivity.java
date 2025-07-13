package com.example.uas_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StrukActivity extends AppCompatActivity {
    TextView txtRingkasan, txtTotal, txtBayar, txtKembalian;
    Button btnRiwayat, btnBeranda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk);

        txtRingkasan = findViewById(R.id.txtRingkasan);
        txtTotal = findViewById(R.id.txtTotal);
        txtBayar = findViewById(R.id.txtBayar);
        txtKembalian = findViewById(R.id.txtKembalian);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String ringkasan = intent.getStringExtra("ringkasan");
        int total = intent.getIntExtra("total", 0);
        int bayar = intent.getIntExtra("bayar", 0);
        int kembali = intent.getIntExtra("kembalian", 0);

        // Set ke TextView
        txtRingkasan.setText(ringkasan);
        txtTotal.setText("Total: Rp" + total);
        txtBayar.setText("Bayar: Rp" + bayar);
        txtKembalian.setText("Kembalian: Rp" + kembali);

        // Inisialisasi tombol
        btnRiwayat = findViewById(R.id.btnRiwayat);
        btnBeranda = findViewById(R.id.btnBeranda);

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StrukActivity.this, RiwayatPembayaranActivity.class);
                startActivity(intent);
            }
        });

        btnBeranda.setOnClickListener(v -> {
            Intent homeIntent = new Intent(StrukActivity.this, MenuMakanan.class); // atau HomeActivity
            startActivity(homeIntent);
            finish();
        });

        // Hapus riwayat pesanan setelah pembayaran
        SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
        int idUser = prefs.getInt("id_user", -1);

        if (idUser != -1) {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ResponseModel> call = apiService.hapusRiwayatPesanan(idUser);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(StrukActivity.this, "Riwayat pesanan dihapus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StrukActivity.this, "Gagal hapus riwayat", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(StrukActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
