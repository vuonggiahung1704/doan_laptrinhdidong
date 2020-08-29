package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.NhanVienAdapter;
import entities.NhanVien;

public class NhanVien_Activity extends AppCompatActivity {
    Button btnThem,btnXoa,btnCapNhat,btnTim,btnQuayLai;
    Spinner spinner_PhanQuyen;
    EditText edtTenDangNhap,edtMatKhau,edtHoTen,edtTim;
    ListView listView;

    List<String> list_phanQuyen;
    ArrayAdapter adapter_phanQuyen;

    List<NhanVien> list_NV;
    NhanVienAdapter adapter_NhanVien;

    DatabaseSQL db;
    private int QUYEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhanvien_layout);

        list_NV = new ArrayList<>();

        list_phanQuyen = new ArrayList<>();

        list_phanQuyen.add("Quản lý");
        list_phanQuyen.add("Nhân viên");

        AnhXa();

        select_NhanVien();

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinner_PhanQuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(list_phanQuyen.get(i).equals("Quản lý"))
                    QUYEN = 2;
                else
                    QUYEN = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_NhanVien();
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
                String tenDangNhap = edtTenDangNhap.getText().toString();
                if(tenDangNhap.equals("")){
                    Toast.makeText(NhanVien_Activity.this,"Vui lòng chọn nhân viên",Toast.LENGTH_SHORT).show();
                }else{
                    hienThiDialog(tenDangNhap);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NhanVien nv = list_NV.get(i);
                valid(nv);
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTim.getText().toString();
                if(ten.equals("")){
                    select_NhanVien();
                }else{
                    tim_NhanVien(ten);
                }
            }
        });

    }

    public  void delete(String tenDangNhap){
        if(db.deleteNhanVien(tenDangNhap)){
            Toast.makeText(this,"Xóa thành công",Toast.LENGTH_SHORT).show();
            clear();
            select_NhanVien();
        }
        else{
            Toast.makeText(this,"Xóa không thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public void update(){
        if(edtTenDangNhap.getText().toString().equals("")) {
            Toast.makeText(this, "Vui lòng chọn nhân viên cần cập nhật", Toast.LENGTH_SHORT).show();
        }
        else {
            NhanVien nv = new NhanVien();
            nv.setTenDangNhap(edtTenDangNhap.getText().toString());
            nv.setMatKhau(edtMatKhau.getText().toString());
            nv.setHoTen(edtHoTen.getText().toString());
            nv.setPhanQuyen(QUYEN);
            if (db.updateNhanVien(nv) > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                clear();
                select_NhanVien();
            } else {
                Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hienThiDialog(final String tenDangNhap_delete){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa nhân viên");
        builder.setMessage("Bạn có muốn xóa nhân viên");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete(tenDangNhap_delete);
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

    public boolean kiemTraNhap(String tenDangNhap,String matKhau,String hoTen){
        if(tenDangNhap.equals("") || matKhau.equals("") || hoTen.equals("")){
            return false;
        }
        return true;
    }

    public void clear(){
        edtHoTen.setText("");
        edtMatKhau.setText("");
        edtTenDangNhap.setText("");
        spinner_PhanQuyen.setAdapter(adapter_phanQuyen);
        edtTim.setText("");
    }

    public void valid(NhanVien nv){
        edtHoTen.setText(nv.getHoTen());
        edtMatKhau.setText(nv.getMatKhau());
        edtTenDangNhap.setText(nv.getTenDangNhap());
        if(nv.getPhanQuyen() == 1){
            spinner_PhanQuyen.setSelection(1);
        }else {
            spinner_PhanQuyen.setSelection(0);
        }
        edtTim.setText("");
    }

    //Nhân viên
    public void save_NhanVien(){
        String tenDangNhap = edtTenDangNhap.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        String hoTen = edtHoTen.getText().toString();
        if(kiemTraNhap(tenDangNhap,matKhau,hoTen)){
            NhanVien nv = new NhanVien(tenDangNhap,matKhau,hoTen,QUYEN);
            if(db.insertNhanVien(nv)>0){
                Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show();
                clear();
                select_NhanVien();
            }else{
                Toast.makeText(this,"Lưu không thành công",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }
    }
    public void select_NhanVien(){
        list_NV = new ArrayList<>();

        list_NV = db.getAllNhanVien();
        if(list_NV.size()>0){
            for(NhanVien nv : list_NV){
                adapter_NhanVien = new NhanVienAdapter(this,R.layout.row_nhanvien,list_NV);
                listView.setAdapter(adapter_NhanVien);
            }
        }else{
            adapter_NhanVien = new NhanVienAdapter(this,R.layout.row_nhanvien,list_NV);
            listView.setAdapter(adapter_NhanVien);
        }
    }

    public void tim_NhanVien(String ten){
        list_NV = new ArrayList<>();
        try {
            list_NV = db.findNhanVien(ten);
            if(list_NV.size()>0){
                for(NhanVien nv : list_NV){
                    adapter_NhanVien = new NhanVienAdapter(this,R.layout.row_nhanvien,list_NV);
                    listView.setAdapter(adapter_NhanVien);
                }
            }else{
                Toast.makeText(this,"Không tìm thấy",Toast.LENGTH_LONG).show();
                adapter_NhanVien = new NhanVienAdapter(this,R.layout.row_nhanvien,list_NV);
                listView.setAdapter(adapter_NhanVien);
            }
        }catch (Exception ex){
            Toast.makeText(this,"Không tìm thấy",Toast.LENGTH_LONG).show();
            adapter_NhanVien = new NhanVienAdapter(this,R.layout.row_nhanvien,list_NV);
            listView.setAdapter(adapter_NhanVien);
        }
        clear();
    }

    public void AnhXa(){
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnTim = findViewById(R.id.btnTimNV);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        edtTenDangNhap = findViewById(R.id.edtTenDN);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtHoTen = findViewById(R.id.edtHoTen);

        edtTim = findViewById(R.id.edtTimNV);

        spinner_PhanQuyen = findViewById(R.id.spinnerQuyen);

        adapter_phanQuyen = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_phanQuyen);
        spinner_PhanQuyen.setAdapter(adapter_phanQuyen);

        listView = findViewById(R.id.listViewNV);

        db = new DatabaseSQL(this);
    }
}