package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appqlquancf.R;

import java.util.List;

import entities.NhanVien;

public class NhanVienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NhanVien> list;

    public NhanVienAdapter(Context context, int layout, List<NhanVien> list) {
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

        TextView textView1 = view.findViewById(R.id.TenDangNhap);
        TextView textView2 = view.findViewById(R.id.MatKhau);
        TextView textView3 = view.findViewById(R.id.HoTen);
        TextView textView4 = view.findViewById(R.id.quyen);

        NhanVien nv = list.get(i);

        textView1.setText(nv.getTenDangNhap());
        textView2.setText("Mật khẩu : "+nv.getMatKhau());
        textView3.setText("Họ và tên: "+nv.getHoTen());

        if(nv.getPhanQuyen() == 1){
            textView4.setText("Nhân viên");
        }else{
            textView4.setText("Quản lý");
        }
        return view;
    }
}
