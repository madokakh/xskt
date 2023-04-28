package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.model.SoDa;

import java.util.List;

public class NguoiBanViewModel extends AndroidViewModel {

    private XoSoRepository repository;
    private LiveData<List<NguoiBan>> allNguoiBan;

    public NguoiBanViewModel(@NonNull Application application) {
        super(application);
        repository = new XoSoRepository(application);
        allNguoiBan = repository.getAllNguoiBans();
    }


    public void insert(NguoiBan nguoiBan) {
        repository.insertNguoiBan(nguoiBan);
    }

    public void update(NguoiBan nguoiBan) {
        repository.updateNguoiBan(nguoiBan);
    }

    public void delete(NguoiBan nguoiBan) {
        repository.deleteNguoiBan(nguoiBan);
    }

    public void deleteAllNguoiBan() {
        repository.deleteAllNguoiBan();
    }
    public LiveData<List<NguoiBan>> getAllNguoiBans() {
        return allNguoiBan;
    }
}
