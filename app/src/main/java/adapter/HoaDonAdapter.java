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
import entities.HoaDon;
import entities.SanPham;

public class HoaDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HoaDon> list;

    public HoaDonAdapter(Context context, int layout, List<HoaDon> list) {
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

        TextView textView1 = view.findViewById(R.id.MaHD);
        TextView textView2 = view.findViewById(R.id.TongTien);
        TextView textView3 = view.findViewById(R.id.ThanhToan);


        HoaDon hd = list.get(i);

        textView1.setText("Mã HD    : "+hd.getMaHD());
        textView2.setText("Tổng tiền: "+hd.getThanhTien()+"đ");

        if(hd.getThanhToan()==0)
            textView3.setText("Chưa thanh toán");
        else
            textView3.setText("Đã thanh toán");
        return view;
    }
}
