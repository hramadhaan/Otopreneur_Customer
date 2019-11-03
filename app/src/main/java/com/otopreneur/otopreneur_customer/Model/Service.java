package com.otopreneur.otopreneur_customer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vendor")
    @Expose
    private Vendor vendor;
    @SerializedName("venichle")
    @Expose
    private String venichle;
    @SerializedName("cost")
    @Expose
    private Integer cost;

    public Service(Integer id, Vendor vendor, String venichle, Integer cost) {
        this.id = id;
        this.vendor = vendor;
        this.venichle = venichle;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getVenichle() {
        return venichle;
    }

    public void setVenichle(String venichle) {
        this.venichle = venichle;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
