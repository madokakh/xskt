package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.database.DauDuoiRepository;
import com.example.myapplication.database.NguoiBanRepository;
import com.example.myapplication.model.Dai;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.GiaiThuong;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.utils.XoSoUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, PareseURLWebScrapping.LayKetQuaCallBack {


    int dem = 0;
    PareseURLWebScrapping layKetQua;
    private  String URL_MIEN_NAM = "https://www.minhngoc.com.vn/xo-so-mien-nam/";
    private  String URL_MIEN_BAC = "https://www.minhngoc.com.vn/ket-qua-xo-so/mien-bac.html";
    WebView wvKetQuaXoSo;
    Button btnXS;

    DauDuoiRepository repository;
    NguoiBanRepository nguoiBanrepository;

    DauDuoiViewModel dauDuoiViewModel;
    SoDaViewModel soDaViewModel;
    BaoLoViewModel baoLoViewModel;
    DaiViewModel daiViewModel;

    GiaiThuong giaiThuong;
    LotteryCity city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wvKetQuaXoSo = findViewById(R.id.kqxs);
        btnXS = findViewById(R.id.btnXS);

        daiViewModel = new ViewModelProvider(this).get(DaiViewModel.class);

        dauDuoiViewModel = new ViewModelProvider(this).get(DauDuoiViewModel.class);
        soDaViewModel = new ViewModelProvider(this).get(SoDaViewModel.class);
        baoLoViewModel = new ViewModelProvider(this).get(BaoLoViewModel.class);
        btnXS.setOnClickListener(this);
        wvKetQuaXoSo.loadUrl("https://xosodaiphat.com/");

        wvKetQuaXoSo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Open links in current WebView
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        /*CurrentDate currentDate = new CurrentDate();
        if(currentDate.getGio() >= 16){
            if(currentDate.getGio() > 16){
                loadingDataFromInternet();
            }else if(currentDate.getPhut() > 50){
                loadingDataFromInternet();
            }
        }*/
        //XoSoUtils.loadKetQuaXoSoFromInternet()
       /* CurrentDate currentDate = new CurrentDate();
        city = XoSoUtils.getLotteryCityByDate(currentDate.getNgay(),currentDate.getThang(),currentDate.getNam());
       //   CurrentDate currentDate = new CurrentDate();
        if(currentDate.getGio() >= 16){
            if(currentDate.getGio() > 16){
                saveGiaiThuongToDatabase();
            }else if(currentDate.getPhut() > 50){
                saveGiaiThuongToDatabase();
            }
        }*/
       daiViewModel.deleteAllDai();
     //   loadKetQuaTrungThuong();
      //  testLoadKetQuaTrungThuong();


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        Date sixDaysAgo = calendar.getTime();
        String sixDaysAgoString = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(sixDaysAgo);
        int day = Integer.parseInt(sixDaysAgoString.split("/")[0]);
        int month = Integer.parseInt(sixDaysAgoString.split("/")[1]);
        sixDaysAgoString = day + "/" + month;
        Log.d("Day6Daysago",  sixDaysAgoString+ "");

        dauDuoiViewModel.deleteAllDauDuoiSixDaysAgo(sixDaysAgoString);
        soDaViewModel.deleteAllSoDasSixDaysAgo(sixDaysAgoString);
        baoLoViewModel.deleteAllBaoLoSixDaysAgo(sixDaysAgoString);

    }

    private void testLoadKetQuaTrungThuong() {

          CurrentDate currentDate = new CurrentDate();
          int day = 28;
          int month = 3;
          int year = 2023;
        city = XoSoUtils.getLotteryCityByDate(day,month,year);
       //   CurrentDate currentDate = new CurrentDate();
       /* if(currentDate.getGio() >= 16){
            if(currentDate.getGio() > 16){
                saveGiaiThuongToDatabase();
            }else if(currentDate.getPhut() > 50){
                saveGiaiThuongToDatabase();
            }
        }*/
        saveGiaiThuongToDatabase();
    }


    void loadingDataFromInternet(){

        PareseURLWebScrapping layKetQua1;
        PareseURLWebScrapping layKetQua2;
        Calendar currentTime = Calendar.getInstance();
        int year = currentTime.get(Calendar.YEAR);
        int month = currentTime.get(Calendar.MONTH);
        int day = currentTime.get(Calendar.DAY_OF_MONTH);
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int dayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK);
        int minute = currentTime.get(Calendar.MINUTE);


        DaiViewModel daiViewModel;
        daiViewModel = new ViewModelProvider(this).get(DaiViewModel.class);
        String URL_MIEN_NAM = "https://www.minhngoc.com.vn/xo-so-mien-nam/";
        LotteryCity lotteryCity = XoSoUtils.getDais(dayOfWeek);
        String dai1 = URL_MIEN_NAM;
        String dai2 = URL_MIEN_NAM;
        dai1  += lotteryCity.getCity1()+ ".html";
        dai2  += lotteryCity.getCity2()+ ".html";

        //The current time is after 4:30 PM, so do something here (e.g. save data to the database using Room)
        layKetQua1 = (PareseURLWebScrapping) new PareseURLWebScrapping().execute(new String[]{dai1});
        layKetQua2 = (PareseURLWebScrapping) new PareseURLWebScrapping().execute(new String[]{dai2});
        layKetQua1.setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
            @Override
            public void ketQua(GiaiThuong giaiThuong) {
                Dai dai = new Dai();
                dai.setDate( XoSoUtils.getCurrentDate());
                dai.setMaSoDai(lotteryCity.getCity1ID()+"");
                dai.setGiaiThuong(giaiThuong);
                String formattedDay = String.format("%02d", day);
                String date = formattedDay + "/" + (month + 1);
                if(XoSoUtils.getCurrentDate().equals(date)){
                    if(hour < 16 && minute < 50){

                    }else{
                        daiViewModel.insert(dai);
                    }
                }else{
                    daiViewModel.insert(dai);
                }
            }
        });

        layKetQua2.setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
            @Override
            public void ketQua(GiaiThuong giaiThuong) {
                Dai dai = new Dai();

                dai.setDate( XoSoUtils.getCurrentDate());
                dai.setMaSoDai(lotteryCity.getCity2ID()+"");
                dai.setGiaiThuong(giaiThuong);
                String formattedDay = String.format("%02d", day);
                String date = formattedDay + (month + 1);
                /*if(XoSoUtils.getCurrentDate().equals(date)){
                    if(hour < 16 && minute < 50){

                    }else{
                        daiViewModel.insert(dai);
                    }
                }else{
                    daiViewModel.insert(dai);
                }*/

            }
        });
