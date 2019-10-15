package com.example.customer_otopedia.Activity.Activity.Fitur.History_Order;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customer_otopedia.R;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final String TAG = "HistoryAdapter";

    private Context context;
    private ArrayList<String> nama = new ArrayList<>();
    private ArrayList<String> alamat = new ArrayList<>();
    private ArrayList<String> foto = new ArrayList<>();

    public HistoryAdapter(Context context, ArrayList<String> nama, ArrayList<String> alamat, ArrayList<String> foto) {
        this.context = context;
        this.nama = nama;
        this.alamat = alamat;
        this.foto = foto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder");
        holder.history_nama.setText(nama.get(position));
        holder.history_alamat.setText(alamat.get(position));

        Glide.with(context)
                .asBitmap()
                .load(foto.get(position))
                .into(holder.history_foto);

        holder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "On Cliked: "+nama.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,DetailHistory.class);
                intent.putExtra("nama",nama.get(position));
                intent.putExtra("alamat",alamat.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nama.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView history_nama,history_alamat;
        ImageView history_foto;
        RelativeLayout history;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            history_nama = itemView.findViewById(R.id.item_history_nama);
            history_alamat = itemView.findViewById(R.id.item_history_alamat);
            history_foto = itemView.findViewById(R.id.item_history_foto);
            history = itemView.findViewById(R.id.item_history);
        }
    }
}
