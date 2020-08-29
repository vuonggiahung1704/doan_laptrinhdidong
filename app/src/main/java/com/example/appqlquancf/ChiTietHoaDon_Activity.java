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
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.LoaiSanPham;
import entities.NhanVien;
import entities.SanPham;

public class ChiTietHoaDon_Activity extends AppCompatActivity {
    //Giao diên
    Button btnThem, btnXoa, btnCapNhat, btnThanhToan, btnQuayLai;
    Spinner spinner_LSP;
    Spinner spinner_SP;

    TextView edtTen, edtDonGia;
    TextView textViewTongTien;

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
    private double tongTien;
    private int maHD;

    private String tenDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donhang_layout);

        //khởi tạo list
        list_CTHD = new ArrayList<>();
        list_LoaiSanPham = new ArrayList<>();
        list_SP = new ArrayList<>();

        AnhXa();
        maHD = getIntent().getIntExtra("maHD", -1);
        tenDangNhap = getIntent().getStringExtra("tenDangNhap");

        select_CTHD_MaHD(maHD);
        select_LoaiSP();
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
                if (sp == null) {
                    Toast.makeText(ChiTietHoaDon_Activity.this, "Chọn sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    them_ChiTietHoaDon();
                    select_LoaiSP();
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

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("hh:mm:ss");
                Date today = new Date();
                String gio = df.format(today);
                db.updateHoaDon_ThanhToan(maHD, 1, tenDangNhap,gio);

                Intent intent = new Intent(ChiTietHoaDon_Activity.this, Main_NV_Activity.class);
                intent.putExtra("tenDangNhap",tenDangNhap);
                startActivity(intent);
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietHoaDon_Activity.this, Main_NV_Activity.class);
                intent.putExtra("tenDangNhap",tenDangNhap);
                startActivity(intent);
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

    //Chạy khi vào trang
    private void AnhXa() {
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        edtTen = findViewById(R.id.textViewTenSP);
        edtDonGia = findViewById(R.id.textViewDonGia);
        edtSoLuong = findViewById(R.id.edtSoluong);
        textViewTongTien = findViewById(R.id.textViewTongHD);

        spinner_LSP = findViewById(R.id.spinnerLSP);
        spinner_SP = findViewById(R.id.spinnerSP);

        listView = findViewById(R.id.listViewCTHD);
        db = new DatabaseSQL(this);
    }

    public void select_LoaiSP() {
        list_LoaiSanPham = new ArrayList<>();

        list_LoaiSanPham = db.getAllLoaiSP();
        if (list_LoaiSanPham.size() > 0) {
            for (LoaiSanPham lsp : list_LoaiSanPham) {
                adapter_LSP = new LoaiSanPhamAdapter(this, R.layout.row_loaisp, list_LoaiSanPham);
                spinner_LSP.setAdapter(adapter_LSP);
            }
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

    //Hiển thị chi tiết hóa đơn theo Ma HS
    public void select_CTHD_MaHD(int maHD) {
        list_CTHD = new ArrayList<>();
        tongTien = 0;
        list_CTHD = db.getAllChiTietHoaDon_HoaDon(maHD);
        if (list_CTHD.size() > 0) {
            for (ChiTietHoaDon cthd : list_CTHD) {
                tongTien += cthd.getTongTien();
                adapter_CTHD = new CTHDAdapter(this, R.layout.row_cthd, list_CTHD);
                listView.setAdapter(adapter_CTHD);
            }
            db.updateHoaDon_Tien(maHD, tongTien);
            textViewTongTien.setText("Tổng tiền: " + tongTien + "đ");
        } else {
            adapter_CTHD = new CTHDAdapter(this, R.layout.row_cthd, list_CTHD);
            listView.setAdapter(adapter_CTHD);
            db.updateHoaDon_Tien(maHD, tongTien);
            textViewTongTien.setText("Tổng tiền: " + tongTien + "đ");
        }
    }

    //Hiển thị thông tin sản phẩm khi được chọn
    public void valid(SanPham sp) {
        edtTen.setText(sp.getTenSP());
        edtDonGia.setText("" + sp.getDonGia());
    }

    //Hiển thị thông tin CTHD khi được chọn
    public void valid_CTHD(ChiTietHoaDon cthd) {
        SanPham sp = db.getIDSanPham(cthd.getSp().getMaSP());
        edtTen.setText(sp.getTenSP());
        edtDonGia.setText("" + sp.getDonGia());
        edtSoLuong.setText("" + cthd.getSoLuong());
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

    //Thêm
    public boolean kiemTraNhap(String soLuong) {
        if (soLuong.equals("")) {
            return false;
        }
        return true;
    }

    public void them_ChiTietHoaDon() {
        String soLuong_string = edtSoLuong.getText().toString();
        if (kiemTraNhap(soLuong_string)) {
            int soluong = Integer.parseInt(soLuong_string);
            double thanhTien = soluong * sp.getDonGia();
            ChiTietHoaDon cthd_them = new ChiTietHoaDon();
            cthd_them.setSp(sp);
            cthd_them.setSoLuong(soluong);
            cthd_them.setTongTien(thanhTien);
            cthd_them.setHd(new HoaDon(maHD));
            if (db.insertChiTietHoaDon(cthd_them) > 0) {
                clear();
                select_CTHD_MaHD(maHD);
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    //Xóa
    public void delete() {
        if (cthd == null) {
            Toast.makeText(this, "Vui lòng chọn chi tiết hóa đơn cần xóa", Toast.LENGTH_SHORT).show();
        } else {
            if (db.deleteCTHD(cthd.getMaCTHD())) {
                clear();
                select_CTHD_MaHD(maHD);
            }
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

    //Cập nhật
    public void update() {
        if (cthd == null) {
            Toast.makeText(this, "Vui lòng chọn chi tiết hóa đơn cần cập nhật", Toast.LENGTH_SHORT).show();
        } else {
            String soLuong_s = edtSoLuong.getText().toString();
            int soLuong = Integer.parseInt(soLuong_s);
            cthd.setSoLuong(soLuong);
            cthd.setSp(sp);
            double tongTien = soLuong * sp.getDonGia();
            cthd.setTongTien(tongTien);
            if (db.updateChiTietHoaDon(cthd) > 0) {
                clear();
                select_CTHD_MaHD(maHD);
            }
        }
    }

}