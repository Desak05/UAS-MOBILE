package com.example.uas_mobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

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
            // TODO: Implementasi hapus
            Toast.makeText(context, "Hapus pesanan: " + riwayat.getNama_makanan(), Toast.LENGTH_SHORT).show();
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
