package com.example.myapplication.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.DauDuoiDao;
import com.example.myapplication.dao.NguoiBanDao;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.NguoiBan;

import java.util.List;

public class NguoiBanRepository {


    private NguoiBanDao nguoiBanDao;

    private XoSoDataBase xoSoDataBase;

    private LiveData<List<DauDuoi>> getAllDauDuois;

    public NguoiBanRepository(Application application) {
        xoSoDataBase = XoSoDataBase.getInstance(application);
        nguoiBanDao = xoSoDataBase.nguoiBanDao();
    }

    public void insert(NguoiBan nguoiBan){
        new InsertDauDuoiAsyncTask(nguoiBanDao).execute(nguoiBan);
    }

   public void update(NguoiBan nguoiBan){
        new UpdateNguoiBanAsyncTask(nguoiBanDao).execute(nguoiBan);
    }

   public void delete(NguoiBan nguoiBan){
        new DeleteNguoiBanAsyncTask(nguoiBanDao).execute(nguoiBan);
    }

   public void deleteAll(){
        new DeleteAllNguoiBanAsyncTask(nguoiBanDao).execute();
    }

    public LiveData<List<DauDuoi>> getGetAllDauDuois() {
        return getAllDauDuois;
    }

    private static class InsertDauDuoiAsyncTask extends AsyncTask<NguoiBan, Void, Void> {
        private NguoiBanDao nguoiBanDao;

        private InsertDauDuoiAsyncTask(NguoiBanDao nguoiBanDao) {
            this.nguoiBanDao = nguoiBanDao;
        }

        @Override
        protected Void doInBackground(NguoiBan... nguoiBans) {
            nguoiBanDao.insert(nguoiBans[0]);
            return null;
        }
    }

    private static class UpdateNguoiBanAsyncTask extends AsyncTask<NguoiBan, Void, Void> {
        private NguoiBanDao nguoiBanDao;

        private UpdateNguoiBanAsyncTask(NguoiBanDao nguoiBanDao) {
            this.nguoiBanDao = nguoiBanDao;
        }

        @Override
        protected Void doInBackground(NguoiBan... nguoiBans) {
            nguoiBanDao.update(nguoiBans[0]);
            return null;
        }
    }

    private static class DeleteNguoiBanAsyncTask extends AsyncTask<NguoiBan, Void, Void> {
        private NguoiBanDao nguoiBanDao;

        private DeleteNguoiBanAsyncTask(NguoiBanDao nguoiBanDao) {
            this.nguoiBanDao = nguoiBanDao ;
        }

        @Override
        protected Void doInBackground(NguoiBan... nguoiBans) {
            nguoiBanDao.delete(nguoiBans[0]);
            return null;
        }
    }

    private static class DeleteAllNguoiBanAsyncTask extends AsyncTask<Void, Void, Void> {
        private NguoiBanDao nguoiBanDao;

        private DeleteAllNguoiBanAsyncTask(NguoiBanDao nguoiBanDao) {
            this.nguoiBanDao = nguoiBanDao;
        }

        @Override
        protected Void doInBackground(Void... dauDuois) {
            nguoiBanDao.deleteAll();
            return null;
        }
    }
}
