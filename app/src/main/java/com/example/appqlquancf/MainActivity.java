package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.LoaiSanPham;
import entities.NhanVien;

public class MainActivity extends AppCompatActivity {
    Button btnDangNhap;
    EditText edtTenDangNhap,edtMatKhau;
    List<NhanVien> list_NV;
    DatabaseSQL db = new DatabaseSQL(this);
    private String tenDangNhap="";
    private int QUYEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap_layout);

        list_NV = new ArrayList<>();
        AnhXa();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap();
            }
        });

    }

    private void AnhXa() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);

        db = new DatabaseSQL(this);
    }

    public boolean kiemTraNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap.equals("") || matKhau.equals("")) {
            return false;
        }
        return true;
    }

    public boolean kiemTraTenDangNhap(String userName) {
        list_NV = new ArrayList<>();
        list_NV = db.getAllNhanVien();
        for (NhanVien nv : list_NV) {
            if (nv.getTenDangNhap().equalsIgnoreCase(userName))
                return true;
        }
        return false;
    }

    public boolean kiemTraMatKhau(String tenDangNhap, String matKhau) {
        list_NV = new ArrayList<>();
        list_NV = db.getAllNhanVien();
        for (NhanVien nv : list_NV) {
            if (nv.getTenDangNhap().equalsIgnoreCase(tenDangNhap)) {
                if (nv.getMatKhau().equalsIgnoreCase(matKhau))
                    return true;
            }
        }
        return false;
    }

    public int getQuyen(String tenDangNhap){
        NhanVien nv = db.getIDNhanVien(tenDangNhap);
        tenDangNhap = nv.getTenDangNhap();
        QUYEN = nv.getPhanQuyen();
        return QUYEN;
    }

    public void dangNhap() {
        String tenDangNhap = edtTenDangNhap.getText().toString();
        String matKhau = edtMatKhau.getText().toString();

        if (kiemTraNhap(tenDangNhap, matKhau) == false) {
            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (kiemTraTenDangNhap(tenDangNhap)) {
                if (kiemTraMatKhau(tenDangNhap, matKhau)) {
                    //Nhân viên
                    if(getQuyen(tenDangNhap) == 1){
                        Intent intent = new Intent(MainActivity.this,Main_NV_Activity.class);
                        intent.putExtra("tenDangNhap",tenDangNhap);
                        startActivity(intent);
                    }
                    //Quản lý
                    else if (getQuyen(tenDangNhap) == 2){
                        Intent intent = new Intent(MainActivity.this,Main_QuanLy_Activity.class);
                        intent.putExtra("tenDangNhap",tenDangNhap);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}