package com.otopreneur.otopreneur_customer.Utils;

import com.otopreneur.otopreneur_customer.Network.ApiService;
import com.otopreneur.otopreneur_customer.Services.RetrofitClient;

public class ApiUtils {
    private ApiUtils(){}

    public static final String API_URL = "http://api-otopreneur.sobatteknologi.com/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(API_URL).create(ApiService.class);
    }
}
