package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.Dai;
import com.example.myapplication.model.SoDa;

import java.util.List;

@Dao
public interface BaoLoDao {
    @Insert
    void insert (BaoLo baoLo);

    @Update
    void update (BaoLo baoLo);

    @Delete
    void delete(BaoLo baoLo);

    @Query("DELETE FROM baolo")
    void deleteAllBaoLo();

    @Query("SELECT * FROM baolo")
    LiveData<List<BaoLo>> getAllBaoLos();
    @Query("SELECT * FROM baolo WHERE nguoiBanID= :nguoiBanID AND date= :date")
    LiveData<List<BaoLo>> getAllBaoLoWithNguoiBanAndDate(String nguoiBanID, String date);

    @Query("SELECT * FROM baolo WHERE nguoiBanID= :nguoiBanID")
    LiveData<List<BaoLo>> getAllBaoLoWithNguoiBan(String nguoiBanID);
}
