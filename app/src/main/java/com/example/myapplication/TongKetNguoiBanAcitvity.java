package com.example.myapplication;

import static android.provider.MediaStore.EXTRA_FULL_SCREEN;
import static com.example.myapplication.NguoiBanActivity.EXTRA_NGUOI_BAN;
import static com.example.myapplication.ThemNguoiBanActivity.EXTRA_TEN_NGUOI_BAN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

public class TongKetNguoiBanAcitvity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_DATE = "com.example.myapplication.EXTRA_DATE";
    private NguoiBan nguoiBan;

    private TextView tvTenNguoiBan;
    private Button btnNhap;

    private LinearLayout lnNoData;
    private LinearLayout lnKqTong;
    private TextView tvDate;
    private TextView tvTongDDBanDuoc;
    private TextView tvTongDDThu;
    private TextView tvTongDa;
    private TextView tvTongThuDa;
    private TextView tvTongTongBaoLo;
    private TextView tvNoData;
    private TextView tvTongThuLo;

    private List<DauDuoi> dauDuoiList;
    private List<SoDa> soDaList;
    private List<BaoLo> baoLoList;
    DauDuoiViewModel dauDuoiViewModel;
    BaoLoViewModel baoLoViewModel;
    SoDaViewModel soDaViewModel;

    private float tongThuDauDuoi;
    private float tongThuSoDa;
    private float tongThuBaoLo;

    private LinearLayout lnDataAnUis;
    private LinearLayout lnDataTrungDauDuois;
    private LinearLayout lnDataTrungSoDas;
    private LinearLayout lnDataTrungBaoLos;
    private LinearLayout lnTrungThuongParent;
    private LinearLayout lnTrungThuongNoData;
    private LinearLayout lnTrungThuongAnUi;
    private LinearLayout lnTrungThuongDauDuoi;
    private LinearLayout lnTrungThuongSoDa;
    private LinearLayout lnTrungThuongBaoLo;
    private LinearLayout lnDauDuoi;
    private LinearLayout lnSoDa;
    private LinearLayout lnBaoLo;


    private LinearLayout lnTongKetLoiLo;
    private TextView tvLoiLoiTong;
    private TextView tvLoiLoTrungThuong;
    private TextView tvLoiLoKetQua;

    private boolean loadingDauDuoiFinished;
    private boolean loadingSoDasFinished;
    private boolean loadingBaoLosFinished;
    private float tongAnUi;

    private TextView tvTongThu;

    private float tongThuTheoNugoiBanMienNam = 0;

    private boolean isHasData;

    private boolean isTongLayoutDataShowed = false;

    private float tongTrungThuong;
    private float loiLoKetQua;
    ProgressBar progressBar;

    private DaiViewModel daiViewModel;
    private float tongBaoLo = 0;
    private float tongDauDuoi;
    private float tongSoDa;

    private boolean isLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_ket_nguoi_ban);
        Intent intent = getIntent();
        nguoiBan = (NguoiBan) intent.getSerializableExtra(EXTRA_NGUOI_BAN);

        connectUiWithXML();
        handleViewsAction();
        initailize();

        setUpDataForViews();

        isLoaded = true;

        daiViewModel.getAllDais().observe(this, dais -> {

            if (dais.size() > 0) {
              /*  Log.d("Dai", dais.get(0).getMaSoDai());
                if(dais.size() % 2 == 1){
                    daiViewModel.delete(dais.get(dais.size() - 1));
                }*/
            }
        });

        dauDuoiList.clear();

    }

    private void setUpDataForViews() {
        tvTenNguoiBan.setText(nguoiBan.getTenNguoiBan());
        /* Get the current date */
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String formattedDay = String.format("%02d", day);
        String selectedDate = formattedDay + "/" + (month + 1);
        tvDate.setText(selectedDate);
        //loadingDataFromDataBase(nguoiBan,selectedDate);
    }

    private void loadingDataFromDataBase(NguoiBan nguoiBan, String selectedDate) {

        lnDataAnUis.removeAllViews();
        lnDataTrungDauDuois.removeAllViews();
        lnDataTrungSoDas.removeAllViews();
        lnDataTrungBaoLos.removeAllViews();
        dauDuoiViewModel.getAllDauDuoiWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(), selectedDate).observe(this, dauDuois -> {
                    dauDuoiList.clear();

                    dauDuoiList = dauDuois;
                    if (dauDuoiList.size() != 0) {
                        showLayoutTinhTong();

                    }
                    handleUiTinhTong();

                }

        );
        soDaViewModel.getAllSoDaWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(), selectedDate).observe(this, soDas -> {
                    soDaList.clear();
                    soDaList.addAll(soDas);
                    if (soDaList.size() != 0) {
                        showLayoutTinhTong();

                    }
                    handleUiTinhTongSoDa();

                }

        );
        baoLoViewModel.getAllBaoLoWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(), selectedDate).observe(this, baoLos -> {
                    baoLoList.clear();
                    baoLoList.addAll(baoLos);
                    if (baoLoList.size() != 0) {
                        showLayoutTinhTong();

                    }
                    handleUiTinhTongBaoLo();

                    fakeLoadingWaitDataLoaded(500);
                }

        );

    }

    private void showLayoutTinhTong() {
        if (isTongLayoutDataShowed == false) {
            isTongLayoutDataShowed = true;
            lnNoData.setVisibility(View.INVISIBLE);
            lnKqTong.setVisibility(View.VISIBLE);
            tvTongThu.setVisibility(View.VISIBLE);
        }
    }

    private void setLayoutTinhTongToDefault() {

        if (isTongLayoutDataShowed == true) {
            isTongLayoutDataShowed = false;
            lnNoData.setVisibility(View.VISIBLE);
            lnKqTong.setVisibility(View.INVISIBLE);
            tvTongThu.setVisibility(View.INVISIBLE);
        }
    }

    private void initailize() {

        /*initialize ViewModel*/
        daiViewModel = new ViewModelProvider(this).get(DaiViewModel.class);
        dauDuoiViewModel = new ViewModelProvider(this).get(DauDuoiViewModel.class);
        soDaViewModel = new ViewModelProvider(this).get(SoDaViewModel.class);
        baoLoViewModel = new ViewModelProvider(this).get(BaoLoViewModel.class);

        /*initialize Lists*/
        dauDuoiList = new ArrayList<>();
        soDaList = new ArrayList<>();
        baoLoList = new ArrayList<>();
    }

    private void handleViewsAction() {
        btnNhap.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        lnDauDuoi.setOnClickListener(this);
        lnSoDa.setOnClickListener(this);
        lnBaoLo.setOnClickListener(this);
    }

    private void connectUiWithXML() {
        tvTenNguoiBan = findViewById(R.id.tvNguoiBan);
        btnNhap = findViewById(R.id.btnInput);
        lnNoData = findViewById(R.id.llNoData);
        lnKqTong = findViewById(R.id.lnKqTong);
        tvDate = findViewById(R.id.tvDate);
        tvTongDDBanDuoc = findViewById(R.id.tvTongDD);
        tvTongDDThu = findViewById(R.id.tvTongThuDD);
        tvNoData = findViewById(R.id.tvNoData);

        tvTongDa = findViewById(R.id.tvTongDa);
        tvTongThuDa = findViewById(R.id.tvTongThuDa);
        tvTongTongBaoLo = findViewById(R.id.tvTongLo);
        tvTongThuLo = findViewById(R.id.tvTongThuLo);
        tvTongThu = findViewById(R.id.tvTongThu);

        lnTrungThuongAnUi = findViewById(R.id.ln_an_ui);
        lnTrungThuongDauDuoi = findViewById(R.id.ln_an_trung_dau_duoi);
        lnTrungThuongSoDa = findViewById(R.id.ln_trung_da);
        lnTrungThuongBaoLo = findViewById(R.id.ln_trung_bao_lo);

        lnDataAnUis = findViewById(R.id.ln_data_an_ui);
        lnDataTrungDauDuois = findViewById(R.id.ln_data_dau_duoi);
        lnDataTrungSoDas = findViewById(R.id.ln_data_da);
        lnDataTrungBaoLos = findViewById(R.id.ln_data_bao_lo);
        lnTrungThuongParent = findViewById(R.id.ln_trung_thuong);
        lnTrungThuongNoData = findViewById(R.id.linear_no_trung_thuong_data);
        lnDauDuoi = findViewById(R.id.lnDauDuoi);
        lnSoDa = findViewById(R.id.lnSoDa);
        lnBaoLo = findViewById(R.id.lnBaoLo);

        lnTongKetLoiLo = findViewById(R.id.lnTongKetLoiLo);
        tvLoiLoiTong = findViewById(R.id.tvLoiLoiTong);
        tvLoiLoTrungThuong = findViewById(R.id.tvLoiLoTrungThuong);
        tvLoiLoKetQua = findViewById(R.id.tvLoiLoKetQua);
    }

    private float handleUiTinhTongSoDa() {

        lnDataTrungSoDas.removeAllViews();
        float tongBanDuoc = XoSoUtils.tongSoDasMienNam(soDaList);
        String kq = XoSoUtils.getInteger(tongBanDuoc);
        // String formattedDay = String.format("%02d", dayOfMonth);
        tvTongDa.setText(kq);
        float tongThu = tongBanDuoc * nguoiBan.getHeSoDa();
        tvTongThuDa.setText(XoSoUtils.getInteger(tongThu));
        tvTongThu.setVisibility(View.VISIBLE);
        tvTongThu.setText(tongThu + "");
        tongThuSoDa = tongThu;

        /* */
        return tongThu;
    }

    private float handleUiTinhTongBaoLo() {
        lnDataTrungBaoLos.removeAllViews();
        float tongBanDuoc = XoSoUtils.tongBaoLoMienNam(baoLoList);
        String kq = XoSoUtils.getInteger(tongBanDuoc);
        tvTongTongBaoLo.setText(kq);
        float tongThu = tongBanDuoc * nguoiBan.getHeSoBaoLo();
        tvTongThuLo.setText(XoSoUtils.getInteger(tongThu));
        tongThuBaoLo = tongThu;

        /*  */
        return tongThu;

    }

    private float handleUiTinhTong() {
        // Create a DatePickerDialog and show it
        lnDataAnUis.removeAllViews();
        lnDataTrungDauDuois.removeAllViews();
        loadingDauDuoiFinished = true;
        float tongBanDuoc = XoSoUtils.tongDauDuoiBanDuoc(dauDuoiList);
        String kq = XoSoUtils.getInteger(tongBanDuoc);
        tvTongDDBanDuoc.setText(kq);
        int tongThu = (int) (tongBanDuoc * nguoiBan.getHeSoDauDuoi()) / 100;
        tvTongDDThu.setText(XoSoUtils.getInteger(tongThu));
        tongThuDauDuoi = tongThu;

        return tongThu;
    }

    void handleTrungBaoLo() {
        daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                dais -> {

                    lnDataTrungBaoLos.removeAllViews();
                    for (BaoLo baoLo : baoLoList) {
                        addTrungBaoLosItem(baoLo);
                    }
                });

    }

    void handleTrungDa() {
        daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                dais -> {
                    lnDataTrungSoDas.removeAllViews();
                    for (SoDa soDa : soDaList) {
                        addTrungSoDa2Item(soDa);
                    }
                });
    }

    void handleTrungAnUi() {
        if (dauDuoiList.size() > 0) {

            daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                    dais -> {
                        lnDataAnUis.removeAllViews();
                        float tong = 0;
                        for (DauDuoi dauDuoi : dauDuoiList) {
                            tong += addAnUiItem(dauDuoi);
                        }
                        Log.d("Tong", tong + "");
                    });

        }

    }

    void handleTrungDauDuoi() {
        if (dauDuoiList.size() > 0) {
            daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this,
                    dais -> {
                        lnDataTrungDauDuois.removeAllViews();
                        if (dais != null) {
                            for (DauDuoi dauDuoi : dauDuoiList) {
                                addTrungDauDuoiItem(dauDuoi);
                            }
                        }
                    });


        }
    }

    @Override
    public void onClick(View v) {

        //Handle Action Nhap clicked
        if (v.getId() == R.id.btnInput) {
            handleActionInputDataClicked(v);
        }

        // Handle action TextView Date Click
        if (v.getId() == R.id.tvDate) {
            handleActionTvDateClicked();
        }
        if (v.getId() == R.id.lnDauDuoi) {
            goToNhapDDActivity(true);

        }
        if (v.getId() == R.id.lnSoDa) {
            goToNhapSoDaActivity(true);

        }
        if (v.getId() == R.id.lnBaoLo) {
            gotoBaoLoActivity(true);

        }

    }

    void goToNhapDDActivity(boolean isFulScreen) {
        Intent intent = new Intent(TongKetNguoiBanAcitvity.this, NhapDDActivity.class);
        intent.putExtra(EXTRA_NGUOI_BAN, nguoiBan);
        intent.putExtra(EXTRA_DATE, tvDate.getText().toString());
        if (isFulScreen) {
            intent.putExtra(EXTRA_FULL_SCREEN, isFulScreen);
        }
        startActivity(intent);
    }

    void goToNhapSoDaActivity(boolean isFulScreen) {
        Intent intent = new Intent(TongKetNguoiBanAcitvity.this, NhapSoDaActitivy.class);
        intent.putExtra(EXTRA_NGUOI_BAN, nguoiBan);
        intent.putExtra(EXTRA_DATE, tvDate.getText().toString());
        if (isFulScreen) {
            intent.putExtra(EXTRA_FULL_SCREEN, isFulScreen);
        }
        startActivity(intent);
    }

    void gotoBaoLoActivity(boolean isFulScreen) {
        Intent intent = new Intent(TongKetNguoiBanAcitvity.this, NhapBaoLoActivity.class);
        intent.putExtra(EXTRA_NGUOI_BAN, nguoiBan);
        intent.putExtra(EXTRA_DATE, tvDate.getText().toString());
        if (isFulScreen) {
            intent.putExtra(EXTRA_FULL_SCREEN, isFulScreen);
        }
        startActivity(intent);
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


    public float addAnUiItem(DauDuoi dauDuoi) {
        // String anUiData = dauDuoi.getSoCuoc() + dauDuoi.

        if (dauDuoi.getDaiI1D() > 0) {
            daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI1D() + "").observe(
                    this, dai -> {
                        if (dai != null) {
                            String kqDau = XoSoUtils.isTrungAnUiDauMienNam(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDau.equals("")) {
                                addItemToLinearLayout(lnDataAnUis, kqDau);
                                int indexEqual = kqDau.indexOf('=');
                                String tong = kqDau.substring(indexEqual + 1);
                                tongAnUi += Float.parseFloat(tong);
                            }

                            String kqDuoi = XoSoUtils.isTrungAnUiDuoiMienNam(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDuoi.equals("")) {
                                addItemToLinearLayout(lnDataAnUis, kqDuoi);
                                int indexEqual = kqDuoi.indexOf('=');
                                String tong = kqDuoi.substring(indexEqual + 1);
                                tongAnUi += Float.parseFloat(tong);
                            }
                        }

                    }
            );
        }
        if (dauDuoi.getDaiI2D() > 0) {

            daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI2D() + "").observe(
                    this, dai -> {
                        if (dai != null) {
                            String kqDau = XoSoUtils.isTrungAnUiDauMienNam(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDau.equals("")) {
                                addItemToLinearLayout(lnDataAnUis, kqDau);
                                int indexEqual = kqDau.indexOf('=');
                                String tong = kqDau.substring(indexEqual + 1);
                                tongAnUi += Float.parseFloat(tong);
                            }

                            String kqDuoi = XoSoUtils.isTrungAnUiDuoiMienNam(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDuoi.equals("")) {
                                addItemToLinearLayout(lnDataAnUis, kqDuoi);
                                int indexEqual = kqDuoi.indexOf('=');
                                String tong = kqDuoi.substring(indexEqual + 1);
                                tongAnUi += Float.parseFloat(tong);
                            }
                        }

                    }
            );
        }

        return tongAnUi;
    }


    public void addTrungDauDuoiItem(DauDuoi dauDuoi) {


        // String anUiData = dauDuoi.getSoCuoc() + dauDuoi.

        if (dauDuoi.getDaiI1D() > 0) {
            daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI1D() + "").observe(
                    this, dai -> {
                        if (dai != null) {
                            String kqDau = XoSoUtils.kqTrungDau(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDau.equals("")) {
                                addItemToLinearLayout(lnDataTrungDauDuois, kqDau);
                                int indexEqual = kqDau.indexOf('=');
                                String tong = kqDau.substring(indexEqual + 1);
                                tongDauDuoi += Float.parseFloat(tong);
                            }

                            String kqDuoi = XoSoUtils.kqTrungDuoi(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDuoi.equals("")) {
                                addItemToLinearLayout(lnDataTrungDauDuois, kqDuoi);
                                int indexEqual = kqDuoi.indexOf('=');
                                String tong = kqDuoi.substring(indexEqual + 1);
                                tongDauDuoi += Float.parseFloat(tong);
                            }
                        }
                    }
            );
        }
        if (dauDuoi.getDaiI2D() > 0) {
            daiViewModel.getDaiByID(tvDate.getText().toString(), dauDuoi.getDaiI2D() + "").observe(
                    this, dai -> {
                        if (dai != null) {
                            String kqDau = XoSoUtils.kqTrungDau(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDau.equals("")) {
                                addItemToLinearLayout(lnDataTrungDauDuois, kqDau);
                                int indexEqual = kqDau.indexOf('=');
                                String tong = kqDau.substring(indexEqual + 1);
                                tongDauDuoi += Float.parseFloat(tong);
                            }

                            String kqDuoi = XoSoUtils.kqTrungDuoi(dai.getGiaiThuong(), dauDuoi);
                            if (!kqDuoi.equals("")) {
                                addItemToLinearLayout(lnDataTrungDauDuois, kqDuoi);
                                int indexEqual = kqDuoi.indexOf('=');
                                String tong = kqDuoi.substring(indexEqual + 1);
                                tongDauDuoi += Float.parseFloat(tong);
                            }
                        }

                    }
            );
        }
    }

    public void addTrungSoDa2Item(SoDa soDa) {

        daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this, dais -> {
            if (dais != null) {
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


        });
    }

    public void addTrungBaoLosItem(BaoLo baoLo) {

        daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this, dais -> {

            if (dais != null) {
                if (dais.size() == 2) {
                    float soTienThuong = XoSoUtils.kqTrungBaoLos2Dai(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(),
                            baoLo);
                    if (soTienThuong > 0) {
                        tongBaoLo += soTienThuong;
                        String result = "";
                       // result = String.format("%1$-5s %2$-4s %3$-2s %4$%-5s", baoLo.getSoCuoc(), "-", baoLo.getTienCuoc(),"=", XoSoUtils.getInteger(soTienThuong));

                        result = String.format("%1$-5s %2$-4s %3$-2s %4$2s %5$-4s", baoLo.getSoCuoc(), "-", baoLo.getTienCuoc(), "=", XoSoUtils.getInteger(soTienThuong*Float.parseFloat(baoLo.getTienCuoc())));

                        //   result = baoLo.getSoCuoc() + " -  " + baoLo.getTienCuoc() + " = " + XoSoUtils.getInteger(soTienThuong);
                        addItemToLinearLayout(lnDataTrungBaoLos, result);
                    }
                }
            }


        });
    }

    private void handleTrung2ConSoDa(List<Dai> dais, SoDa soDa, int soCuoc1, int soCuoc2) {

        String result = "";
        float soTienThuong = XoSoUtils.kqTrung2ConSoDa(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(),
                soDa, soCuoc1, soCuoc2);
        int heSo = (int) (soTienThuong / AppConstants.TRUNG_THUONG_SO_DA);
        tongSoDa += soTienThuong;
        //Handle Trung 2 con
        if (soTienThuong > 0) {

            if (heSo > 1) {
                result = "(" + soCuoc1 + " - " + soCuoc2 + ")x" + heSo
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            } else {
                result = "(" + soCuoc1 + " - " + soCuoc2 + ")"
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            }
            addItemToLinearLayout(lnDataTrungSoDas, result);
        }
    }

    private void handleTrung3ConSoDa(List<Dai> dais, SoDa soDa, int soCuoc1, int soCuoc2, int soCuoc3) {

        String result = "";
        float soTienThuong = XoSoUtils.isTrung3Con(dais.get(0).getGiaiThuong(), dais.get(1).getGiaiThuong(), soDa);
        int heSo = (int) (soTienThuong / AppConstants.TRUNG_THUONG_SO_DA_3_CON);


        //Handle Trung 3 con
        if (soTienThuong > 0) {
            tongSoDa += soTienThuong;
            if (heSo > 1) {
                result = "(" + soCuoc1 + " - " + soCuoc2 + " - " + soCuoc3 + ")x" + heSo
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            } else {
                result = "(" + soCuoc1 + " - " + soCuoc2 + " - " + soCuoc3 + ")"
                        + "- " + XoSoUtils.getInteger(soDa.getTienCuoc()) + " = " + XoSoUtils.getInteger(soTienThuong) + "";
            }
        }
        addItemToLinearLayout(lnDataTrungSoDas, result);
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

    void setEnableAnUiLayoutTrungThuong(boolean hasData) {
        if (hasData == true) {
            lnTrungThuongParent.setVisibility(View.VISIBLE);
            lnTrungThuongNoData.setVisibility(View.INVISIBLE);
            lnTrungThuongAnUi.setVisibility(View.VISIBLE);
        }
    }

    void setEnableDauDuoiiLayoutTrungThuong(boolean hasData) {
        if (hasData == true) {
            lnTrungThuongParent.setVisibility(View.VISIBLE);
            lnTrungThuongNoData.setVisibility(View.INVISIBLE);
            lnTrungThuongDauDuoi.setVisibility(View.VISIBLE);
        }
    }

    void setEnableSoDaLayoutTrungThuong(boolean hasData) {
        if (hasData == true) {
            lnTrungThuongParent.setVisibility(View.VISIBLE);
            lnTrungThuongNoData.setVisibility(View.INVISIBLE);
            lnTrungThuongSoDa.setVisibility(View.VISIBLE);
        }
    }

    void setEnableBaoLoLayoutTrungThuong(boolean hasData) {
        if (hasData == true) {
            lnTrungThuongParent.setVisibility(View.VISIBLE);
            lnTrungThuongNoData.setVisibility(View.INVISIBLE);
            lnTrungThuongBaoLo.setVisibility(View.VISIBLE);
        }
    }


    void handleActionTvDateClicked() {

        //Initialize default selected date dialog
        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        String currentDate = tvDate.getText().toString(); // Your current date string
        String[] dateParts = currentDate.split("/"); // Split the date string into day and month parts
        int day = Integer.parseInt(dateParts[0]); // Convert the day string to an integer
        int month = Integer.parseInt(dateParts[1]) - 1; // Convert the month string to an integer
        dauDuoiList.clear();

      /*  c.set(2023, Calendar.APRIL, 14);
        day =  c.get(c.DAY_OF_MONTH);
        month =  c.get(c.MONTH);
        year =  c.get(c.YEAR);*/
        DatePickerDialog datePickerDialog = new DatePickerDialog(TongKetNguoiBanAcitvity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Do something with the selected date
                String formattedDay = String.format("%02d", dayOfMonth);
                //  String formattedMonth = String.format("%02d",(monthOfYear + 1) );
                String selectedDate = formattedDay + "/" + (monthOfYear + 1);
                tvDate.setText(selectedDate);
                final Calendar c = Calendar.getInstance();
                int currentDay = c.get(Calendar.YEAR);
                CurrentDate today = new CurrentDate();

                if (today.getNgay() == dayOfMonth && today.getThang() == monthOfYear) {

                } else {
                    LotteryCity city = XoSoUtils.getLotteryCityByDate(dayOfMonth, monthOfYear, year);
                    XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity1()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                        @Override
                        public void ketQua(GiaiThuong giaiThuong) {
                            Dai dai = new Dai();
                            dai.setDate(selectedDate);
                            dai.setMaSoDai(city.getCity1ID() + "");
                            dai.setGiaiThuong(giaiThuong);
                            daiViewModel.insert(dai);
                            Log.d("Giai Tam1", giaiThuong.getGiaiTam());
                        }
                    });

                    XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity2()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                        @Override
                        public void ketQua(GiaiThuong giaiThuong) {
                            Dai dai = new Dai();
                            dai.setDate(selectedDate);
                            dai.setMaSoDai(city.getCity2ID() + "");
                            dai.setGiaiThuong(giaiThuong);
                            daiViewModel.insert(dai);
                            Log.d("Giai Tam2", giaiThuong.getGiaiTam());
                        }
                    });
                }

                /*if(dayOfMonth < currentDay){
                    LotteryCity city = XoSoUtils.getLotteryCityByDate(dayOfMonth,monthOfYear,year);
                    boolean isDaiExisted = daiViewModel.isDaiExist((city.getCity1ID()+""),selectedDate);
                    if(isDaiExisted == false){
                        XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity1()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                            @Override
                            public void ketQua(GiaiThuong giaiThuong) {
                                Dai dai = new Dai();

                                dai.setMaSoDai(city.getCity1ID()+"");
                                dai.setGiaiThuong(giaiThuong);

                                String selectedDate = tvDate.getText().toString(); // Your current date string
                                dai.setDate(selectedDate);
                                daiViewModel.insert(dai);
                            }
                        });
                        XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity2()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                            @Override
                            public void ketQua(GiaiThuong giaiThuong) {
                                Dai dai = new Dai();
                                dai.setMaSoDai(city.getCity2ID()+"");
                                dai.setGiaiThuong(giaiThuong);

                                String selectedDate = tvDate.getText().toString(); // Your current date string
                                dai.setDate(selectedDate);

                                daiViewModel.insert(dai);
                            }
                        });
                    }

                }*/

              /*  String formattedMonth = String.format("%02d",  (monthOfYear + 1));
                String d = formattedDay + "/" + formattedMonth + "/" + year;
                int daysOfWeek = XoSoUtils.getDateOfWeek(d);
                city = XoSoUtils.getDais(daysOfWeek);

                XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity1()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                    @Override
                    public void ketQua(GiaiThuong giaiThuong) {
                        Dai dai = new Dai();
                        dai.setDate( XoSoUtils.getCurrentDate());
                        dai.setMaSoDai(city.getCity1ID()+"");
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

                XoSoUtils.loadKetQuaXoSoFromInternet(city.getCity2()).setListener(new PareseURLWebScrapping.LayKetQuaCallBack() {
                    @Override
                    public void ketQua(GiaiThuong giaiThuong) {

                    }
                });*/
                loadingDataFromDataBase(nguoiBan, selectedDate);
            }
        }, year, month, day);
        XoSoUtils.limited7DaysSelectedDatePicker(datePickerDialog);
        datePickerDialog.show();
        setLayoutTinhTongToDefault();

    }

    void fakeLoadingWaitDataLoaded(int miliseconds) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                if (isTongLayoutDataShowed = true) {
                    tongThuTheoNugoiBanMienNam = tongThuDauDuoi + tongThuBaoLo + tongThuSoDa;
                    tvTongThu.setText(XoSoUtils.getInteger(tongThuTheoNugoiBanMienNam));
                    //  Toast.makeText(TongKetNguoiBanAcitvity.this, "Có dữ liệu", Toast.LENGTH_SHORT).show();
                    setLayoutTrungThuongShow();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvLoiLoiTong.setText(XoSoUtils.getInteger(tongThuTheoNugoiBanMienNam));
                            float tongThuong = tongAnUi + tongDauDuoi + tongSoDa + tongBaoLo;
                            tvLoiLoTrungThuong.setText(XoSoUtils.getInteger(tongThuong));
                            float tongKqLoiLo = tongThuTheoNugoiBanMienNam - tongThuong;
                            tvLoiLoKetQua.setText(XoSoUtils.getInteger(tongKqLoiLo));
                            if (tongKqLoiLo > 0) {
                                tvLoiLoKetQua.setTextColor(Color.BLUE);
                            } else {
                                tvLoiLoKetQua.setTextColor(Color.RED);
                            }

                            tongAnUi = 0;
                            tongDauDuoi = 0;
                            tongSoDa = 0;
                            tongBaoLo = 0;
                        }
                    }, 600);

                }
            }
        }, miliseconds);
    }

    void setLayoutTrungThuongShow() {
        if (dauDuoiList.size() > 0 || soDaList.size() > 0 || baoLoList.size() > 0) {


            daiViewModel.getDaisByDate(tvDate.getText().toString()).observe(this, dais -> {

                if (dais.size() > 0) {
                    lnTrungThuongNoData.setVisibility(View.INVISIBLE);
                    lnTrungThuongParent.setVisibility(View.VISIBLE);
                    lnTongKetLoiLo.setVisibility(View.VISIBLE);
                    handleTrungAnUi();
                    handleTrungDauDuoi();
                    handleTrungDa();
                    handleTrungBaoLo();

                } else {
                    lnTrungThuongNoData.setVisibility(View.VISIBLE);
                    lnTrungThuongParent.setVisibility(View.INVISIBLE);
                    lnTongKetLoiLo.setVisibility(View.INVISIBLE);
                    tvNoData.setText("Chưa có kết quả trúng thưởng\n Hãy quay lại sau 4h:30");
                }

            });
        } else {
            lnTrungThuongNoData.setVisibility(View.VISIBLE);
            lnTrungThuongParent.setVisibility(View.INVISIBLE);
            lnTongKetLoiLo.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data here
        isTongLayoutDataShowed = false;
        loadingDataFromDataBase(nguoiBan, tvDate.getText().toString());
    }

    /*void loadDataFromDatabase() {

        // In MainActivity:

        // Declare a MediatorLiveData to observe changes in the three LiveData instances
         MediatorLiveData<Float> totalLiveData = new MediatorLiveData<>();

        // In onCreate():
        // Add the three LiveData instances to the MediatorLiveData and observe changes
        totalLiveData.addSource(dauDuoiViewModel.getAllDauDuoiWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(),tvDate.getText().toString()), new Observer<List<DauDuoi>>() {
            @Override
            public void onChanged(List<DauDuoi> dauDuoiList) {
                // Update the MediatorLiveData with the sum of soTienCuoc
               // float total = calculateTotal(dauDuoiList, soDaViewModel.getSoDaListLiveData().getValue(), baoLoViewModel.getBaoLoListLiveData().getValue());
             //   totalLiveData.setValue(total);
            }
        });

        totalLiveData.addSource(soDaViewModel.getAllSoDaWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(),tvDate.getText().toString()), new Observer<List<SoDa>>() {
            @Override
            public void onChanged(List<SoDa> soDaList) {
                // Update the MediatorLiveData with the sum of soTienCuoc
                float total = calculateTotal(dauDuoiViewModel.getDauDuoiListLiveData().getValue(), soDaList, baoLoViewModel.getBaoLoListLiveData().getValue());
                totalLiveData.setValue(total);
            }
        });

        totalLiveData.addSource(baoLoViewModel.getAllBaoLoWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(),tvDate.getText().toString()), new Observer<List<BaoLo>>() {
            @Override
            public void onChanged(List<BaoLo> baoLoList) {
                // Update the MediatorLiveData with the sum of soTienCuoc
                float total = calculateTotal(dauDuoiViewModel.getDauDuoiListLiveData().getValue(), soDaViewModel.getSoDaListLiveData().getValue(), baoLoList);
                totalLiveData.setValue(total);
            }
        });

        // Define a method to calculate the total of soTienCuoc from the three lists
         float calculateTotal;
        (List<DauDuoi> dauDuoiList, List < SoDa > soDaList, List < BaoLo > baoLoList){
            float total = 0;

            // Calculate the total for dauDuoiList
            if (dauDuoiList != null) {
                for (DauDuoi dauDuoi : dauDuoiList) {
                    total += dauDuoi.getSoTienCuoc();
                }
            }

            // Calculate the total for soDaList
            if (soDaList != null) {
                for (SoDa soDa : soDaList) {
                    total += soDa.getSoTienCuoc();
                }
            }

            // Calculate the total for baoLoList
            if (baoLoList != null) {
                for (BaoLo baoLo : baoLoList) {
                    total += baoLo.getSoTienCuoc();
                }
            }

            return total;
        }

        // Finally, observe changes in the totalLiveData and update the TextView
        totalLiveData.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                if (total != null) {
                    tvTongThu.setText(String.format(Locale.getDefault(), "Total: %.2f", total));
                }
            }
        });

    }*/
}