package com.example.customer_otopedia.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.customer_otopedia.Adapter.DaftarBengkelAdapter;
import com.example.customer_otopedia.R;

import java.util.ArrayList;

public class DaftarBengkel extends AppCompatActivity {

    RecyclerView item_bengkel;

    Toolbar toolbar;
    TextView judul;
    
    private ArrayList<String> namaBengkel = new ArrayList<>();
    private ArrayList<String> alamatBengkel = new ArrayList<>();
    private ArrayList<String> HargaBengkel = new ArrayList<>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bengkel);

        toolbar = findViewById(R.id.daftarbengkel_toolbar);
        judul = toolbar.findViewById(R.id.daftarbengkel_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item_bengkel = findViewById(R.id.daftarbengkel_recycler);
        
        initImage();
    }

    private void initImage() {
        namaBengkel.add("Hanif Ramadhan Bengkel");
        alamatBengkel.add("Jl Pam No. 4 Nusa Loka");
        HargaBengkel.add("Rp.5000-Rp.10.000");

        namaBengkel.add("Hanif Ramadhan Bengkel");
        alamatBengkel.add("Jl Pam No. 4 Nusa Loka");
        HargaBengkel.add("Rp.5000-Rp.10.000");

        namaBengkel.add("Hanif Ramadhan Bengkel");
        alamatBengkel.add("Jl Pam No. 4 Nusa Loka");
        HargaBengkel.add("Rp.5000-Rp.10.000");
        
        initRecyclerView();
    }

    private void initRecyclerView() {
        DaftarBengkelAdapter adapter = new DaftarBengkelAdapter(this,namaBengkel,alamatBengkel,HargaBengkel);
        item_bengkel.setAdapter(adapter);
        item_bengkel.setLayoutManager(new LinearLayoutManager(this));
    }
}
