package com.example.customer_otopedia.Activity.Activity.Fitur.Service_Motor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.customer_otopedia.Activity.Activity.Fitur.DaftarBengkel;
import com.example.customer_otopedia.R;

public class InputServiceMotor extends AppCompatActivity {

    Toolbar toolbar;
    TextView judul;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_service_motor);

        toolbar = findViewById(R.id.input_sm_toolbar);
        judul = toolbar.findViewById(R.id.input_sm_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.sm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InputServiceMotor.this, DaftarBengkel.class));
            }
        });

    }
}
