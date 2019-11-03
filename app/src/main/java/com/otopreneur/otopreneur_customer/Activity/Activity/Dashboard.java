package com.otopreneur.otopreneur_customer.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.History_Order.HistoryOrderActivity;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Order;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Service_Motor.MenuServiceMotor;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Settings;
import com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Tambal_Ban;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.R;

public class Dashboard extends AppCompatActivity {

    CardView tambalban,servicemotor, settings, history;

    Toolbar toolbar;
    TextView judul,tambalBan;
    TextView nama;
    private AppState appState;

    boolean doubleBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nama= findViewById(R.id.dashboard_nama);
        Userdata currentUser = AppState.getInstance().getUser();
        nama.setText(currentUser.getName());

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        if (AppState.getInstance().isOrdered()){
            startActivity(new Intent(Dashboard.this,WaitingActivity.class));
            finish();
        }

        if (AppState.getInstance().isPesanan()){
            startActivity(new Intent(Dashboard.this, Order.class));
            finish();
        }

        tambalban = findViewById(R.id.dashboard_cv_tambalban);
        servicemotor = findViewById(R.id.dashboard_cv_service);
        settings = findViewById(R.id.dashboard_cv_settings);
        tambalBan = findViewById(R.id.dashboard_tambalban);

        history = findViewById(R.id.dashboard_cv_history);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, HistoryOrderActivity.class));
            }
        });

        toolbar = findViewById(R.id.dashboard_toolbar);
        judul = toolbar.findViewById(R.id.dashboard_judul);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setSupportActionBar(toolbar);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Settings.class));
            finish();
            }
        });

        tambalban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambalban = new Intent(Dashboard.this,Tambal_Ban.class);
                startActivity(tambalban);
            }
        });

        servicemotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, MenuServiceMotor.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBack){
            super.onBackPressed();
            return;
        }
        this.doubleBack = true;
        Toast.makeText(this,"Please Click Back Again to Exit",Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack=false;
            }
        },2000);
    }
}
