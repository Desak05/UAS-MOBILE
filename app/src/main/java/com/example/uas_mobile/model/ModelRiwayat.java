package com.example.uas_mobile.model;

import com.google.gson.annotations.SerializedName;

public class ModelRiwayat {

    @SerializedName("id")
    private int id;

    @SerializedName("id_makanan")
    private int id_makanan;

    @SerializedName("nama_makanan")
    private String nama_makanan;

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("total_harga")
    private int total_harga;

    // Getter
    public int getId() {
        return id;
    }

    public int getId_makanan() {
        return id_makanan;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    // Setter
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }
}
