package com.otopreneur.otopreneur_customer.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;


import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Coba extends AppCompatActivity {

    private static String hasilToken;


    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .header("Authorization", "Bearer " + hasilToken)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    Retrofit.Builder builder = new Retrofit.Builder()
            .client(client)
            .baseUrl("http://api-otopreneur.sobatteknologi.com/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiService apiService = retrofit.create(ApiService.class);

    Button token,secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba);

        token = findViewById(R.id.coba_token);
        secret = findViewById(R.id.coba_secret);

        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doToken();
            }
        });

//        secret.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doSecret();
//            }
//        });

    }

    private void doToken() {

    }

//    private void doSecret() {
//        Call<ResponseBody> call = apiService.getSecret(hasilToken);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    try {
//                        Toast.makeText(Coba.this,response.body().string(),Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(Coba.this,"Login incorrect",Toast.LENGTH_LONG).show();
//                    Log.d("Token",response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(Coba.this,"Login failure",Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