/*
        // After 16: 50 Delete the previous 7 days ago Dai item from Dai Database
        if(hour > 16 && minute >= 50){
            String formattedDay = String.format("%02d", (day - 7));
            String date = formattedDay + "/" + (month+1);
            daiViewModel.getDaisByDate(date).observe(MainActivity.this, dais -> {
                if(dais.size() == 2){
                    daiViewModel.delete(dais.get(0));
                    daiViewModel.delete(dais.get(1));
                }
            });
        }*/

    }


    @Override
    public void onClick(View v) {
        dem++;
       // Toast.makeText(this, dem + "", Toast.LENGTH_SHORT).show();
        if(dem == 5){
            Intent newIntent = new Intent(MainActivity.this,NguoiBanActivity.class);
            startActivity(newIntent);
            dem = 0;

        }

    }

    @Override
    public void ketQua(GiaiThuong giaiThuong) {
        DauDuoi dd = new DauDuoi();
        String soCuoc = "36";
        dd.setSoCuoc(soCuoc);
        dd.setSoCuocDau(soCuoc);
        dd.setSoCuocDuoi(soCuoc);

        dd.setTienCuocSoDau(10);
        dd.setTienCuocSoDuoi(10);

        dd.setDaiI1D(AppConstants.TIEN_GIANG);
       // dd.setDaiI2D(AppConstants.KIEN_GIANG);
        dd = XoSoUtils.doKetQuaDauDuoiMienNam(giaiThuong,giaiThuong,dd);

    }


    void getKetQuaXoSoFromInternet(){

        // Get the context and create an instance of the AlarmManager
        Context context = getApplicationContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Get the current time and set the alarm time to 4:30 PM
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, 15);
        alarmTime.set(Calendar.MINUTE, 16);

        // If the alarm time is in the past, add a day to the alarm time
        if (alarmTime.getTimeInMillis() < System.currentTimeMillis()) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Create an Intent to trigger the alarm receiver
        Intent intent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Schedule the alarm to trigger at the specified time and repeat daily
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void saveGiaiThuongToDatabase(){

        XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity1()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
            @Override
            public void ketQua(GiaiThuong giaiThuong) {

                Dai dai = new Dai();
                dai.setDate( XoSoUtils.getCurrentDate());
                dai.setMaSoDai(city.getCity1ID()+"");
                dai.setGiaiThuong(giaiThuong);
                daiViewModel.insert(dai);
                Log.d("Giai Tam1", giaiThuong.getGiaiTam());
            }
        });
        XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity2()+"").setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
            @Override
            public void ketQua(GiaiThuong giaiThuong) {

                Dai dai = new Dai();
                dai.setDate( XoSoUtils.getCurrentDate());
                dai.setMaSoDai(city.getCity2ID()+"");
                dai.setGiaiThuong(giaiThuong);
                daiViewModel.insert(dai);
            }
        });

    }
}