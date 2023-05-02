package com.example.myapplication;

import static android.provider.MediaStore.EXTRA_FULL_SCREEN;
import static com.example.myapplication.NguoiBanActivity.EXTRA_NGUOI_BAN;
import static com.example.myapplication.TongKetNguoiBanAcitvity.EXTRA_DATE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Dai;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.utils.XoSoUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NhapDDActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, DauDuoiAdapter.OnItemClickListener, DauDuoiAdapter.OnItemLongClickListener {

    public static final String EXTRA_VUNG_MIEN = "com.example.myapplication.EXTRA_VUNG_MIEN";
    EditText etTienSoDau;
    EditText etTienSoDuoi;
    EditText etSoCuoc;

    CheckBox cbDai1;
    CheckBox cbDai2;
    CheckBox cbSoDau;
    CheckBox cbSoDuoi;

    TextView tvTenDai1;
    TextView tvTenDai2;
    Button btnXem;
    private LinearLayout lnHeader;

    RecyclerView rvDauDuoi;
    private int currentPointer = 0;
    private String currentValue = "0";

    DauDuoiViewModel dauDuoiViewModel;
    Calendar calendar;

    private NguoiBan nguoiBan;
    List<DauDuoi> dauDuoiList;
    List<DauDuoi> dauDuoiList2;
    private boolean isDateIsClicked;
    private boolean isXemButtonClicked;

    Date date;
    TextView tvDate;

    DatePicker datePicker;
    private  ItemTouchHelper itemTouchHelper;

    private boolean isTouch = false;
    private String sDate;
    private int buttons[] = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnC,
            R.id.btn4,R.id.btn5,R.id.btn6,
            R.id.btn7,R.id.btn8,R.id.btn9,
            R.id.btnZero,R.id.btnDot, R.id.btnEnter, R.id.btnOk
    };
     DauDuoiAdapter adapter = null;
    // DauDuoiAdapter adapter2 = null;
    int firstTime = 0;

     TextView tvNguoiBan;
     boolean isFullScreen = false;

     private int vungMien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_dd);

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


        dauDuoiList = new ArrayList<>();
        dauDuoiList2 = new ArrayList<>();
        tvNguoiBan = findViewById(R.id.tvNguoiBan);

        tvNguoiBan.setText(nguoiBan.getTenNguoiBan());


        dauDuoiViewModel = new ViewModelProvider(this).get(DauDuoiViewModel.class);
         rvDauDuoi = findViewById(R.id.rvDD);

        rvDauDuoi.setLayoutManager(new LinearLayoutManager(this));

      //  adapter.submitList(dauDuoiList);

       /* dauDuoiViewModel.getAllDauDuois().observe(this, dauDuois ->
        {


        });*/

        lnHeader = findViewById(R.id.lnHeader);
        etTienSoDau = findViewById(R.id.etSoDau);
        etTienSoDuoi = findViewById(R.id.etSoDuoi);
        etSoCuoc = findViewById(R.id.etSoCuoc);
        cbDai1 = findViewById(R.id.cbDai1);
        cbDai2 = findViewById(R.id.cbDai2);
        cbSoDau = findViewById(R.id.cbSoDau);
        cbSoDuoi = findViewById(R.id.cbSoDuoi);
        rvDauDuoi = findViewById(R.id.rvDD);
        tvTenDai1 = findViewById(R.id.tvDai1);
        tvTenDai2 = findViewById(R.id.tvDai2);

        btnXem = findViewById(R.id.tvInput);


        etTienSoDau.setInputType(InputType.TYPE_NULL);
        etSoCuoc.setInputType(InputType.TYPE_NULL);
        etTienSoDuoi.setInputType(InputType.TYPE_NULL);
        etTienSoDau.requestFocus();
        etTienSoDau.setBackground(getDrawable(R.drawable.edittext_border));

        etTienSoDau.setOnTouchListener(this);
        etTienSoDuoi.setOnTouchListener(this);
        etSoCuoc.setOnTouchListener(this);
        btnXem.setOnClickListener(new View.OnClickListener() {
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

        for(int i : buttons){

            findViewById(i).setOnClickListener(this);
        }



       // dauDuoiAdapter = new DauDuoiAdapter(danhSachDauDuoi,this);
       // rvDauDuoi.setLayoutManager(new LinearLayoutManager(this));
      //  rvDauDuoi.setAdapter(dauDuoiAdapter);

        datePicker = new DatePicker(this);

        tvDate = findViewById(R.id.tvDate);

        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        int dayOfWeek;

        tvDate.setText(sDate);


        String sday = sDate.split("/")[0];
        String sMonth = sDate.split("/")[1];

        String formattedDay = String.format("%02d", Integer.parseInt(sday));
        String formattedMonth = String.format("%02d", Integer.parseInt(sMonth));
        String selectedDate = formattedDay + "/" + formattedMonth + "/" + year;

        dayOfWeek = XoSoUtils.getDateOfWeek(selectedDate);
        String tenDai1 = XoSoUtils.getTenDaiByDate(dayOfWeek,1);
        String tenDai2 = XoSoUtils.getTenDaiByDate(dayOfWeek,2);


        tvTenDai1.setText(tenDai1);
        tvTenDai2.setText(tenDai2);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                String dates[] = tvDate.getText().toString().split("/");
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                CurrentDate currentDate = new CurrentDate();
                int year = currentDate.getNam();
                //dauDuoiList.clear();

                firstTime++;
                isDateIsClicked = true;
                dauDuoiList.clear();

                DatePickerDialog datePickerDialog = new DatePickerDialog(NhapDDActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Do something with the selected date
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d",(monthOfYear + 1) );
                        String selectedDate = formattedDay + "/" + (monthOfYear + 1);
                        tvDate.setText(selectedDate);

                        String date = formattedDay + "/" + formattedMonth + "/" + year;
                        int dayOfWeek = XoSoUtils.getDateOfWeek(date);
                        String tenDai1 = XoSoUtils.getTenDaiByDate(dayOfWeek,1);
                        String tenDai2 = XoSoUtils.getTenDaiByDate(dayOfWeek,2);

                        tvTenDai1.setText(tenDai1);
                        tvTenDai2.setText(tenDai2);

                        final Observer<List<DauDuoi>> observer2 = new Observer<List<DauDuoi>>() {
                            @Override
                            public void onChanged(@Nullable final List<DauDuoi> dauDuois) {

                             //   adapter1.submitList(dauDuois);
                                if(isDateIsClicked == true){
                                    dauDuoiList.addAll(dauDuois);
                                    adapter.submitList(dauDuoiList);
                                    adapter.notifyDataSetChanged();
                                    isDateIsClicked = false;
                                    if(dauDuoiList.size() > 3){
                                        // Assuming you have a reference to your RecyclerView and its adapter
                                        rvDauDuoi.smoothScrollToPosition(adapter.getItemCount() - 1);
                                    }
                                }


                            }
                        };


                        dauDuoiViewModel.getAllDauDuoiWithNguoiBanIDAndDateVungMien(nguoiBan.getNguoiBanID(),
                                selectedDate,AppConstants.MIEN_NAM).observe(NhapDDActivity.this,observer2);


                    }
                }, year, month - 1, day);
                XoSoUtils.limited7DaysSelectedDatePicker(datePickerDialog);
                datePickerDialog.show();



            }
        });

        Log.d("NgayThang",tvDate.getText().toString());
        // Create the observer which updates the UI.
        adapter = new DauDuoiAdapter(new DauDuoiAdapter.DauDuoiDiff());

        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        rvDauDuoi.setAdapter(adapter);
        final Observer<List<DauDuoi>> observer1 = new Observer<List<DauDuoi>>() {
            @Override
            public void onChanged(@Nullable final List<DauDuoi> dauDuois) {
                // Update the UI, in this case, a TextView.
              //  nameTextView.setText(newName);

                if(dauDuoiList.size() == 0){
                    dauDuoiList.addAll(dauDuois);
                    adapter.submitList(dauDuoiList);
                    adapter.notifyDataSetChanged();

                    if(dauDuoiList.size() > 3){
                        // Assuming you have a reference to your RecyclerView and its adapter
                        rvDauDuoi.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }
                }

                if(isFullScreen){
                    setRecyclerViewHeightToMatchParent();
                    isXemButtonClicked = true;
                    isFullScreen = false;
                }

            }
        };

        dauDuoiViewModel.getAllDauDuoiWithNguoiBanIDAndDateVungMien(nguoiBan.getNguoiBanID(),
                tvDate.getText().toString(),AppConstants.MIEN_NAM).observe(this,observer1);


        /* dauDuoiViewModel.getAllDauDuoiWithNguoiBanID(nguoiBan.getNguoiBanID()).observe(this,dauDuois -> {
            adapter.submitList(dauDuois);

        });*/


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
                DauDuoi dauDuoi = dauDuoiList.get(position);
                dauDuoiList.remove(position);
                // Notify the adapter that the item was removed
                adapter.notifyItemRemoved(position);
                dauDuoiViewModel.delete(dauDuoi);

            }
        }).attachToRecyclerView(rvDauDuoi);



    }

    private void setRecyclerViewHeightToMatchParent() {

        // set layout params of RecyclerView to match parent
        // set layout params of RecyclerView to match parent RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());
        rvDauDuoi.setLayoutParams(layoutParams);

        btnXem.setText("Nhập");
    }
    private void restoreRecyclerLayout(){

        // create new LayoutParams object with height of 180dp
        int heightInDp = 180;
        float scale = getResources().getDisplayMetrics().density;
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPixels);
        layoutParams.addRule(RelativeLayout.BELOW, lnHeader.getId());

        rvDauDuoi.setLayoutParams(layoutParams);
        btnXem.setText("Xem");
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.tvInput){
           // goToXemThongTinNhapActivity();
        }
        Button btn= null;
        if(view.getId() != R.id.btnC){
            btn = (Button) view;
        }

        //Toast.makeText(this, btn.getText().toString(), Toast.LENGTH_SHORT).show();
        if(currentValue.equals("0")){
            currentValue = "";
        }
        if(view.getId() == R.id.btnOk){
            currentPointer += 1;
            if(currentPointer == 2 || currentPointer == 3){
                addDauDuoiToList();
                return;
            }

            if(currentPointer == 0){
                currentValue = etTienSoDau.getText().toString();
                etTienSoDau.setSelection(currentValue.length());
                etTienSoDau.requestFocus();
                // etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                // etSoDuoi.setText(etSoDau.getText().toString());
                etTienSoDau.setBackground(getDrawable(R.drawable.edittext_border));
                etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
                etTienSoDuoi.setBackground(getDrawable(R.drawable.etborder_unselected));
            }
            if(currentPointer == 1){
                currentValue = etSoCuoc.getText().toString();
                etSoCuoc.setSelection(currentValue.length());
                etSoCuoc.requestFocus();
                // etTienCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                etTienSoDuoi.setText(etTienSoDau.getText().toString());
                etSoCuoc.setBackground(getDrawable(R.drawable.edittext_border));
                etTienSoDau.setBackground(getDrawable(R.drawable.etborder_unselected));
                etTienSoDuoi.setBackground(getDrawable(R.drawable.etborder_unselected));
            }
            if(currentPointer == 2){
                currentValue = etTienSoDuoi.getText().toString();
                etTienSoDuoi.setSelection(currentValue.length());
                etTienSoDuoi.requestFocus();
                etTienSoDuoi.setBackground(getDrawable(R.drawable.edittext_border));
                etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
                etTienSoDau.setBackground(getDrawable(R.drawable.etborder_unselected));

            }


        }else if(view.getId() == R.id.btnC){
            if(isTouch)
                isTouch = false;
            if(!currentValue.equals("0")&& currentValue.length() > 1) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
            else if(currentValue.length() == 1){
                currentValue = "0";
            }else if(currentValue.isEmpty()){
                currentValue = "0";
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


            Log.d("TEXT", currentValue);
            etTienSoDau.setText(currentValue);
        }
        if(currentPointer == 1){

            String formattedSoCuoc = String.format("%02d",(Integer.parseInt(currentValue)));
            etSoCuoc.setText(formattedSoCuoc);
        }
        if(currentPointer == 2){
            etTienSoDuoi.setText(currentValue);
        }
    }

    private void addDauDuoiToList() {

        DauDuoi dauDuoi = new DauDuoi();

        Date date = new Date();

        dauDuoi.setDateString(tvDate.getText().toString());

        dauDuoi.setDate(date);
        dauDuoi.setVungMien(vungMien);

        dauDuoi.setTenDai1(tvTenDai1.getText().toString());
        dauDuoi.setTenDai2(tvTenDai2.getText().toString());


        if(!etTienSoDau.getText().toString().isEmpty()){
            dauDuoi.setTienCuocSoDau(Float.parseFloat(etTienSoDau.getText().toString()));
        }
        if(!etTienSoDuoi.getText().toString().isEmpty()){
            dauDuoi.setTienCuocSoDuoi(Float.parseFloat(etTienSoDuoi.getText().toString()));
        }

        // dauDuoi.setTienCuocSoDuoi(Float.parseFloat(etTienSoDuoi.getText().toString()));
        String soCuoc = etSoCuoc.getText().toString();
        dauDuoi.setSoCuoc(soCuoc);
        dauDuoi.setNguoiBanID(nguoiBan.getNguoiBanID());

        calendar = Calendar.getInstance();
        int dayOfWeek;
        String dateSelected = tvDate.getText().toString();
        dateSelected += "/" + calendar.get(Calendar.YEAR);
        dayOfWeek = XoSoUtils.getDateOfWeek(dateSelected);

      //  LotteryCity city = XoSoUtils.getLotteryCityByDate();

        if(cbDai1.isChecked()){
            dauDuoi.setDaiI1D(XoSoUtils.getDais(dayOfWeek).getCity1ID());
          //  dauDuoi.setTenDai1();
        }
        if(cbDai2.isChecked()){
            dauDuoi.setDaiI2D(XoSoUtils.getDais(dayOfWeek).getCity2ID());
        }

        if(cbSoDau.isChecked()){

            dauDuoi.setSoCuocDau(soCuoc);
        }
        if(cbSoDuoi.isChecked()){
            dauDuoi.setSoCuocDuoi(soCuoc);
        }
        //dauDuoiList.add(dauDuoi);
        //adapter.addItem(dauDuoi);
        dauDuoiViewModel.insert(dauDuoi);
        dauDuoiList.add(dauDuoi);
        adapter.notifyDataSetChanged();
        if(dauDuoiList.size() > 3){
            // Assuming you have a reference to your RecyclerView and its adapter
            rvDauDuoi.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
        resetInput();
        //adapter.notifyDataSetChanged();

    }

    private void resetInput() {
        if(etSoCuoc.getText().toString().equals("0"))
            return;
        currentValue = "0";
        currentPointer = 0;
        etTienSoDau.setText(currentValue);
        etSoCuoc.setText(currentValue);
        etTienSoDuoi.setText(currentValue);
        etTienSoDau.setBackground(getDrawable(R.drawable.edittext_border));
        etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
        etTienSoDuoi.setBackground(getDrawable(R.drawable.etborder_unselected));
        cbSoDau.setChecked(true);
        cbSoDuoi.setChecked(true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

       // Toast.makeText(this, "Tessst", Toast.LENGTH_SHORT).show();
        isTouch = true;
        if(view.getId() == etTienSoDau.getId()){
            currentPointer = 0;
            currentValue = etTienSoDau.getText().toString();
            etTienSoDau.setSelection(currentValue.length());

            etTienSoDau.setBackground(getDrawable(R.drawable.edittext_border));
            etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            etTienSoDuoi.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        if(view.getId() == etSoCuoc.getId()){
            currentPointer = 1;
            currentValue = etSoCuoc.getText().toString();
            etSoCuoc.setSelection(currentValue.length());
            etSoCuoc.setBackground(getDrawable(R.drawable.edittext_border));
            etTienSoDau.setBackground(getDrawable(R.drawable.etborder_unselected));
            etTienSoDuoi.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        if(view.getId() == etTienSoDuoi.getId()){
            currentPointer = 2;
            currentValue = etTienSoDuoi.getText().toString();
            etTienSoDuoi.setSelection(currentValue.length());
            etTienSoDuoi.setBackground(getDrawable(R.drawable.edittext_border));
            etSoCuoc.setBackground(getDrawable(R.drawable.etborder_unselected));
            etTienSoDau.setBackground(getDrawable(R.drawable.etborder_unselected));

        }
        return false;
    }

    @Override
    public void onItemClick(DauDuoi dauDuoi) {

    }

    @Override
    public void onItemLongClick(View v, DauDuoi dauDuoi, int position) {

        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.item_long_cliked);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_update) {

                // Create a new dialog
                final Dialog dialog = new Dialog(this);
                // Set the content view to your custom layout
                dialog.setContentView(R.layout.update_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                // Find the EditText fields and "Submit" button in the layout
                final EditText editText1 = dialog.findViewById(R.id.editText1);
                final EditText editText2 = dialog.findViewById(R.id.editText2);
                final EditText editText3 = dialog.findViewById(R.id.editText3);

                Button submitButton = dialog.findViewById(R.id.submit_button);
                // Set up the click listener for the "Submit" button
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the values from the EditText fields
                        String soDau = editText1.getText().toString();
                        String soCuoc = editText2.getText().toString();
                        String soDuoi = editText3.getText().toString();

                        dauDuoi.setTienCuocSoDau(Float.parseFloat(soDau));
                        dauDuoi.setTienCuocSoDuoi(Float.parseFloat(soDuoi));
                        dauDuoi.setSoCuoc(soCuoc);

                        dauDuoiViewModel.update(dauDuoi);
                        dauDuoiList.add(position,dauDuoi);
                        adapter.notifyItemInserted(position);

                        // Perform your update action here using the values from the EditText fields

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
                // Show the dialog
                dialog.show();
                return true;
            } else if (id == R.id.menu_delete) {
                // Handle the "Delete" menu item click
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                dauDuoiViewModel.delete(dauDuoi);
                dauDuoiList.remove(dauDuoi);
                adapter.notifyItemRemoved(position);
              //  adapter.notifyDataSetChanged();
                return true;
            }
            return false;
        });
        popupMenu.show();

    }
}