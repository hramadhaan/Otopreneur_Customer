package com.example.customer_otopedia.Activity.Activity.Fitur.History_Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.customer_otopedia.R;

import java.util.ArrayList;

public class HistoryOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    Toolbar toolbar;
    TextView judul;

    private ArrayList<String> nama = new ArrayList<>();
    private ArrayList<String> alamat = new ArrayList<>();
    private ArrayList<String> foto = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        recyclerView = findViewById(R.id.history_recycler);

        toolbar = findViewById(R.id.history_toolbar);
        judul = toolbar.findViewById(R.id.history_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniData();

    }

    private void iniData() {
        nama.add("Cecep");
        alamat.add("Jl. Soekarno Hatta No.123");
        foto.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQuPYEZrGa4NCFzpppf3puEISro7DBQaRAtRLl3mBqTUKsdgo0e");

        nama.add("Cecep");
        alamat.add("Jl. Soekarno Hatta No.123");
        foto.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQuPYEZrGa4NCFzpppf3puEISro7DBQaRAtRLl3mBqTUKsdgo0e");

        nama.add("Cecep");
        alamat.add("Jl. Soekarno Hatta No.123");
        foto.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQuPYEZrGa4NCFzpppf3puEISro7DBQaRAtRLl3mBqTUKsdgo0e");
        
        initRecyclerView();
    
    }

    private void initRecyclerView() {
        HistoryAdapter adapter = new HistoryAdapter(this,nama,alamat,foto);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
