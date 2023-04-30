package com.example.myapplication;

import static com.example.myapplication.NhapDDActivity.EXTRA_VUNG_MIEN;
import static com.example.myapplication.ThemNguoiBanActivity.EXTRA_TEN_NGUOI_BAN;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.model.NguoiBan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NguoiBanActivity extends AppCompatActivity implements NguoiBanAdapter.OnItemClickListener {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final String EXTRA_NGUOI_BAN = "com.example.myapplication.EXTRA_NGUOI_BAN";
    NguoiBanViewModel nguoiBanViewModel;




    Intent resultIntent;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        // Handle the Intent
                        NguoiBan nguoiBan = new NguoiBan();

                        String tenNguoiBan = intent.getStringExtra(EXTRA_TEN_NGUOI_BAN);
                        String heSoDauDuoi = intent.getStringExtra(ThemNguoiBanActivity.EXTRA_HE_SO_DAU_DUOI);
                        String heSoDa = intent.getStringExtra(ThemNguoiBanActivity.EXTRA_HE_SO_DA);
                        String heSoBaoLo = intent.getStringExtra(ThemNguoiBanActivity.EXTRA_HE_SO_BAO_LO);


                        nguoiBan.setNguoiBanID(tenNguoiBan.replace(" ","").toUpperCase());
                        nguoiBan.setTenNguoiBan(tenNguoiBan);
                        nguoiBan.setHeSoDauDuoi(Integer.parseInt(heSoDauDuoi));
                        nguoiBan.setHeSoDa(Integer.parseInt(heSoDa));
                        nguoiBan.setHeSoBaoLo(Integer.parseInt(heSoBaoLo));

                        nguoiBanViewModel.insert(nguoiBan);


                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_ban);
        nguoiBanViewModel = new ViewModelProvider(this).get(NguoiBanViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final NguoiBanAdapter adapter = new NguoiBanAdapter(new NguoiBanAdapter.NguoiBanDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        nguoiBanViewModel.getAllNguoiBans().observe(this, nguoiBans -> {

            adapter.submitList(nguoiBans);
        });


        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NguoiBanActivity.this, ThemNguoiBanActivity.class);
                mStartForResult.launch(intent);
            }
        });


        adapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(NguoiBan nguoiBan) {
       // Intent intent = new Intent(NguoiBanActivity.this,TongKetNguoiBanAcitvity.class);
        Intent intent = new Intent(NguoiBanActivity.this,TongKetActivity.class);
       // intent.putExtra(EXTRA_TEN_NGUOI_BAN,nguoiBan.getTenNguoiBan());
        intent.putExtra(EXTRA_NGUOI_BAN,nguoiBan);
        intent.putExtra(EXTRA_VUNG_MIEN,AppConstants.MIEN_NAM);
        startActivity(intent);


    }
}