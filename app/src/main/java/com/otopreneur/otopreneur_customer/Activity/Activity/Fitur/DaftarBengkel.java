package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Adapter.DaftarBengkelAdapter;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Service;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Services.RetrofitClient;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarBengkel extends AppCompatActivity {

    RecyclerView item_bengkel;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    TextView judul;

    RetrofitClient retrofitClient;

    String vehicle,tambalBan,tipeKendaraan,catatanKendaraan,lokasiKendaraan;
    int id_customer;

    private AppState appState;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bengkel);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.daftarbengkel_toolbar);
        judul = toolbar.findViewById(R.id.daftarbengkel_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item_bengkel = findViewById(R.id.daftarbengkel_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        item_bengkel.setLayoutManager(mLayoutManager);

//        GET INTENT
        Intent getIntent = getIntent();
        tambalBan = getIntent.getStringExtra("jenisService");
        vehicle = getIntent.getStringExtra("venichleService");
        tipeKendaraan = getIntent.getStringExtra("tipeKendaraan");
        catatanKendaraan = getIntent.getStringExtra("catatanKendaraan");
        lokasiKendaraan = getIntent.getStringExtra("lokasiKendaraan");
        refresh();
    }

    private void refresh() {
        Call<ArrayList<Service>> call = apiService.getService(tambalBan,vehicle);
        call.enqueue(new Callback<ArrayList<Service>>() {
            @Override
            public void onResponse(Call<ArrayList<Service>> call, Response<ArrayList<Service>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(DaftarBengkel.this,"Berhasil dapat data",Toast.LENGTH_LONG).show();
                    ArrayList<Service> serviceList = response.body();
//                    Toast.makeText(DaftarBengkel.this,String.valueOf(serviceList.size()),Toast.LENGTH_LONG).show();
                    mAdapter = new DaftarBengkelAdapter(getApplicationContext(),tipeKendaraan,catatanKendaraan,lokasiKendaraan,serviceList);
                    item_bengkel.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount()==0){
                        Toast.makeText(DaftarBengkel.this,"Daftar Bengkel Tidak Tersedia",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DaftarBengkel.this,response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Service>> call, Throwable t) {
                Toast.makeText(DaftarBengkel.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
