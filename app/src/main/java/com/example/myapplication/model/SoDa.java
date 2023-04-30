package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "soda")
public class SoDa {
    @PrimaryKey (autoGenerate = true)
    private int soDaID;
    private String nguoiBanID;

    private int soCuocThu1;
    private int soCuocThu2;
    private int soCuocThu3;

    private int dai1;
    private int dai2;
    private int dai3;
    private int dai4;

    private String date;

    private float tienCuoc;

    private String tenDai1;
    private String tenDai2;

    private int vungMien;

    public SoDa(int soDaID, String nguoiBanID, int soCuocThu1, int soCuocThu2, int soCuocThu3, int dai1, int dai2, int dai3, int dai4, String date) {
        this.soDaID = soDaID;
        this.nguoiBanID = nguoiBanID;
        this.soCuocThu1 = soCuocThu1;
        this.soCuocThu2 = soCuocThu2;
        this.soCuocThu3 = soCuocThu3;
        this.dai1 = dai1;
        this.dai2 = dai2;
        this.dai3 = dai3;
        this.dai4 = dai4;
        this.date = date;
    }

    public SoDa() {

    }


    public int getSoDaID() {
        return soDaID;
    }

    public void setSoDaID(int soDaID) {
        this.soDaID = soDaID;
    }

    public String getNguoiBanID() {
        return nguoiBanID;
    }

    public void setNguoiBanID(String nguoiBanID) {
        this.nguoiBanID = nguoiBanID;
    }

    public int getSoCuocThu1() {
        return soCuocThu1;
    }

    public void setSoCuocThu1(int soCuocThu1) {
        this.soCuocThu1 = soCuocThu1;
    }

    public int getSoCuocThu2() {
        return soCuocThu2;
    }

    public void setSoCuocThu2(int soCuocThu2) {
        this.soCuocThu2 = soCuocThu2;
    }

    public int getSoCuocThu3() {
        return soCuocThu3;
    }

    public void setSoCuocThu3(int soCuocThu3) {
        this.soCuocThu3 = soCuocThu3;
    }

    public int getDai1() {
        return dai1;
    }

    public void setDai1(int dai1) {
        this.dai1 = dai1;
    }

    public int getDai2() {
        return dai2;
    }

    public void setDai2(int dai2) {
        this.dai2 = dai2;
    }

    public int getDai3() {
        return dai3;
    }

    public void setDai3(int dai3) {
        this.dai3 = dai3;
    }

    public int getDai4() {
        return dai4;
    }

    public void setDai4(int dai4) {
        this.dai4 = dai4;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTienCuoc() {
        return tienCuoc;
    }

    public void setTienCuoc(float tienCuoc) {
        this.tienCuoc = tienCuoc;
    }

    public String getTenDai1() {
        return tenDai1;
    }

    public void setTenDai1(String tenDai1) {
        this.tenDai1 = tenDai1;
    }

    public String getTenDai2() {
        return tenDai2;
    }

    public void setTenDai2(String tenDai2) {
        this.tenDai2 = tenDai2;
    }


    public int getVungMien() {
        return vungMien;
    }

    public void setVungMien(int vungMien) {
        this.vungMien = vungMien;
    }
}
