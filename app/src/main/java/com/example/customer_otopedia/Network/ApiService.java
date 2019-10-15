package com.example.customer_otopedia.Network;

import com.example.customer_otopedia.Activity.Activity.Coba;
import com.example.customer_otopedia.Model.Token;
import com.example.customer_otopedia.Model.User;
import com.example.customer_otopedia.Model.UserList;
import com.example.customer_otopedia.Model.UserResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiService {
    @POST("login/")
    Call<Token> login(@Body UserList userList);

    @GET("roles")
    Call<ResponseBody> getSecret(@Header("Authorization")String authHeader);
}
