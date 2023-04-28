package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ThemNguoiBanActivity extends AppCompatActivity {


    public static final String EXTRA_ID = "com.example.myapplication.EXTRA_ID";
    public static final String EXTRA_TEN_NGUOI_BAN = "com.example.myapplication.EXTRA_TEN_NGUOI_BAN";
    public static final String EXTRA_HE_SO_DAU_DUOI = "com.example.myapplication.EXTRA_HE_SO_DAU_DUOI";
    public static final String EXTRA_HE_SO_BAO_LO = "com.example.myapplication.EXTRA_HE_SO_BAO_LO";
    public static final String EXTRA_HE_SO_DA = "com.example.myapplication.EXTRA_HE_SO_DA";
    EditText etTenNguoiBan;
    EditText etHeSoDauDuoi;
    EditText etHeSoDa;
    EditText etHeSoBaoLo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_ban);
        etHeSoBaoLo = findViewById(R.id.edit_text_he_so_dau_bao_lo_mn);
        etHeSoDa = findViewById(R.id.edit_text_he_so_dau_da_mn);
        etHeSoDauDuoi = findViewById(R.id.edit_text_he_so_dau_duoi_mn);
        etTenNguoiBan = findViewById(R.id.edit_ten_mn);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Thêm Người Bán");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_nguoi_ban_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNguoiBan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNguoiBan() {

        String tenNguoiBan = etTenNguoiBan.getText().toString();
        String heSoDauDuoi = etHeSoDauDuoi.getText().toString();
        String heSoBaoLo = etHeSoBaoLo.getText().toString();
        String heSoDa = etHeSoDa.getText().toString();

        if (tenNguoiBan.trim().isEmpty() || heSoDauDuoi.trim().isEmpty() || heSoBaoLo.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TEN_NGUOI_BAN, tenNguoiBan);
        data.putExtra(EXTRA_HE_SO_DAU_DUOI, heSoDauDuoi);
        data.putExtra(EXTRA_HE_SO_DA, heSoDa);
        data.putExtra(EXTRA_HE_SO_BAO_LO, heSoBaoLo);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}