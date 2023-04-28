package com.example.myapplication;

import static android.provider.MediaStore.EXTRA_FULL_SCREEN;
import static com.example.myapplication.NguoiBanActivity.EXTRA_NGUOI_BAN;
import static com.example.myapplication.TongKetNguoiBanAcitvity.EXTRA_DATE;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.model.SoDa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NhapBaoLoActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {


    private TextView tvNguoiBan;
    private TextView tvDate;
    private EditText etSo1;
    private EditText etTienCuoc;
    private RecyclerView recyclerView;
    private boolean isDateisClicked = false;

    private BaoLoAdapter baoLoAdapter;

    private List<BaoLo> baoLoList;

    private String currentValue = "";
    private int currentPointer = 0;

    private boolean isTouch;
    private  Button tvInput;
    private boolean isXemButtonClicked;

    private boolean zeroFirstPress = false;

    private LinearLayout lnHeader;

    private BaoLoViewModel baoLoViewModel;
    private int buttons[] = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnC,
            R.id.btn4,R.id.btn5,R.id.btn6,
            R.id.btn7,R.id.btn8,R.id.btn9,
            R.id.btnZero,R.id.btnDot, R.id.btnEnter, R.id.btnOk
    };
    private NguoiBan nguoiBan;
    private boolean isFullScreen;
    private String sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_bao_lo);

        Intent intent = getIntent();
        nguoiBan  = (NguoiBan) intent.getSerializableExtra(EXTRA_NGUOI_BAN);
        isFullScreen = intent.getBooleanExtra(EXTRA_FULL_SCREEN,isFullScreen);
        sDate = intent.getStringExtra(EXTRA_DATE);
        connectView();

        connectData();

    }

    private void connectView() {
        tvNguoiBan = findViewById(R.id.tvNguoiBan);
        // connect button
        for(int i = 0; i < buttons.length; i++){
            findViewById(buttons[i]).setOnClickListener(this);
        }

        etSo1 = findViewById(R.id.etSo1);
        etTienCuoc = findViewById(R.id.etTienCuoc);
        recyclerView = findViewById(R.id.rvSoDa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvDate = findViewById(R.id.tvDate);
        tvInput = findViewById(R.id.tvInput);
        lnHeader = findViewById(R.id.lnHeader);


        etSo1.setInputType(InputType.TYPE_NULL);

        etTienCuoc.setInputType(InputType.TYPE_NULL);

        etSo1.requestFocus();
        etSo1.setBackground(getDrawable(R.drawable.edittext_border));
        etSo1.setOnTouchListener(this);
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

    private void setRecyclerViewHeightToMatchParent() {
        // set layout params of RecyclerView to match parent
        // set layout params of RecyclerView to match parent RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());
        recyclerView.setLayoutParams(layoutParams);

        tvInput.setText("Nhập");
    }
    private void restoreRecyclerLayout(){

        // create new LayoutParams object with height of 180dp
        int heightInDp = 180;
        float scale = getResources().getDisplayMetrics().density;
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPixels);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());

        recyclerView.setLayoutParams(layoutParams);
        tvInput.setText("Xem");
    }


    private void connectData() {
        tvNguoiBan.setText(nguoiBan.getTenNguoiBan());
        baoLoAdapter = new BaoLoAdapter(new BaoLoAdapter.BaoLoDiff());
        baoLoList = new ArrayList<>();

      //  soDaAdapter = new SoDaAdapter(new SoDaAdapter.SoDaDiff());

        baoLoViewModel = new ViewModelProvider(this).get(BaoLoViewModel.class);

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

        baoLoViewModel.getAllBaoLoWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(),tvDate.getText().toString()).observe(this, baoLos -> {

            // soDaAdapter.submitList(soDas);
            // Log.d("SODA_LIST",soDas.size()+"");
            if(baoLoList.size() == 0){
                baoLoList.addAll(baoLos);
                baoLoAdapter.submitList(baoLoList);
                baoLoAdapter.notifyDataSetChanged();
                if(baoLoList.size() > 4 ){
                    recyclerView.smoothScrollToPosition(baoLoList.size() - 1);
                }
            }

            if(isFullScreen){
                setRecyclerViewHeightToMatchParent();
                isXemButtonClicked = true;
                isFullScreen = false;
            }
        });


        recyclerView.setAdapter(baoLoAdapter);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                isDateisClicked = true;

                DatePickerDialog datePickerDialog = new DatePickerDialog(NhapBaoLoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Do something with the selected date
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d",(monthOfYear + 1) );
                        String selectedDate = formattedDay + "/" + (monthOfYear + 1);
                        tvDate.setText(selectedDate);

                        String date = formattedDay + "/" + formattedMonth + "/" + year;
                        Log.d("Date",selectedDate + "   "+ nguoiBan.getNguoiBanID());
                        baoLoList.clear();
                        baoLoViewModel.getAllBaoLoWithNguoiBanIDAndDate(nguoiBan.getNguoiBanID(),selectedDate).observe(NhapBaoLoActivity.this, baoLos -> {

                            //  soDaAdapter.submitList(soDas);

                            if(isDateisClicked){

                                baoLoList.addAll(baoLos);
                                baoLoAdapter.submitList(baoLoList);
                                baoLoAdapter.notifyDataSetChanged();
                                if(baoLoList.size() > 4 ){
                                    recyclerView.smoothScrollToPosition(baoLoList.size() - 1);
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
                baoLoViewModel.delete(baoLoList.get(position));
                baoLoList.remove(position);

                // Notify the adapter that the item was removed
                baoLoAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);
     //   itemTouchHelper.attachToRecyclerView(recyclerView);

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
                addBaoLoToList();
                return;
            }
            if(currentPointer == 0){
                currentValue = etSo1.getText().toString();
                etSo1.requestFocus();
                // etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                // etSoDuoi.setText(etSoDau.getText().toString());
                etSo1.setBackground(getDrawable(R.drawable.edittext_border));

                etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            }

            if(currentPointer == 1){
                currentValue = etTienCuoc.getText().toString();
                etTienCuoc.setSelection(currentValue.length());
                etTienCuoc.requestFocus();
                etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));

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

            addBaoLoToList();

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
            etSo1.setText(currentValue);

        }

        if(currentPointer == 1){
            findViewById(R.id.btnDot).setEnabled(true);
            //  String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            etTienCuoc.setText(currentValue);
        }
    }

    private void resultInput() {

        currentValue = "";
        currentPointer = 0;
        etSo1.setText(currentValue);

        etTienCuoc.setText("");
        etSo1.setBackground(getDrawable(R.drawable.edittext_border));
        etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
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
            etTienCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        else if(view.getId() == etTienCuoc.getId()){
            findViewById(R.id.btnDot).setEnabled(true);
            currentPointer = 1;
            currentValue = etTienCuoc.getText().toString();
            // etTienCuoc.setSelection(currentValue.length());

            etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
            etSo1.setBackground(getDrawable(R.drawable.etborder_unselected));

        }

        return false;
    }

    void addBaoLoToList(){

        BaoLo baoLo = new BaoLo();

        baoLo.setSoCuoc(etSo1.getText().toString());
        baoLo.setTienCuoc(etTienCuoc.getText().toString());
        baoLo.setNguoiBanID(nguoiBan.getNguoiBanID());

        baoLo.setDate(tvDate.getText().toString());

        baoLoViewModel.insert(baoLo);
        baoLoList.add(baoLo);
        baoLoAdapter.notifyDataSetChanged();
        if(baoLoList.size() > 4 ){
            recyclerView.smoothScrollToPosition(baoLoList.size() - 1);
        }

        resultInput();
    }
}