package com.example.customer_otopedia.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customer_otopedia.Activity.Activity.Fitur.Rating.RatingActivity;
import com.example.customer_otopedia.R;

public class Order extends AppCompatActivity {

    Dialog dialog;
    TextView finish;

    Toolbar toolbar;
    TextView judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = findViewById(R.id.order_toolbar);
        judul = toolbar.findViewById(R.id.order_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new Dialog(this);
        finish = findViewById(R.id.order_finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Order.this, RatingActivity.class));
            }
        });

        dialogSekarang();
    }

    private void dialogSekarang() {
        TextView done;
        dialog.setContentView(R.layout.dialog_order);
        done = dialog.findViewById(R.id.do_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
