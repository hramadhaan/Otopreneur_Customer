package com.otopreneur.otopreneur_customer.Model;

import com.squareup.moshi.Json;

public class AccessToken {
    @Json(name="token_type")
    String tokenType;
}
