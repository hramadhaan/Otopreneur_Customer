package com.example.customer_otopedia.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customer_otopedia.R;

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

        motor = findViewById(R.id.tambalban_cv_motor);
        motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tambal_Ban.this,InputTambalBan.class));
            }
        });
        mobil = findViewById(R.id.tambalban_cv_mobil);
        mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tambal_Ban.this,InputTambalBan.class));
            }
        });
    }
}
