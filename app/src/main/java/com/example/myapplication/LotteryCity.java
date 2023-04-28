package com.example.myapplication;

public class LotteryCity {
    private String city1;
    private String city2;

    public LotteryCity(String city1, String city2) {
        this.city1 = city1;
        this.city2 = city2;
    }

    public String getCity1() {
        return city1;
    }

    public String getCity2() {
        return city2;
    }

    public int getCity1ID(){
        if(city1.equals("dong-thap")){
            return AppConstants.DONG_THAP;
        }
        if(city1.equals("ca-mau")){
            return AppConstants.CA_MAU;
        }
        if(city1.equals("ben-tre")){
            return AppConstants.BEN_TRE;
        }
        if(city1.equals("bac-lieu")){
            return AppConstants.BAC_LIEU;
        }
        if(city1.equals("can-tho")){
            return AppConstants.CAN_THO;
        }
        if(city1.equals("soc-trang")){
            return AppConstants.SOC_TRANG;
        }
        if(city1.equals("an-giang")){
            return AppConstants.AN_GIANG;
        }
        if(city1.equals("binh-thuan")){
            return AppConstants.BINH_THUAN;
        } if(city1.equals("vinh-long")){
            return AppConstants.VINH_LONG;
        }
        if(city1.equals("tra-vinh")){
            return AppConstants.TRA_VINH;
        }
        if(city1.equals("long-an")){
            return AppConstants.LONG_AN;
        }
        if(city1.equals("hau-giang")){
            return AppConstants.HAU_GIANG;
        }
        if(city1.equals("tien-giang")){
            return AppConstants.TIEN_GIANG;
        }
        if(city1.equals("kien-giang")){
            return AppConstants.KIEN_GIANG;
        }
        if(city1.equals("vung-tau")){
            return AppConstants.VUNG_TAU;
        }
        if(city1.equals("da-lat")){
            return AppConstants.DA_LAT;
        }
        if(city1.equals("binh-phuoc")){
            return AppConstants.BINH_PHUOC;
        }

        return 1;
    }

    public int getCity2ID(){
        if(city2.equals("dong-thap")){
            return AppConstants.DONG_THAP;
        }
        if(city2.equals("ca-mau")){
            return AppConstants.CA_MAU;
        }
        if(city2.equals("ben-tre")){
            return AppConstants.BEN_TRE;
        }
        if(city2.equals("bac-lieu")){
            return AppConstants.BAC_LIEU;
        }
        if(city2.equals("can-tho")){
            return AppConstants.CAN_THO;
        }
        if(city2.equals("soc-trang")){
            return AppConstants.SOC_TRANG;
        }
        if(city2.equals("an-giang")){
            return AppConstants.AN_GIANG;
        }
        if(city2.equals("binh-thuan")){
            return AppConstants.BINH_THUAN;
        } if(city2.equals("vinh-long")){
            return AppConstants.VINH_LONG;
        }
        if(city2.equals("tra-vinh")){
            return AppConstants.TRA_VINH;
        }
        if(city2.equals("long-an")){
            return AppConstants.LONG_AN;
        }
        if(city2.equals("hau-giang")){
            return AppConstants.HAU_GIANG;
        }
        if(city2.equals("tien-giang")){
            return AppConstants.TIEN_GIANG;
        }
        if(city2.equals("kien-giang")){
            return AppConstants.KIEN_GIANG;
        }
        if(city2.equals("vung-tau")){
            return AppConstants.VUNG_TAU;
        }
        if(city2.equals("da-lat")){
            return AppConstants.DA_LAT;
        }
        if(city2.equals("binh-phuoc")){
            return AppConstants.BINH_PHUOC;
        }

        return 1;
    }
}
