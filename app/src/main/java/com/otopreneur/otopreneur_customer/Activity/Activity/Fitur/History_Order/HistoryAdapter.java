package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.History_Order;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otopreneur.otopreneur_customer.Model.History;
import com.otopreneur.otopreneur_customer.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final String TAG = "HistoryAdapter";

    private Context context;
    ArrayList<History> histories;

    public HistoryAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
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
        holder.history_nama.setText(histories.get(position).getVendordata().getName());
        holder.history_alamat.setText(histories.get(position).getLocation());

        Glide.with(context)
                .asBitmap()
                .load(histories.get(position).getVendordata().getAvatar())
                .into(holder.history_foto);

        holder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "On Cliked: "+histories.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,DetailHistory.class);
                intent.putExtra("invoice",histories.get(position).getInvoiceNo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView history_nama,history_alamat;
        ImageView history_foto;
        CardView history;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            history_nama = itemView.findViewById(R.id.item_history_nama);
            history_alamat = itemView.findViewById(R.id.item_history_alamat);
            history_foto = itemView.findViewById(R.id.item_history_foto);
            history = itemView.findViewById(R.id.item_history);
        }
    }
}
