package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Dai;
import com.example.myapplication.model.NguoiBan;

import java.util.List;

@Dao
public interface NguoiBanDao {

    @Insert
    void insert (NguoiBan nguoiBan);

    @Update
    void update (NguoiBan nguoiBan);

    @Delete
    void delete(NguoiBan nguoiBan);

    @Query("DELETE FROM nguoiban")
    void deleteAll();

    @Query("SELECT * FROM nguoiban")
   LiveData<List<NguoiBan>> getAllNguoiBans();
}
