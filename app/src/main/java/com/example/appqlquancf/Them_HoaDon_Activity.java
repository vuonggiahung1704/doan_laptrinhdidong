package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.CTHDAdapter;
import adapter.LoaiSanPhamAdapter;
import adapter.SPAdapter;
import adapter.SanPhamAdapter;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.LoaiSanPham;
import entities.NhanVien;
import entities.SanPham;

public class Them_HoaDon_Activity extends AppCompatActivity {
    Button btnThem, btnXoa, btnCapNhat, btnHuy, btnLuu;
    Spinner spinner_LSP;
    Spinner spinner_SP;

    TextView edtTen, edtDonGia,textViewTongTien;
    EditText edtSoLuong;
    ListView listView;

    List<LoaiSanPham> list_LoaiSanPham;
    LoaiSanPhamAdapter adapter_LSP;

    List<SanPham> list_SP;
    SPAdapter adapter_SP;

    List<ChiTietHoaDon> list_CTHD;
    CTHDAdapter adapter_CTHD;

    DatabaseSQL db;
    private SanPham sp = null;
    private LoaiSanPham lsp = null;
    private HoaDon hd = null;
    private ChiTietHoaDon cthd = null;
    private double tongTien = 0;

    private String tenDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themdonhang_layout);

        list_CTHD = new ArrayList<>();
        hd = new HoaDon();

        AnhXa();

        tenDangNhap = getIntent().getStringExtra("tenDangNhap");

        select_LoaiSP();
        select_CTHD();
        spinner_LSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lsp = list_LoaiSanPham.get(i);
                select_SanPham(lsp.getMaLoai());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp = list_SP.get(i);
                valid(sp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp == null){
                    Toast.makeText(Them_HoaDon_Activity.this, "Chọn sản phẩm", Toast.LENGTH_SHORT).show();
                }else {
                    them_ChiTietHoaDon();
                }
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
                hienThiDialog();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taoMoiHoaDon();
                quayLai();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quayLai();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cthd = list_CTHD.get(i);
                valid_CTHD(cthd);
                try{
                    for (int ctr=0;ctr<= list_CTHD.size() ;ctr++){
                        if(i==ctr){
                            listView.getChildAt(ctr).setBackgroundColor(Color.LTGRAY);
                        }else{
                            listView.getChildAt(ctr).setBackgroundColor(Color.WHITE);
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void AnhXa() {
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        textViewTongTien = findViewById(R.id.textViewTongHD);

        edtTen = findViewById(R.id.textViewTenSP);
        edtDonGia = findViewById(R.id.textViewDonGia);
        edtSoLuong = findViewById(R.id.edtSoluong);

        spinner_LSP = findViewById(R.id.spinnerLSP);
        spinner_SP = findViewById(R.id.spinnerSP);

        listView = findViewById(R.id.listViewCTHD);
        db = new DatabaseSQL(this);
    }

    public void delete() {
        if (cthd == null) {
            Toast.makeText(this, "Vui lòng chọn chi tiết hóa đơn cần xóa", Toast.LENGTH_SHORT).show();
        } else {
            for (ChiTietHoaDon cthd_list : list_CTHD) {
                if (cthd_list.getSp().getMaSP() == cthd.getSp().getMaSP()) {
                    list_CTHD.remove(cthd_list);
                    select_CTHD();
                    clear();
                    return;
                }
            }
        }
    }

    public void update() {
        if (cthd == null) {
            Toast.makeText(this, "Vui lòng chọn chi tiết hóa đơn cần cập nhật", Toast.LENGTH_SHORT).show();
        } else {
            String soLuong_s = edtSoLuong.getText().toString();
            int soLuong = Integer.parseInt(soLuong_s);
            double thanhTien = soLuong * cthd.getSp().getDonGia();
            for (ChiTietHoaDon cthd_list : list_CTHD){
                if(cthd_list.getSp().getMaSP() == sp.getMaSP()){
                    cthd_list.setSoLuong(soLuong);
                    cthd_list.setTongTien(cthd.getSoLuong()*sp.getDonGia());
                    clear();
                    select_CTHD();
                    return;
                }
            }
            cthd.setSp(sp);
            cthd.setSoLuong(soLuong);
            cthd.setTongTien(thanhTien);
            clear();
            select_CTHD();
        }
    }

    public void hienThiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa sản phẩm");
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

    public boolean kiemTraNhap(String soLuong) {
        if (soLuong.equals("")) {
            return false;
        }
        return true;
    }

    public void select_CTHD() {
        tongTien = 0;
        if (list_CTHD.size() > 0) {
            for (ChiTietHoaDon cthd : list_CTHD) {
                tongTien += cthd.getTongTien();
                adapter_CTHD = new CTHDAdapter(this, R.layout.row_cthd, list_CTHD);
                listView.setAdapter(adapter_CTHD);
            }
            textViewTongTien.setText("Tổng tiền:"+ tongTien+"đ");
        } else {
            adapter_CTHD = new CTHDAdapter(this, R.layout.row_cthd, list_CTHD);
            listView.setAdapter(adapter_CTHD);
            textViewTongTien.setText("Tổng tiền:"+ tongTien+"đ");
        }
    }

    public void them_ChiTietHoaDon() {
        String soLuong_string = edtSoLuong.getText().toString();
        if (kiemTraNhap(soLuong_string)) {
            int soluong = Integer.parseInt(soLuong_string);
            double tongTien = soluong * sp.getDonGia();
            for (ChiTietHoaDon cthd:list_CTHD){
                if(cthd.getSp().getMaSP() == sp.getMaSP()){
                    cthd.setSoLuong(cthd.getSoLuong()+soluong);
                    cthd.setTongTien(cthd.getSoLuong()*sp.getDonGia());
                    clear();
                    select_CTHD();
                    return;
                }
            }
            ChiTietHoaDon cthd_new = new ChiTietHoaDon();
            cthd_new.setSp(sp);
            cthd_new.setSoLuong(soluong);
            cthd_new.setTongTien(tongTien);
            list_CTHD.add(cthd_new);
            clear();
            select_CTHD();
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    public void select_LoaiSP() {
        list_LoaiSanPham = new ArrayList<>();

        list_LoaiSanPham = db.getAllLoaiSP();
        if (list_LoaiSanPham.size() > 0) {
            for (LoaiSanPham lsp : list_LoaiSanPham) {
                adapter_LSP = new LoaiSanPhamAdapter(this, R.layout.row_loaisp, list_LoaiSanPham);
                spinner_LSP.setAdapter(adapter_LSP);
            }
        }else{
            adapter_LSP = new LoaiSanPhamAdapter(this, R.layout.row_loaisp, list_LoaiSanPham);
            spinner_LSP.setAdapter(adapter_LSP);
        }
    }

    public void select_SanPham(int maLoai) {
        list_SP = new ArrayList<>();

        list_SP = db.getSanPhamTheo_LoaiSanPham(maLoai);
        if (list_SP.size() > 0) {
            for (SanPham sp : list_SP) {
                adapter_SP = new SPAdapter(this, R.layout.row_sanpham_2, list_SP);
                spinner_SP.setAdapter(adapter_SP);
            }
        }
    }

    public void valid(SanPham sp) {
        edtTen.setText(sp.getTenSP());
        edtDonGia.setText("" + sp.getDonGia());
    }

    public void valid_CTHD(ChiTietHoaDon cthd) {
        SanPham sp_sql = db.getIDSanPham(cthd.getSp().getMaSP());
        edtTen.setText(sp_sql.getTenSP());
        edtDonGia.setText("" + sp_sql.getDonGia());
        edtSoLuong.setText("" + cthd.getSoLuong());
    }

    public void save_ChiTietHoaDon() {
        tongTien = 0;
        if(list_CTHD.size()>0) {
            for (ChiTietHoaDon chiTietHoaDon : list_CTHD) {
                tongTien += chiTietHoaDon.getTongTien();
                chiTietHoaDon.setHd(new HoaDon(hd.getMaHD()));
                db.insertChiTietHoaDon(chiTietHoaDon);
            }
        }
    }

    public void taoMoiHoaDon() {
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat df2 = new SimpleDateFormat("hh:mm:ss");
        Date today = new Date();
        String ngay = df1.format(today);
        String gio = df2.format(today);
        hd = new HoaDon();
        hd.setNvLapHD(new NhanVien(tenDangNhap));
        hd.setNgayLapHD(ngay);
        hd.setThoiGianVao(gio);
        hd.setThanhTien(0);
        hd.setThanhToan(0);
        db.insertHoaDon(hd);

        hd = db.getHoaDon_MoiLap(tenDangNhap, ngay, gio);
        save_ChiTietHoaDon();
        db.updateHoaDon_Tien(hd.getMaHD(),tongTien);
    }

    public void clear() {
        edtTen.setText("");
        edtDonGia.setText("");
        edtSoLuong.setText("");

        sp = null;
        lsp = null;
        cthd = null;

        spinner_LSP.setAdapter(adapter_LSP);
        spinner_SP.setAdapter(adapter_SP);

    }

    public void quayLai(){
        Intent intent = new Intent(Them_HoaDon_Activity.this,Main_NV_Activity.class);
        intent.putExtra("tenDangNhap",tenDangNhap);
        startActivity(intent);
    }
}