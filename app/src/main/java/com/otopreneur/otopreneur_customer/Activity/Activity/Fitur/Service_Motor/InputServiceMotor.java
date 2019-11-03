package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Service_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.DaftarBengkel;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

public class InputServiceMotor extends AppCompatActivity {

    Toolbar toolbar;
    TextView judul;
    Button button;
    EditText tipe,catatan,lokasi;

    String vehicle,serviceMotor,tipeKendaraan,catatanKendaraan,lokasiKendaraan;

    private AppState appState;
    private ApiService apiService;

    int id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_service_motor);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        toolbar = findViewById(R.id.input_sm_toolbar);
        judul = toolbar.findViewById(R.id.input_sm_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tipe = findViewById(R.id.sm_input_nama_kendaraan);
        catatan = findViewById(R.id.sm_input_catatan);
        lokasi = findViewById(R.id.sm_input_lokasi);

        Intent getIntent = getIntent();
        vehicle = getIntent.getStringExtra("jenisKendaraan");
        serviceMotor = getIntent.getStringExtra("tipeService");

        Userdata currentUser = AppState.getInstance().getUser();
        id_customer = currentUser.getId();

        button = findViewById(R.id.sm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipeKendaraan = tipe.getText().toString().trim();
                catatanKendaraan = catatan.getText().toString().trim();
                lokasiKendaraan = lokasi.getText().toString().trim();

                Intent daftarBengkel = new Intent(InputServiceMotor.this,DaftarBengkel.class);
                daftarBengkel.putExtra("currentUser",id_customer);
                daftarBengkel.putExtra("jenisService",serviceMotor);
                daftarBengkel.putExtra("venichleService",vehicle);
                daftarBengkel.putExtra("tipeKendaraan",tipeKendaraan);
                daftarBengkel.putExtra("catatanKendaraan",catatanKendaraan);
                daftarBengkel.putExtra("lokasiKendaraan",lokasiKendaraan);
                startActivity(daftarBengkel);
            }
        });

    }
}
