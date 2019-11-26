package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Service_Motor;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.DaftarBengkel;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.InputTambalBan;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import java.util.Arrays;

public class InputServiceMotor extends AppCompatActivity {

    Toolbar toolbar;
    TextView judul;
    Button button;
    EditText tipe,catatan;

    String vehicle,serviceMotor,tipeKendaraan,catatanKendaraan,lokasiKendaraan,hasilLatitude,hasilLongtitude;

    private AppState appState;
    private ApiService apiService;

    String apiKey = "AIzaSyAlDUmTEgkKRuPntrA5bMy4BAlPcEdgV_o";
    PlacesClient placesClient;
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

        if (!Places.isInitialized()){
            Places.initialize(getApplicationContext(),apiKey);
        }

        placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.tb_input_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteSupportFragment.setCountry("ID");
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                lokasiKendaraan = place.getName();
                hasilLongtitude = String.valueOf(latLng.longitude);
                hasilLatitude = String.valueOf(latLng.latitude);
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(InputServiceMotor.this,status.getStatusMessage(),Toast.LENGTH_LONG).show();
            }
        });

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

                Intent daftarBengkel = new Intent(InputServiceMotor.this,DaftarBengkel.class);
                daftarBengkel.putExtra("currentUser",id_customer);
                daftarBengkel.putExtra("jenisService",serviceMotor);
                daftarBengkel.putExtra("venichleService",vehicle);
                daftarBengkel.putExtra("tipeKendaraan",tipeKendaraan);
                daftarBengkel.putExtra("catatanKendaraan",catatanKendaraan);
                daftarBengkel.putExtra("lokasiKendaraan",lokasiKendaraan);
                daftarBengkel.putExtra("latitude",hasilLatitude);
                daftarBengkel.putExtra("longtitude",hasilLongtitude);
                startActivity(daftarBengkel);
            }
        });

    }
}
