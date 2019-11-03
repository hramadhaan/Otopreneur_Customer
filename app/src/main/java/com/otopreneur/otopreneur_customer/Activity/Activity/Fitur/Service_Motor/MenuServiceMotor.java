package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Service_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.DaftarBengkel;
import com.otopreneur.otopreneur_customer.Adapter.ServiceMotorAdapter;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Variant;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuServiceMotor extends AppCompatActivity {

    Toolbar toolbar;
    TextView judul;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private AppState appState;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_service_motor);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.servicemotor_toolbar);
//        judul = toolbar.findViewById(R.id.servicemotor_judul);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Service Motor");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.servicemotor_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        refresh();

    }

    private void refresh() {
        Call<ArrayList<Variant>> call = apiService.getVariant();
        call.enqueue(new Callback<ArrayList<Variant>>() {
            @Override
            public void onResponse(Call<ArrayList<Variant>> call, Response<ArrayList<Variant>> response) {
                if (response.isSuccessful()){
                    ArrayList<Variant> variants = response.body();
                    mAdapter = new ServiceMotorAdapter(getApplicationContext(),variants);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount()==0){
                        Toast.makeText(MenuServiceMotor.this,"Service Motor Tidak Ada",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MenuServiceMotor.this,response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Variant>> call, Throwable t) {
                Toast.makeText(MenuServiceMotor.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
