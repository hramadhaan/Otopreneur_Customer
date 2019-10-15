package com.example.customer_otopedia.Activity.Activity.Fitur.Service_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.customer_otopedia.R;

public class MenuServiceMotor extends AppCompatActivity {

    Toolbar toolbar;
    TextView judul;

    CardView gantioli,tuneup,kampasrem,other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_service_motor);

        toolbar = findViewById(R.id.servicemotor_toolbar);
        judul = toolbar.findViewById(R.id.servicemotor_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gantioli = findViewById(R.id.servicemotor_cv_gantioli);
        gantioli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input();
            }
        });
        tuneup = findViewById(R.id.servicemotor_cv_tuneup);
        tuneup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input();

            }
        });
        kampasrem = findViewById(R.id.servicemotor_cv_kampasrem);
        kampasrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input();

            }
        });
        other = findViewById(R.id.servicemotor_cv_other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input();

            }
        });

    }

    private void input() {
        Intent intent = new Intent(MenuServiceMotor.this,InputServiceMotor.class);
        startActivity(intent);
    }
}
