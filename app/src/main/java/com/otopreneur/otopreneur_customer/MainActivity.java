package com.otopreneur.otopreneur_customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.Dashboard;
import com.otopreneur.otopreneur_customer.Activity.Activity.InputNomorTelp;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Roles;
import com.otopreneur.otopreneur_customer.Model.Token;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karan.churi.PermissionManager.PermissionManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextView daftar;
    Button signIn;

    ProgressBar progressBar;

    PermissionManager permissionManager;

    int RC_DAFTAR = 0;
    int RC_SIGN_IN = 1;
    GoogleSignInClient googleSignInClient;
    private AppState appState;
    private ApiService apiService;
    String hasilToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depan);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        progressBar = findViewById(R.id.depan_progress);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        if (AppState.getInstance().isLoggedIn()){
            startActivity(new Intent(MainActivity.this,Dashboard.class));
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);

        signIn = findViewById(R.id.depan_login);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        daftar = findViewById(R.id.depan_google);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               daftarAkun();
            }
        });

    }

    private void login() {
        signIn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Intent login = googleSignInClient.getSignInIntent();
        startActivityForResult(login,RC_SIGN_IN);
    }

    private void daftarAkun() {
        Intent daftar = googleSignInClient.getSignInIntent();
        startActivityForResult(daftar,RC_DAFTAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Masuk ACCT","resultCode");

        if (requestCode==RC_DAFTAR) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleDaftarResult(task);
        } else if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task2 = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignIn(task2);
        }
    }

    private void handleSignIn(Task<GoogleSignInAccount> task2) {
        try {
            GoogleSignInAccount accout = task2.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

            Log.d("Masuk TRY","Tidak");

            if (acct!=null){
                String personEmail = acct.getEmail();
//                Uri personPhoto = acct.getPhotoUrl();

                Log.d("Masuk ACCT","Tidak");

                Call<Token> call = apiService.login(personEmail,"password");
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.d("Masuk Response","Tidak");

                        if (response.isSuccessful()){
                            hasilToken = response.body().getToken();
                            if (hasilToken.equals("null")){
                                Toast.makeText(MainActivity.this,"Account Anda Belum Terdaftar",Toast.LENGTH_LONG).show();
                            } else {
                                signIn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                Log.d("Token : ",hasilToken);
                                appState.setToken(hasilToken);
                                appState.setIsLoggedIn(true);
                                getUser();
                                signOut();
                            }
//                            Toast.makeText(InputNomorTelp.this,hasilToken,Toast.LENGTH_LONG).show();
                        } else {
                            signIn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this,"Gagal mendapatkan token",Toast.LENGTH_LONG).show();
                            Log.d("Gagal Token ",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        signIn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this,"Failure "+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            }

        } catch (ApiException e){

        }
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Keluar","Revoke Account");
            }
        });
    }

    private void handleDaftarResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount accout = task.getResult(ApiException.class);
            startActivity(new Intent(MainActivity.this,InputNomorTelp.class));
            finish();
        } catch (ApiException e){

        }
    }

    private void getUser()
    {
        apiService.getUser().enqueue(new Callback<Roles>() {
            @Override
            public void onResponse(@NotNull Call<Roles> call, @NotNull Response<Roles> response) {
                if (response.isSuccessful())
                {
                    appState.saveUser(response.body().getUserdata());
                    startActivity(new Intent(MainActivity.this,Dashboard.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Roles> call, Throwable t) {

            }
        });
    }

    //    private void infoAccount() {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account!=null) {
//            String personName = account.getDisplayName();
//            String personEmail = account.getEmail();
//            Uri personPhoto = account.getPhotoUrl();
//
//            User user = new User();
//            user.setNama(personName);
//            user.setEmail(personEmail);
//            user.setFoto(String.valueOf(personPhoto));
//
//            signIn();
//        }
//    }


}
