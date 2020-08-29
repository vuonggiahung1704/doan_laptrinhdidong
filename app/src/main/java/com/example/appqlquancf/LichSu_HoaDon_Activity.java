package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.HoaDonAdapter;
import entities.HoaDon;

public class LichSu_HoaDon_Activity extends AppCompatActivity {
    Button btnQuayLai;
    ListView lv;
    List<HoaDon> list_HD;
    HoaDonAdapter adapter_HD;
    DatabaseSQL db;
    private HoaDon hd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsu_hoadon_layout);

        list_HD = new ArrayList<>();
        AnhXa();
        select_HD();

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hd = list_HD.get(i);
                Intent intent = new Intent(LichSu_HoaDon_Activity.this,HoaDon_ThanhToan_Activity.class);
                intent.putExtra("maHD",hd.getMaHD());
                startActivity(intent);
            }
        });
    }

    public void AnhXa(){
        btnQuayLai = findViewById(R.id.btnQuayLai);
        lv = findViewById(R.id.listViewDH);

        db = new DatabaseSQL(this);
    }

    public void select_HD() {
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String ngay = df1.format(today);
        list_HD = new ArrayList<>();

        int thanhToan = 1;
        list_HD = db.getAllHoaDon_ThanhToan(thanhToan,ngay);
        if (list_HD.size() > 0) {
            for (HoaDon hd : list_HD) {
                adapter_HD = new HoaDonAdapter(this,R.layout.row_hoadon,list_HD);
                lv.setAdapter(adapter_HD);
            }
        } else {
            adapter_HD = new HoaDonAdapter(this,R.layout.row_hoadon,list_HD);
            lv.setAdapter(adapter_HD);        }
    }

}