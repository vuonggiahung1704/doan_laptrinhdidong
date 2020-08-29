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

import entities.LoaiSanPham;
import entities.SanPham;

public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SanPham> list;

    public SanPhamAdapter(Context context, int layout, List<SanPham> list) {
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

        TextView textView1 = view.findViewById(R.id.tenSP);
        TextView textView2 = view.findViewById(R.id.donGia);
        TextView textView3 = view.findViewById(R.id.Loai);

        SanPham sp = list.get(i);

        textView1.setText(sp.getTenSP());
        textView2.setText(sp.getDonGia()+"đ");

        DatabaseSQL db = new DatabaseSQL(context);
        LoaiSanPham lsp = db.getIDLoaiSanPham(sp.getLoaiSP().getMaLoai());

        textView3.setText(lsp.getTenLoai());
        return view;
    }
}
