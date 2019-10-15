package com.example.customer_otopedia.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResult {
    @SerializedName("user")
    @Expose
    private List<User> users = null;

    public List<User> getUsers(){
        return users;
    }
}
