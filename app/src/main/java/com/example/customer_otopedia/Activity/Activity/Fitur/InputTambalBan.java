package com.example.customer_otopedia.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.customer_otopedia.R;

public class InputTambalBan extends AppCompatActivity {

    Button button;
    Toolbar toolbar;
    TextView judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_tambal_ban);

        toolbar = findViewById(R.id.input_tb_toolbar);
        judul = toolbar.findViewById(R.id.input_tb_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.input_tb_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InputTambalBan.this, DaftarBengkel.class));
            }
        });

    }
}
