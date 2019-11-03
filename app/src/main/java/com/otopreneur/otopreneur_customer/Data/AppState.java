package com.otopreneur.otopreneur_customer.Data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.otopreneur.otopreneur_customer.Model.Invoice;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.google.gson.Gson;

public class AppState {
    private static AppState instance;
    private static final String TOKEN_KEY = "access_token";
    private static final String IS_LOGED_IN = "is_logged_in";
    private static final String IS_ORDERED = "is_ordered";
    private static final String IS_PESANAN = "is_pesanan";
    private static final String CURRENT_USER = "current_user";
    private static final String CURRENT_INVOICE = "current_invoice";
    private static final String INVOICE_NO = "invoice_no";

    private AppState(){}
    private SharedPreferences pref;

    public static AppState getInstance() {
        if (instance == null) {
            synchronized (AppState.class) {
                if (instance == null) {
                    instance = new AppState();
                }
            }
        }
        return instance;
    }

    public void initSharedPrefs(Application application) {
        pref = application.getSharedPreferences("com.example.customer_otopedia.SHARED_PREF", Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        pref.edit().putString(TOKEN_KEY, token).apply();
    }

    public boolean hasToken() {
        return pref.contains(TOKEN_KEY);
    }

    public String provideToken() {
        return pref.getString(TOKEN_KEY, null);
    }

    public void removeToken() {
        pref.edit().remove(TOKEN_KEY).apply();
    }
    public void removeInvoice() {
        pref.edit().remove(INVOICE_NO).apply();
    }

    public void setInvoice(String invoice){
        pref.edit().putString(INVOICE_NO,invoice).apply();
    }

    public String provideInvoice(){
        return pref.getString(INVOICE_NO,null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGED_IN, false);
    }

    public void setIsLoggedIn(Boolean status) {
        pref.edit().putBoolean(IS_LOGED_IN, status).apply();
    }

    public boolean isOrdered(){
        return pref.getBoolean(IS_ORDERED,false);
    }

    public void setIsOrdered(Boolean ordered){
        pref.edit().putBoolean(IS_ORDERED,ordered).apply();
    }

    public void hapusOrder(){
        setIsOrdered(false);
    }

    public void hapusPesanan() {
        removeInvoice();
        setIsPesanan(false);
    }

    public boolean isPesanan(){
        return pref.getBoolean(IS_PESANAN,false);
    }

    public void setIsPesanan(Boolean pesanan){
        pref.edit().putBoolean(IS_PESANAN,pesanan).apply();
    }

    public void logout() {
        removeToken();
        setIsLoggedIn(false);
    }

    public void saveUser(Userdata userdata) {
        Gson gson = new Gson();
        pref.edit().putString(CURRENT_USER,gson.toJson(userdata)).apply();
    }

    public void saveInvoice(Invoice invoice) {
        Gson gson = new Gson();
        pref.edit().putString(CURRENT_INVOICE,gson.toJson(invoice)).apply();
    }

    public Userdata getUser() {
        Gson gson = new Gson();
        String userJson = pref.getString(CURRENT_USER, null);

        if (userJson == null) {
            return null;
        } else {
            return gson.fromJson(userJson, Userdata.class);
        }
    }

    public Invoice getInvoice() {
        Gson gson = new Gson();
        String invoiceJson = pref.getString(CURRENT_INVOICE, null);

        if (invoiceJson == null) {
            return null;
        } else {
            return gson.fromJson(invoiceJson, Invoice.class);
        }
    }
}
