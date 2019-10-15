package com.example.customer_otopedia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customer_otopedia.Activity.Activity.Fitur.Order;
import com.example.customer_otopedia.R;

import java.util.ArrayList;

public class DaftarBengkelAdapter extends RecyclerView.Adapter<DaftarBengkelAdapter.ViewHolder> {

    private static final String TAG = "DaftarBengkelAdapter";

    private Context context;
    private ArrayList<String> namaBengkel = new ArrayList<>();
    private ArrayList<String> alamatBengkel = new ArrayList<>();
    private ArrayList<String> hargaBengkel = new ArrayList<>();


    public DaftarBengkelAdapter(Context context, ArrayList<String> namaBengkel, ArrayList<String> alamatBengkel, ArrayList<String> hargaBengkel) {
        this.context = context;
        this.namaBengkel = namaBengkel;
        this.alamatBengkel = alamatBengkel;
        this.hargaBengkel = hargaBengkel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_bengkel,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: Called = ");

        holder.nama.setText(namaBengkel.get(position));
        holder.harga.setText(hargaBengkel.get(position));
        holder.alamat.setText(alamatBengkel.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick : Clicled on: "+namaBengkel.get(position));

                Toast.makeText(context,namaBengkel.get(position),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, Order.class);
                intent.putExtra("nama",namaBengkel.get(position));
                intent.putExtra("alamat",alamatBengkel.get(position));
                intent.putExtra("harga",hargaBengkel.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return namaBengkel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama,alamat,harga;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.item_bengkel_nama);
            alamat = itemView.findViewById(R.id.item_bengkel_alamat);
            harga = itemView.findViewById(R.id.item_bengkel_harga);
            parentLayout = itemView.findViewById(R.id.item_parent);
        }
    }
}
