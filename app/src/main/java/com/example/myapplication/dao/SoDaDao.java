package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Dai;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.SoDa;

import java.util.List;

@Dao
public interface SoDaDao {

    @Insert
    void insert (SoDa soDa);

    @Update
    void update (SoDa soDa);

    @Delete
    void delete(SoDa soDa);

    @Query("DELETE FROM soda")
    void deleteAllSoDa();

    @Query("SELECT * FROM soda")
    LiveData<List<SoDa>> getAllSoDas();

    @Query("SELECT * FROM soda WHERE nguoiBanID= :nguoiBanID AND date= :date")
    LiveData<List<SoDa>> getAllSoDasWithNguoiBanAndDate(String nguoiBanID, String date);

    @Query("SELECT * FROM soda WHERE nguoiBanID= :nguoiBanID")
    LiveData<List<SoDa>> getAllSoDasWithNguoiBan(String nguoiBanID);
}
