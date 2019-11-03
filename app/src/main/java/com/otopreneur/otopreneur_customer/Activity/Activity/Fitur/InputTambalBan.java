package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur;

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

import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

public class InputTambalBan extends AppCompatActivity {

    Button button;
    Toolbar toolbar;
    TextView judul;

    EditText tipe,catatan,lokasi;

    String vehicle,tambalBan,tipeKendaraan,catatanKendaraan,lokasiKendaraan;

    private AppState appState;
    private ApiService apiService;

    int id_customer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_tambal_ban);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.input_tb_toolbar);
        judul = toolbar.findViewById(R.id.input_tb_judul);
        tipe = findViewById(R.id.tb_input_nama_kendaraan);
        catatan = findViewById(R.id.tb_input_catatan);
        lokasi = findViewById(R.id.tb_input_lokasi);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        Intent getIntent = getIntent();
        vehicle = getIntent.getStringExtra("jenisKendaraan");
        tambalBan = getIntent.getStringExtra("tipeService");

        Userdata currentUser = AppState.getInstance().getUser();
        id_customer = currentUser.getId();

//        Toast.makeText(InputTambalBan.this,vehicle,Toast.LENGTH_LONG).show();

        button = findViewById(R.id.input_tb_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipeKendaraan = tipe.getText().toString().trim();
                catatanKendaraan = catatan.getText().toString().trim();
                lokasiKendaraan = lokasi.getText().toString().trim();

                Intent daftarBengkel = new Intent(InputTambalBan.this,DaftarBengkel.class);
                daftarBengkel.putExtra("currentUser",id_customer);
                daftarBengkel.putExtra("jenisService",tambalBan);
                daftarBengkel.putExtra("venichleService",vehicle);
                daftarBengkel.putExtra("tipeKendaraan",tipeKendaraan);
                daftarBengkel.putExtra("catatanKendaraan",catatanKendaraan);
                daftarBengkel.putExtra("lokasiKendaraan",lokasiKendaraan);
                startActivity(daftarBengkel);
            }
        });
    }
}
