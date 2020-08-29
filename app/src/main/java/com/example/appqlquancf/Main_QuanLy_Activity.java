package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import entities.SanPham;

public class Main_QuanLy_Activity extends AppCompatActivity {
    ImageButton btnLSHD,btnHD,btnSP,btnLoaiSP,btnThongKe,btnNV,btnDangXuat;

    private String tenDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_quanly);

        anhXa();

        tenDangNhap = getIntent().getStringExtra("tenDangNhap");

        btnLSHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this,Main_NV_Activity.class);
                intent.putExtra("tenDangNhap",tenDangNhap);
                startActivity(intent);
            }
        });

        btnHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this, HoaDon_Activity.class);
                startActivity(intent);
            }
        });

        btnSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this, SanPham_Activity.class);
                startActivity(intent);
            }
        });

        btnLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this,LoaiSP_Activity.class);
                startActivity(intent);
            }
        });

        btnNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this,NhanVien_Activity.class);
                startActivity(intent);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_QuanLy_Activity.this,ThongKe_Activity.class);
                startActivity(intent);
            }
        });

    }

    public void anhXa(){
        btnLSHD = findViewById(R.id.btnLSHD);
        btnHD = findViewById(R.id.btnHD);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnSP = findViewById(R.id.btnSanPham);
        btnLoaiSP = findViewById(R.id.btnLoaiSP);
        btnNV = findViewById(R.id.btnNhanVien);
        btnDangXuat = findViewById(R.id.btnDangXuat);
    }
}