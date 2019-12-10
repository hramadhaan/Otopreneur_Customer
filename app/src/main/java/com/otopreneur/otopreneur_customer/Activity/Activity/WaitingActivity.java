package com.otopreneur.otopreneur_customer.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Order;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.ChangeStatus;
import com.otopreneur.otopreneur_customer.Model.getStatus;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;
import com.skyfishjy.library.RippleBackground;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingActivity extends AppCompatActivity {

    RippleBackground rippleBackground;
    ImageView gambar;
    TextView batalkan,nama_bengkel;

    int invoice_no;

    Thread t;

    public String hasil_status;
    private static final String waiting = "waiting";

    private AppState appState;
    private ApiService apiService;

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);


        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        rippleBackground = findViewById(R.id.waiting_ripplebook);
        gambar = findViewById(R.id.waiting_gambar);
        rippleBackground.startRippleAnimation();

        nama_bengkel = findViewById(R.id.waiting_nama_bengkel);
        nama_bengkel.setText("");

        refreshLayout = findViewById(R.id.waiting_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        Intent getIntent = getIntent();
//        hasil_status = getIntent.getStringExtra("status");

//        Toast.makeText(WaitingActivity.this,appState.provideInvoice(),Toast.LENGTH_LONG).show();

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                refresh();
            }
        });

        batalkan = findViewById(R.id.waiting_batalkan);
        batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int invoice = Integer.valueOf(AppState.getInstance().provideInvoice());
                Call<ChangeStatus> changeStatusCall = apiService.changeStatus(invoice,"canceled");
                changeStatusCall.enqueue(new Callback<ChangeStatus>() {
                    @Override
                    public void onResponse(Call<ChangeStatus> call, Response<ChangeStatus> response) {
                        if (response.isSuccessful()){
                            AppState.getInstance().hapusOrder();
                            Toast.makeText(WaitingActivity.this,"Status anda : "+response.body().getStatus(),Toast.LENGTH_LONG).show();
                            keluar();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ChangeStatus> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void keluar() {
        startActivity(new Intent(WaitingActivity.this,Dashboard.class));
        finish();
    }

    private void refresh() {
        invoice_no = Integer.valueOf(AppState.getInstance().provideInvoice());

        Call<getStatus> getStatusCall = apiService.getStatus(invoice_no);
        getStatusCall.enqueue(new Callback<getStatus>() {
            @Override
            public void onResponse(Call<getStatus> call, Response<getStatus> response) {
                if (response.isSuccessful()){
                    hasil_status = response.body().getStatus();
                    if (hasil_status.equals("canceled")){
                        Toast.makeText(WaitingActivity.this,"Pesanan Anda Dibatalkan",Toast.LENGTH_LONG).show();
                        AppState.getInstance().hapusOrder();
                        startActivity(new Intent(WaitingActivity.this,Dashboard.class));
                        finish();
                    } else if (hasil_status.equals("ordered")){
                        AppState.getInstance().hapusOrder();
                        appState.setIsPesanan(true);
                        startActivity(new Intent(WaitingActivity.this,Order.class));
                        finish();
                    } else if (hasil_status.equals("finished")){
                        Toast.makeText(WaitingActivity.this,"Pesanan Anda telah Selesai",Toast.LENGTH_LONG).show();
                        AppState.getInstance().hapusOrder();
                        startActivity(new Intent(WaitingActivity.this,Dashboard.class));
                        finish();
                    } else {
                        Toast.makeText(WaitingActivity.this,"Sebentar ya, Pesanan Anda sedang kami cari",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getStatus> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(WaitingActivity.this,"Tidak Bisa Keluar",Toast.LENGTH_LONG).show();
    }
}
