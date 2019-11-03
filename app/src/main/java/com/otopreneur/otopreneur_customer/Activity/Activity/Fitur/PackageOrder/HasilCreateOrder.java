package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.PackageOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.WaitingActivity;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Invoice;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasilCreateOrder extends AppCompatActivity {

    EditText id_service,nama_vendor,harga,nama_pemesan,tipe_kendaraan,catatan,lokasi_kendaraan;
    Button submit;
    String hasil_vendor,hasil_harga,hasil_nama_pemesanan,hasil_tipe_kendaraan,hasil_catatan,hasil_lokasi_kendaraan,invoice,status;
    int hasil_service,hasil_id_customer;

    Toolbar toolbar;
    TextView judul;

    private AppState appState;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_create_order);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.hco_toolbar);
        judul = toolbar.findViewById(R.id.hco_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        nama_vendor = findViewById(R.id.hco_nama_vendor);
        harga = findViewById(R.id.hco_harga_vendor);
        nama_pemesan = findViewById(R.id.hco_nama_customer);
        tipe_kendaraan = findViewById(R.id.hco_tipekendaraan);
        catatan = findViewById(R.id.hco_catatan);
        lokasi_kendaraan = findViewById(R.id.hco_lokasi_kendaraan);
        submit = findViewById(R.id.hco_button);

//        GET INTENT
        Intent getIntent = getIntent();
        hasil_service = getIntent.getIntExtra("id_service",0);
        hasil_harga = getIntent.getStringExtra("harga");
        hasil_tipe_kendaraan = getIntent.getStringExtra("tipeKendaraana");
        hasil_catatan = getIntent.getStringExtra("catatanKendaraana");
        hasil_lokasi_kendaraan = getIntent.getStringExtra("lokasiKendaraana");
        hasil_vendor = getIntent.getStringExtra("namaVendor");

        Userdata currentUser = AppState.getInstance().getUser();
        hasil_id_customer = currentUser.getId();
        hasil_nama_pemesanan = currentUser.getName();

//        SET EDITTEXT
        nama_vendor.setText(hasil_vendor);
        harga.setText("Rp. "+hasil_harga);
        nama_pemesan.setText(hasil_nama_pemesanan);
        tipe_kendaraan.setText(hasil_tipe_kendaraan);
        catatan.setText(hasil_catatan);
        lokasi_kendaraan.setText(hasil_lokasi_kendaraan);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });

        Log.d("TESTING ",hasil_lokasi_kendaraan);

    }

    private void kirim() {
        Call<Invoice> call = apiService.createOrder(hasil_service,hasil_id_customer,hasil_catatan,hasil_tipe_kendaraan,hasil_lokasi_kendaraan);
        call.enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()){
                    invoice = String.valueOf(response.body().getInvoiceNo());
                    status = response.body().getStatus();
                    appState.setInvoice(invoice);
                    appState.setIsOrdered(true);
                    Intent intent = new Intent(HasilCreateOrder.this,WaitingActivity.class);
                    intent.putExtra("invoice",invoice);
                    intent.putExtra("status",status);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(HasilCreateOrder.this,response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(HasilCreateOrder.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
