package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.CTHDAdapter;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.NhanVien;

public class HoaDon_ThanhToan_Activity extends AppCompatActivity {
    Button btnQuayLai;

    TextView txtMaHD,txtNgay,txtNVLap,txtVao,txtRa,txtNVTT,txtTongTien;

    ListView lv;

    List<ChiTietHoaDon> list_CTHD;
    CTHDAdapter adapter_CTHD;

    DatabaseSQL db;
    private HoaDon hd = null;
    private int maHD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoadon_thanhtoan_layout);

        list_CTHD = new ArrayList<>();
        maHD = getIntent().getIntExtra("maHD",-1);

        AnhXa();

        getHoaDon();

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void AnhXa(){
        btnQuayLai = findViewById(R.id.btnQuayLai);
        txtMaHD = findViewById(R.id.maHD_ThanhToan);
        txtNgay = findViewById(R.id.ngayLapHD_ThanhToan);
        txtNVLap = findViewById(R.id.nvLap_ThanhToan);
        txtVao = findViewById(R.id.tg_vao_ThanhToan);
        txtRa = findViewById(R.id.tg_ra_ThanhToan);
        txtNVTT = findViewById(R.id.nvThanhToan_ThanhToan);
        txtTongTien = findViewById(R.id.tongTien_ThanhToan);

        lv = findViewById(R.id.listViewCTHD);

        db = new DatabaseSQL(this);
    }

    public void getHoaDon(){
        hd = db.getIDHoaDon(maHD);
        txtMaHD.setText(""+hd.getMaHD());
        txtNgay.setText(hd.getNgayLapHD());
        NhanVien nv_lap = db.getIDNhanVien(hd.getNvLapHD().getTenDangNhap());
        txtNVLap.setText(nv_lap.getHoTen());
        txtVao.setText(""+hd.getThoiGianVao());
        txtTongTien.setText(""+hd.getThanhTien()+"đ");

        if(hd.getThanhToan()==1) {
            txtRa.setText("" + hd.getThoiGianRa());
            NhanVien nv_tt = db.getIDNhanVien(hd.getNvTTHD().getTenDangNhap());
            txtNVTT.setText(nv_tt.getHoTen());
        }
        else{
            txtRa.setText("~");
            txtNVTT.setText("");
        }

        select_CTHD_MaHD(hd.getMaHD());
    }

    public void select_CTHD_MaHD(int maHD_sql) {
        list_CTHD = new ArrayList<>();
        list_CTHD = db.getAllChiTietHoaDon_HoaDon(maHD_sql);
        if (list_CTHD.size() > 0) {
            for (ChiTietHoaDon cthd : list_CTHD) {
                adapter_CTHD = new CTHDAdapter(this,R.layout.row_cthd,list_CTHD);
                lv.setAdapter(adapter_CTHD);
            }
        } else {
            adapter_CTHD = new CTHDAdapter(this,R.layout.row_cthd,list_CTHD);
            lv.setAdapter(adapter_CTHD);
        }
    }
}