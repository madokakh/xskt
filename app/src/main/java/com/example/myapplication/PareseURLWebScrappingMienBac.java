package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.model.GiaiThuong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PareseURLWebScrappingMienBac extends AsyncTask<String, Void, GiaiThuongMienBac> {

    //TextView resultText;
    String result;

    private LayKetQuaCallBack listener;
    public PareseURLWebScrappingMienBac() {

    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgessDialog();
        }

        @Override
        protected GiaiThuongMienBac doInBackground(String... params) {
            StringBuffer buffer = new StringBuffer();
            String result="";

            // Create a list to store the prize results
            List<String> prizeResults = new ArrayList<>();
            GiaiThuongMienBac giaiThuongMienBac = new GiaiThuongMienBac();


            try {
                Document doc = Jsoup.connect(params[0]).get();

                if(doc != null){

                    // Find the table element with the given class name
                    Element table = doc.selectFirst(".bkqtinhmienbac");

                    // Find all the rows in the table body
                    Elements rows = table.select("tbody tr");


                    String gt[];

                    // Iterate through each row and extract the prize result from the corresponding cell
                    for (Element row : rows) {
                        Element prizeElement = row.selectFirst("td.giaidb, td.giai1, td.giai2, td.giai3, td.giai4, td.giai5, td.giai6, td.giai7");
                        if (prizeElement != null) {
                            result = prizeElement.text();
                            prizeResults.add(result);
                        }
                    }


                    for(int i = 0; i < prizeResults.size(); i++){
                        if(i == 0 || i == (prizeResults.size() - 1)){
                            if(i == 0){
                                giaiThuongMienBac.getDauDuois().add(prizeResults.get(i));
                            }else{
                                String gts[] = prizeResults.get(i).split(" ");
                                for(int j = 0; j < gts.length; j++){
                                    giaiThuongMienBac.getDauDuois().add(gts[j]);
                                }
                            }
                        }else{
                            gt = prizeResults.get(i).split(" ");
                            if(gt.length > 1){
                                String gts[] = prizeResults.get(i).split(" ");
                                for(int j = 0; j < gts.length; j++){
                                    giaiThuongMienBac.getGiaiKhac().add(gts[j]);
                                }
                            }else{
                                giaiThuongMienBac.getGiaiKhac().add(prizeResults.get(i));
                            }
                        }
                    }
                    giaiThuongMienBac.setDate(params[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return giaiThuongMienBac;
        }

        @Override
        protected void onPostExecute(GiaiThuongMienBac s) {
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
           void ketQua(GiaiThuongMienBac giaiThuong);
        }

    public LayKetQuaCallBack getListener() {
        return listener;
    }

    public void setListener(LayKetQuaCallBack listener) {
        this.listener = listener;
    }
}

