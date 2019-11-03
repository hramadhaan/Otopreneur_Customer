package com.otopreneur.otopreneur_customer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.PackageOrder.HasilCreateOrder;
import com.otopreneur.otopreneur_customer.Model.Service;
import com.otopreneur.otopreneur_customer.R;

import java.util.ArrayList;

public class DaftarBengkelAdapter extends RecyclerView.Adapter<DaftarBengkelAdapter.ViewHolder> {

    private static final String TAG = "DaftarBengkelAdapter";

    private Context context;
//    List<Vendor> mVendor;
    String tipeAdapter;
    String catatanAdapter;
    String lokasiAdapter;
    ArrayList<Service> mService;


    public DaftarBengkelAdapter(Context context, String tipeAdapter, String catatanAdapter, String lokasiAdapter, ArrayList<Service> mService) {
        this.context = context;
        this.tipeAdapter = tipeAdapter;
        this.catatanAdapter = catatanAdapter;
        this.lokasiAdapter = lokasiAdapter;
        this.mService = mService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_bengkel,parent,false);
        ViewHolder woke = new ViewHolder(view);
        return woke;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: Called = ");

        holder.nama.setText(mService.get(position).getVendor().getName());
        holder.harga.setText("Rp. "+mService.get(position).getCost().toString());
        holder.alamat.setText("Alamat");

        Glide.with(context).asBitmap().load(mService.get(position).getVendor().getAvatar()).into(holder.image);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick : Clicked on: "+mService.get(position).getId());

                Intent intent = new Intent(context, HasilCreateOrder.class);
                intent.putExtra("id_service",mService.get(position).getId());
                intent.putExtra("harga",mService.get(position).getCost().toString());
                intent.putExtra("tipeKendaraana",tipeAdapter);
                intent.putExtra("catatanKendaraana",catatanAdapter);
                intent.putExtra("lokasiKendaraana",lokasiAdapter);
                intent.putExtra("namaVendor",mService.get(position).getVendor().getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama,alamat,harga;
        CardView parentLayout;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.item_bengkel_nama);
            alamat = itemView.findViewById(R.id.item_bengkel_alamat);
            harga = itemView.findViewById(R.id.item_bengkel_harga);
            parentLayout = itemView.findViewById(R.id.item_parent);
            image = itemView.findViewById(R.id.item_bengkel_foto);

        }
    }
}
