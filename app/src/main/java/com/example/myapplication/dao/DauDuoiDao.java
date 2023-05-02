package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.DauDuoi;

import java.util.Date;
import java.util.List;

@Dao
public interface DauDuoiDao {
    @Insert
    void insert (DauDuoi dauDuoi);

    @Update
    void update (DauDuoi dauDuoi);

    @Delete
    void delete(DauDuoi dauDuoi);

    @Query("DELETE FROM dauduoi")
    void deleteAllDauDuoi();

    @Query("SELECT * FROM dauduoi")
    LiveData<List<DauDuoi>> getAllDauDuois();
    @Query("SELECT * FROM dauduoi WHERE nguoiBanID= :nguoiBanID")
    LiveData<List<DauDuoi>> getAllDauDuoisWithNguoiBan(String nguoiBanID);

    @Query("SELECT * FROM dauduoi WHERE nguoiBanID= :nguoiBanID AND dateString= :date")
    LiveData<List<DauDuoi>> getAllDauDuoisWithNguoiBanAndDate(String nguoiBanID, String date);

    @Query("SELECT * FROM dauduoi WHERE nguoiBanID= :nguoiBanID AND dateString= :date AND vungMien= :vungMien")
    LiveData<List<DauDuoi>> getAllDauDuoisWithNguoiBanAndDateVungMien(String nguoiBanID, String date,int vungMien);
    @Query("DELETE FROM DauDuoi WHERE dateString= :sixDaysAgo")
    void deleteDauDuoiOlderThan(String sixDaysAgo);

}
