package com.example.customer_otopedia.Utils;

import com.example.customer_otopedia.Network.ApiService;
import com.example.customer_otopedia.Services.RetrofitClient;

public class ApiUtils {
    private ApiUtils(){}

    public static final String API_URL = "http://api-otopreneur.sobatteknologi.com/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(API_URL).create(ApiService.class);
    }
}
