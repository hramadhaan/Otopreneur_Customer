package com.otopreneur.otopreneur_customer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Service_Motor.InputServiceMotor;
import com.otopreneur.otopreneur_customer.Model.Variant;
import com.otopreneur.otopreneur_customer.R;

import java.util.ArrayList;

public class ServiceMotorAdapter extends RecyclerView.Adapter<ServiceMotorAdapter.ViewHolder> {

    private static final String TAG = "ServiceMotorAdapter" ;
    private Context context;
    private ArrayList<Variant> variants;

    public ServiceMotorAdapter(Context context, ArrayList<Variant> variants) {
        this.context = context;
        this.variants = variants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_motor,parent,false);
        ViewHolder woke = new ViewHolder(view);
        return woke;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: Called = ");

        holder.nama.setText(variants.get(position).getServiceName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InputServiceMotor.class);
                intent.putExtra("jenisKendaraan","Motor");
                intent.putExtra("tipeService",variants.get(position).getServiceCode());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return variants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView nama;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_sm_cv);
            nama = itemView.findViewById(R.id.item_sm_nama);
        }
    }
}
