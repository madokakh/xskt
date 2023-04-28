package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.model.Dai;
import com.example.myapplication.model.GiaiThuong;
import com.example.myapplication.utils.XoSoUtils;

import java.util.Calendar;

public class MyAlarmReceiver extends BroadcastReceiver {

    PareseURLWebScrapping layKetQua1;
    PareseURLWebScrapping layKetQua2;
    DaiViewModel daiViewModel;
    private  String URL_MIEN_NAM = "https://www.minhngoc.com.vn/xo-so-mien-nam/";

    @Override
    public void onReceive(Context context, Intent intent) {
       /* // Get the current time and check if it's after 4:30 PM
        Calendar currentTime = Calendar.getInstance();
        int dayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK);

        daiViewModel = new ViewModelProvider((AppCompatActivity) context).get(DaiViewModel.class);

        LotteryCity lotteryCity = XoSoUtils.getDais(dayOfWeek);
        String dai1 = URL_MIEN_NAM;
        String dai2 = URL_MIEN_NAM;
        dai1  += lotteryCity.getCity1()+ ".html";
        dai2  += lotteryCity.getCity2()+ ".html";


        if (currentTime.get(Calendar.HOUR_OF_DAY) >= 15 && currentTime.get(Calendar.MINUTE) >= 16) {
            // The current time is after 4:30 PM, so do something here (e.g. save data to the database using Room)
            layKetQua1 = (PareseURLWebScrapping) new PareseURLWebScrapping().execute(new String[]{dai1});
            layKetQua2 = (PareseURLWebScrapping) new PareseURLWebScrapping().execute(new String[]{dai2});
            layKetQua1.setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                @Override
                public void ketQua(GiaiThuong giaiThuong) {
                    Dai dai = new Dai();

                    dai.setDate( XoSoUtils.getCurrentDate());
                    dai.setMaSoDai(lotteryCity.getCity1ID()+"");
                    dai.setGiaiThuong(giaiThuong);
                    daiViewModel.insert(dai);
                }
            });

            layKetQua2.setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                @Override
                public void ketQua(GiaiThuong giaiThuong) {
                    Dai dai = new Dai();

                    dai.setDate( XoSoUtils.getCurrentDate());
                    dai.setMaSoDai(lotteryCity.getCity2ID()+"");
                    dai.setGiaiThuong(giaiThuong);
                    daiViewModel.insert(dai);
                }
            });
        }*/
    }
}