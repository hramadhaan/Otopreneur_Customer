package com.otopreneur.otopreneur_customer.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.MainActivity;
import com.otopreneur.otopreneur_customer.Model.Roles;
import com.otopreneur.otopreneur_customer.Model.Token;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Services.RetrofitClient;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputNomorTelp extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;

    EditText name,email,phone;
    ImageView imageView;

    Button daftar;

    Toolbar toolbar;
    TextView judul;

    Uri personPhoto;

    ProgressBar progressBar;

    RetrofitClient retrofitClient;

    ApiService apiService;
    String hasilToken,hasilNama,hasilPhone,hasilFoto,hasilEmail;
    String personPhotos;
    private AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_nomor_telp);
        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        progressBar = findViewById(R.id.inp_progress);

        toolbar = findViewById(R.id.inp_toolbar);
        judul = toolbar.findViewById(R.id.inp_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        name = findViewById(R.id.inp_nama);
        email = findViewById(R.id.inp_email);
        phone = findViewById(R.id.inp_phone);
        imageView = findViewById(R.id.inp_foto);
        daftar = findViewById(R.id.inp_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(InputNomorTelp.this);
        if (acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(personPhoto).into(imageView);
        }

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftar.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                hasilNama = name.getText().toString();
                hasilEmail = email.getText().toString();
                hasilFoto = String.valueOf(personPhoto);
                hasilPhone = phone.getText().toString();

                Call<Token> call = apiService.createUser(hasilNama,hasilFoto
                        ,hasilEmail,hasilPhone);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful()){
                            hasilToken = response.body().getToken();
                            if (hasilToken.equals("null")){
                                Toast.makeText(InputNomorTelp.this,"Email Exist or Phone Exist",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(InputNomorTelp.this,"Berhasil Membuat Akun",Toast.LENGTH_LONG).show();
                                daftar.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                daftar.setEnabled(false);
                                appState.setToken(hasilToken);
                                appState.setIsLoggedIn(true);
                                Log.d("Token : ",hasilToken);
                                getuser();
                            }
                        } else {
                            Toast.makeText(InputNomorTelp.this,"Gagal mendapatkan token",Toast.LENGTH_LONG).show();
                            Log.d("Gagal Token ",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(InputNomorTelp.this,"Failure "+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void getuser() {
        apiService.getUser().enqueue(new Callback<Roles>() {
            @Override
            public void onResponse(Call<Roles> call, Response<Roles> response) {
                if (response.isSuccessful()){
                    appState.saveUser(response.body().getUserdata());
                    startActivity(new Intent(InputNomorTelp.this,Dashboard.class));
                    finish();
                } else {
                    Toast.makeText(InputNomorTelp.this,response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Roles> call, Throwable t) {
                Toast.makeText(InputNomorTelp.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InputNomorTelp.this, MainActivity.class));
        finish();
        signOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signOut();
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(InputNomorTelp.this,"Gagal Membuat Akun",Toast.LENGTH_LONG).show();
            }
        });
    }



}
