package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.myapplication.utils.GiaiThuongTypeConverter;


@Entity(primaryKeys = {"maSoDai", "date"})
public class Dai {

    @NonNull
    private String date;

    @NonNull
    private String maSoDai;

    private GiaiThuong giaiThuong;

    public Dai() {
    }

    public Dai(int daiID, String date, String maSoDai, GiaiThuong giaiThuong) {
        this.date = date;
        this.maSoDai = maSoDai;
        this.giaiThuong = giaiThuong;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaSoDai() {
        return maSoDai;
    }

    public void setMaSoDai(String maSoDai) {
        this.maSoDai = maSoDai;
    }

    public GiaiThuong getGiaiThuong() {
        return giaiThuong;
    }

    public void setGiaiThuong(GiaiThuong giaiThuong) {
        this.giaiThuong = giaiThuong;
    }
}
