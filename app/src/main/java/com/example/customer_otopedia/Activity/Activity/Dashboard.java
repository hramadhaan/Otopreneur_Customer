package com.example.customer_otopedia.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customer_otopedia.Activity.Activity.Fitur.History_Order.HistoryOrderActivity;
import com.example.customer_otopedia.Activity.Activity.Fitur.Service_Motor.MenuServiceMotor;
import com.example.customer_otopedia.Activity.Activity.Fitur.Settings;
import com.example.customer_otopedia.Activity.Activity.Fitur.Tambal_Ban;
import com.example.customer_otopedia.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Dashboard extends AppCompatActivity {

    CardView tambalban,servicemotor, settings, history;
    GoogleSignInClient mGoogleSignInClient;

    Toolbar toolbar;
    TextView judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tambalban = findViewById(R.id.dashboard_cv_tambalban);
        servicemotor = findViewById(R.id.dashboard_cv_service);
        settings = findViewById(R.id.dashboard_cv_settings);

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Settings.class));
            }
        });

        tambalban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Tambal_Ban.class));
            }
        });

        servicemotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, MenuServiceMotor.class));
            }
        });
    }
}
