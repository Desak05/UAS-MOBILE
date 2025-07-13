package com.example.uas_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uas_mobile.R;
import com.example.uas_mobile.detail_makanan;
import com.example.uas_mobile.model.ModelMenuMakanan;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
//mengatur menu tampilan
    private Context context;
    private List<ModelMenuMakanan> menuList;

    public MenuAdapter(Context context, List<ModelMenuMakanan> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        ModelMenuMakanan menu = menuList.get(position);
        holder.txtNama.setText(menu.getNama());
        holder.txtHarga.setText("Rp " + menu.getHarga());
        Glide.with(context).load(menu.getGambar()).into(holder.imgMenu);

        // Tambahkan ini untuk pindah ke halaman detail saat item diklik
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, detail_makanan.class);
            intent.putExtra("id", menu.getId());
            intent.putExtra("nama", menu.getNama());
            intent.putExtra("gambar", menu.getGambar());
            intent.putExtra("harga", menu.getHarga());
            intent.putExtra("deskripsi", menu.getDeskripsi());

            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtHarga;
        ImageView imgMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtHarga = itemView.findViewById(R.id.txtHarga);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
