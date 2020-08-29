package com.example.appqlquancf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.NhanVien;

public class Main_NV_Activity extends AppCompatActivity {
    ListView lv;
    Button btnThemDH;

    List<ChiTietHoaDon> list_CTHD;
    List<HoaDon> list_HD;
    List<NhanVien> list_NV;

    HoaDonAdapter adapter_HD;

    private HoaDon hd = null;
    DatabaseSQL db;
    private String tenDangNhap;
    private int QUYEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_nhanvien);

        list_HD = new ArrayList<>();
        list_CTHD = new ArrayList<>();
        list_NV = new ArrayList<>();

        AnhXa();

        tenDangNhap = getIntent().getStringExtra("tenDangNhap");
        setQuyen(tenDangNhap);

        select_HD_Ngay();

        btnThemDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_NV_Activity.this,Them_HoaDon_Activity.class);
                intent.putExtra("tenDangNhap",tenDangNhap);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hd = list_HD.get(i);
                Intent intent = new Intent(Main_NV_Activity.this,ChiTietHoaDon_Activity.class);
                intent.putExtra("maHD",hd.getMaHD());
                intent.putExtra("tenDangNhap",tenDangNhap);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        if(QUYEN == 1){
            menu.findItem(R.id.mnQuannLy).setVisible(false);
            this.invalidateOptionsMenu();
        }else{
            menu.findItem(R.id.mnQuannLy).setVisible(true);
            this.invalidateOptionsMenu();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnQuannLy:
                Intent intent1 = new Intent(Main_NV_Activity.this,Main_QuanLy_Activity.class);
                intent1.putExtra("tenDangNhap",tenDangNhap);
                startActivity(intent1);
                break;
            case R.id.mnLichSuHD:
                Intent intent2 = new Intent(Main_NV_Activity.this,LichSu_HoaDon_Activity.class);
                startActivity(intent2);
                break;
            case R.id.mnDangXuat:
                Intent intent3 = new Intent(Main_NV_Activity.this,MainActivity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AnhXa(){
        btnThemDH = findViewById(R.id.btnThemDH);
        lv = findViewById(R.id.listViewDH);
        db = new DatabaseSQL(this);
    }

    public void select_HD_Ngay() {
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String ngay = df1.format(today);
        list_HD = new ArrayList<>();

        int thanhToan = 0;
        list_HD = db.getAllHoaDon_ThanhToan(thanhToan,ngay);
        if (list_HD.size() > 0) {
            for (HoaDon hd : list_HD) {
                adapter_HD = new HoaDonAdapter(this, R.layout.row_hoadon, list_HD);
                lv.setAdapter(adapter_HD);
            }
        } else {
            adapter_HD = new HoaDonAdapter(this, R.layout.row_hoadon, list_HD);
            lv.setAdapter(adapter_HD);
        }
    }

    public void setQuyen(String ten){
        list_NV = new ArrayList<>();
        list_NV = db.getAllNhanVien();
        for (NhanVien nv:list_NV){
            if(nv.getTenDangNhap().equalsIgnoreCase(ten)){
                QUYEN = nv.getPhanQuyen();
            }
        }
    }
}
