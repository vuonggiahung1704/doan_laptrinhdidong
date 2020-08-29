package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appqlquancf.R;

import java.util.List;

import entities.LoaiSanPham;

public class LoaiSanPhamAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LoaiSanPham> list;

    public LoaiSanPhamAdapter(Context context, int layout, List<LoaiSanPham> list) {
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

        TextView textView2 = view.findViewById(R.id.TenLoaiSP);

        LoaiSanPham lsp = list.get(i);

        textView2.setText(lsp.getTenLoai());
        return view;
    }
}
