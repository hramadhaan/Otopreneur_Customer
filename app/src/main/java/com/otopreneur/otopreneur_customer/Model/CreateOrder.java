package com.otopreneur.otopreneur_customer.Model;

public class CreateOrder {
    String tipeService;
    String kendaraanService;

    public CreateOrder(String tipeService, String kendaraanService) {
        this.tipeService = tipeService;
        this.kendaraanService = kendaraanService;
    }

    public CreateOrder() {
    }

    public String getTipeService() {
        return tipeService;
    }

    public void setTipeService(String tipeService) {
        this.tipeService = tipeService;
    }

    public String getKendaraanService() {
        return kendaraanService;
    }

    public void setKendaraanService(String kendaraanService) {
        this.kendaraanService = kendaraanService;
    }
}
