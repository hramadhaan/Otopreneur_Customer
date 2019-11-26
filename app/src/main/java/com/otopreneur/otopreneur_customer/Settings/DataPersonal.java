package com.otopreneur.otopreneur_customer.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPersonal extends AppCompatActivity {

    TextView nama,email,nomor;
    ImageView gambar;

    Toolbar toolbar;

    private AppState appState;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_personal);

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        toolbar = findViewById(R.id.datapersonal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        nama = findViewById(R.id.datapersonal_name);
        email = findViewById(R.id.datapersonal_email);
        nomor = findViewById(R.id.datapersonal_telpon);
        gambar = findViewById(R.id.datapersonal_image);

        refresh();

    }

    private void refresh() {
        int id_vendor = AppState.getInstance().getUser().getId();
        Call<Userdata> userdataCall = apiService.getSpesificCustomer(id_vendor);
        userdataCall.enqueue(new Callback<Userdata>() {
            @Override
            public void onResponse(Call<Userdata> call, Response<Userdata> response) {
                if (response.isSuccessful()){
                    nama.setText(response.body().getName());
                    email.setText(response.body().getEmail());
                    nomor.setText(response.body().getPhone());
                    Glide.with(getApplicationContext()).asBitmap().load(response.body().getAvatar()).apply(RequestOptions.circleCropTransform()).into(gambar);
                } else {
                    Toast.makeText(DataPersonal.this,response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Userdata> call, Throwable t) {
                Toast.makeText(DataPersonal.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
