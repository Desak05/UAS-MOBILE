package com.example.uas_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StrukActivity extends AppCompatActivity {
    TextView txtRingkasan, txtTotal, txtBayar, txtKembalian;
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

    }
}