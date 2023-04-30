package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.database.DauDuoiRepository;
import com.example.myapplication.database.XoSoDataBase;
import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.DauDuoi;

import java.util.Date;
import java.util.List;

public class DauDuoiViewModel extends AndroidViewModel {

    private XoSoRepository repository;
    private LiveData<List<DauDuoi>> allDauDuois;

    public DauDuoiViewModel(@NonNull Application application) {
        super(application);
        repository = new XoSoRepository(application);
        allDauDuois = repository.getAllDauDuois();

    }


    public void insert(DauDuoi dauDuoi) {
        repository.insertDauDuoi(dauDuoi);
    }

    public void update(DauDuoi dauDuoi) {
        repository.updateDauDuoi(dauDuoi);
    }

    public void delete(DauDuoi dauDuoi) {
        repository.deleteDauDuoi(dauDuoi);
    }

    public void deleteAllDauDuoi() {
        repository.deleteAllDauDuoi();
    }
    public LiveData<List<DauDuoi>> getAllDauDuois() {
        return allDauDuois;
    }

    public LiveData<List<DauDuoi>> getAllDauDuoiWithNguoiBanID(String nguoiBanID) {


        return repository.getAllDauDuoiWithNguoiBanID(nguoiBanID);
    }

    public LiveData<List<DauDuoi>> getAllDauDuoiWithNguoiBanIDAndDate(String nguoiBanID, String date) {


        return repository.getAllDauDuoiWithNguoiBanIDAndDate(nguoiBanID,date);
    }

    public LiveData<List<DauDuoi>> getAllDauDuoisWithNguoiBan(String nguoiBanID) {
        return repository.getAllDauDuoisWithNguoiBan(nguoiBanID);
    }
}
