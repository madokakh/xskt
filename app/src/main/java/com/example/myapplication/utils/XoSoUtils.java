package com.example.myapplication.utils;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;

import com.example.myapplication.AppConstants;
import com.example.myapplication.LotteryCity;
import com.example.myapplication.LotterySchedule;
import com.example.myapplication.PareseURLWebScrapping;
import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.GiaiThuong;
import com.example.myapplication.model.SoDa;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XoSoUtils {

    public GiaiThuong stringKetquaToGiaiThuongObject(String ketqua) {

        GiaiThuong giaiThuong = new GiaiThuong();

        return giaiThuong;
    }

    public static List<String> giaiThuongToList(GiaiThuong giaiThuong) {

        List<String> giaiThuongList = new ArrayList<>();
        giaiThuongList.add(giaiThuong.getGiaiTam());
        giaiThuongList.add(giaiThuong.getGiaiBay());
        giaiThuongList.addAll(giaiThuong.getGiaiSau());
        giaiThuongList.add(giaiThuong.getGiaiNam());
        giaiThuongList.addAll(giaiThuong.getGiaiTu());
        giaiThuongList.addAll(giaiThuong.getGiaiBa());
        giaiThuongList.add(giaiThuong.getGiaiNhi());
        giaiThuongList.add(giaiThuong.getGiaiNhat());
        giaiThuongList.add(giaiThuong.getGiaiDacBiet());
        return giaiThuongList;
    }

    public static int isContainNumber(GiaiThuong giaiThuong1, GiaiThuong giaiThuong2, String soCuoc) {

        int dem = 0;
        List<String> lists = giaiThuongToList(giaiThuong1);
        lists.addAll(giaiThuongToList(giaiThuong2));
        for (String s : lists) {

            String lastTwoChars;
            if (s.length() >= 2) {
                lastTwoChars = s.substring(s.length() - 2);
            } else {
                lastTwoChars = s;
            }
            if (lastTwoChars.equals(soCuoc)) {
                dem++;
            }
        }

        return dem;
    }

    public static float kqTrung2ConSoDa(GiaiThuong giaiThuong1, GiaiThuong giaiThuong2, SoDa soDa, int soCuoc1, int soCuoc2) {

        int soLanXuanHienSo1 = isContainNumber(giaiThuong1, giaiThuong2, soCuoc1 + "");
        int soLanXuanHienSo2 = isContainNumber(giaiThuong1, giaiThuong2, soCuoc2 + "");

        int heSoTrung = 0;
        boolean isTrung;
        if (soLanXuanHienSo1 >= 1 && soLanXuanHienSo2 >= 1) {
            isTrung = true;
            if (soLanXuanHienSo1 >= soLanXuanHienSo2) {
                heSoTrung = soLanXuanHienSo2;
            } else {
                heSoTrung = soLanXuanHienSo1;
            }
        }

        float tienThuong = AppConstants.TRUNG_THUONG_SO_DA * soDa.getTienCuoc() * heSoTrung;

        return tienThuong;
    }

    public static float isTrung3Con(GiaiThuong giaiThuong1, GiaiThuong giaiThuong2,
                                    SoDa soDa) {

        int soLanXuanHienSo1 = isContainNumber(giaiThuong1, giaiThuong2, soDa.getSoCuocThu1() + "");
        int soLanXuanHienSo2 = isContainNumber(giaiThuong1, giaiThuong2, soDa.getSoCuocThu2() + "");
        int soLanXuanHienSo3 = isContainNumber(giaiThuong1, giaiThuong2, soDa.getSoCuocThu3() + "");


        int heSoTrung = 0;

        if (soLanXuanHienSo1 >= 1 && soLanXuanHienSo2 >= 1 && soLanXuanHienSo3 >= 1) {
            heSoTrung = soLanXuanHienSo1;
            if (soLanXuanHienSo2 < heSoTrung) {
                heSoTrung = soLanXuanHienSo2;
            }
            if (soLanXuanHienSo3 < heSoTrung) {
                heSoTrung = soLanXuanHienSo3;
            }
        }

        float tienThuong = AppConstants.TRUNG_THUONG_SO_DA_3_CON * soDa.getTienCuoc() * heSoTrung;

        return tienThuong;
    }

    public static boolean isTrungDauDuoiMienNam(GiaiThuong giaiThuong, DauDuoi dauDuoi) {

        String soCuoc = dauDuoi.getSoCuoc();
        if (giaiThuong.getGiaiTam().equals(soCuoc)
                || giaiThuong.getGiaiDacBiet().substring(4, 6).equals(soCuoc)) {
            return true;
        }
        return false;
    }

    public static boolean isTrungAnUiDauDuoi(GiaiThuong giaiThuong, DauDuoi dauDuoi) {

        String soDau = giaiThuong.getGiaiTam();
        String soDuoi = giaiThuong.getGiaiDacBiet().substring(4, 6);
        boolean isTrungAnUi = false;
        String soCuoc1 = String.format("%02d", Integer.parseInt(dauDuoi.getSoCuoc()) + 1) + "";
        String soCuoc2 = String.format("%02d", Integer.parseInt(dauDuoi.getSoCuoc()) - 1) + "";

        if (soCuoc1.equals(soDau) || soCuoc1.equals(soDuoi) ||
                soCuoc2.equals(soDau) || soCuoc2.equals(soDuoi)
        ) {
            return true;
        }

        return isTrungAnUi;

    }

    public static boolean isTrungSoDauMienNam(GiaiThuong giaiThuong, String soCuoc) {

        return giaiThuong.getGiaiTam().equals(soCuoc);
    }

    public static boolean isTrungSoCuoiMienNam(GiaiThuong giaiThuong, String soCuoc) {

        String soCuocCuoi = soCuoc;
        String haiSoCuoiGiaiDacBiet = giaiThuong.getGiaiDacBiet().substring(4, 6);
        return soCuocCuoi.equals(haiSoCuoiGiaiDacBiet);
    }

    public static String kqTrungDau(GiaiThuong giaiThuong, DauDuoi dauDuoi) {

        String result = "";

        float tienThuong = dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_THUONG_DAU_DUOI;
        if (isTrungSoDauMienNam(giaiThuong, dauDuoi.getSoCuoc())) {
            result = dauDuoi.getSoCuoc() + " - " + getInteger(dauDuoi.getTienCuocSoDau()) + " = " + getInteger(tienThuong);
        }
        return result;
    }

    public static String kqTrungDuoi(GiaiThuong giaiThuong, DauDuoi dauDuoi) {

        String result = "";

        float tienThuong = dauDuoi.getTienCuocSoDuoi() * AppConstants.TRUNG_THUONG_DAU_DUOI;
        if (isTrungSoCuoiMienNam(giaiThuong, dauDuoi.getSoCuoc())) {
            result = dauDuoi.getSoCuoc() + " - " + getInteger(dauDuoi.getTienCuocSoDuoi()) + " = " + getInteger(tienThuong);
        }
        return result;
    }

    public static boolean isTrungAnUiDauMienNam(GiaiThuong giaiThuong, String soCuoc) {

        String soDau = giaiThuong.getGiaiTam();
        // String soDuoi = giaiThuong.getGiaiDacBiet().substring(4,6);
        boolean isTrungAnUi = false;
        String soCuoc1 = String.format("%02d", Integer.parseInt(soCuoc) + 1) + "";
        String soCuoc2 = String.format("%02d", Integer.parseInt(soCuoc) - 1) + "";


        if (soDau.equals(soCuoc1) || soDau.equals(soCuoc2))
            return true;

        return isTrungAnUi;

    }

    public static boolean isTrungAnUiDuoiMienNam(GiaiThuong giaiThuong, String soCuoc) {

        // String soDuoi = giaiThuong.getGiaiTam();
        String soDuoi = giaiThuong.getGiaiDacBiet().substring(4, 6);
        boolean isTrungAnUi = false;
        String soCuoc1 = String.format("%02d", Integer.parseInt(soCuoc) + 1) + "";
        String soCuoc2 = String.format("%02d", Integer.parseInt(soCuoc) - 1) + "";


        if (soDuoi.equals(soCuoc1) || soDuoi.equals(soCuoc2))
            return true;

        return isTrungAnUi;

    }

    public static String isTrungAnUiDauMienNam(GiaiThuong giaiThuong, DauDuoi dauDuoi) {

        String result = "";
        float tienThuong;

        if (isTrungAnUiDauMienNam(giaiThuong, dauDuoi.getSoCuoc())) {
            tienThuong = dauDuoi.getTienCuocSoDau() * 5;
            result = dauDuoi.getSoCuoc() + " - " + getInteger(dauDuoi.getTienCuocSoDau()) + " = " + getInteger(tienThuong);
        }
        return result;
    }

    public static String isTrungAnUiDuoiMienNam(GiaiThuong giaiThuong, DauDuoi dauDuoi) {

        String result = "";
        float tienThuong;

        if (isTrungAnUiDuoiMienNam(giaiThuong, dauDuoi.getSoCuoc())) {
            tienThuong = dauDuoi.getTienCuocSoDuoi() * 5;
            result = dauDuoi.getSoCuoc() + " - " + getInteger(dauDuoi.getTienCuocSoDuoi()) + " = " + getInteger(tienThuong);
        }
        return result;
    }

    private static int containsNumberBaoLos(GiaiThuong giaiThuong, String soCuoc) {

        int dem = 0;
        List<String> listGiai = giaiThuongToList(giaiThuong);
        int soCon = soCuoc.length();
        for (String s : listGiai) {
            if (s.length() >= soCon) {

                String lastTwoChars;

                if (s.length() >= soCon) {
                    lastTwoChars = s.substring(s.length() - soCon);
                } else {
                    lastTwoChars = s;
                }
                if (lastTwoChars.contains(soCuoc)) {
                    dem++;
                }
            }
        }

        return dem;
    }

    public static float kqTrungBaoLos2Dai(GiaiThuong giaiThuong1, GiaiThuong giaiThuong2, BaoLo baoLo) {

        int soLanXuatHienDai1 = containsNumberBaoLos(giaiThuong1, baoLo.getSoCuoc());
        int soLanXuatHienDai2 = containsNumberBaoLos(giaiThuong2, baoLo.getSoCuoc());
        int tongXuatHien = soLanXuatHienDai1 + soLanXuatHienDai2;

        float tienThuong = 0;
        if (tongXuatHien > 0) {
            if (baoLo.getSoCuoc().length() == 2) {
                tienThuong = tongXuatHien * AppConstants.TRUNG_THUONG_BAO_LO_2CON;
            } else if (baoLo.getSoCuoc().length() == 3) {
                tienThuong = tongXuatHien * AppConstants.TRUNG_THUONG_BAO_LO_3CON;
            } else if (baoLo.getSoCuoc().length() == 4) {
                tienThuong = tongXuatHien * AppConstants.TRUNG_THUONG_BAO_LO_4CON;
            }
        }
        return tienThuong;
    }

    public static DauDuoi doKetQuaDauDuoiMienNam(GiaiThuong dai1, GiaiThuong dai2, DauDuoi dauDuoi) {

        float soTienThuong = 0;
        String soDau = dauDuoi.getSoCuoc();
        boolean isTrungDauDuoi = false;

        // dai2.setGiaiTam("02");
        //Kiem tra co cuoc dai 1 khong?
        if (dauDuoi.getDaiI1D() != AppConstants.KHONG_CUOC) {

            if (dauDuoi.getTienCuocSoDau() > 0) {
                if (isTrungSoDauMienNam(dai1, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_THUONG_DAU_DUOI;
                    dauDuoi.setTrungThuong(true);
                }
                if (isTrungAnUiDuoiMienNam(dai1, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_AN_UI_DAU_DUOI;
                    dauDuoi.setTrungAnUi(true);
                }
            }
            if (dauDuoi.getTienCuocSoDuoi() > 0) {
                if (isTrungSoCuoiMienNam(dai1, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_THUONG_DAU_DUOI;
                    dauDuoi.setTrungThuong(true);
                }
                if (isTrungAnUiDuoiMienNam(dai1, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_AN_UI_DAU_DUOI;
                    dauDuoi.setTrungAnUi(true);
                }

            }

        }

        //Kiem tra co cuoc dai 2 khong?
        if (dauDuoi.getDaiI2D() != AppConstants.KHONG_CUOC) {


            if (dauDuoi.getTienCuocSoDau() > 0) {
                if (isTrungSoDauMienNam(dai2, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_THUONG_DAU_DUOI;
                    dauDuoi.setTrungThuong(true);
                }
                if (isTrungAnUiDauMienNam(dai2, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_AN_UI_DAU_DUOI;
                    dauDuoi.setTrungAnUi(true);
                }
            }
            if (dauDuoi.getTienCuocSoDuoi() > 0) {
                if (isTrungSoCuoiMienNam(dai2, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_THUONG_DAU_DUOI;
                    dauDuoi.setTrungThuong(true);

                }

                if (isTrungAnUiDuoiMienNam(dai2, dauDuoi.getSoCuoc())) {
                    soTienThuong += dauDuoi.getTienCuocSoDau() * AppConstants.TRUNG_AN_UI_DAU_DUOI;
                    dauDuoi.setTrungAnUi(true);
                }
            }

        }
        dauDuoi.setTienThuong(soTienThuong);

        return dauDuoi;
    }


    public static String tenDaiVietTat(int tenDai) {

        if (tenDai == AppConstants.AN_GIANG) {
            return "AG";
        }
        if (tenDai == AppConstants.LONG_AN) {
            return "LA";
        }
        if (tenDai == AppConstants.TRA_VINH) {
            return "TV";
        }
        if (tenDai == AppConstants.CA_MAU) {
            return "CM";
        }
        if (tenDai == AppConstants.CAN_THO) {
            return "CT";
        }
        if (tenDai == AppConstants.HAU_GIANG) {
            return "HG";
        }
        if (tenDai == AppConstants.DONG_THAP) {
            return "ĐT";
        }
        if (tenDai == AppConstants.TIEN_GIANG) {
            return "TG";
        }
        if (tenDai == AppConstants.TPHCM) {
            return "HCM";
        }
        if (tenDai == AppConstants.DONG_NAI) {
            return "DN";
        }
        if (tenDai == AppConstants.VINH_LONG) {
            return "VL";
        }
        if (tenDai == AppConstants.TAY_NINH) {
            return "TN";
        }
        if (tenDai == AppConstants.BINH_DUONG) {
            return "BD";
        }

        if (tenDai == AppConstants.SOC_TRANG) {
            return "ST";
        }
        if (tenDai == AppConstants.BAC_LIEU) {
            return "BL";
        }
        if (tenDai == AppConstants.BINH_THUAN) {
            return "BT";
        }
        if (tenDai == AppConstants.BEN_TRE) {
            return "BTRE";
        }
        if (tenDai == AppConstants.VUNG_TAU) {
            return "VT";
        }

        if (tenDai == AppConstants.KIEN_GIANG) {
            return "KG";
        }
        if (tenDai == AppConstants.DA_LAT) {
            return "ĐL";
        }
        return "";
    }

    public static LotteryCity getDais(int date) {

        if (date == 2) {
            return LotterySchedule.MONDAY;
        }
        if (date == 3) {
            return LotterySchedule.TUESDAY;
        }
        if (date == 4) {
            return LotterySchedule.WEDNESDAY;
        }
        if (date == 5) {
            return LotterySchedule.THURSDAY;
        }
        if (date == 6) {
            return LotterySchedule.FRIDAY;
        }
        if (date == 7) {
            return LotterySchedule.SATURDAY;
        }
        if (date == 1) {
            return LotterySchedule.SUNDAY;
        }

        return new LotteryCity("dong-thap", "ca-mau");
    }

    public static String getTenDaiByDate(int dayOfWeek, int dai) {

        String tenDai = "";
        LotteryCity cites = XoSoUtils.getDais(dayOfWeek);
        if (dai == 1) {
            tenDai = XoSoUtils.tenDaiVietTat(cites.getCity1ID());
        }
        if (dai == 2) {
            int id = cites.getCity2ID();
            tenDai = XoSoUtils.tenDaiVietTat(id);
        }


        return tenDai;
    }

    public static int getDateOfWeek(String inputDate) {
        // inputDate = "17/04/2023"; // Input date in the format "dd/mm/yyyy"

// Parse the input date string into a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Get the day of the week from the Date object using a Calendar object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);


        return dayOfWeek;
    }

    public static String getInteger(float number) {


        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedNumber = decimalFormat.format(number);

        return formattedNumber;
    }

    public static float tongDauDuoiBanDuoc(List<DauDuoi> dauDuoiList) {

        //  DauDuoi dauDuoi = new DauDuoi();
        float tongDauDuoi = 0;

        float tongKetQuaDauDuoi = 0;

        for (DauDuoi dauDuoi : dauDuoiList) {

            tongDauDuoi = dauDuoi.getTienCuocSoDau() + dauDuoi.getTienCuocSoDuoi();
            if (dauDuoi.getDaiI1D() != 0 && dauDuoi.getDaiI2D() != 0) {
                tongDauDuoi = tongDauDuoi * 2;
            }
            tongKetQuaDauDuoi += tongDauDuoi;
        }


        return tongKetQuaDauDuoi;
    }

    public static float tongSoDasMienNam(List<SoDa> soDaList) {

        float tong = 0;
        for (SoDa soDa : soDaList) {
            if (soDa.getSoCuocThu3() != AppConstants.KHONG_CUOC_3_CON) {
                tong += soDa.getTienCuoc() * 3;
            } else {

                tong += soDa.getTienCuoc();
            }
        }

        return tong;
    }

    public static float tongBaoLoMienNam(List<BaoLo> baoLoList) {

        float tong = 0;

        for (BaoLo baoLo : baoLoList) {
            tong += Float.parseFloat(baoLo.getTienCuoc());
        }
        return tong * 2;
    }

    public static String getCurrentDate() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String formattedDay = String.format("%02d", day);
        // String formattedMonth = String.format("%02d", );

        String selectedDate = formattedDay + "/" + (month + 1);

        return selectedDate;
    }

    public static void limited7DaysSelectedDatePicker(DatePickerDialog datePickerDialog) {

        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Calculate the minimum and maximum selectable dates
        long minDateMillis = currentTimeMillis - (6 * 24 * 60 * 60 * 1000); // 6 days ago
        long maxDateMillis = currentTimeMillis; // Today

        // Create a calendar object with the current time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);


        // Set the minimum and maximum selectable dates
        datePickerDialog.getDatePicker().setMinDate(minDateMillis);
        datePickerDialog.getDatePicker().setMaxDate(maxDateMillis);
    }

    public static PareseURLWebScrapping loadKetQuaXoSoFromInternet(String maDaiEndPoint) {

        String url = AppConstants.URL_BASE_MIEN_NAM + maDaiEndPoint + ".html";
        return (PareseURLWebScrapping) new PareseURLWebScrapping().execute(new String[]{url});
    }

    public static LotteryCity getLotteryCityByDate(int dayOfMonth, int monthOfYear, int year) {

        LotteryCity city;

        String formattedDay = String.format("%02d", dayOfMonth);
        String formattedMonth = String.format("%02d", (monthOfYear + 1));
        String d = formattedDay + "/" + formattedMonth + "/" + year;
        int daysOfWeek = XoSoUtils.getDateOfWeek(d);
        city = XoSoUtils.getDais(daysOfWeek);
        return city;
    }

}
