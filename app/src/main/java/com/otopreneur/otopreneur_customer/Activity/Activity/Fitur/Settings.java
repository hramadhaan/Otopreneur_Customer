package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.otopreneur.otopreneur_customer.Activity.Activity.Dashboard;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.MainActivity;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.otopreneur.otopreneur_customer.Settings.DataPersonal;
import com.otopreneur.otopreneur_customer.Settings.GantiNomor;


public class Settings extends AppCompatActivity {

    ImageView avatar;
    TextView name, logout,data,nomor;

    GoogleSignInClient mGoogleSignInClient;

    Toolbar toolbar;
    TextView judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatar = findViewById(R.id.settings_image);
        name = findViewById(R.id.settings_nama);
        logout = findViewById(R.id.settings_logout);

        data = findViewById(R.id.settings_data);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, DataPersonal.class));
            }
        });
        nomor = findViewById(R.id.settings_ganti);
        nomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, GantiNomor.class));
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
//                finish();
            }
        });

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Userdata currentUser = AppState.getInstance().getUser();
        name.setText(currentUser.getName());
        Glide.with(this).load(currentUser.getAvatar()).apply(RequestOptions.circleCropTransform()).into(avatar);
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = currentUser.getName();
//            Uri personPhoto = acct.getPhotoUrl();
//
//            name.setText(personId);
//
//            Glide.with(this).load(String.valueOf(personPhoto)).apply(RequestOptions.circleCropTransform()).into(avatar);
//        }
    }

    private void keluar() {
       if (AppState.getInstance().isLoggedIn()){
           AppState.getInstance().logout();
           startActivity(new Intent(Settings.this,MainActivity.class));
           finish();
       }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}