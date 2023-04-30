package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.DauDuoi;

import java.util.List;

public class BaoLoViewModel extends AndroidViewModel {

    private XoSoRepository repository;
    private LiveData<List<BaoLo>> allBaoLos;

    public BaoLoViewModel(@NonNull Application application) {
        super(application);
        repository = new XoSoRepository(application);
        allBaoLos = repository.getAllBaoLos();
    }


    public void insert(BaoLo baoLo) {
        repository.insertBaoLo(baoLo);
    }

    public void update(BaoLo baoLo) {
        repository.updateBaoLo(baoLo);
    }

    public void delete(BaoLo baoLo) {
        repository.deleteBaoLo(baoLo);
    }

    public void deleteAllBaoLo() {
        repository.deleteAllBaoLo();
    }
    public LiveData<List<BaoLo>> getAllBaoLos() {
        return allBaoLos;
    }
    public LiveData<List<BaoLo>> getAllBaoLoWithNguoiBanIDAndDate(String nguoiBanID, String date) {
        return repository.getAllBaoLoWithNguoiBanIDAndDate(nguoiBanID, date);
    }

    public LiveData<List<BaoLo>> getAllBaoLoWithNguoiBan(String nguoiBanID) {
        return repository.getAllBaoLosWithNguoiBan(nguoiBanID);
    }

}
