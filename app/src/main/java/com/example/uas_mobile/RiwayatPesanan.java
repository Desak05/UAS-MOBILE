package com.example.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pesanan);

        recyclerView = findViewById(R.id.recyclerRiwayat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getRiwayatData(); // Panggil saat pertama kali
    }

    private void getRiwayatData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<ModelRiwayat>> call = apiService.getRiwayat();

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

    // Agar saat kembali dari EditPesananActivity, data diperbarui
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            getRiwayatData(); // Refresh data
        }
    }
}
