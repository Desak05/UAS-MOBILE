package com.example.uas_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import com.example.uas_mobile.api.ApiClient;
import com.example.uas_mobile.api.ApiService;
import com.example.uas_mobile.model.ModelRiwayat;
import com.example.uas_mobile.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private final Context context;
    private final List<ModelRiwayat> riwayatList;

    public RiwayatAdapter(Context context, List<ModelRiwayat> riwayatList) {
        this.context = context;
        this.riwayatList = riwayatList;
    }

    @Override
    public RiwayatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RiwayatViewHolder holder, int position) {
        ModelRiwayat riwayat = riwayatList.get(position);
        holder.txtNamaMakanan.setText(riwayat.getNama_makanan());
        holder.txtJumlah.setText("Jumlah: " + riwayat.getJumlah());
        holder.txtTotal.setText("Total: Rp " + riwayat.getTotal_harga());

        // Tombol Edit
        holder.btnEdit.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                ModelRiwayat selected = riwayatList.get(pos);

                Intent intent = new Intent(context, EditPesananActivity.class);
                intent.putExtra("id", selected.getId());
                intent.putExtra("id_makanan", selected.getId_makanan());
                intent.putExtra("jumlah", selected.getJumlah());
                intent.putExtra("total_harga", selected.getTotal_harga());

                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 101); // requestCode: 101
                }
            }
        });

        // Tombol Hapus (versi dengan dialog konfirmasi)
        holder.btnHapus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                ModelRiwayat selected = riwayatList.get(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hapus Pesanan")
                        .setMessage("Yakin ingin menghapus pesanan ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            ApiService apiService = ApiClient.getClient().create(ApiService.class);
                            Call<ResponseModel> call = apiService.hapusRiwayat(selected.getId());

                            call.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    if (response.isSuccessful() && response.body().isSuccess()) {
                                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        riwayatList.remove(pos);
                                        notifyItemRemoved(pos);
                                        notifyItemRangeChanged(pos, riwayatList.size());
                                    } else {
                                        Toast.makeText(context, "Gagal menghapus", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    static class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaMakanan, txtJumlah, txtTotal;
        Button btnEdit, btnHapus;

        public RiwayatViewHolder(View itemView) {
            super(itemView);
            txtNamaMakanan = itemView.findViewById(R.id.txtNamaMakanan);
            txtJumlah = itemView.findViewById(R.id.txtJumlah);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
