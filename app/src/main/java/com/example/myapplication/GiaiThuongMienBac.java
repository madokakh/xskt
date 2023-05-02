package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class GiaiThuongMienBac {

    private List<String> dauDuois;
    private List<String> giaiKhac;

    private String date;
    public GiaiThuongMienBac() {
        dauDuois = new ArrayList<>();
        giaiKhac = new ArrayList<>();
    }

    public List<String> getDauDuois() {
        return dauDuois;
    }

    public void setDauDuois(List<String> dauDuois) {
        this.dauDuois = dauDuois;
    }

    public List<String> getGiaiKhac() {
        return giaiKhac;
    }

    public void setGiaiKhac(List<String> giaiKhac) {
        this.giaiKhac = giaiKhac;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
