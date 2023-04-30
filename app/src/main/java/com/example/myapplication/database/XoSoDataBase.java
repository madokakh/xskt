package com.example.myapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.dao.BaoLoDao;
import com.example.myapplication.dao.DaiDao;
import com.example.myapplication.dao.DauDuoiDao;
import com.example.myapplication.dao.NguoiBanDao;
import com.example.myapplication.dao.SoDaDao;
import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.Dai;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.model.SoDa;
import com.example.myapplication.utils.DateConverter;
import com.example.myapplication.utils.GiaiThuongTypeConverter;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Dai.class,DauDuoi.class, SoDa.class, NguoiBan.class, BaoLo.class},version = 2)
@TypeConverters ({GiaiThuongTypeConverter.class,DateConverter.class})
public  abstract class XoSoDataBase extends RoomDatabase {

    private  static XoSoDataBase instance;
    public abstract DaiDao daiDao();
    public abstract DauDuoiDao dauDuoiDao();
    public abstract SoDaDao soDaDao();
    public abstract NguoiBanDao nguoiBanDao();

    public abstract BaoLoDao baoLoDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized XoSoDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    XoSoDataBase.class,"xo_so_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
               /* WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);*/

                NguoiBan soc = new NguoiBan();
                soc.setTenNguoiBan("Soc");
                soc.setHeSoDauDuoi(82);
                soc.setHeSoDa(62);
                soc.setHeSoBaoLo(14);
                soc.setNguoiBanID(soc.getTenNguoiBan().replace(" ","").toUpperCase());

                NguoiBan thao = new NguoiBan();
                thao.setTenNguoiBan("Thao");
                thao.setHeSoDauDuoi(85);
                thao.setHeSoDa(62);
                thao.setHeSoBaoLo(14);
                thao.setNguoiBanID(thao.getTenNguoiBan().replace(" ","").toUpperCase());

                NguoiBan hai = new NguoiBan();
                hai.setTenNguoiBan("Hai");
                hai.setHeSoDauDuoi(85);
                hai.setHeSoDa(62);
                hai.setHeSoBaoLo(15);
                hai.setNguoiBanID(hai.getTenNguoiBan().replace(" ","").toUpperCase());

                NguoiBanDao nguoiBanDao = instance.nguoiBanDao();
                nguoiBanDao.insert(soc);
                nguoiBanDao.insert(hai);
                nguoiBanDao.insert(thao);
            });
        }
    };

   /* private  static RoomDatabase.Callback roomCallBack
            = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynTask(instance).execute();
        }
    };

    private static class PopulateDbAsynTask extends AsyncTask<Void, Void, Void>{

        private DaiDao daiDao;

        private PopulateDbAsynTask(XoSoDataBase xoSoDataBase){
            daiDao = xoSoDataBase.daiDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            daiDao.insert(new Dai());
            return null;
        }
    }*/
}
