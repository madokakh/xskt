package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.SoDa;

import java.util.List;

public class SoDaViewModel extends AndroidViewModel {

    private XoSoRepository repository;
    private LiveData<List<SoDa>> allSoDa;

    public SoDaViewModel(@NonNull Application application) {
        super(application);
        repository = new XoSoRepository(application);
        allSoDa = repository.getAllSoDas();
    }


    public void insert(SoDa soDa) {
        repository.insertSoDa(soDa);
    }

    public void update(SoDa soDa) {
        repository.updateSoDa(soDa);
    }

    public void delete(SoDa soDa) {
        repository.deleteDa(soDa);
    }

    public void deleteAllSoDa() {
        repository.deleteAllDa();
    }
    public LiveData<List<SoDa>> getAllSoDas() {
        return allSoDa;
    }
    public LiveData<List<SoDa>> getAllSoDaWithNguoiBanIDAndDate(String nguoiBanID, String date) {


        return repository.getAllSoDaWithNguoiBanIDAndDate(nguoiBanID,date);
    }

    public LiveData<List<SoDa>> getAllSoDasWithNguoiBan(String nguoiBanID) {
        return repository.getAllSoDasWithNguoiBan(nguoiBanID);
    }
}
