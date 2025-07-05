package com.example.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuMakanan extends AppCompatActivity {

    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    ApiService apiService;
    Button btnRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan);

        recyclerView = findViewById(R.id.recyclerMenu);
        btnRiwayat = findViewById(R.id.btnRiwayatPesanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient().create(ApiService.class);

        // Perhatikan: Gunakan ModelMenuMakanan
        Call<List<ModelMenuMakanan>> call = apiService.getMenu();
        call.enqueue(new Callback<List<ModelMenuMakanan>>() {
            @Override
            public void onResponse(Call<List<ModelMenuMakanan>> call, Response<List<ModelMenuMakanan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    menuAdapter = new MenuAdapter(MenuMakanan.this, response.body());
                    recyclerView.setAdapter(menuAdapter);
                } else {
                    Toast.makeText(MenuMakanan.this, "Gagal memuat menu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelMenuMakanan>> call, Throwable t) {
                Toast.makeText(MenuMakanan.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnRiwayat.setOnClickListener(v -> {
            startActivity(new Intent(this, RiwayatPesanan.class));
        });
    }
}
