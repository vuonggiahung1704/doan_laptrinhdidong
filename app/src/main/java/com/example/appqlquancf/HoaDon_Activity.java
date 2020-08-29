package com.example.appqlquancf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import adapter.HoaDonAdapter;
import entities.HoaDon;

public class HoaDon_Activity extends AppCompatActivity {
    Button btnLoc, btnQuayLai, btnTim;
    EditText edtTim;
    ListView lv;

    List<HoaDon> list_HD;
    HoaDonAdapter adapter_HD;

    DatabaseSQL db;
    private HoaDon hd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanly_hoadon);

        list_HD = new ArrayList<>();
        AnhXa();
        select_HD();

        registerForContextMenu(lv);

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hd = list_HD.get(i);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                hd = list_HD.get(i);
                return false;
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String ngay = "";
                        String thang = "";
                        if(month<10)
                            thang = "0"+month;
                        else
                            thang = ""+month;
                        if(day<10)
                            ngay = "0"+day;
                        else
                            ngay = ""+day;

                        String ngay_tim = ""+ngay+"/"+thang+"/"+year+"";
                        select_HD_Ngay(ngay_tim);
                    }
                };
                DatePickerDialog pic = new DatePickerDialog(HoaDon_Activity.this,callback,2020,8,29);
                pic.setTitle("Chọn ngày");
                pic.show();
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtTim.getText().toString();
                if(ma.equals("")){
                    select_HD();
                }else{
                    int maHD = Integer.parseInt(ma);
                    tim_HD(maHD);
                }
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnCTHD:
                Intent intent = new Intent(this, HoaDon_ThanhToan_Activity.class);
                intent.putExtra("maHD", hd.getMaHD());
                startActivity(intent);
                break;
            case R.id.mnXoa:
                xoa_HD();
                select_HD();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void AnhXa() {
        btnLoc = findViewById(R.id.btnLoc);
        btnTim = findViewById(R.id.btnTim);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        edtTim = findViewById(R.id.edtTim);
        lv = findViewById(R.id.listViewHD);
        db = new DatabaseSQL(this);
    }

    public void select_HD() {
        list_HD = new ArrayList<>();

        int thanhToan = 0;
        list_HD = db.getAllHoaDon();
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

    public void select_HD_Ngay(String ngay) {
        list_HD = new ArrayList<>();

        list_HD = db.getAllHoaDon_TheoNgay(ngay);
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

    public void xoa_HD(){
        int ma = hd.getMaHD();
        db.deleteCTHD_MaHD(ma);
        db.deleteHoaDon(ma);
    }

    public void tim_HD(int maHD) {
        list_HD = new ArrayList<>();
        try {
            HoaDon hd = db.getIDHoaDon(maHD);
            list_HD.add(hd);
            adapter_HD = new HoaDonAdapter(this, R.layout.row_hoadon, list_HD);
            lv.setAdapter(adapter_HD);
        }catch (Exception ex){
            Toast.makeText(this,"Không tìm thấy",Toast.LENGTH_LONG).show();
            adapter_HD = new HoaDonAdapter(this, R.layout.row_hoadon, list_HD);
            lv.setAdapter(adapter_HD);
        }
    }
}