package com.example.uas_mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ModelRiwayat;
import com.example.uas_mobile.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity {

    TextView txtRingkasanPesanan, txtTotalHarga;
    EditText inputUangBayar;
    Button btnBayar, btnKembali;

    int totalHarga = 0;
    String ringkasanText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        txtRingkasanPesanan = findViewById(R.id.txtRingkasanPesanan);
        txtTotalHarga = findViewById(R.id.txtTotalHarga);
        inputUangBayar = findViewById(R.id.inputUangBayar);
        btnBayar = findViewById(R.id.btnBayar);
        btnKembali = findViewById(R.id.btnKembali);

        tampilkanRingkasanPesanan();

        btnBayar.setOnClickListener(v -> {
            String uangInput = inputUangBayar.getText().toString().trim();

            if (uangInput.isEmpty()) {
                Toast.makeText(this, "Masukkan uang bayar terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            int uangBayar = Integer.parseInt(uangInput);

            if (uangBayar < totalHarga) {
                Toast.makeText(this, "Uang tidak cukup!", Toast.LENGTH_SHORT).show();
            } else {
                int kembalian = uangBayar - totalHarga;
                simpanPembayaran(totalHarga); // Simpan ke database

                // Tampilkan struk
                Intent intent = new Intent(Checkout.this, StrukActivity.class);
                intent.putExtra("ringkasan", ringkasanText);
                intent.putExtra("total", totalHarga);
                intent.putExtra("bayar", uangBayar);
                intent.putExtra("kembalian", kembalian);
                startActivity(intent);
                finish();

            }
        });

        btnKembali.setOnClickListener(v -> finish());
    }

    private void tampilkanRingkasanPesanan() {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        int idUser = preferences.getInt("id_user", -1);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<ModelRiwayat>> call = apiService.getRiwayat(idUser);

        call.enqueue(new Callback<List<ModelRiwayat>>() {
            @Override
            public void onResponse(Call<List<ModelRiwayat>> call, Response<List<ModelRiwayat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelRiwayat> list = response.body();
                    StringBuilder builder = new StringBuilder();
                    totalHarga = 0;
                    int no = 1;

                    for (ModelRiwayat item : list) {
                        int subtotal = item.getTotal_harga();
                        builder.append(no++).append(". ")
                                .append(item.getNama_makanan())
                                .append("   x").append(item.getJumlah())
                                .append("   Rp").append(subtotal).append("\n");
                        totalHarga += subtotal;
                    }

                    ringkasanText = builder.toString();
                    txtRingkasanPesanan.setText(ringkasanText);
                    txtTotalHarga.setText("Total: Rp" + totalHarga);
                }
            }

            @Override
            public void onFailure(Call<List<ModelRiwayat>> call, Throwable t) {
                Toast.makeText(Checkout.this, "Gagal mengambil pesanan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void simpanPembayaran(int total) {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        int idUser = preferences.getInt("id_user", -1);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseModel> call = apiService.simpanPembayaran(idUser, total);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                // Tidak harus menunggu respon ini untuk lanjut
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Checkout.this, "Gagal menyimpan riwayat pembayaran", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
