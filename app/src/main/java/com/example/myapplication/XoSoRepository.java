package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.BaoLoDao;
import com.example.myapplication.dao.DaiDao;
import com.example.myapplication.dao.DauDuoiDao;
import com.example.myapplication.dao.NguoiBanDao;
import com.example.myapplication.dao.SoDaDao;
import com.example.myapplication.database.XoSoDataBase;
import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.Dai;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.model.SoDa;
import com.example.myapplication.utils.XoSoUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class XoSoRepository {

    private DaiDao daiDao;
    private DauDuoiDao dauDuoiDao;
    private NguoiBanDao nguoiBanDao;
    private SoDaDao soDaDao;
    private BaoLoDao baoLoDao;
    private LiveData<List<Dai>> allDais;
    private LiveData<List<NguoiBan>> allNguoiBans;
    private LiveData<List<SoDa>> allSoDas;
    private LiveData<List<DauDuoi>> allDauDuois;
    private LiveData<List<BaoLo>> allBaoLos;
    private XoSoDataBase xoSoDataBase;


    public XoSoRepository(Application application) {
        XoSoDataBase xoSoDataBase = XoSoDataBase.getInstance(application);
        daiDao = xoSoDataBase.daiDao();
        dauDuoiDao = xoSoDataBase.dauDuoiDao();
        nguoiBanDao = xoSoDataBase.nguoiBanDao();
        soDaDao = xoSoDataBase.soDaDao();
        baoLoDao = xoSoDataBase.baoLoDao();

        allDauDuois = dauDuoiDao.getAllDauDuois();
        allDais = daiDao.getAllDais();
        allSoDas = soDaDao.getAllSoDas();
        allNguoiBans = nguoiBanDao.getAllNguoiBans();
        allBaoLos = baoLoDao.getAllBaoLos();


    }

    public LiveData<List<Dai>> getAllDais() {
        allDais = daiDao.getAllDais();
        return allDais;
    }

    public LiveData<Dai> getDaiByID(String date, String mSoDai){

        return daiDao.getDaiByID(date, mSoDai);
    }
    public LiveData<List<NguoiBan>> getAllNguoiBans() {
        return allNguoiBans;
    }

    public LiveData<List<SoDa>> getAllSoDas() {
        return allSoDas;
    }

    public LiveData<List<DauDuoi>> getAllDauDuois() {
        return allDauDuois;
    }
    public LiveData<List<DauDuoi>> getAllDauDuoisWithNguoiBan(String nguoiBanID) {
        return dauDuoiDao.getAllDauDuoisWithNguoiBan(nguoiBanID);
    }

    public LiveData<List<BaoLo>> getAllBaoLos() {
        return allBaoLos;
    }
    public LiveData<List<BaoLo>> getAllBaoLosWithNguoiBan(String nguoiBanID){
        return baoLoDao.getAllBaoLoWithNguoiBan(nguoiBanID);
    }

   public void insertDai(Dai dai){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            if (daiDao.daiExists(dai.getMaSoDai(), dai.getDate()) == 0) {
                daiDao.insert(dai);
            } else {
                // Dai object already exists in database
            }
        });
    }

    public boolean isDaiExisted(String maSoDai, String date){
        AtomicBoolean exists = new AtomicBoolean(false);
        Thread thread = new Thread(() -> {
            int count = daiDao.daiExists(maSoDai, date);
            exists.set(count > 0);
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exists.get();
    }

    void updateDai(Dai dai){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            daiDao.update(dai);
        });
    }

    public LiveData<List<Dai>> getAllDaisByDate(String date) {

        return daiDao.getDaisByDate(date);
    }
    void deleteDai(Dai dai){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            daiDao.delete(dai);
        });
    }

    void deleteAllDai(){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            daiDao.deleteAllDai();
        });
    }

    int DaiExists(String maSoDai, String date){

        return daiDao.daiExists(maSoDai, date);
    }
    void insertSoDa(SoDa soDa){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {

            soDaDao.insert(soDa);
        });
    }
    void updateSoDa(SoDa soDa){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            soDaDao.update(soDa);
        });
    }
    public LiveData<List<SoDa>> getAllSoDasWithNguoiBan(String nguoiBanID) {
        return soDaDao.getAllSoDasWithNguoiBan(nguoiBanID);
    }
    void deleteDa(SoDa soDa){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            soDaDao.delete(soDa);
        });
    }

    void deleteAllDa(){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            soDaDao.deleteAllSoDa();
        });
    }

    void insertBaoLo(BaoLo baoLo){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            baoLoDao.insert(baoLo);
        });
    }
    void updateBaoLo(BaoLo baoLo){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            baoLoDao.update(baoLo);
        });
    }
    void deleteBaoLo(BaoLo baoLo){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            baoLoDao.delete(baoLo);
        });
    }

    void deleteAllBaoLo(){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            baoLoDao.deleteAllBaoLo();
        });
    }
    public LiveData<List<BaoLo>> getAllBaoLoWithNguoiBanIDAndDate(String nguoiBanID, String date) {

        return baoLoDao.getAllBaoLoWithNguoiBanAndDate(nguoiBanID,date);
    }


    void insertNguoiBan(NguoiBan nguoiBan){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            nguoiBanDao.insert(nguoiBan);
        });
    }
    void updateNguoiBan(NguoiBan nguoiBan){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            nguoiBanDao.update(nguoiBan);
        });
    }
    void deleteNguoiBan(NguoiBan nguoiBan){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            nguoiBanDao.delete(nguoiBan);
        });
    }

    void deleteAllNguoiBan(){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            nguoiBanDao.deleteAll();
        });
    }

    void insertDauDuoi(DauDuoi dauDuoi){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            dauDuoiDao.insert(dauDuoi);
        });
    }
    void updateDauDuoi(DauDuoi dauDuoi){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            dauDuoiDao.update(dauDuoi);
        });
    }
    void deleteDauDuoi(DauDuoi dauDuoi){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            dauDuoiDao.delete(dauDuoi);
        });
    }

    void deleteAllDauDuoi(){
        XoSoDataBase.databaseWriteExecutor.execute(() -> {
            dauDuoiDao.deleteAllDauDuoi();
        });
    }

    public LiveData<List<DauDuoi>> getAllDauDuoiWithNguoiBanID(String nguoiBanID) {

        return dauDuoiDao.getAllDauDuoisWithNguoiBan(nguoiBanID);
    }

    public LiveData<List<DauDuoi>> getAllDauDuoiWithNguoiBanIDAndDate(String nguoiBanID, String date) {

        return dauDuoiDao.getAllDauDuoisWithNguoiBanAndDate(nguoiBanID,date);
    }

    public LiveData<List<SoDa>> getAllSoDaWithNguoiBanIDAndDate(String nguoiBanID, String date) {

        return soDaDao.getAllSoDasWithNguoiBanAndDate(nguoiBanID,date);
    }
}
