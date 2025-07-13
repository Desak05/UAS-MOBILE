package com.example.uas_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.uas_mobile.adapter.RiwayatPembayaranAdapter;
import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ModelRiwayatPembayaran;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPembayaranActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RiwayatPembayaranAdapter adapter;
    private List<ModelRiwayatPembayaran> list = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pembayaran);

        // ✅ Inisialisasi tombol kembali
        Button btnKembali = findViewById(R.id.btnKembaliKeMenu);
        btnKembali.setOnClickListener(v -> {
            startActivity(new Intent(RiwayatPembayaranActivity.this, MenuMakanan.class));
            finish();
        });

        recyclerView = findViewById(R.id.recyclerRiwayatPembayaran);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RiwayatPembayaranAdapter(this, list);
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
        int idUser = prefs.getInt("id_user", -1);

        if (idUser == -1) {
            Toast.makeText(this, "ID User tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<ModelRiwayatPembayaran>> call = apiService.getRiwayatPembayaran(idUser);
        call.enqueue(new Callback<List<ModelRiwayatPembayaran>>() {
            @Override
            public void onResponse(Call<List<ModelRiwayatPembayaran>> call, Response<List<ModelRiwayatPembayaran>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("RIWAYAT", "Data berhasil dimuat: " + new Gson().toJson(response.body()));
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("RIWAYAT", "Gagal parsing: " + errorBody);
                        Toast.makeText(RiwayatPembayaranActivity.this, "Gagal parsing JSON", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelRiwayatPembayaran>> call, Throwable t) {
                Log.e("RIWAYAT", "onFailure: ", t);
                Toast.makeText(RiwayatPembayaranActivity.this, "Retrofit Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
