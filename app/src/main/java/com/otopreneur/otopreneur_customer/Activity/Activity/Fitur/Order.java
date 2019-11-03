package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.Dashboard;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.ChangeStatus;
import com.otopreneur.otopreneur_customer.Model.getStatus;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order extends AppCompatActivity {

    Dialog dialog;
    TextView finish;

    Toolbar toolbar;
    TextView judul;
    String nama,hasil_call;

    private AppState appState;
    private ApiService apiService;

    TextView customer,tipekendaraan,tipeservice,lokasi,catatan;

    Button call;

    int invoice,harga,waktu;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

//        EDITTEXT
        customer = findViewById(R.id.order_hasil_customer);
        tipekendaraan = findViewById(R.id.order_hasil_tipekendaraan);
        tipeservice = findViewById(R.id.order_hasil_tipeservice);
        lokasi = findViewById(R.id.order_hasil_lokasi);
        catatan = findViewById(R.id.order_hasil_catatan);

        toolbar = findViewById(R.id.order_toolbar);
        judul = toolbar.findViewById(R.id.order_judul);
        judul.setText("Order: "+AppState.getInstance().provideInvoice());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        call = findViewById(R.id.order_call);

//        GET INTENT

        dialog = new Dialog(this);
        finish = findViewById(R.id.order_finish);



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.fromParts("tel",hasil_call,null));
                startActivity(intent);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finissh();
            }
        });

        refresh();
        swipeRefreshLayout = findViewById(R.id.order_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void finissh() {
        int invoice = Integer.valueOf(AppState.getInstance().provideInvoice());
        Call<ChangeStatus> changeStatusCall = apiService.changeStatus(invoice,"finished");
        changeStatusCall.enqueue(new Callback<ChangeStatus>() {
            @Override
            public void onResponse(Call<ChangeStatus> call, Response<ChangeStatus> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Order.this,"Status anda : "+response.body().getStatus(),Toast.LENGTH_LONG).show();
                    keluar();
                } else {

                }
            }

            @Override
            public void onFailure(Call<ChangeStatus> call, Throwable t) {

            }
        });
    }


    private void keluar() {
        if (AppState.getInstance().isPesanan()){
            AppState.getInstance().hapusPesanan();
            startActivity(new Intent(Order.this, Dashboard.class));
            finish();
        }
    }

    private void swipe(){
        invoice = Integer.valueOf(AppState.getInstance().provideInvoice());
        Toast.makeText(Order.this,String.valueOf(invoice),Toast.LENGTH_LONG).show();
        Call<getStatus> getStatusCall = apiService.getStatus(invoice);
        getStatusCall.enqueue(new Callback<getStatus>() {
            @Override
            public void onResponse(Call<getStatus> call, Response<getStatus> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("ordered")){

                    } else {
                        keluar();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<getStatus> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        invoice = Integer.valueOf(AppState.getInstance().provideInvoice());
        Toast.makeText(Order.this,String.valueOf(invoice),Toast.LENGTH_LONG).show();
        Call<getStatus> getStatusCall = apiService.getStatus(invoice);
        getStatusCall.enqueue(new Callback<getStatus>() {
            @Override
            public void onResponse(Call<getStatus> call, Response<getStatus> response) {
                if (response.isSuccessful()){
                        customer.setText(response.body().getCustomerdata().getName());
                        tipekendaraan.setText(response.body().getVenichleSeries());
                        tipeservice.setText(response.body().getVenicheType());
                        lokasi.setText(response.body().getLocation());
                        harga = response.body().getPrice();
                        waktu = response.body().getDuration();
                        hasil_call = response.body().getCustomerdata().getPhone();
                        catatan.setText(response.body().getNote());
                        dialogSekarang();
                } else {

                }
            }

            @Override
            public void onFailure(Call<getStatus> call, Throwable t) {

            }
        });
    }

    private void dialogSekarang() {
        TextView done,setHarga,setNama;
        dialog.setContentView(R.layout.dialog_order);
        setHarga = dialog.findViewById(R.id.do_hasil_harga);
        setHarga.setText("Rp."+harga);
        setNama = dialog.findViewById(R.id.do_hasil_waktu);
        setNama.setText(String.valueOf(waktu+" Menit"));
        done = dialog.findViewById(R.id.do_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
