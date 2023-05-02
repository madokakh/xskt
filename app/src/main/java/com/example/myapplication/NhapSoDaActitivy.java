package com.example.myapplication;

import static android.provider.MediaStore.EXTRA_FULL_SCREEN;
import static com.example.myapplication.NguoiBanActivity.EXTRA_NGUOI_BAN;
import static com.example.myapplication.NhapDDActivity.EXTRA_VUNG_MIEN;
import static com.example.myapplication.TongKetNguoiBanAcitvity.EXTRA_DATE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.model.SoDa;
import com.example.myapplication.utils.XoSoUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NhapSoDaActitivy extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, TextWatcher {


    private String currentValue = "";
    private int currentPointer = 0;

    private EditText etSo1;
    private EditText etSo2;
    private EditText etSo3;
    private EditText etTienCuoc;
    private TextView tvNguoiBan;
    private TextView tvDate;
    private NguoiBan nguoiBan;

    private List<SoDa> soDaList;

    private SoDaViewModel soDaViewModel;
    private RecyclerView recyclerView;

    private SoDaAdapter soDaAdapter;
    private boolean isDateisClicked = false;
    private boolean isTouch = false;
    private Button btnInput;
    private boolean isXemButtonClicked;
    private LinearLayout lnHeader;
    private boolean isFullScreen;

    private int buttons[] = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnC,
            R.id.btn4,R.id.btn5,R.id.btn6,
            R.id.btn7,R.id.btn8,R.id.btn9,
            R.id.btnZero,R.id.btnDot, R.id.btnEnter, R.id.btnOk
    };
    private String sDate;
    private int vungMien = AppConstants.MIEN_NAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_so_da);
        Intent intent = getIntent();
        nguoiBan  = (NguoiBan) intent.getSerializableExtra(EXTRA_NGUOI_BAN);
        isFullScreen = intent.getBooleanExtra(EXTRA_FULL_SCREEN,isFullScreen);
        sDate = intent.getStringExtra(EXTRA_DATE);
        vungMien = intent.getIntExtra(EXTRA_VUNG_MIEN,0);
        String toolBarTitle = "";

        if(vungMien == AppConstants.MIEN_NAM){
            toolBarTitle += "Miền Nam";
        }else{
            toolBarTitle += "Miền Bắc";
        }
        toolBarTitle += "- Số Đá";
        getSupportActionBar().setTitle(toolBarTitle);
        connectView();

        connectData();
    }

    private void connectData() {
        tvNguoiBan.setText(nguoiBan.getTenNguoiBan());
        soDaList = new ArrayList<>();

        soDaAdapter = new SoDaAdapter(new SoDaAdapter.SoDaDiff());

        soDaViewModel = new ViewModelProvider(this).get(SoDaViewModel.class);

        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String formattedDay = String.format("%02d", day);
        // String formattedMonth = String.format("%02d", );
        String selectedDate = formattedDay + "/" + (month + 1);
      //  tvDate.setText(selectedDate);
        tvDate.setText(sDate);

        soDaViewModel.getAllSoDaWithNguoiBanIDAndDateVungMien(nguoiBan.getNguoiBanID(),tvDate.getText().toString(),
                vungMien).observe(this, soDas -> {

           // soDaAdapter.submitList(soDas);
           // Log.d("SODA_LIST",soDas.size()+"");

            if(soDaList.size() == 0){
                soDaList.addAll(soDas);
                soDaAdapter.submitList(soDaList);
                soDaAdapter.notifyDataSetChanged();
                if(soDaList.size() > 4 ){
                    recyclerView.smoothScrollToPosition(soDaList.size() - 1);
                }
            }

            if(isFullScreen){
                setRecyclerViewHeightToMatchParent();
                isXemButtonClicked = false;
                isFullScreen = false;
            }

        });


        recyclerView.setAdapter(soDaAdapter);



        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                String dates[] = tvDate.getText().toString().split("/");
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                CurrentDate currentDate = new CurrentDate();
                int year = currentDate.getNam();
                isDateisClicked = true;

                DatePickerDialog datePickerDialog = new DatePickerDialog(NhapSoDaActitivy.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Do something with the selected date
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d",(monthOfYear + 1) );
                        String selectedDate = formattedDay + "/" + (monthOfYear + 1);
                        tvDate.setText(selectedDate);

                        String date = formattedDay + "/" + formattedMonth + "/" + year;
                        Log.d("Date",selectedDate + "   "+ nguoiBan.getNguoiBanID());
                        soDaList.clear();
                        soDaViewModel.getAllSoDaWithNguoiBanIDAndDateVungMien(nguoiBan.getNguoiBanID(),selectedDate,vungMien).observe(NhapSoDaActitivy.this, soDas -> {

                         //  soDaAdapter.submitList(soDas);

                            if(isDateisClicked){

                                soDaList.addAll(soDas);
                                soDaAdapter.submitList(soDaList);
                                soDaAdapter.notifyDataSetChanged();
                                if(soDaList.size() > 4 ){
                                    recyclerView.smoothScrollToPosition(soDaList.size() - 1);
                                }

                            }
                            isDateisClicked = false;


                        });

                    }
                }, year, month - 1, day);
                datePickerDialog.show();



            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //  dauDuoiViewModel.delete(adapter.getDauDuoiAt(viewHolder.getAdapterPosition()));
                //  Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                // Get the position of the item that was swiped
                int position = viewHolder.getAdapterPosition();

                // Remove the item from the list
                soDaViewModel.delete(soDaList.get(position));
                soDaList.remove(position);

                // Notify the adapter that the item was removed
                soDaAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void connectView() {

        tvNguoiBan = findViewById(R.id.tvNguoiBan);
        // connect button
        for(int i = 0; i < buttons.length; i++){
            findViewById(buttons[i]).setOnClickListener(this);
        }

        etSo1 = findViewById(R.id.etSo1);
        etSo2 = findViewById(R.id.etSo2);
        etSo3 = findViewById(R.id.etSo3);

        lnHeader = findViewById(R.id.lnHeader);
        etTienCuoc = findViewById(R.id.etTienCuoc);
        recyclerView = findViewById(R.id.rvSoDa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvDate = findViewById(R.id.tvDate);
        btnInput = findViewById(R.id.btnInput);


        etTienCuoc.addTextChangedListener(this);

        etSo1.setInputType(InputType.TYPE_NULL);
        etSo2.setInputType(InputType.TYPE_NULL);
        etSo3.setInputType(InputType.TYPE_NULL);
        etTienCuoc.setInputType(InputType.TYPE_NULL);

        etSo1.requestFocus();
        etSo1.setBackground(getDrawable(R.drawable.edittext_border));
        etSo1.setOnTouchListener(this);
        etSo2.setOnTouchListener(this);
        etSo3.setOnTouchListener(this);
        etTienCuoc.setOnTouchListener(this);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isXemButtonClicked == false){
                    setRecyclerViewHeightToMatchParent();
                    isXemButtonClicked = true;
                }else {
                    restoreRecyclerLayout();
                    isXemButtonClicked = false;
                }
            }
        });


    }



    @Override
    public void onClick(View view) {
        // handle buttons click

        Button btn = null;
        //number buttons
       // if(v.getId() == R.id.)
        if(view.getId() != R.id.btnC){
            btn = (Button) view;
        }

        //Toast.makeText(this, btn.getText().toString(), Toast.LENGTH_SHORT).show();
        if(currentValue.equals("00")|| currentValue.equals("0")){
            currentValue = "";
        }
        if(view.getId() == R.id.btnOk){
            currentPointer += 1;

            if(currentPointer == 4){
               addSoDaToList();
               return;
            }

            if(currentPointer == 0){
                currentValue = etSo1.getText().toString();
                etSo1.setSelection(currentValue.length());
                etSo1.requestFocus();
                // etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                // etSoDuoi.setText(etSoDau.getText().toString());
                etSo1.setBackground(getDrawable(R.drawable.edittext_border));
                etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));
                etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));
                etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            }
            if(currentPointer == 1){
                currentValue = etSo2.getText().toString();
                etSo2.setSelection(currentValue.length());
                etSo2.requestFocus();
                // etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                //etSo3.setText(etSo1.getText().toString());
                etSo2.setBackground(getDrawable(R.drawable.edittext_border));
                etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));
                etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));
                etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            }
            if(currentPointer == 2){
                currentPointer += 1;
                currentValue = etSo3.getText().toString();
                etSo3.setSelection(currentValue.length());
                etSo3.requestFocus();
                etSo3.setBackground(getDrawable(R.drawable.edittext_border));
                etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));
                etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
                etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));

            }
            if(currentPointer == 3){
                currentValue = etTienCuoc.getText().toString();
                etTienCuoc.setSelection(currentValue.length());
                etTienCuoc.requestFocus();

                etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));
                etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));
                etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));

            }


        }else if(view.getId() == R.id.btnC){
            if(isTouch == true){
                isTouch = false;
            }
            if(!currentValue.equals("0")&& currentValue.length() > 1) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
            else if(currentValue.length() == 1){
                currentValue = "0";
            }else if(currentValue.isEmpty()){
                currentValue = "0";
            }
        } else if(view.getId() == R.id.btnEnter){

            addSoDaToList();

        }else if(view.getId() == R.id.btnDot){
            if(currentValue.equals("")){
                currentValue = "0";
            }
            currentValue += btn.getText();
        }
        else {
            if(isTouch == true){
                isTouch = false;
                currentValue = "";
            }
            currentValue += btn.getText();
        }


        if(currentPointer == 0){

            findViewById(R.id.btnDot).setEnabled(false);
            String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            Log.d("TEXT", currentValue);
            etSo1.setText(formattedSoCuoc);

        }
        if(currentPointer == 1){

            findViewById(R.id.btnDot).setEnabled(false);
            String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            etSo2.setText(formattedSoCuoc);
        }
        if(currentPointer == 2){
            findViewById(R.id.btnDot).setEnabled(false);
            String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            etSo3.setText(formattedSoCuoc);
        }
        if(currentPointer == 3){
            findViewById(R.id.btnDot).setEnabled(true);
          //  String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            etTienCuoc.setText(currentValue);
        }
    }

    private void addSoDaToList() {
        SoDa soDa = new SoDa();

        soDa.setVungMien(vungMien);
        soDa.setSoCuocThu1(etSo1.getText().toString());
        soDa.setSoCuocThu2(etSo2.getText().toString());

        if(!etSo3.getText().toString().equals("")){
            soDa.setSoCuocThu3(etSo3.getText().toString());
        }else{
            soDa.setSoCuocThu3(AppConstants.KHONG_CUOC_3_CON+"");
        }

        soDa.setDate(tvDate.getText().toString());
        soDa.setTienCuoc(Float.parseFloat(etTienCuoc.getText().toString()));
        soDa.setNguoiBanID(nguoiBan.getNguoiBanID());
        soDaViewModel.insert(soDa);
        soDaList.add(soDa);
        resetInput();
        soDaAdapter.submitList(soDaList);
        soDaAdapter.notifyDataSetChanged();
        if(soDaList.size() > 4 ){
            recyclerView.smoothScrollToPosition(soDaList.size() - 1);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        isTouch = true;
        if(view.getId() == etSo1.getId()){
            currentPointer = 0;
            currentValue = etSo1.getText().toString();
           // etSo1.setSelection(currentValue.length());
            findViewById(R.id.btnDot).setEnabled(false);
            etSo1.setBackground(getDrawable(R.drawable.edittext_border));
            etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));
            etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));
            etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        else if(view.getId() == etSo2.getId()){
            findViewById(R.id.btnDot).setEnabled(false);
            currentPointer = 1;
            currentValue = etSo2.getText().toString();
          //  etSo2.setSelection(currentValue.length());

            etSo2.setBackground(getDrawable(R.drawable.edittext_border));
            etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));
            etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));
            etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        else if(view.getId() == etSo3.getId()){
            findViewById(R.id.btnDot).setEnabled(false);
            currentPointer = 2;
            currentValue = etSo3.getText().toString();
           // etSo3.setSelection(currentValue.length());

            etSo3.setBackground(getDrawable(R.drawable.edittext_border));
            etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));
            etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        else if(view.getId() == etTienCuoc.getId()){
            findViewById(R.id.btnDot).setEnabled(true);
            currentPointer = 3;
            currentValue = etSo3.getText().toString();
           // etTienCuoc.setSelection(currentValue.length());

            etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
            etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));
            etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));
            etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    private void setRecyclerViewHeightToMatchParent() {

        // set layout params of RecyclerView to match parent
        // set layout params of RecyclerView to match parent RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());
        recyclerView.setLayoutParams(layoutParams);

        btnInput.setText("Nhập");
    }
    private void restoreRecyclerLayout(){

        // create new LayoutParams object with height of 180dp
        int heightInDp = 180;
        float scale = getResources().getDisplayMetrics().density;
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPixels);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());

        recyclerView.setLayoutParams(layoutParams);
        btnInput.setText("Xem");
    }

    private void resetInput() {
        if(etSo2.getText().toString().equals("0"))
            return;
        currentValue = "00";
        currentPointer = 0;
        etSo1.setText(currentValue);
        etSo2.setText(currentValue);
        etSo3.setText("");
        etSo3.setHint("3 con");
        etTienCuoc.setText("0");
        etSo1.setBackground(getDrawable(R.drawable.edittext_border));
        etSo2.setBackground(getDrawable(R.drawable.etborder_unselected));
        etSo3.setBackground(getDrawable(R.drawable.etborder_unselected));
        etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

    }

}