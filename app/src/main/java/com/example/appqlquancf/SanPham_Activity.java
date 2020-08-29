package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.LoaiSanPhamAdapter;
import adapter.SanPhamAdapter;
import entities.LoaiSanPham;
import entities.SanPham;

public class SanPham_Activity extends AppCompatActivity {
    Button btnThem,btnXoa,btnCapNhat,btnTim,btnQuayLai,btnLoc;
    Spinner spinner_LSP;
    Spinner spinner_LSP_Tim;

    EditText edtMa,edtTen,edtDonGia;
    ListView listView;

    List<LoaiSanPham> list_LoaiSanPham;
    LoaiSanPhamAdapter adapter_LSP;

    List<LoaiSanPham> list_LoaiSanPham_Tim;
    LoaiSanPhamAdapter adapter_LSP_Tim;

    List<SanPham> list_SP;
    SanPhamAdapter adapter_SP;

    DatabaseSQL db;
    private SanPham sp = null;
    private LoaiSanPham lsp = null;
    private LoaiSanPham lsp_tim = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanpham_layout);

        list_LoaiSanPham = new ArrayList<>();
        list_LoaiSanPham_Tim = new ArrayList<>();
        list_SP = new ArrayList<>();

        AnhXa();

        select_LoaiSP();
        select_LoaiSP_Tim();
        select_SanPham();

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinner_LSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lsp = list_LoaiSanPham.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_LSP_Tim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lsp_tim = list_LoaiSanPham_Tim.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_SanPham();
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp == null){
                    Toast.makeText(SanPham_Activity.this,"Vui lòng chọn sản phẩm",Toast.LENGTH_SHORT).show();
                }else{
                    hienThiDialog();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sp = list_SP.get(i);
                int maLoai = sp.getLoaiSP().getMaLoai();
                int vitri = timVitri(maLoai);
                valid(sp,vitri);
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_SanPham_Loc(lsp_tim.getMaLoai());
                clear();
            }
        });

    }
    private void AnhXa(){
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnLoc = findViewById(R.id.btnLoc);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        edtTen = findViewById(R.id.edtTenSP);
        edtDonGia = findViewById(R.id.edtDonGia);

        spinner_LSP = findViewById(R.id.spinnerLSP_SP);
        spinner_LSP_Tim = findViewById(R.id.spinnerLoc_LSP);

        listView = findViewById(R.id.listViewSP);
        db = new DatabaseSQL(this);
    }

    public void select_LoaiSP(){
        list_LoaiSanPham = new ArrayList<>();

        list_LoaiSanPham = db.getAllLoaiSP();
        if(list_LoaiSanPham.size()>0){
            for(LoaiSanPham lsp : list_LoaiSanPham){
                adapter_LSP = new LoaiSanPhamAdapter(this,R.layout.row_loaisp,list_LoaiSanPham);
                spinner_LSP.setAdapter(adapter_LSP);
            }
        }
    }

    public void select_LoaiSP_Tim(){
        list_LoaiSanPham_Tim = new ArrayList<>();

        list_LoaiSanPham_Tim = db.getAllLoaiSP();
        if(list_LoaiSanPham_Tim.size()>0){
            for(LoaiSanPham lsp : list_LoaiSanPham_Tim){
                adapter_LSP_Tim = new LoaiSanPhamAdapter(this,R.layout.row_loaisp,list_LoaiSanPham_Tim);
                spinner_LSP_Tim.setAdapter(adapter_LSP_Tim);
            }
        }
    }

    public  void delete(int maSP){
        if(db.deleteSanPham(maSP)){
            Toast.makeText(this,"Xóa thành công",Toast.LENGTH_SHORT).show();
            clear();
            select_SanPham();
        }
        else{
            Toast.makeText(this,"Xóa không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void update(){
        if(sp == null) {
            Toast.makeText(this, "Vui lòng chọn sản phẩm cần cập nhật", Toast.LENGTH_SHORT).show();
        }
        else {
            sp.setLoaiSP(lsp);
            sp.setTenSP(edtTen.getText().toString());
            sp.setDonGia(Double.parseDouble(edtDonGia.getText().toString()));
            if (db.updateSanPham(sp) > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                clear();
                select_SanPham();
            } else {
                Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hienThiDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa sản phẩm");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete(sp.getMaSP());
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean kiemTraNhap(String tenSP,String donGia){
        if(tenSP.equals("") || donGia.equals("")){
            return false;
        }
        return true;
    }

    public void clear(){
        spinner_LSP_Tim.setAdapter(adapter_LSP_Tim);
        spinner_LSP.setAdapter(adapter_LSP);
        edtTen.setText("");
        edtDonGia.setText("");
        sp = null;
    }

    public void valid(SanPham sp, int vitri){
        edtTen.setText(sp.getTenSP());
        edtDonGia.setText(""+sp.getDonGia());
        spinner_LSP.setSelection(vitri);
    }

    //Sản phẩm
    public void save_SanPham(){
        String tenSP = edtTen.getText().toString();
        String dongia_string = edtDonGia.getText().toString();
        if(kiemTraNhap(tenSP,dongia_string)){
            Double donGia = Double.parseDouble(dongia_string);
            SanPham sp = new SanPham();
            sp.setTenSP(tenSP);
            sp.setDonGia(donGia);
            sp.setLoaiSP(lsp);
            if(db.insertSanPham(sp)>0){
                select_SanPham();
                clear();
                Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Lưu không thành công",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }
    }
    public void select_SanPham(){
        list_SP = new ArrayList<>();

        list_SP = db.getAllSanPham();
        if(list_SP.size()>0){
            for(SanPham sp : list_SP){
                adapter_SP = new SanPhamAdapter(this,R.layout.row_sanpham,list_SP);
                listView.setAdapter(adapter_SP);
            }
        }else{
            adapter_SP = new SanPhamAdapter(this,R.layout.row_sanpham,list_SP);
            listView.setAdapter(adapter_SP);
        }
    }

    public void select_SanPham_Loc(int maLSP){
        list_SP = new ArrayList<>();

        list_SP = db.getSanPhamTheo_LoaiSanPham(maLSP);
        if(list_SP.size()>0){
            for(SanPham sp : list_SP){
                adapter_SP = new SanPhamAdapter(this,R.layout.row_sanpham,list_SP);
                listView.setAdapter(adapter_SP);
            }
        }else{
            adapter_SP = new SanPhamAdapter(this,R.layout.row_sanpham,list_SP);
            listView.setAdapter(adapter_SP);
        }
    }

    public int timVitri(int maLoai){
        int vitri = 0;
        for(LoaiSanPham lsp : list_LoaiSanPham){
            if(lsp.getMaLoai() == maLoai) {
                return vitri;
            }
            vitri++;
        }
        return vitri;
    }
}