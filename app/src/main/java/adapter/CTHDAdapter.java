package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appqlquancf.DatabaseSQL;
import com.example.appqlquancf.R;

import java.util.List;

import entities.ChiTietHoaDon;
import entities.LoaiSanPham;
import entities.SanPham;

public class CTHDAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ChiTietHoaDon> list;

    public CTHDAdapter(Context context, int layout, List<ChiTietHoaDon> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        TextView textView1 = view.findViewById(R.id.TenSP);
        TextView textView4 = view.findViewById(R.id.DonGia);
        TextView textView2 = view.findViewById(R.id.SoLuong);
        TextView textView3 = view.findViewById(R.id.ThanhTien);

        ChiTietHoaDon cthd = list.get(i);
        DatabaseSQL db = new DatabaseSQL(context);
        SanPham sp = db.getIDSanPham(cthd.getSp().getMaSP());

        textView1.setText(sp.getTenSP());
        textView4.setText(""+sp.getDonGia());
        textView2.setText(""+cthd.getSoLuong());

        double thanhTien = sp.getDonGia() * cthd.getSoLuong();

        textView3.setText(""+thanhTien);

        return view;
    }
}
