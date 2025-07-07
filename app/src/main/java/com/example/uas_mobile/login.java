package com.example.uas_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show();
                return;
            }

            Call<ResponseModel> call = apiService.login(username, password);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        // Simpan ID user ke SharedPreferences
                        int idUser = response.body().getId(); // Pastikan ada getter getId()
                        SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("id_user", idUser);
                        editor.apply();

                        Toast.makeText(login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, MenuMakanan.class));
                        finish(); // agar tidak kembali ke login saat tekan back
                    } else {
                        Toast.makeText(login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(login.this, "Gagal: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(login.this, Register.class));
            finish();
        });
    }
}
