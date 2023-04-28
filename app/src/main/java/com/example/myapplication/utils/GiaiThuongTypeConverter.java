package com.example.myapplication.utils;

import androidx.room.TypeConverter;

import com.example.myapplication.model.GiaiThuong;
import com.google.gson.Gson;

public class GiaiThuongTypeConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static GiaiThuong fromJson(String json) {
        return gson.fromJson(json, GiaiThuong.class);
    }

    @TypeConverter
    public static String toJson(GiaiThuong giaiThuong) {
        return gson.toJson(giaiThuong);
    }
}
