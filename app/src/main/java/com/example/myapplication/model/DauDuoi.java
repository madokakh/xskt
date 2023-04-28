package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "dauduoi")
public class DauDuoi {

    @PrimaryKey (autoGenerate = true)
    private int dauDuoiID;

    private String nguoiBanID;// ForeinKey

    private String soCuoc;

    //neu tienCuocSoDau = 0, nguoi choi khong cuoc
    private float tienCuocSoDau;
    private float tienCuocSoDuoi;

    private Date date;

    @ColumnInfo(defaultValue = "100")
    private int daiI1D;
    @ColumnInfo(defaultValue = "100")
    private int daiI2D;
    @ColumnInfo(defaultValue = "100")
    private int daiI3D;
    @ColumnInfo(defaultValue = "100")
    private int daiI4D;
    @ColumnInfo(defaultValue = "100")

    private String soCuocDau;
    private String soCuocDuoi;
    private int vungMien;

    private boolean isTrungThuong;
    private boolean isTrungAnUi;

    private float tienThuong;

    private String dateString;

    private String tenDai1;
    private String tenDai2;


    public DauDuoi(int dauDuoiID, String nguoiBanID, String soCuoc, float tienCuocSoDau, float tienCuocSoDuoi, Date date, int daiI1D, int daiI2D, int daiI3D, int daiI4D) {
        this.dauDuoiID = dauDuoiID;
        this.nguoiBanID = nguoiBanID;
        this.soCuoc = soCuoc;
        this.tienCuocSoDau = tienCuocSoDau;
        this.tienCuocSoDuoi = tienCuocSoDuoi;
        this.date = date;
        this.daiI1D = daiI1D;
        this.daiI2D = daiI2D;
        this.daiI3D = daiI3D;
        this.daiI4D = daiI4D;
    }

    public DauDuoi() {
    }

    public int getDauDuoiID() {
        return dauDuoiID;
    }

    public void setDauDuoiID(int dauDuoiID) {
        this.dauDuoiID = dauDuoiID;
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

    public float getTienCuocSoDau() {
        return tienCuocSoDau;
    }

    public void setTienCuocSoDau(float tienCuocSoDau) {
        this.tienCuocSoDau = tienCuocSoDau;
    }

    public float getTienCuocSoDuoi() {
        return tienCuocSoDuoi;
    }

    public void setTienCuocSoDuoi(float tienCuocSoDuoi) {
        this.tienCuocSoDuoi = tienCuocSoDuoi;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDaiI1D() {
        return daiI1D;
    }

    public void setDaiI1D(int daiI1D) {
        this.daiI1D = daiI1D;
    }

    public int getDaiI2D() {
        return daiI2D;
    }

    public void setDaiI2D(int daiI2D) {
        this.daiI2D = daiI2D;
    }

    public int getDaiI3D() {
        return daiI3D;
    }

    public void setDaiI3D(int daiI3D) {
        this.daiI3D = daiI3D;
    }

    public int getDaiI4D() {
        return daiI4D;
    }

    public void setDaiI4D(int daiI4D) {
        this.daiI4D = daiI4D;
    }

    public String getSoCuocDau() {
        return soCuocDau;
    }

    public void setSoCuocDau(String soCuocDau) {
        this.soCuocDau = soCuocDau;
    }

    public String getSoCuocDuoi() {
        return soCuocDuoi;
    }

    public void setSoCuocDuoi(String soCuocDuoi) {
        this.soCuocDuoi = soCuocDuoi;
    }

    public int getVungMien() {
        return vungMien;
    }

    public void setVungMien(int vungMien) {
        this.vungMien = vungMien;
    }

    public boolean isTrungThuong() {
        return isTrungThuong;
    }

    public void setTrungThuong(boolean trungThuong) {
        isTrungThuong = trungThuong;
    }

    public boolean isTrungAnUi() {
        return isTrungAnUi;
    }

    public void setTrungAnUi(boolean trungAnUi) {
        isTrungAnUi = trungAnUi;
    }

    public float getTienThuong() {
        return tienThuong;
    }

    public void setTienThuong(float tienThuong) {
        this.tienThuong = tienThuong;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
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
