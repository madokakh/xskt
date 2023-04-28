package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "nguoiban")
public class NguoiBan implements Serializable {
    @PrimaryKey
    @NonNull
    private String nguoiBanID;

    private String tenNguoiBan;

    private int heSoDauDuoi;
    private int heSoDa;
    private int heSoBaoLo;

    public NguoiBan(String nguoiBanID, String tenNguoiBan, int heSoDauDuoi, int heSoDa, int heSoBaoLo) {
        this.nguoiBanID = nguoiBanID;
        this.tenNguoiBan = tenNguoiBan;
        this.heSoDauDuoi = heSoDauDuoi;
        this.heSoDa = heSoDa;
        this.heSoBaoLo = heSoBaoLo;
    }

    public NguoiBan() {
    }


    public String getNguoiBanID() {
        return nguoiBanID;
    }

    public void setNguoiBanID(String nguoiBanID) {
        this.nguoiBanID = nguoiBanID;
    }

    public String getTenNguoiBan() {
        return tenNguoiBan;
    }

    public void setTenNguoiBan(String tenNguoiBan) {
        this.tenNguoiBan = tenNguoiBan;
    }

    public int getHeSoDauDuoi() {
        return heSoDauDuoi;
    }

    public void setHeSoDauDuoi(int heSoDauDuoi) {
        this.heSoDauDuoi = heSoDauDuoi;
    }

    public int getHeSoDa() {
        return heSoDa;
    }

    public void setHeSoDa(int heSoDa) {
        this.heSoDa = heSoDa;
    }

    public int getHeSoBaoLo() {
        return heSoBaoLo;
    }

    public void setHeSoBaoLo(int heSoBaoLo) {
        this.heSoBaoLo = heSoBaoLo;
    }
}
