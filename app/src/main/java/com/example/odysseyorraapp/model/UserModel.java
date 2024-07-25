package com.example.nalsbasreng.model;

public class UserModel {
    String nama;
    String email;
    String sandi;

    public UserModel() {
    }

    public UserModel(String nama, String email, String sandi) {
        this.nama = nama;
        this.email = email;
        this.sandi = sandi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSandi() {
        return sandi;
    }

    public void setSandi(String sandi) {
        this.sandi = sandi;
    }
}
