package com.otopreneur.otopreneur_customer.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.otopreneur.otopreneur_customer.Data.AppState;
import com.otopreneur.otopreneur_customer.Model.Resend;
import com.otopreneur.otopreneur_customer.Model.Roles;
import com.otopreneur.otopreneur_customer.Model.Status;
import com.otopreneur.otopreneur_customer.Model.Token;
import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.R;
import com.otopreneur.otopreneur_customer.Utils.ApiUtils;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    ApiService apiService;
    private AppState appState;

    Toolbar toolbar;
    ProgressBar progressBar;
    TextView judul,resend,ubahNomor;
    OtpTextView otpTextView;
    String email;
    Dialog dialogNomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        progressBar = findViewById(R.id.otp_progress_bar);

        appState = AppState.getInstance();
        apiService = ApiUtils.getApiService();

        toolbar = findViewById(R.id.otp_toolbar);
        judul = toolbar.findViewById(R.id.otp_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        dialogNomor = new Dialog(this);

        ubahNomor = findViewById(R.id.otp_ubahnomor);
        ubahNomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNomor();
            }
        });

        resend = findViewById(R.id.otp_resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Resend> resendCall = apiService.resendOTP(email);
                resendCall.enqueue(new Callback<Resend>() {
                    @Override
                    public void onResponse(Call<Resend> call, Response<Resend> response) {
                        if (response.isSuccessful()){
                            if(response.body().getResend().equals("sucess")){
                                new CountDownTimer(55000, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        resend.setEnabled(false);
                                        resend.setText(millisUntilFinished/1000+" detik lagi");
//                        resend.setVisibility(View.INVISIBLE);
//                        resend.setText("seconds remaining: " + millisUntilFinished / 1000);
                                    }

                                    public void onFinish() {
                                        resend.setEnabled(true);
                                        resend.setText("Resend");
                                    }
                                }.start();
                            } else {
                                Toast.makeText(OTPActivity.this,"Gagal Mengirimkan OTP",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(OTPActivity.this,response.message(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Resend> call, Throwable t) {
                        Toast.makeText(OTPActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        otpTextView = findViewById(R.id.otp_otpview);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                progressBar.setVisibility(View.VISIBLE);

                Call<Token> call = apiService.verifyOTP(email,otp);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            String token = response.body().getToken();
                            Log.d("Token",token);
                            appState.setToken(token);
                            appState.setIsLoggedIn(true);
                            useruser();
                            otpTextView.showSuccess();
                        } else {
                            otpTextView.showError();
                            Toast.makeText(OTPActivity.this,response.message(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        otpTextView.showError();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(OTPActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

    private void dialogNomor() {
        TextView done;
        final EditText nomor;
        dialogNomor.setContentView(R.layout.dialog_gantinomor);
        done = dialogNomor.findViewById(R.id.gantinomor_submit);
        nomor = dialogNomor.findViewById(R.id.gantinomor_input);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Status> statusCall = apiService.editPhoneNumber(email, nomor.getText().toString());
                statusCall.enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus().equals("success")){
                                Toast.makeText(OTPActivity.this,"Nomor Anda Berhasil di Ganti",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(OTPActivity.this,"Nomor Anda Gagal Diganti",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(OTPActivity.this,response.message(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(OTPActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                dialogNomor.dismiss();
            }
        });
        dialogNomor.show();

    }

    private void useruser() {
        apiService.getUser().enqueue(new Callback<Roles>() {
            @Override
            public void onResponse(Call<Roles> call, Response<Roles> response) {
                if (response.isSuccessful()){
                    appState.saveUser(response.body().getUserdata());
                    startActivity(new Intent(OTPActivity.this,Dashboard.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Roles> call, Throwable t) {

            }
        });
    }
}
