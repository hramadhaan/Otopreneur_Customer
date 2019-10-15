package com.example.customer_otopedia.Activity.Activity.Fitur.History_Order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.customer_otopedia.R;

public class DetailHistory extends AppCompatActivity {

    TextView nama,alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        nama = findViewById(R.id.orderhistory_customer);
        alamat = findViewById(R.id.orderhistory_lokasi);

        DataHistory();
//        setHistory();
    }

    private void DataHistory() {
        if (getIntent().hasExtra("nama")&&getIntent().hasExtra("alamat")){
            String namaUrl = getIntent().getStringExtra("nama");
            String alamatUrl = getIntent().getStringExtra("alamat");
//            setHistory();
            setHistory(namaUrl,alamatUrl);
        }
    }

    private void setHistory(String namaUrl, String alamatUrl) {
        nama.setText(namaUrl);
        alamat.setText(alamatUrl);
    }

}
