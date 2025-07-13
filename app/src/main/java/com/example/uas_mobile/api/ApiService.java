package com.example.uas_mobile.api;

import com.example.uas_mobile.model.ModelMenuMakanan;
import com.example.uas_mobile.model.ModelRiwayat;
import com.example.uas_mobile.model.ModelRiwayatPembayaran;
import com.example.uas_mobile.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("get_menu.php")
    Call<List<ModelMenuMakanan>> getMenu();

    @GET("get_riwayat.php")
    Call<List<ModelRiwayat>> getRiwayat();

    // Untuk register user
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseModel> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    // Untuk login user
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> login(
            @Field("username") String username,
            @Field("password") String password
    );

    // Untuk menyimpan pesanan
    @FormUrlEncoded
    @POST("pesan_sekarang.php")
    Call<ResponseModel> insertPesanan(
            @Field("id_user") int id_user,
            @Field("id_makanan") int id_makanan,
            @Field("jumlah") int jumlah,
            @Field("total_harga") int total_harga
    );


    //hapus
    @FormUrlEncoded
    @POST("hapus_riwayat.php")
    Call<ResponseModel> hapusRiwayat(@Field("id") int id);



    // Update riwayat pesanan
    @FormUrlEncoded
    @POST("update_riwayat.php")
    Call<ResponseModel> updateRiwayat(
            @Field("id") int id,
            @Field("id_makanan") int id_makanan,
            @Field("jumlah") int jumlah,
            @Field("total_harga") int total_harga
    );

    @FormUrlEncoded
    @POST("get_riwayat.php")
    Call<List<ModelRiwayat>> getRiwayat(@Field("id_user") int idUser);

    @FormUrlEncoded
    @POST("simpan_pembayaran.php")
    Call<ResponseModel> simpanPembayaran(
            @Field("id_user") int idUser,
            @Field("total") int total
    );


    @POST("checkout") // Ganti sesuai nama endpoint checkout kamu di backend
    Call<Void> checkoutSemuaPesanan();

    //untuk riwayat pembayaran
    @FormUrlEncoded
    @POST("get_riwayat_pembayaran.php")
    Call<List<ModelRiwayatPembayaran>> getRiwayatPembayaran(
            @Field("id_user") int idUser
    );

    //hapus riwayat pesanan
    @FormUrlEncoded
    @POST("hapus_riwayat_pesanan.php")
    Call<ResponseModel> hapusRiwayatPesanan(
            @Field("id_user") int idUser
    );
}
