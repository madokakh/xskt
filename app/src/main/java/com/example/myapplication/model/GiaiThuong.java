package com.example.myapplication.model;

import androidx.room.TypeConverter;

import com.example.myapplication.utils.GiaiThuongTypeConverter;

import java.util.List;


public class GiaiThuong {

    private String giaiDacBiet;
    private String giaiNhat;
    private String giaiNhi;
    private List<String> giaiBa;
    private List<String> giaiTu;
    private String giaiNam;
    private List<String> giaiSau;
    private String giaiBay;
    private String giaiTam;


    public GiaiThuong(String giaiDacBiet, String giaiNhat, String giaiNhi, List<String> giaiBa, List<String> giaiTu, String giaiNam, List<String> giaiSau, String giaiBay, String giaiTam) {
        this.giaiDacBiet = giaiDacBiet;
        this.giaiNhat = giaiNhat;
        this.giaiNhi = giaiNhi;
        this.giaiBa = giaiBa;
        this.giaiTu = giaiTu;
        this.giaiNam = giaiNam;
        this.giaiSau = giaiSau;
        this.giaiBay = giaiBay;
        this.giaiTam = giaiTam;
    }

    public GiaiThuong() {
    }


    public String getGiaiDacBiet() {
        return giaiDacBiet;
    }

    public void setGiaiDacBiet(String giaiDacBiet) {
        this.giaiDacBiet = giaiDacBiet;
    }

    public String getGiaiNhat() {
        return giaiNhat;
    }

    public void setGiaiNhat(String giaiNhat) {
        this.giaiNhat = giaiNhat;
    }

    public String getGiaiNhi() {
        return giaiNhi;
    }

    public void setGiaiNhi(String giaiNhi) {
        this.giaiNhi = giaiNhi;
    }

    public List<String> getGiaiBa() {
        return giaiBa;
    }

    public void setGiaiBa(List<String> giaiBa) {
        this.giaiBa = giaiBa;
    }

    public List<String> getGiaiTu() {
        return giaiTu;
    }

    public void setGiaiTu(List<String> giaiTu) {
        this.giaiTu = giaiTu;
    }

    public String getGiaiNam() {
        return giaiNam;
    }

    public void setGiaiNam(String giaiNam) {
        this.giaiNam = giaiNam;
    }

    public List<String> getGiaiSau() {
        return giaiSau;
    }

    public void setGiaiSau(List<String> giaiSau) {
        this.giaiSau = giaiSau;
    }

    public String getGiaiBay() {
        return giaiBay;
    }

    public void setGiaiBay(String giaiBay) {
        this.giaiBay = giaiBay;
    }

    public String getGiaiTam() {
        return giaiTam;
    }

    public void setGiaiTam(String giaiTam) {
        this.giaiTam = giaiTam;
    }
}
