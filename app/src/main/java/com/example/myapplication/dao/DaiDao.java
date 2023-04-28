package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Dai;

import java.util.List;

@Dao
public interface DaiDao {

    @Insert
    void insert (Dai dai);

    @Update
    void update (Dai dai);

    @Delete
    void delete(Dai dai);

    @Query("DELETE FROM dai")
    void deleteAllDai();

    @Query("SELECT * FROM dai")
   LiveData<List<Dai>> getAllDais();

    @Query("SELECT * FROM dai WHERE date= :mDate AND maSoDai= :mSoDai")
    LiveData<Dai> getDaiByID(String mDate, String mSoDai);

    @Query("SELECT COUNT(*) FROM dai WHERE maSoDai = :maSoDai AND date = :date")
    int daiExists(String maSoDai, String date);

    @Query("SELECT * FROM dai WHERE date= :mDate")
    LiveData<List<Dai>> getDaisByDate(String mDate);
}
