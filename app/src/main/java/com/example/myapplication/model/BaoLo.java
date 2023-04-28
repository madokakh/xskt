package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "baolo")
public class BaoLo {
    @PrimaryKey(autoGenerate = true)
    private int baoLoID;

    private String nguoiBanID;


    private String soCuoc;
    private String tienCuoc;

    private int dai1;
    private int dai2;
    private int dai3;
    private int dai4;

    private String tenDai1;
    private String tenDai2;

    private String date;

    public BaoLo(int baoLoID, String nguoiBanID, String soCuoc, int dai1, int dai2, int dai3, int dai4) {
        this.baoLoID = baoLoID;
        this.nguoiBanID = nguoiBanID;
        this.soCuoc = soCuoc;
        this.dai1 = dai1;
        this.dai2 = dai2;
        this.dai3 = dai3;
        this.dai4 = dai4;
    }

    public BaoLo() {
    }

    public int getBaoLoID() {
        return baoLoID;
    }

    public void setBaoLoID(int baoLoID) {
        this.baoLoID = baoLoID;
    }

    public String getNguoiBanID() {
        return nguoiBanID;
    }

    public void setNguoiBanID(String nguoiBanID) {
        this.nguoiBanID = nguoiBanID;
    }

    public String getSoCuoc() {
        return soCuoc;
    }

    public void setSoCuoc(String soCuoc) {
        this.soCuoc = soCuoc;
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

    public String getTienCuoc() {
        return tienCuoc;
    }

    public void setTienCuoc(String tienCuoc) {
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
}
