package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.otopreneur.otopreneur_customer.R;

public class Tambal_Ban extends AppCompatActivity {

    CardView motor,mobil;

    Toolbar toolbar;
    TextView judul;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambal__ban);

        toolbar = findViewById(R.id.tambalban_toolbar);
        judul = toolbar.findViewById(R.id.tambalban_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        motor = findViewById(R.id.tambalban_cv_motor);
        motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipeService = "tambalban";
                String jenisKendaraan = "Motor";

                Intent motor = new Intent(Tambal_Ban.this,InputTambalBan.class);
                motor.putExtra("tipeService",tipeService);
                motor.putExtra("jenisKendaraan",jenisKendaraan);
                startActivity(motor);
            }
        });
        mobil = findViewById(R.id.tambalban_cv_mobil);
        mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipeService = "tambalban";
                String jenisKendaraan = "Mobil";

                Intent motor = new Intent(Tambal_Ban.this,InputTambalBan.class);
                motor.putExtra("tipeService",tipeService);
                motor.putExtra("jenisKendaraan",jenisKendaraan);
                startActivity(motor);
               }
        });
    }
}
