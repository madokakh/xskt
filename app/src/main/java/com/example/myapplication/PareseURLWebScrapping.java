package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.GiaiThuong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PareseURLWebScrapping extends AsyncTask<String, Void, GiaiThuong> {

    //TextView resultText;
    String result;
    List<Integer> listDaySo;
    GiaiThuong giaiThuong;
    private LayKetQuaCallBack listener;
    public PareseURLWebScrapping() {

    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgessDialog();
        }

        @Override
        protected GiaiThuong doInBackground(String... params) {
            StringBuffer buffer = new StringBuffer();
            String result="";

            GiaiThuong giaiThuong = new GiaiThuong();
            try {
                Document doc = Jsoup.connect(params[0]).get();

                if(doc != null){

                    Element table = doc.select("table.box_kqxs_content").first();
                    Elements rows = table.select("tr");

                    for(int i = 0; i < rows.size();i++){
                       result = rows.get(i).text();

                       String [] r = result.split(" ");

                       if(i == 0){
                           giaiThuong.setGiaiDacBiet(r[2]);
                       }
                        if(i == 0){
                            giaiThuong.setGiaiDacBiet(r[2]);
                        }
                        if(i == 1){
                            giaiThuong.setGiaiNhat(r[2]);
                        }
                        if(i == 2){
                            giaiThuong.setGiaiNhi(r[2]);
                        }
                        if(i == 3){
                            List<String> g3 = new ArrayList<>();
                            g3.add(r[2]);
                            g3.add(r[3]);
                            giaiThuong.setGiaiBa(g3);
                        }
                        if(i == 4){
                            List<String> g4 = new ArrayList<>();
                            g4.add(r[2]);
                            g4.add(r[3]);
                            g4.add(r[4]);
                            g4.add(r[5]);
                            g4.add(r[6]);
                            g4.add(r[7]);
                            g4.add(r[8]);
                            giaiThuong.setGiaiTu(g4);
                        }
                        if(i == 5){
                            giaiThuong.setGiaiNam(r[2]);
                        }
                        if(i == 6){
                            List<String> g6 = new ArrayList<>();
                            g6.add(r[2]);
                            g6.add(r[3]);
                            g6.add(r[4]);
                            giaiThuong.setGiaiSau(g6);
                        }
                        if(i == 7){
                            giaiThuong.setGiaiBay(r[2]);
                        }
                        if(i == 8){
                            giaiThuong.setGiaiTam(r[2]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return giaiThuong;
        }

        @Override
        protected void onPostExecute(GiaiThuong s) {
            super.onPostExecute(s);
          //  Log.d("GiaiThuong", s.getGiaiTu().get(6));
           // hideProgressDialog();
           /* if (s != null){
                resultText.setText(s);
            } else {
                resultText.setText("Error ?");
            }*/
            listener.ketQua(s);
        }


        public interface LayKetQuaCallBack{
           void ketQua(GiaiThuong giaiThuong);
        }

    public LayKetQuaCallBack getListener() {
        return listener;
    }

    public void setListener(LayKetQuaCallBack listener) {
        this.listener = listener;
    }
}

