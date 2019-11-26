package com.otopreneur.otopreneur_customer.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.otopreneur.otopreneur_customer.Activity.Activity.Dashboard;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Status;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiNomor extends AppCompatActivity {

    EditText gantinomor;
    Button submit;
    String email;
    ImageView gambar;

    private AppState appState;
    private ApiService apiService;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_nomor);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color));
        }

        gambar = findViewById(R.id.ubahnomor_image);
        Glide.with(getApplicationContext()).asBitmap().load(AppState.getInstance().getUser().getAvatar()).apply(RequestOptions.circleCropTransform()).into(gambar);

        toolbar = findViewById(R.id.ubahnomor_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gantinomor = findViewById(R.id.ubahnomor_nomor);
        submit=findViewById(R.id.ubahnomor_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = AppState.getInstance().getUser().getEmail();
                Call<Status> statusCall = apiService.editPhoneNumber(email,gantinomor.getText().toString());
                statusCall.enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus().equals("success")){
                                Toast.makeText(GantiNomor.this,"Berhasil Mengganti Nomor",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(GantiNomor.this, Dashboard.class));
                                finish();
                            } else {
                                Toast.makeText(GantiNomor.this,"Gagal Mengganti Nomor Telpon",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(GantiNomor.this,response.message(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(GantiNomor.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
