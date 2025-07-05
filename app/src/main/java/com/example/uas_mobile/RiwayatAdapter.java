package com.example.uas_mobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private Context context;
    private List<ModelRiwayat> riwayatList;

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

        holder.btnEdit.setOnClickListener(v -> {
            // TODO: Implementasi edit
            Toast.makeText(context, "Edit pesanan: " + riwayat.getNama_makanan(), Toast.LENGTH_SHORT).show();
        });

        holder.btnHapus.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ResponseModel> call = apiService.hapusRiwayat(riwayat.getId());

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body().isSuccess()) {
                        // Hapus dari list dan update tampilan RecyclerView
                        riwayatList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, riwayatList.size());
                        Toast.makeText(context, "Berhasil menghapus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Gagal menghapus", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    class RiwayatViewHolder extends RecyclerView.ViewHolder {
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
