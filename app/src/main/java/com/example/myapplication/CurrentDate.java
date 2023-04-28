package com.example.myapplication;

import java.util.Calendar;

public class CurrentDate {

    private int thu;

    private int gio;
    private int phut;
    private int ngay;
    private  int thang;
    private int nam;

    Calendar calendar;


    public CurrentDate(int thu, int gio, int phut, int ngay, int thang, int nam) {
        this.thu = thu;
        this.gio = gio;
        this.phut = phut;
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }

    public CurrentDate() {
        calendar = Calendar.getInstance();
    }

    public int getThu() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Day of week is returned as an integer, where Sunday is 1 and Saturday is 7
        // You can use a switch statement or an array to convert the integer to a string representation
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                // Do something for Sunday
                thu = Calendar.SUNDAY;
                break;
            case Calendar.MONDAY:
                // Do something for Monday
                thu = Calendar.MONDAY;
                break;
            case Calendar.TUESDAY:
                // Do something for Tuesday
                thu = Calendar.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                // Do something for Wednesday
                thu = Calendar.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                // Do something for Thursday
                thu = Calendar.THURSDAY;
                break;
            case Calendar.FRIDAY:
                // Do something for Friday
                thu = Calendar.FRIDAY;
                break;
            case Calendar.SATURDAY:
                // Do something for Saturday
                thu = Calendar.SATURDAY;
                break;
        }
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public int getGio() {
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return hourOfDay;
    }

    public void setGio(int gio) {
        this.gio = gio;
    }

    public int getPhut() {
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    public void setPhut(int phut) {
        this.phut = phut;
    }

    public int getNgay() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        int month = calendar.get(Calendar.MONTH);

        return month;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }
}
