package com.example.uas_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPesananActivity extends AppCompatActivity {

    EditText etJumlah;
    Button btnSimpan;

    int id, id_makanan, jumlah, total_harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pesanan);

        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);

        // Ambil data dari intent
        id = getIntent().getIntExtra("id", -1);
        id_makanan = getIntent().getIntExtra("id_makanan", -1);
        jumlah = getIntent().getIntExtra("jumlah", 0);
        total_harga = getIntent().getIntExtra("total_harga", 0);

        int hargaSatuan = (jumlah != 0) ? (total_harga / jumlah) : 0;
        etJumlah.setText(String.valueOf(jumlah));

        btnSimpan.setOnClickListener(v -> {
            String input = etJumlah.getText().toString().trim();
            if (input.isEmpty() || input.equals("0")) {
                etJumlah.setError("Jumlah tidak boleh kosong atau 0");
                etJumlah.requestFocus();
                return;
            }

            int newJumlah = Integer.parseInt(input);
            int newTotal = newJumlah * hargaSatuan;

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ResponseModel> call = apiService.updateRiwayat(id, id_makanan, newJumlah, newTotal);

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(EditPesananActivity.this, "Berhasil diupdate", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK); // <- memberi tahu activity sebelumnya bahwa ada perubahan
                        finish(); // kembali ke RiwayatPesanan
                    } else {
                        Toast.makeText(EditPesananActivity.this, "Gagal mengupdate", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(EditPesananActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
