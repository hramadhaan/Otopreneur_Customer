package com.otopreneur.otopreneur_customer.Model;

public class UserList {
    private String email;
    private String password;

    public UserList() {
    }

    public UserList(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
