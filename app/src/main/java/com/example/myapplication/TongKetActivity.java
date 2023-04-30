package com.example.myapplication;

import static android.provider.MediaStore.EXTRA_FULL_SCREEN;
import static com.example.myapplication.NguoiBanActivity.EXTRA_NGUOI_BAN;
import static com.example.myapplication.NhapDDActivity.EXTRA_VUNG_MIEN;
import static com.example.myapplication.TongKetNguoiBanAcitvity.EXTRA_DATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.Dai;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.GiaiThuong;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.model.SoDa;
import com.example.myapplication.utils.XoSoUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TongKetActivity extends AppCompatActivity implements View.OnClickListener {


    //View Models
    DauDuoiViewModel dauDuoiViewModel;
    SoDaViewModel soDaViewModel;
    BaoLoViewModel baoLoViewModel;
    DaiViewModel daiViewModel;
    //List Data
    private List<Dai> listDais;
    private List<DauDuoi> listDauDuois;
    private List<DauDuoi> listDauDuoisByDate;
    private List<SoDa> listSodas;
    private List<SoDa> listSodasByDate;
    private List<BaoLo> listBaoLos;
    private List<BaoLo> listBaoLosByDate;
    private NguoiBan nguoiBan;

    private boolean isMienBacOn;


    private int vungMien = AppConstants.MIEN_NAM;



    private TextView tvTongDD;
    private TextView tvTongThuDD;
    private TextView tvDate;
    private Button btnNhap;
    private TextView tvTongDa;
    private TextView tvTongThuDa;
    private TextView tvTongLo;
    private TextView tvTongThuLo;
    private TextView tvTongThu;
    private TextView tvLoiLoiTong;
    private TextView tvLoiLoTrungThuong;
    private TextView tvLoiLoKetQua;
    private Button btnMienNam;
    private Button btnMienBac;


    private TextView tvNguoiBan;

    private LinearLayout ln_data_dau_duoi;
    private LinearLayout ln_data_an_ui;
    private LinearLayout ln_data_da;
    private LinearLayout ln_data_bao_lo;

    private boolean isFulScreen;
    private float tongAnUi = 0;
    private float tongSoDa;
    private float tongBaoLo;
    private float tong;
    private float tongTrungThuongAnUi = 0;
    private float tongTrungDauDuoi = 0;
    private float tongTrungThuongSoDa = 0;
    private float tongTrungThuongBaoLo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_ket_nguoi_ban);
        Intent intent = getIntent();
        nguoiBan = (NguoiBan) intent.getSerializableExtra(EXTRA_NGUOI_BAN);

        connectViewIDs();
        initialize();
        action();
        loadingDataFromDataBase();


    }

    private void action() {
        btnNhap.setOnClickListener(this);
        tvDate.setOnClickListener(this);
    }

    private void connectViewIDs() {
        tvTongDD = findViewById(R.id.tvTongDD);
        tvDate = findViewById(R.id.tvDate);
        btnNhap = findViewById(R.id.btnInput);
        tvTongThuDD = findViewById(R.id.tvTongThuDD);
        tvTongDa = findViewById(R.id.tvTongDa);
        tvTongThuDa = findViewById(R.id.tvTongThuDa);
        tvNguoiBan = findViewById(R.id.tvNguoiBan);
        tvTongLo = findViewById(R.id.tvTongLo);
        tvTongThuLo = findViewById(R.id.tvTongThuLo);
        tvTongThu = findViewById(R.id.tvTongThu);
        ln_data_dau_duoi = findViewById(R.id.ln_data_dau_duoi);
        ln_data_an_ui = findViewById(R.id.ln_data_an_ui);
        ln_data_da = findViewById(R.id.ln_data_da);
        ln_data_bao_lo = findViewById(R.id.ln_data_bao_lo);
        tvLoiLoiTong = findViewById(R.id.tvLoiLoiTong);
        tvLoiLoTrungThuong = findViewById(R.id.tvLoiLoTrungThuong);
        tvLoiLoKetQua = findViewById(R.id.tvLoiLoKetQua);
        btnMienNam = findViewById(R.id.btnMienNam);
        btnMienBac = findViewById(R.id.btnMienBac);
    }

    private void initialize() {
        dauDuoiViewModel = new ViewModelProvider(this).get(DauDuoiViewModel.class);
        soDaViewModel = new ViewModelProvider(this).get(SoDaViewModel.class);
        baoLoViewModel = new ViewModelProvider(this).get(BaoLoViewModel.class);
        daiViewModel = new ViewModelProvider(this).get(DaiViewModel.class);

        listDais = new ArrayList<>();
        listBaoLos = new ArrayList<>();
        listDauDuois = new ArrayList<>();
        listSodas = new ArrayList<>();
        listDauDuoisByDate = new ArrayList<>();
        listSodasByDate = new ArrayList<>();
        listBaoLosByDate = new ArrayList<>();


        tvNguoiBan.setText(nguoiBan.getTenNguoiBan());

        CurrentDate today = new CurrentDate();
        int day = today.getNgay();
        int month = today.getThang();

        String formattedDay = String.format("%02d", day);
        //  String formattedMonth = String.format("%02d",(monthOfYear + 1) );
        String selectedDate = formattedDay + "/" + (month + 1);
        tvDate.setText(selectedDate);

    }

    private void loadingDataFromDataBase() {

        daiViewModel.getAllDais().observe(this,dais -> {

            if(dais != null){
                listDais.clear();
                listDais.addAll(dais);

            }
        });

        dauDuoiViewModel.getAllDauDuoisWithNguoiBan(nguoiBan.getNguoiBanID()).observe(this,dauDuoiList -> {
            if(dauDuoiList != null){
                listDauDuois.clear();
                listDauDuois.addAll(dauDuoiList);
                updateUIDauDuoi(tvDate.getText().toString());
            }
        });

        soDaViewModel.getAllSoDasWithNguoiBan(nguoiBan.getNguoiBanID()).observe(this, soDas -> {
            if(soDas != null){
                listSodas.clear();
                listSodas.addAll(soDas);
                updateUISoDa(tvDate.getText().toString());
            }
        });
        baoLoViewModel.getAllBaoLoWithNguoiBan(nguoiBan.getNguoiBanID()).observe(this,baoLos -> {

            if(baoLos != null){
                listBaoLos.clear();
                listBaoLos.addAll(baoLos);
                updateUIBaoLo(tvDate.getText().toString());
            }
        });



    }

    private void loadDai1byDate(LotteryCity city) {


        XoSoUtils.loadKetQuaXoSoFromInternet(tvDate.getText().toString(),1).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
            @Override
            public void ketQua(GiaiThuong giaiThuong) {
                Dai dai = new Dai();
                dai.setDate(tvDate.getText().toString());
                dai.setMaSoDai(city.getCity1()+"");
                dai.setGiaiThuong(giaiThuong);
                daiViewModel.insert(dai);
               // handleTrungAnUi();
            }
        });
    }
    private void loadDai2byDate(LotteryCity city) {

        XoSoUtils.loadKetQuaXoSoFromInternet(tvDate.getText().toString(),2).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
            @Override
            public void ketQua(GiaiThuong giaiThuong) {
                Dai dai = new Dai();
                dai.setDate(tvDate.getText().toString());
                dai.setMaSoDai(city.getCity2());
                dai.setGiaiThuong(giaiThuong);
                daiViewModel.insert(dai);
              //  handleTrungAnUi();
            }
        });
    }

    private void updateUIBaoLo(String date) {

        listBaoLosByDate.clear();
        float totalBaoLo = 0;

        // IF bao lo mien Nam
        if(vungMien == AppConstants.MIEN_NAM){
            for(BaoLo baoLo : listBaoLos){
                if(baoLo.getVungMien() == AppConstants.MIEN_NAM &&
                        baoLo.getDate().equals(date)){
                    listBaoLosByDate.add(baoLo);
                }
            }

            totalBaoLo = XoSoUtils.tongBaoLoMienNam(listBaoLosByDate);

            float ketQua = totalBaoLo * nguoiBan.getHeSoBaoLo();
            tvTongThuLo.setText(XoSoUtils.getInteger(ketQua));
            tvTongLo.setText(XoSoUtils.getInteger(totalBaoLo));
        }
        // IF Dau Duoi Mien Bac
        else{
            //Handle mien bac
        }

        updateTotal();

    }

    private void updateUISoDa(String date) {

        listSodasByDate.clear();
        float totalSoDa = 0;

        // IF soDa mien Nam
        if(vungMien == AppConstants.MIEN_NAM){
            for(SoDa soDa : listSodas){
                if(soDa.getVungMien() == AppConstants.MIEN_NAM &&
                        soDa.getDate().equals(date)){
                    listSodasByDate.add(soDa);
                }
            }

            totalSoDa = XoSoUtils.tongSoDasMienNam(listSodasByDate);

            float tongThuSoDa = totalSoDa*nguoiBan.getHeSoDa();
            tvTongThuDa.setText(XoSoUtils.getInteger(tongThuSoDa));
            tvTongDa.setText(XoSoUtils.getInteger(totalSoDa));
        }
        // IF soDa Mien Bac
        else{
            //Handle mien bac
        }
        updateTotal();
    }

    private void updateUIDauDuoi(String date) {


        listDauDuoisByDate.clear();
        float totalDauDuoi = 0;

        // IF dau duoi mien Nam
        if(vungMien == AppConstants.MIEN_NAM){
            for(DauDuoi dd : listDauDuois){
                if(dd.getVungMien() == AppConstants.MIEN_NAM &&
                dd.getDateString().equals(date)){
                    listDauDuoisByDate.add(dd);
                }
            }

            totalDauDuoi = XoSoUtils.tongDauDuoiBanDuoc(listDauDuoisByDate);

            int tongThuDauDuoi = (int) ((totalDauDuoi*nguoiBan.getHeSoDauDuoi())/100);
            tvTongThuDD.setText(tongThuDauDuoi+"");
            tvTongDD.setText(XoSoUtils.getInteger(totalDauDuoi));
        }
        // IF Dau Duoi Mien Bac
        else{
            //Handle mien bac
        }


        updateTotal();

    }

    private void updateTotal() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                float tongDauDuoi = Float.parseFloat(tvTongThuDD.getText().toString());
                float tongSoDa = Float.parseFloat(tvTongThuDa.getText().toString());
                float tongBaoLo = Float.parseFloat(tvTongThuLo.getText().toString());

                float tong = tongDauDuoi + tongSoDa + tongBaoLo;

                tvTongThu.setText(XoSoUtils.getInteger(tong));
                tvLoiLoiTong.setText(XoSoUtils.getInteger(tong));

                CurrentDate today = new CurrentDate();


                String dates[] = tvDate.getText().toString().split("/");
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                LotteryCity city = XoSoUtils.getLotteryCityByDate(day,month-1,today.getNam());
                if(day == today.getNgay()){
                    if(today.getGio() > 4){
                        boolean isDaiExisted = daiViewModel.isDaiExist((city.getCity1()+""),tvDate.getText().toString());
                        if(!isDaiExisted){
                            loadDai1byDate(city);
                            loadDai2byDate(city);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    handleTrungAnUi();
                                    handleTrungDauDuoi();
                                    handleTrungDa();
                                    handleTrungBaoLo();
                                }
                            },600);

                        }else{
                            handleTrungAnUi();
                            handleTrungDauDuoi();
                            handleTrungDa();
                            handleTrungBaoLo();
                        }
                    }
                }else{
                    boolean isDaiExisted = daiViewModel.isDaiExist((city.getCity1ID()+""),tvDate.getText().toString());
                    if(!isDaiExisted){
                        loadDai1byDate(city);
                        loadDai2byDate(city);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handleTrungAnUi();
                                handleTrungDauDuoi();
                                handleTrungDa();
                                handleTrungBaoLo();
                            }
                        },600);

                    }else{
                        handleTrungAnUi();
                        handleTrungDauDuoi();
                        handleTrungDa();
                        handleTrungBaoLo();
                    }
                }


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setUpTotalLoiLo();
                    }
                },1000);



            }
        }, 600);
    }

    @Override
    public void onClick(View v) {
        //Handle Action Nhap clicked
        if (v.getId() == R.id.btnInput) {
            handleActionInputDataClicked(v);
        }

        if(v.getId() == R.id.tvDate){
            String dates[] = tvDate.getText().toString().split("/");
            int day = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            CurrentDate currentDate = new CurrentDate();
            int year = currentDate.getNam();
            removeDataTrungThuongView();
            DatePickerDialog datePickerDialog = new DatePickerDialog(TongKetActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // Do something with the selected date
                    String formattedDay = String.format("%02d", dayOfMonth);
                    //  String formattedMonth = String.format("%02d",(monthOfYear + 1) );
                    String selectedDate = formattedDay + "/" + (monthOfYear + 1);
                    tvDate.setText(selectedDate);


                    updateUIDauDuoi(tvDate.getText().toString());
                    updateUISoDa(tvDate.getText().toString());
                    updateUIBaoLo(tvDate.getText().toString());

                }
            }, year, month, day);
            XoSoUtils.limited7DaysSelectedDatePicker(datePickerDialog);
            datePickerDialog.show();
        }
    }

    private void handleActionInputDataClicked(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.items_nhap);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_dau_duoi) {
                goToNhapDDActivity(false);

                return true;
            } else if (id == R.id.menu_da) {
                goToNhapSoDaActivity(false);
                return true;
            } else if (id == R.id.menu_bao_lo) {
                gotoBaoLoActivity(false);

                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void gotoBaoLoActivity(boolean b) {
        Intent intent = new Intent(TongKetActivity.this, NhapBaoLoActivity.class);
        intent.putExtra(EXTRA_NGUOI_BAN, nguoiBan);
        intent.putExtra(EXTRA_DATE, tvDate.getText().toString());
        intent.putExtra(EXTRA_VUNG_MIEN, AppConstants.MIEN_NAM);
        if (isFulScreen) {
            intent.putExtra(EXTRA_FULL_SCREEN, isFulScreen);
        }
        startActivity(intent);

    }

    private void goToNhapSoDaActivity(boolean b) {

        Intent intent = new Intent(TongKetActivity.this, NhapSoDaActitivy.class);
        intent.putExtra(EXTRA_NGUOI_BAN, nguoiBan);
        intent.putExtra(EXTRA_DATE, tvDate.getText().toString());
        intent.putExtra(EXTRA_VUNG_MIEN, AppConstants.MIEN_NAM);
        if (isFulScreen) {
            intent.putExtra(EXTRA_FULL_SCREEN, isFulScreen);
        }
        startActivity(intent);
    }

    private void goToNhapDDActivity(boolean b) {
        Intent intent = new Intent(TongKetActivity.this, NhapDDActivity.class);
        intent.putExtra(EXTRA_NGUOI_BAN, nguoiBan);
        intent.putExtra(EXTRA_DATE, tvDate.getText().toString());
        intent.putExtra(EXTRA_VUNG_MIEN, AppConstants.MIEN_NAM);
        if (isFulScreen) {
            intent.putExtra(EXTRA_FULL_SCREEN, isFulScreen);
        }
        startActivity(intent);
    }

    void handleTrungAnUi() {
        if (listDauDuoisByDate.size() > 0) {

            daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                    dais -> {
                        ln_data_an_ui.removeAllViews();
                        if(dais.size() > 0){
                            tongTrungThuongAnUi = 0;
                            for (DauDuoi dauDuoi : listDauDuoisByDate) {
                                tongTrungThuongAnUi += addAnUiItem(dauDuoi,dais);
                            }
                            Log.d("Tong", tong + "");
                        }

                    });

        }

    }

    void handleTrungDauDuoi() {

        if (listDauDuoisByDate.size() > 0) {
            daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                    dais -> {
                        ln_data_dau_duoi.removeAllViews();
                        if (dais != null) {
                            tongTrungDauDuoi = 0;
                            for (DauDuoi dauDuoi : listDauDuoisByDate) {
                                addTrungDauDuoiItem(dauDuoi,dais);
                            }
                        }
                    });


        }
    }

    void handleTrungDa() {

        daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                dais -> {
                    ln_data_da.removeAllViews();
                    tongTrungThuongSoDa = 0;
                    for (SoDa soDa : listSodasByDate) {
                        addTrungSoDa2Item(soDa,dais);
                    }
                });
    }

    void handleTrungBaoLo() {
        ln_data_da.removeAllViews();
        daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                dais -> {

                    ln_data_bao_lo.removeAllViews();
                    tongTrungThuongBaoLo = 0;
                    for (BaoLo baoLo : listBaoLosByDate) {
                        addTrungBaoLosItem(baoLo,dais);
                    }
                });

    }
    public void addTrungBaoLosItem(BaoLo baoLo, List<Dai> dais) {

        /*daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this, dais -> {

            if (dais != null) {

            }


        });*/
        if (dais.size() == 2) {
            float soTienThuong = XoSoUtils.kqTrungBaoLos2Dai(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(),
                    baoLo);
            if (soTienThuong > 0) {
                tongTrungThuongBaoLo += soTienThuong;
                String result = "";
                // result = String.format("%1$-5s %2$-4s %3$-2s %4$%-5s", baoLo.getSoCuoc(), "-", baoLo.getTienCuoc(),"=", XoSoUtils.getInteger(soTienThuong));

                result = String.format("%1$-5s %2$-4s %3$-2s %4$2s %5$-4s", baoLo.getSoCuoc(), "-", baoLo.getTienCuoc(), "=", XoSoUtils.getInteger(soTienThuong*Float.parseFloat(baoLo.getTienCuoc())));

                //   result = baoLo.getSoCuoc() + " -  " + baoLo.getTienCuoc() + " = " + XoSoUtils.getInteger(soTienThuong);
                addItemToLinearLayout(ln_data_bao_lo, result);
            }
        }
    }
    public void addTrungSoDa2Item(SoDa soDa, List<Dai> dais) {

       /* daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this, dais -> {
            if (dais != null) {

            }


        });*/

        if (dais.size() == 2) {
            if (soDa.getSoCuocThu3() == AppConstants.KHONG_CUOC_3_CON) {
                handleTrung2ConSoDa(dais, soDa, soDa.getSoCuocThu1(), soDa.getSoCuocThu2());
            } else {
                float soTienThuong = XoSoUtils.isTrung3Con(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(), soDa);
                if (soTienThuong > 0) {
                    handleTrung3ConSoDa(dais, soDa, soDa.getSoCuocThu1(), soDa.getSoCuocThu2(), soDa.getSoCuocThu3());
                } else {
                    handleTrung2ConSoDa(dais, soDa, soDa.getSoCuocThu1(), soDa.getSoCuocThu2());
                    handleTrung2ConSoDa(dais, soDa, soDa.getSoCuocThu1(), soDa.getSoCuocThu3());
                    handleTrung2ConSoDa(dais, soDa, soDa.getSoCuocThu2(), soDa.getSoCuocThu3());
                }
            }
        }
    }

    private void handleTrung2ConSoDa(List<Dai> dais, SoDa soDa, int soCuoc1, int soCuoc2) {

        String result = "";
        float soTienThuong = XoSoUtils.kqTrung2ConSoDa(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(),
                soDa, soCuoc1, soCuoc2);
        int heSo = (int) (soTienThuong / AppConstants.TRUNG_THUONG_SO_DA);
        tongTrungThuongSoDa += soTienThuong;
        //Handle Trung 2 con
        if (soTienThuong > 0) {

            if (heSo > 1) {
                result = "(" + soCuoc1 + " - " + soCuoc2 + ")x" + heSo
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            } else {
                result = "(" + soCuoc1 + " - " + soCuoc2 + ")"
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            }
            addItemToLinearLayout(ln_data_da, result);
        }
    }

    private void handleTrung3ConSoDa(List<Dai> dais, SoDa soDa, int soCuoc1, int soCuoc2, int soCuoc3) {

        String result = "";
        float soTienThuong = XoSoUtils.isTrung3Con(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(), soDa);
        int heSo = (int) (soTienThuong / AppConstants.TRUNG_THUONG_SO_DA_3_CON);


        //Handle Trung 3 con
        if (soTienThuong > 0) {
            tongTrungThuongSoDa += soTienThuong;
            if (heSo > 1) {
                result = "(" + soCuoc1 + " - " + soCuoc2 + " - " + soCuoc3 + ")x" + heSo
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            } else {
                result = "(" + soCuoc1 + " - " + soCuoc2 + " - " + soCuoc3 + ")"
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            }
        }
        addItemToLinearLayout(ln_data_da, result);
    }

    public void addTrungDauDuoiItem(DauDuoi dauDuoi, List<Dai> dais) {


        // String anUiData = dauDuoi.getSoCuoc() + dauDuoi.

        float tongDauDuoi = 0;
        if (dauDuoi.getDaiI1D() > 0) {
           /* daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI1D() + "").observe(
                    this, dai -> {

                    }
            );*/

            if (dais.get(0) != null) {
                String kqDau = XoSoUtils.kqTrungDau(dais.get(0).getGiaiThuong(), dauDuoi);
                if (!kqDau.equals("")) {
                    addItemToLinearLayout(ln_data_dau_duoi, kqDau);
                    int indexEqual = kqDau.indexOf('=');
                    String tong = kqDau.substring(indexEqual + 1);
                    tongTrungDauDuoi += Float.parseFloat(tong);
                }

                String kqDuoi = XoSoUtils.kqTrungDuoi(dais.get(0).getGiaiThuong(), dauDuoi);
                if (!kqDuoi.equals("")) {
                    addItemToLinearLayout(ln_data_dau_duoi, kqDuoi);
                    int indexEqual = kqDuoi.indexOf('=');
                    String tong = kqDuoi.substring(indexEqual + 1);
                    tongTrungDauDuoi += Float.parseFloat(tong);
                }
            }
        }
        if (dauDuoi.getDaiI2D() > 0) {
/*            daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI2D() + "").observe(
                    this, dai -> {


                    }
            );*/

            if (dais.get(1) != null) {
                String kqDau = XoSoUtils.kqTrungDau(dais.get(1).getGiaiThuong(), dauDuoi);
                if (!kqDau.equals("")) {
                    addItemToLinearLayout(ln_data_dau_duoi, kqDau);
                    int indexEqual = kqDau.indexOf('=');
                    String tong = kqDau.substring(indexEqual + 1);
                    tongTrungDauDuoi += Float.parseFloat(tong);
                }

                String kqDuoi = XoSoUtils.kqTrungDuoi(dais.get(1).getGiaiThuong(), dauDuoi);
                if (!kqDuoi.equals("")) {
                    addItemToLinearLayout(ln_data_dau_duoi, kqDuoi);
                    int indexEqual = kqDuoi.indexOf('=');
                    String tong = kqDuoi.substring(indexEqual + 1);
                    tongTrungDauDuoi += Float.parseFloat(tong);
                }
            }
        }
    }

    public float addAnUiItem(DauDuoi dauDuoi, List<Dai> dais) {
        // String anUiData = dauDuoi.getSoCuoc() + dauDuoi.

        if (dauDuoi.getDaiI1D() > 0) {
           /* daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI1D() + "").observe(
                    this, dai -> {

                    }
            );*/

            if (dais.get(0) != null) {
                String kqDau = XoSoUtils.isTrungAnUiDauMienNam(dais.get(0).getGiaiThuong(), dauDuoi);
                if (!kqDau.equals("")) {
                    addItemToLinearLayout(ln_data_an_ui, kqDau);
                    int indexEqual = kqDau.indexOf('=');
                    String tong = kqDau.substring(indexEqual + 1);
                    tongTrungThuongAnUi += Float.parseFloat(tong);
                }

                String kqDuoi = XoSoUtils.isTrungAnUiDuoiMienNam(dais.get(0).getGiaiThuong(), dauDuoi);
                if (!kqDuoi.equals("")) {
                    addItemToLinearLayout(ln_data_an_ui, kqDuoi);
                    int indexEqual = kqDuoi.indexOf('=');
                    String tong = kqDuoi.substring(indexEqual + 1);
                    tongTrungThuongAnUi += Float.parseFloat(tong);
                }
            }

        }
        if (dauDuoi.getDaiI2D() > 0) {

           /* daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI2D() + "").observe(
                    this, dai -> {


                    }
            );*/

            if (dais.get(1) != null) {
                String kqDau = XoSoUtils.isTrungAnUiDauMienNam(dais.get(1).getGiaiThuong(), dauDuoi);
                if (!kqDau.equals("")) {
                    addItemToLinearLayout(ln_data_an_ui, kqDau);
                    int indexEqual = kqDau.indexOf('=');
                    String tong = kqDau.substring(indexEqual + 1);
                    tongTrungThuongAnUi += Float.parseFloat(tong);
                }

                String kqDuoi = XoSoUtils.isTrungAnUiDuoiMienNam(dais.get(1).getGiaiThuong(), dauDuoi);
                if (!kqDuoi.equals("")) {
                    addItemToLinearLayout(ln_data_an_ui, kqDuoi);
                    int indexEqual = kqDuoi.indexOf('=');
                    String tong = kqDuoi.substring(indexEqual + 1);
                    tongTrungThuongAnUi += Float.parseFloat(tong);
                }
            }
        }

        return tongTrungThuongAnUi;
    }

    void addItemToLinearLayout(LinearLayout linearLayout, String text) {

        TextView textView = new TextView(this);
        textView.setText(text);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        linearLayout.addView(textView);
    }
    void removeDataTrungThuongView(){
        ln_data_an_ui.removeAllViews();
        ln_data_dau_duoi.removeAllViews();
        ln_data_da.removeAllViews();
        ln_data_bao_lo.removeAllViews();
        tongTrungThuongAnUi = 0;
        tongTrungDauDuoi = 0;
        tongTrungThuongSoDa = 0;
        tongTrungThuongBaoLo = 0;
    }

    void setUpTotalLoiLo(){
        float tongThuong;
        tongThuong = tongTrungThuongAnUi + tongTrungDauDuoi + tongTrungThuongSoDa + tongTrungThuongBaoLo;
        Log.d("TongAnUi", tongTrungThuongAnUi+"");
        Log.d("TongDauDuoi", tongTrungDauDuoi+"");
        Log.d("TongSoDa", tongTrungThuongSoDa+"");
        Log.d("TongBaoLo", tongTrungThuongBaoLo+"");

        tvLoiLoTrungThuong.setText(XoSoUtils.getInteger(tongThuong));
        Log.d("TongThu",tvTongThu.getText().toString());
        Log.d("TongLoiLo",tvLoiLoiTong.getText().toString());
        float ketQua = Float.parseFloat(tvTongThu.getText().toString()) - tongThuong;

        if(ketQua < 0){
            tvLoiLoKetQua.setTextColor(Color.RED);
        }else if(ketQua > 0){
            tvLoiLoKetQua.setTextColor(Color.BLUE);
        }

        tvLoiLoKetQua.setText(XoSoUtils.getInteger(ketQua));


    }
}