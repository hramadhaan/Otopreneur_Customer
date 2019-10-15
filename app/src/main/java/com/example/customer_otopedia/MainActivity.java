package com.example.customer_otopedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.customer_otopedia.Activity.Activity.Coba;
import com.example.customer_otopedia.Activity.Activity.Dashboard;
import com.example.customer_otopedia.Model.User;
import com.example.customer_otopedia.Network.ApiService;
import com.example.customer_otopedia.Utils.ApiUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

//    private static final int REQ_CODE = 1;
//    Button login;
    SignInButton daftar;
//    GoogleSignInClient googleSignInClient;
    GoogleApiClient googleApiClient;

    ApiService apiService;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depan);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        daftar = findViewById(R.id.depan_google);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });

        apiService = ApiUtils.getApiService();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            daftarAkun();

        } else {
            Toast.makeText(MainActivity.this,"Sign In Cancel",Toast.LENGTH_LONG).show();
        }
    }

    private void daftarAkun() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            Uri personPhoto = account.getPhotoUrl();

            User user = new User();
            user.setNama(personName);
            user.setEmail(personEmail);
            user.setFoto(String.valueOf(personPhoto));

//            submit(user);
        }
    }

//    private void submit(User user) {
//        Call<User> call = apiService.createUser(user);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.d("OnResponse",response.message());
//                if (response.isSuccessful()){
//                    Toast.makeText(MainActivity.this,"Membuat Akun Berhasil",Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this,"Gagal Membuat Akun",Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(MainActivity.this,"Terjadi Error",Toast.LENGTH_LONG).show();
//                signIn();
//            }
//        });
//    }
//
//    private void signIn() {
//        Intent intent = new Intent(MainActivity.this,Dashboard.class);
//        startActivity(intent);
//        finish();
//    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
