package com.otopreneur.otopreneur_customer.Network;

import com.otopreneur.otopreneur_customer.Model.ChangeStatus;
import com.otopreneur.otopreneur_customer.Model.History;
import com.otopreneur.otopreneur_customer.Model.Invoice;
import com.otopreneur.otopreneur_customer.Model.Resend;
import com.otopreneur.otopreneur_customer.Model.Roles;
import com.otopreneur.otopreneur_customer.Model.Service;
import com.otopreneur.otopreneur_customer.Model.Status;
import com.otopreneur.otopreneur_customer.Model.Token;
import com.otopreneur.otopreneur_customer.Model.Userdata;
import com.otopreneur.otopreneur_customer.Model.Variant;
import com.otopreneur.otopreneur_customer.Model.getStatus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {
    @FormUrlEncoded
    @POST("login/")
    Call<Token> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("getAPI/createUser/")
    Call<Status> createUser(
            @Field("nama") String nama,
            @Field("foto") String foto,
            @Field("email") String email,
            @Field("phone") String phone
    );

    @GET("roles")
    Call<Roles> getUser();

    @FormUrlEncoded
    @POST("getAPI/createOrder")
    Call<Invoice> createOrder(
            @Field("id_service") int id_service,
            @Field("id_customer") int id_customer,
            @Field("note") String note,
            @Field("venichleSeries") String venichleSeries,
            @Field("location") String location,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @GET("getAPI/getService/{service_code}/{venichle}/")
    Call<ArrayList<Service>> getService(@Path("service_code") String service_code, @Path("venichle") String venichle);

    @GET("getAPI/getSpecificOrder/{invoice_no}")
    Call<getStatus> getStatus(@Path("invoice_no") int invoice_no);

    @GET("getAPI/changeStatus/{invoice_no}/{status}")
    Call<ChangeStatus> changeStatus(
            @Path("invoice_no") int invoice_no,
            @Path("status") String status
    );

    @GET("getAPI/getUsersCustomerLogCF/{id_customer}")
    Call<ArrayList<History>> getHistory(@Path("id_customer")int id_customer);

    @GET("getAPI/getServiceVariant")
    Call<ArrayList<Variant>> getVariant();

    @GET("getAPI/verifyOTP/{email}/{otp}")
    Call<Token> verifyOTP(@Path("email") String email,@Path("otp") String otp);

    @GET("getAPI/editPhoneNumber/{email}/{telp}")
    Call<Status> editPhoneNumber(
            @Path("email") String email,
            @Path("telp") String telp
    );

    @GET("getAPI/resendOTP/{email}")
    Call<Resend> resendOTP(
            @Path("email") String email
    );

    @GET("getAPI/getSpecificCustomer/{id}")
    Call<Userdata> getSpesificCustomer(
            @Path("id") int id
    );

    @GET("getAPI/addRating/{invoice}/{rating}")
    Call<Status> setRating(
            @Path("invoice") int invoice,
            @Path("rating") int rating
    );

}
