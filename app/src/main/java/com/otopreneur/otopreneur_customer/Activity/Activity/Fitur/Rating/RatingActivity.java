package com.otopreneur.otopreneur_customer.Activity.Activity.Fitur.Rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.otopreneur.otopreneur_customer.Activity.Activity.Dashboard;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Status;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {

    AppCompatRatingBar ratingBar;

    Button rate;
    String invoice;

    Toolbar toolbar;
    TextView judul;

    private AppState appState;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.rating_toolbar);
        judul = toolbar.findViewById(R.id.rating_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        ratingBar = findViewById(R.id.simpleRatingBar);

        Intent intent = getIntent();
        invoice = intent.getStringExtra("invoice");

        judul.setText("Order : "+invoice);

        rate = findViewById(R.id.rating_button);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ratinghasil = Math.round(ratingBar.getRating());
                int invoiceNo = Integer.parseInt(invoice);
                Log.d("RatingActivity = ","Rating = "+ratinghasil+"\n"+invoice);
                Call<Status> statusCall = apiService.setRating(invoiceNo,ratinghasil);
                statusCall.enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus().equals("success")){
                                startActivity(new Intent(RatingActivity.this,Dashboard.class));
                                finish();
                            } else {
                                Toast.makeText(RatingActivity.this,"Gagal Mengirim Response",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RatingActivity.this,response.message(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(RatingActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
