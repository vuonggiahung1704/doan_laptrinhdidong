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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.LoaiSanPhamAdapter;
import entities.LoaiSanPham;

public class LoaiSP_Activity extends AppCompatActivity {
    Button btnThem, btnXoa, btnCapNhat, btnQuayLai;
    EditText edtTen;
    ListView listView;
    LoaiSanPhamAdapter adapter;
    List<LoaiSanPham> list;
    DatabaseSQL db;
    private LoaiSanPham lsp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaisanpham_layout);

        list = new ArrayList<>();

        AnhXa();
        select_LoaiSP();
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_LoaiSP();
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
                if(lsp == null){
                    Toast.makeText(LoaiSP_Activity.this,"Vui lòng chọn loại sản phẩm",Toast.LENGTH_SHORT).show();
                }else{
                    hienThiDialog();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lsp = list.get(i);
                valid(lsp);
            }
        });
    }

    public void update() {
        if (lsp == null) {
            Toast.makeText(this, "Vui lòng chọn loại sản phẩm cần cập nhật", Toast.LENGTH_SHORT).show();
        } else {
            lsp.setTenLoai(edtTen.getText().toString());
            if (db.updateLoaiSanPham(lsp) > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                clear();
                select_LoaiSP();
            } else {
                Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hienThiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa loại sản phẩm");
        builder.setMessage("Bạn có muốn xóa loại sản phẩm");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
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

    public boolean kiemTraNhap(String tenLSP) {
        if (tenLSP.equals("")) {
            return false;
        }
        return true;
    }
    public void clear() {
        edtTen.setText("");
        lsp = null;
    }

    public void valid(LoaiSanPham lsp) {
        edtTen.setText(lsp.getTenLoai());
    }

    public void delete() {
        if (db.deleteLoaiSanPham(lsp.getMaLoai())) {
            select_LoaiSP();
            clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    //Loại sản phẩm
    public void save_LoaiSP(){
        String tenLoai = edtTen.getText().toString();
        if(kiemTraNhap(tenLoai)){
            LoaiSanPham lsp = new LoaiSanPham();
            lsp.setTenLoai(tenLoai);
            if(db.insertLoaiSanPham(lsp)>0){
                Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
                clear();
                select_LoaiSP();
            }else{
                Toast.makeText(this,"Lưu không thành công",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }
    }
    public void select_LoaiSP(){
        list = new ArrayList<>();

        list = db.getAllLoaiSP();
        if(list.size()>0){
            for(LoaiSanPham lsp : list){
                adapter = new LoaiSanPhamAdapter(this,R.layout.row_loaisp,list);
                listView.setAdapter(adapter);
            }
        }
        else{
            adapter = new LoaiSanPhamAdapter(this,R.layout.row_loaisp,list);
            listView.setAdapter(adapter);
        }
    }

    public void AnhXa() {
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        edtTen = findViewById(R.id.edtTenLoaiSP);
        listView = findViewById(R.id.listViewLSP);
        db = new DatabaseSQL(this);
    }
}