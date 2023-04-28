package com.example.myapplication.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.DauDuoiDao;
import com.example.myapplication.model.DauDuoi;

import java.util.List;

public class DauDuoiRepository{


    private DauDuoiDao dauDuoiDao;

    private XoSoDataBase xoSoDataBase;

    private LiveData<List<DauDuoi>> getAllDauDuois;

    public DauDuoiRepository(Application application) {
        xoSoDataBase = XoSoDataBase.getInstance(application);
        dauDuoiDao = xoSoDataBase.dauDuoiDao();
    }

    public void insert(DauDuoi dauDuoi){
        new InsertDauDuoiAsyncTask(dauDuoiDao).execute(dauDuoi);
    }

   public void update(DauDuoi dauDuoi){
        new UpdateDauDuoiAsyncTask(dauDuoiDao).execute(dauDuoi);
    }

   public void delete(DauDuoi dauDuoi){
        new DeleteDauDuoiAsyncTask(dauDuoiDao).execute(dauDuoi);
    }

   public void deleteAll(){
        new DeleteAllDauDuoiAsyncTask(dauDuoiDao).execute();
    }

    public LiveData<List<DauDuoi>> getGetAllDauDuois() {
        return getAllDauDuois;
    }

    private static class InsertDauDuoiAsyncTask extends AsyncTask<DauDuoi, Void, Void> {
        private DauDuoiDao dauDuoiDao;

        private InsertDauDuoiAsyncTask(DauDuoiDao dauDuoiDao) {
            this.dauDuoiDao = dauDuoiDao;
        }

        @Override
        protected Void doInBackground(DauDuoi... dauDuois) {
            dauDuoiDao.insert(dauDuois[0]);
            return null;
        }
    }

    private static class UpdateDauDuoiAsyncTask extends AsyncTask<DauDuoi, Void, Void> {
        private DauDuoiDao dauDuoiDao;

        private UpdateDauDuoiAsyncTask(DauDuoiDao dauDuoiDao) {
            this.dauDuoiDao = dauDuoiDao;
        }

        @Override
        protected Void doInBackground(DauDuoi... dauDuois) {
            dauDuoiDao.update(dauDuois[0]);
            return null;
        }
    }

    private static class DeleteDauDuoiAsyncTask extends AsyncTask<DauDuoi, Void, Void> {
        private DauDuoiDao dauDuoiDao;

        private DeleteDauDuoiAsyncTask(DauDuoiDao dauDuoiDao) {
            this.dauDuoiDao = dauDuoiDao;
        }

        @Override
        protected Void doInBackground(DauDuoi... dauDuois) {
            dauDuoiDao.delete(dauDuois[0]);
            return null;
        }
    }

    private static class DeleteAllDauDuoiAsyncTask extends AsyncTask<Void, Void, Void> {
        private DauDuoiDao dauDuoiDao;

        private DeleteAllDauDuoiAsyncTask(DauDuoiDao dauDuoiDao) {
            this.dauDuoiDao = dauDuoiDao;
        }

        @Override
        protected Void doInBackground(Void... dauDuois) {
            dauDuoiDao.getAllDauDuois();
            return null;
        }
    }
}
