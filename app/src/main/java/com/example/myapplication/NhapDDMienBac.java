package com.example.myapplication;

import static android.provider.MediaStore.EXTRA_FULL_SCREEN;
import static com.example.myapplication.NguoiBanActivity.EXTRA_NGUOI_BAN;
import static com.example.myapplication.NhapDDActivity.EXTRA_VUNG_MIEN;
import static com.example.myapplication.TongKetNguoiBanAcitvity.EXTRA_DATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.myapplication.utils.XoSoUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NhapDDMienBac extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {



    private int buttons[] = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnC,
            R.id.btn4,R.id.btn5,R.id.btn6,
            R.id.btn7,R.id.btn8,R.id.btn9,
            R.id.btnZero,R.id.btnDot, R.id.btnEnter, R.id.btnOk
    };

    private NguoiBan nguoiBan;
    boolean isFullScreen;
    private int vungMien = AppConstants.MIEN_NAM;
    private String sDate;
    private TextView tvNguoiBan;
    private EditText etSoCuoc;
    private EditText etTienCuoc;
    private int currentPointer = 0;
    private boolean isTouch = false;
    private String currentValue = "";
    private RecyclerView recyclerView;
    private TextView tvDate;
    private TextView tvInput;
    private LinearLayout lnHeader;
    private DauDuoiMienBacAdapter dauDuoiMienBacAdapter;
    private List<DauDuoi> dauDuoiList;
    private DauDuoiViewModel dauDuoiViewModel;
    private boolean isXemButtonClicked;
    private boolean isDateisClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_ddmien_bac);

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
        toolBarTitle += "- Đầu Đuôi";
        getSupportActionBar().setTitle(toolBarTitle);
        connectView();

        connectData();
    }

    private void connectData() {

        tvNguoiBan.setText(nguoiBan.getTenNguoiBan());
        dauDuoiMienBacAdapter = new DauDuoiMienBacAdapter(new DauDuoiMienBacAdapter.DauDuoiDiff());
        dauDuoiList = new ArrayList<>();

        //  soDaAdapter = new SoDaAdapter(new SoDaAdapter.SoDaDiff());

        dauDuoiViewModel = new ViewModelProvider(this).get(DauDuoiViewModel.class);

        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String formattedDay = String.format("%02d", day);
        // String formattedMonth = String.format("%02d", );
        String selectedDate = formattedDay + "/" + (month + 1);
        // tvDate.setText(selectedDate);
        tvDate.setText(sDate);

        dauDuoiViewModel.getAllDauDuoiWithNguoiBanIDAndDateVungMien(nguoiBan.getNguoiBanID(),
                tvDate.getText().toString(),AppConstants.MIEN_BAC).observe(this, baoLos -> {

            // soDaAdapter.submitList(soDas);
            // Log.d("SODA_LIST",soDas.size()+"");
            if(dauDuoiList.size() == 0){
                dauDuoiList.addAll(baoLos);
                dauDuoiMienBacAdapter.submitList(dauDuoiList);
                dauDuoiMienBacAdapter.notifyDataSetChanged();
                if(dauDuoiList.size() > 4 ){
                    recyclerView.smoothScrollToPosition(dauDuoiList.size() - 1);
                }
            }

            if(isFullScreen){
                setRecyclerViewHeightToMatchParent();
                isXemButtonClicked = true;
                isFullScreen = false;
            }
        });


        recyclerView.setAdapter(dauDuoiMienBacAdapter);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                 isDateisClicked = true;

                DatePickerDialog datePickerDialog = new DatePickerDialog(NhapDDMienBac.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Do something with the selected date
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d",(monthOfYear + 1) );
                        String selectedDate = formattedDay + "/" + (monthOfYear + 1);
                        tvDate.setText(selectedDate);

                        String date = formattedDay + "/" + formattedMonth + "/" + year;
                        Log.d("Date",selectedDate + "   "+ nguoiBan.getNguoiBanID());
                        dauDuoiList.clear();
                        dauDuoiViewModel.getAllDauDuoiWithNguoiBanIDAndDateVungMien(nguoiBan.getNguoiBanID(),selectedDate,
                                AppConstants.MIEN_BAC).observe(NhapDDMienBac.this, baoLos -> {

                            //  soDaAdapter.submitList(soDas);

                            if(isDateisClicked){

                                dauDuoiList.addAll(baoLos);
                                dauDuoiMienBacAdapter.submitList(dauDuoiList);
                                dauDuoiMienBacAdapter.notifyDataSetChanged();
                                if(dauDuoiList.size() > 4 ){
                                    recyclerView.smoothScrollToPosition(dauDuoiList.size() - 1);
                                }

                            }
                            isDateisClicked = false;


                        });

                    }
                }, year, month, day);
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
                dauDuoiViewModel.delete(dauDuoiList.get(position));
                dauDuoiList.remove(position);

                // Notify the adapter that the item was removed
                dauDuoiMienBacAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);
        //   itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setRecyclerViewHeightToMatchParent() {
        // set layout params of RecyclerView to match parent
        // set layout params of RecyclerView to match parent RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());
        recyclerView.setLayoutParams(layoutParams);

        tvInput.setText("Nhập");
    }

    private void connectView() {
        tvNguoiBan = findViewById(R.id.tvNguoiBan);
        // connect button
        for(int i = 0; i < buttons.length; i++){
            findViewById(buttons[i]).setOnClickListener(this);
        }

        etSoCuoc = findViewById(R.id.etSoCuoc);
        etTienCuoc = findViewById(R.id.etTienCuoc);

        recyclerView = findViewById(R.id.rvSoDa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvDate = findViewById(R.id.tvDate);
        tvInput = findViewById(R.id.tvInput);
        lnHeader = findViewById(R.id.lnHeader);

        etSoCuoc.setInputType(InputType.TYPE_NULL);

        etTienCuoc.setInputType(InputType.TYPE_NULL);

        etSoCuoc.requestFocus();
        etSoCuoc.setBackground(getDrawable(R.drawable.edittext_border));
        etSoCuoc.setOnTouchListener(this);
        etTienCuoc.setOnTouchListener(this);

        tvInput.setOnClickListener(new View.OnClickListener() {
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

    private void restoreRecyclerLayout() {
        // create new LayoutParams object with height of 180dp
        int heightInDp = 180;
        float scale = getResources().getDisplayMetrics().density;
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPixels);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());

        recyclerView.setLayoutParams(layoutParams);
        tvInput.setText("Xem");
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

        if(currentPointer == 1){
            if(currentValue.equals("0")){
                currentValue = "";
            }
        }
        if(view.getId() == R.id.btnOk){

            currentPointer += 1;

            if(currentPointer == 2){
                addDauDuoiToList();
                return;
            }
            if(currentPointer == 0){
                currentValue = etSoCuoc.getText().toString();
                etSoCuoc.requestFocus();
                // etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                // etSoDuoi.setText(etSoDau.getText().toString());
                etSoCuoc.setBackground(getDrawable(R.drawable.edittext_border));

                etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            }

            if(currentPointer == 1){
                currentValue = etTienCuoc.getText().toString();
                etTienCuoc.setSelection(currentValue.length());
                etTienCuoc.requestFocus();
                etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

            }


        }else if(view.getId() == R.id.btnC){
            if(isTouch){
                isTouch = false;
            }
            if(!currentValue.equals("0")&& currentValue.length() > 1) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
            else if(currentValue.length() == 1){
                if(currentPointer == 1)
                    currentValue = "0";
                else
                    currentValue = "";
            }else if(currentValue.isEmpty()){
                if(currentPointer == 1)
                    currentValue = "0";
                else
                    currentValue = "";
            }
        } else if(view.getId() == R.id.btnEnter){

            addDauDuoiToList();

        }else if(view.getId() == R.id.btnDot){
            if(currentValue.equals("")){
                currentValue = "0";
            }
            currentValue += btn.getText();
        }
        else {
            if(isTouch == true){
                currentValue = "";
                isTouch = false;
            }
            currentValue += btn.getText();
        }


        if(currentPointer == 0){

            findViewById(R.id.btnDot).setEnabled(false);
            etSoCuoc.setText(currentValue);

        }

        if(currentPointer == 1){
            findViewById(R.id.btnDot).setEnabled(true);
            //  String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            etTienCuoc.setText(currentValue);
        }
    }

    private void addDauDuoiToList() {
        DauDuoi dauDuoi = new DauDuoi();

        Date date = new Date();

        dauDuoi.setDateString(tvDate.getText().toString());

        dauDuoi.setDate(date);
        dauDuoi.setVungMien(AppConstants.MIEN_BAC);




        if(!etTienCuoc.getText().toString().isEmpty()){
            dauDuoi.setTienCuocSoDau(Float.parseFloat(etTienCuoc.getText().toString()));
        }


        // dauDuoi.setTienCuocSoDuoi(Float.parseFloat(etTienSoDuoi.getText().toString()));
        String soCuoc = etSoCuoc.getText().toString();
        dauDuoi.setSoCuoc(soCuoc);
        dauDuoi.setNguoiBanID(nguoiBan.getNguoiBanID());

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek;
        String dateSelected = tvDate.getText().toString();
        dateSelected += "/" + calendar.get(Calendar.YEAR);
        dayOfWeek = XoSoUtils.getDateOfWeek(dateSelected);

        //  LotteryCity city = XoSoUtils.getLotteryCityByDate();


        //dauDuoiList.add(dauDuoi);
        //adapter.addItem(dauDuoi);
        dauDuoiViewModel.insert(dauDuoi);
        dauDuoiList.add(dauDuoi);
        dauDuoiMienBacAdapter.notifyDataSetChanged();
        if(dauDuoiList.size() > 3){
            // Assuming you have a reference to your RecyclerView and its adapter
            recyclerView.smoothScrollToPosition(dauDuoiMienBacAdapter.getItemCount() - 1);
        }
      resetInput();
    }

    private void resetInput() {
        currentValue = "";
        currentPointer = 0;
        etSoCuoc.setText(currentValue);

        etTienCuoc.setText("");
        etSoCuoc.setBackground(getDrawable(R.drawable.edittext_border));
        etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        isTouch = true;
        if(view.getId() == etSoCuoc.getId()){
            currentPointer = 0;
            currentValue = etSoCuoc.getText().toString();
            // etSo1.setSelection(currentValue.length());
            findViewById(R.id.btnDot).setEnabled(false);
            etSoCuoc.setBackground(getDrawable(R.drawable.edittext_border));
            etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        else if(view.getId() == etTienCuoc.getId()){
            findViewById(R.id.btnDot).setEnabled(true);
            currentPointer = 1;
            currentValue = etTienCuoc.getText().toString();
            // etTienCuoc.setSelection(currentValue.length());

            etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
            etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        return false;
    }
}