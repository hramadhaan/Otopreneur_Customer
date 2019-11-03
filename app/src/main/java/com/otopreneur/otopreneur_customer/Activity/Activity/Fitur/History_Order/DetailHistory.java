package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.History_Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.getStatus;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistory extends AppCompatActivity {

    TextView nama,alamat,tipekendaraan,tipservice,judul,catatan;

    Toolbar toolbar;

    private AppState appState;
    private ApiService apiService;

    int invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.orderhistory_toolbar);
        judul = toolbar.findViewById(R.id.orderhistory_judul);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Service Motor");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catatan = findViewById(R.id.orderhistory_catatan);
        nama = findViewById(R.id.orderhistory_customer);
        alamat = findViewById(R.id.orderhistory_lokasi);
        tipekendaraan = findViewById(R.id.orderhistory_tipekendaraan);
        tipservice = findViewById(R.id.orderhistory_tipeservice);

        Intent getIntent = getIntent();
        invoice = getIntent.getIntExtra("invoice",0);

        refresh();
    }

    private void refresh() {
        Call<getStatus> getStatusCall = apiService.getStatus(invoice);
        getStatusCall.enqueue(new Callback<getStatus>() {
            @Override
            public void onResponse(Call<getStatus> call, Response<getStatus> response) {
                if (response.isSuccessful()){
                    nama.setText(response.body().getVendordata().getName());
                    alamat.setText(response.body().getLocation());
                    tipekendaraan.setText(response.body().getVenicheType());
                    catatan.setText(response.body().getNote());
                    tipservice.setText(response.body().getVenichleSeries());
                    judul.setText("Invoice : "+String.valueOf(response.body().getInvoiceNo()));
                }
            }

            @Override
            public void onFailure(Call<getStatus> call, Throwable t) {

            }
        });
    }

}
