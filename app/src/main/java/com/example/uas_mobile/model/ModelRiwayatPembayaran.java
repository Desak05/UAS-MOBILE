package com.example.uas_mobile.model;

import com.google.gson.annotations.SerializedName;

public class ModelRiwayatPembayaran {

    @SerializedName("id")
    private int id;

    @SerializedName("id_user")
    private int idUser;

    @SerializedName("total_harga")
    private int totalHarga;

    @SerializedName("tanggal")
    private String tanggal;

    // Getter
    public int getId() { return id; }
    public int getIdUser() { return idUser; }
    public int getTotalHarga() { return totalHarga; }
    public String getTanggal() { return tanggal; }
}

