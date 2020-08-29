package com.example.appqlquancf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.HoaDon;
import entities.SanPham;

public class ThongKe_Activity extends AppCompatActivity implements OnChartValueSelectedListener {
    DatabaseSQL db;
    List<SanPham> list_SP;
    Button btnQuayLai;

    TextView txtTongDoangThu,txtSP1,txtSP2;

    List<String> list_TenSP;
    List<Integer> list_data;

    List<String> list_TenSP_1;
    List<Integer> list_data_1;

    PieChart mChart,m1Chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_ke_layout);

        list_SP = new ArrayList<>();

        AnhXa();
        HienThi1();
        HienThi2();

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DoanhThu();

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int vitri = (int) h.getX();
                Toast.makeText(ThongKe_Activity.this, "Số lượng: "
                        + e.getY()
                        + ", sản phẩm: "
                        + list_TenSP.get(vitri), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        m1Chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int vitri = (int) h.getX();
                Toast.makeText(ThongKe_Activity.this, "Số lượng: "
                        + e.getY()
                        + ", sản phẩm: "
                        + list_TenSP_1.get(vitri), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        timSP1_Min_Max();
        timSP2_Min_Max();
    }

    public void AnhXa(){
        db= new DatabaseSQL(this);
        mChart = findViewById(R.id.piechart);
        m1Chart = findViewById(R.id.piechart_Tong);
        txtTongDoangThu = findViewById(R.id.DoanhThu);
        txtSP1 = findViewById(R.id.SP1);
        txtSP2 = findViewById(R.id.SP2);
        btnQuayLai = findViewById(R.id.btnQuayLai);
    }

    public void HienThi1(){
        list_SP = db.getAllSanPham();
        list_TenSP = new ArrayList<>();
        list_data = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String ngay = df.format(today);

        for(SanPham sp : list_SP){
            list_TenSP.add(sp.getTenSP());
            list_data.add(db.get_SanPham_CTHD_ThongKE(sp.getMaSP(),ngay));
        }

        addDataSet(mChart,list_TenSP,list_data);
    }

    public void HienThi2(){
        list_SP = db.getAllSanPham();
        list_TenSP_1 = new ArrayList<>();
        list_data_1 = new ArrayList<>();

        for(SanPham sp : list_SP){
            list_TenSP_1.add(sp.getTenSP());
            list_data_1.add(db.get_SanPham_CTHD_ThongKE(sp.getMaSP()));
        }

        addDataSet(m1Chart,list_TenSP_1,list_data_1);
    }

    public void DoanhThu(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String ngay = df.format(today);
        double tongTien = 0;
        List<HoaDon> list = db.getAllHoaDon_ThanhToan(1,ngay);
        for (HoaDon hd : list){
            tongTien += hd.getThanhTien();
        }
        txtTongDoangThu.setText("Doanh thu hôm nay: "+tongTien +"đ");
    }

    public void timSP1_Min_Max(){
        list_SP = db.getAllSanPham();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String ngay = df.format(today);
        int max = db.get_SanPham_CTHD_ThongKE(list_SP.get(0).getMaSP(),ngay);
        int vitri = 0;
        for (int i=1;i<list_SP.size();i++){
            int soLuong = db.get_SanPham_CTHD_ThongKE(list_SP.get(i).getMaSP(),ngay);
            if(soLuong > max){
                max = soLuong;
                vitri = i;
            }
        }

        int min = db.get_SanPham_CTHD_ThongKE(list_SP.get(0).getMaSP(),ngay);
        int vitri_min = 0;
        for (int i=1;i<list_SP.size();i++){
            int soLuong = db.get_SanPham_CTHD_ThongKE(list_SP.get(i).getMaSP(),ngay);
            if(soLuong < min){
                min = soLuong;
                vitri_min = i;
            }
        }

        txtSP1.setText("Được bán nhiều nhất: "+ list_SP.get(vitri).getTenSP() + "("+max+")"
                    +"\nĐược bán ít nhất   : "+ list_SP.get(vitri_min).getTenSP() + "("+min+")" );
    }

    public void timSP2_Min_Max(){
        list_SP = db.getAllSanPham();
        int max = db.get_SanPham_CTHD_ThongKE(list_SP.get(0).getMaSP());
        int vitri = 0;
        for (int i=1;i<list_SP.size();i++){
            int soLuong = db.get_SanPham_CTHD_ThongKE(list_SP.get(i).getMaSP());
            if(soLuong > max){
                max = soLuong;
                vitri = i;
            }
        }

        int min = db.get_SanPham_CTHD_ThongKE(list_SP.get(0).getMaSP());
        int vitri_min = 0;
        for (int i=1;i<list_SP.size();i++){
            int soLuong = db.get_SanPham_CTHD_ThongKE(list_SP.get(i).getMaSP());
            if(soLuong < min){
                min = soLuong;
                vitri_min = i;
            }
        }
        txtSP2.setText("Được bán nhiều nhất: "+ list_SP.get(vitri).getTenSP() + "("+max+")"
                +"\nĐược bán ít nhất   : "+ list_SP.get(vitri_min).getTenSP() + "("+min+")" );
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }

    @Override
    public void onNothingSelected() {
    }

    public void addDataSet(PieChart pieChart ,List<String> list_TenSP,List<Integer> list_data) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < list_data.size();i++){
            yEntrys.add(new PieEntry(list_data.get(i),i));
        }
        for (int i = 0; i < list_TenSP.size();i++){
            xEntrys.add(list_TenSP.get(i));
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Sản phẩm");
//        pieDataSet.setSliceSpace(2);
//        pieDataSet.setHighlightEnabled(true);
//        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.WHITE);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.TRANSPARENT);

        pieDataSet.setColors(colors);
        pieDataSet.setVisible(true);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);

        PieData pieData=new PieData(pieDataSet);

        pieChart.setData(pieData);
        //pieChart.invalidate();
    }

}