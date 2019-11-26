package com.otopreneur.otopreneur_customer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resend {
    @SerializedName("resend")
    @Expose
    private String resend;

    public Resend(String resend) {
        this.resend = resend;
    }

    public String getResend() {
        return resend;
    }

    public void setResend(String resend) {
        this.resend = resend;
    }
}
