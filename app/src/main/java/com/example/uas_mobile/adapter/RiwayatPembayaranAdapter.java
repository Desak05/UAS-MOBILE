package com.example.uas_mobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uas_mobile.R;
import com.example.uas_mobile.model.ModelRiwayatPembayaran;

import java.util.List;

public class RiwayatPembayaranAdapter extends RecyclerView.Adapter<RiwayatPembayaranAdapter.ViewHolder> {

    private Context context;
    private List<ModelRiwayatPembayaran> list;

    public RiwayatPembayaranAdapter(Context context, List<ModelRiwayatPembayaran> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txtTanggalPembayaran);
            txtTotal = itemView.findViewById(R.id.txtTotalPembayaran);
        }
    }

    @NonNull
    @Override
    public RiwayatPembayaranAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_riwayat_pembayaran, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelRiwayatPembayaran data = list.get(position);
        holder.txtTanggal.setText("Tanggal: " + data.getTanggal());
        holder.txtTotal.setText("Total: Rp" + data.getTotalHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
