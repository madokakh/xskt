package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.Dai;
import com.example.myapplication.model.NguoiBan;

import java.util.List;

public class DaiViewModel extends AndroidViewModel {

    private XoSoRepository repository;
    private LiveData<List<Dai>> allDais;

    public DaiViewModel(@NonNull Application application) {
        super(application);
        repository = new XoSoRepository(application);
        allDais = repository.getAllDais();
    }


    public void insert(Dai dai) {
        repository.insertDai(dai);
    }

    public void update(Dai dai) {
        repository.updateDai(dai);
    }

    public void delete(Dai dai) {
        repository.deleteDai(dai);
    }

    public void deleteAllDai() {
        repository.deleteAllDai();
    }
    public LiveData<List<Dai>> getAllDais() {
        return allDais;
    }

    public LiveData<Dai> getDaiByID(String date, String mMaSoDai){

        return repository.getDaiByID(date,mMaSoDai);
    }

    public int DaiExists(String maSoDai, String date){

        return repository.DaiExists(maSoDai,date);
    }

    public LiveData<List<Dai>> getDaisByDate(String date){

        return repository.getAllDaisByDate(date);
    }

    public boolean isDaiExist(String maSoDai, String date){

        boolean exists = repository.isDaiExisted(maSoDai,date);
        if (exists) {
            // The object exists
            return true;
        } else {
            // The object does not exist
            return false;
        }
    }
}
