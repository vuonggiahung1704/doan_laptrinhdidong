package com.example.appqlquancf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.LoaiSanPham;
import entities.NhanVien;
import entities.SanPham;

public class DatabaseSQL extends SQLiteOpenHelper {

    public DatabaseSQL(@Nullable Context context) {
        super(context, "DB_QL_CF", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table NhanVien(tenDangNhap text primary key, " +
                "matKhau text, " +
                "hoTen text, " +
                "quyen integer)");

        sqLiteDatabase.execSQL("create table LoaiSanPham(maLoaiSP integer primary key autoincrement, " +
                "tenLoai text)");

        sqLiteDatabase.execSQL("create table SanPham(maSanPham integer primary key autoincrement, " +
                "tenSanPham text, " +
                "donGia double, " +
                "maLoai integer constraint maLoaiSP references LoaiSanPham(maLoaiSP))");

        sqLiteDatabase.execSQL("create table HoaDon(maHD integer primary key autoincrement, " +
                "nv_lap text constraint tenDN1 references NhanVien(tenDangNhap), " +
                "nv_ltt text constraint tenDN2 references NhanVien(tenDangNhap), " +
                "ngayLapHD text , " +
                "thoiGian_vao text ," +
                "thoiGian_ra text ," +
                "thanhTien double ," +
                "thanhToan integer)");

        sqLiteDatabase.execSQL("create table ChiTietHD(maCTHD integer primary key autoincrement, " +
                "soluong integer, " +
                "tongTien double, " +
                "maHD integer constraint maHD_HD references HoaDon(maHD), " +
                "maSP integer constraint maSP references SanPham(maSanPham))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //Nhân viên
    public int insertNhanVien(NhanVien nv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenDangNhap", nv.getTenDangNhap());
        contentValues.put("matKhau", nv.getMatKhau());
        contentValues.put("hoTen", nv.getHoTen());
        contentValues.put("quyen", nv.getPhanQuyen());

        int result = (int) db.insert("NhanVien", null, contentValues);
        db.close();
        return result;
    }

    public NhanVien getIDNhanVien(String tenDangNhap) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien where tenDangNhap = '" + tenDangNhap +"'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        NhanVien nv = new NhanVien(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        cursor.moveToNext();
        cursor.close();
        return nv;
    }

    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            list.add(new NhanVien(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<NhanVien> findNhanVien(String ten) {
        ArrayList<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien where tenDangNhap like '%"+ten+"%' or hoTen like '%"+ten+"%'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            list.add(new NhanVien(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public boolean deleteNhanVien(String tenDangNhap) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete("NhanVien", "tenDangNhap" + "=?", new String[]{String.valueOf(tenDangNhap)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    public int updateNhanVien(NhanVien nv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", nv.getMatKhau());
        values.put("hoTen", nv.getHoTen());
        values.put("quyen", nv.getPhanQuyen());

        int result = db.update("NhanVien", values, "tenDangNhap" + "=?", new String[]{String.valueOf(nv.getTenDangNhap())});
        db.close();
        return result;
    }

    //Loại sản phẩm
    public int insertLoaiSanPham(LoaiSanPham lsp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", lsp.getTenLoai());

        int result = (int) db.insert("LoaiSanPham", null, contentValues);
        db.close();
        return result;
    }

    public LoaiSanPham getIDLoaiSanPham(int maLoaiSP) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from LoaiSanPham where maLoaiSP=" + maLoaiSP, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        LoaiSanPham lsp = new LoaiSanPham(cursor.getInt(0), cursor.getString(1));
        cursor.moveToNext();
        cursor.close();
        return lsp;
    }

    public ArrayList<LoaiSanPham> getAllLoaiSP() {
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from LoaiSanPham", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            list.add(new LoaiSanPham(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteLoaiSanPham(int maLSP) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete("LoaiSanPham", "maLoaiSP" + "=?", new String[]{String.valueOf(maLSP)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    public int updateLoaiSanPham(LoaiSanPham lsp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", lsp.getTenLoai());

        int result = db.update("LoaiSanPham", values, "maLoaiSP" + "=?", new String[]{String.valueOf(lsp.getMaLoai())});
        db.close();
        return result;
    }

    //Sản phẩm
    public int insertSanPham(SanPham sp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", sp.getLoaiSP().getMaLoai());
        contentValues.put("tenSanPham", sp.getTenSP());
        contentValues.put("donGia", sp.getDonGia());

        int result = (int) db.insert("SanPham", null, contentValues);
        db.close();
        return result;
    }

    public SanPham getIDSanPham(int maSP) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham where maSanPham=" + maSP, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        LoaiSanPham lsp = new LoaiSanPham(cursor.getInt(3));
        SanPham sp = new SanPham(cursor.getInt(0), lsp, cursor.getString(1), cursor.getDouble(2));
        cursor.moveToNext();
        cursor.close();
        return sp;
    }

    public ArrayList<SanPham> getAllSanPham() {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            LoaiSanPham lsp = new LoaiSanPham(cursor.getInt(3));
            SanPham sp = new SanPham(cursor.getInt(0), lsp, cursor.getString(1), cursor.getDouble(2));
            list.add(sp);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<SanPham> getSanPhamTheo_LoaiSanPham(int maLoaiSP) {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham where maLoai=" + maLoaiSP, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            LoaiSanPham lsp = new LoaiSanPham(cursor.getInt(3));
            SanPham sp = new SanPham(cursor.getInt(0), lsp, cursor.getString(1), cursor.getDouble(2));
            list.add(sp);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteSanPham(int maSP) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete("SanPham", "maSanPham" + "=?", new String[]{String.valueOf(maSP)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    public int updateSanPham(SanPham sp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSanPham", sp.getTenSP());
        values.put("donGia", sp.getDonGia());
        values.put("maLoai", sp.getLoaiSP().getMaLoai());
        int result = db.update("SanPham", values, "maSanPham" + "=?", new String[]{String.valueOf(sp.getMaSP())});
        db.close();
        return result;
    }

    //Hóa đơn
    public int insertHoaDon(HoaDon hd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nv_lap", hd.getNvLapHD().getTenDangNhap());
        contentValues.put("ngayLapHD", hd.getNgayLapHD());
        contentValues.put("thoiGian_vao", hd.getThoiGianVao());
        contentValues.put("thanhTien", hd.getThanhTien());
        contentValues.put("thanhToan", hd.getThanhToan());

        int result = (int) db.insert("HoaDon", null, contentValues);
        db.close();
        return result;
    }

    public HoaDon getIDHoaDon(int maHD) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HoaDon where maHD=" + maHD, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        NhanVien nv_lap = new NhanVien(cursor.getString(1));
        NhanVien nv_tt = new NhanVien(cursor.getString(2));
        String ngay = cursor.getString(3);
        String time_vao = cursor.getString(4);
        String time_ra = cursor.getString(5);
        double thanhTien = cursor.getDouble(6);
        int thanhToan = cursor.getInt(7);
        HoaDon hd = new HoaDon(cursor.getInt(0), nv_lap, nv_tt, ngay,time_vao ,time_ra,thanhTien, thanhToan);
        cursor.moveToNext();
        cursor.close();
        return hd;
    }

    public HoaDon getHoaDon_MoiLap(String maNV,String ngay_tim,String gio_tim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HoaDon " +
                "where nv_lap = '" + maNV + "'" +
                "and thoiGian_vao ='"+ gio_tim + "'" +
                "and ngayLapHD ='"+ ngay_tim +"'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        NhanVien nv_lap = new NhanVien(cursor.getString(1));
        NhanVien nv_tt = new NhanVien(cursor.getString(2));
        String ngay = cursor.getString(3);
        String time_vao = cursor.getString(4);
        String time_ra = cursor.getString(5);
        double thanhTien = cursor.getDouble(6);
        int thanhToan = cursor.getInt(7);
        HoaDon hd = new HoaDon(cursor.getInt(0), nv_lap, nv_tt, ngay,time_vao ,time_ra,thanhTien, thanhToan);
        cursor.moveToNext();
        cursor.close();
        return hd;
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HoaDon", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            NhanVien nv_lap = new NhanVien(cursor.getString(1));
            NhanVien nv_tt = new NhanVien(cursor.getString(2));
            String ngay = cursor.getString(3);
            String time_vao = cursor.getString(4);
            String time_ra = cursor.getString(5);
            double thanhTien = cursor.getDouble(6);
            int thanhToan = cursor.getInt(7);
            HoaDon hd = new HoaDon(cursor.getInt(0), nv_lap, nv_tt, ngay,time_vao ,time_ra,thanhTien, thanhToan);
            list.add(hd);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<HoaDon> getAllHoaDon_TheoNgay(String ngay) {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HoaDon where ngayLapHD='"+ngay+"'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            NhanVien nv_lap = new NhanVien(cursor.getString(1));
            NhanVien nv_tt = new NhanVien(cursor.getString(2));
            String ngay_lap = cursor.getString(3);
            String time_vao = cursor.getString(4);
            String time_ra = cursor.getString(5);
            double thanhTien = cursor.getDouble(6);
            int thanhToan = cursor.getInt(7);
            HoaDon hd = new HoaDon(cursor.getInt(0), nv_lap, nv_tt, ngay_lap,time_vao ,time_ra,thanhTien, thanhToan);
            list.add(hd);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<HoaDon> getAllHoaDon_ThanhToan(int isThanhToan,String ngay_tim) {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HoaDon where thanhToan="+isThanhToan +" and ngaylapHD = '"+ngay_tim+"'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            NhanVien nv_lap = new NhanVien(cursor.getString(1));
            NhanVien nv_tt = new NhanVien(cursor.getString(2));
            String ngay = cursor.getString(3);
            String time_vao = cursor.getString(4);
            String time_ra = cursor.getString(5);
            double thanhTien = cursor.getDouble(6);
            int thanhToan = cursor.getInt(7);
            HoaDon hd = new HoaDon(cursor.getInt(0), nv_lap, nv_tt, ngay,time_vao ,time_ra,thanhTien, thanhToan);
            list.add(hd);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteHoaDon(int maHD) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete("HoaDon", "maHD" + "=?", new String[]{String.valueOf(maHD)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    public int updateHoaDon_Tien(int maHD, double thanhTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("thanhTien", thanhTien);
        int result = db.update("HoaDon", values, "maHD" + "=?", new String[]{String.valueOf(maHD)});
        db.close();
        return result;
    }

    public int updateHoaDon_ThanhToan(int maHD, int thanhToan,String maNV,String tg_ra) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("thanhToan", thanhToan);
        values.put("nv_ltt", maNV);
        values.put("thoiGian_ra", tg_ra);
        int result = db.update("HoaDon", values, "maHD" + "=?", new String[]{String.valueOf(maHD)});
        db.close();
        return result;
    }


    //Chi tiết hóa đơn
    public int insertChiTietHoaDon(ChiTietHoaDon cthd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soluong", cthd.getSoLuong());
        contentValues.put("tongTien", cthd.getTongTien());
        contentValues.put("maHD", cthd.getHd().getMaHD());
        contentValues.put("maSP", cthd.getSp().getMaSP());

        int result = (int) db.insert("ChiTietHD", null, contentValues);
        db.close();
        return result;
    }

    public ChiTietHoaDon getIDChiTietHoaDon(int maCTHD) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ChiTietHD where maCTHD=" + maCTHD, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int soLuong = cursor.getInt(1);
        double tongTien = cursor.getDouble(2);
        HoaDon hd = new HoaDon(cursor.getInt(3));
        SanPham sp = new SanPham(cursor.getInt(4));
        ChiTietHoaDon cthd = new ChiTietHoaDon(cursor.getInt(0), hd, sp, soLuong, tongTien);
        cursor.moveToNext();
        cursor.close();
        return cthd;
    }

    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon() {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ChiTietHD", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            int soLuong = cursor.getInt(1);
            double tongTien = cursor.getDouble(2);
            HoaDon hd = new HoaDon(cursor.getInt(3));
            SanPham sp = new SanPham(cursor.getInt(4));
            ChiTietHoaDon cthd = new ChiTietHoaDon(cursor.getInt(0), hd, sp, soLuong, tongTien);
            list.add(cthd);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon_HoaDon(int maHD) {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ChiTietHD where maHD=" + maHD, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            int soLuong = cursor.getInt(1);
            double tongTien = cursor.getDouble(2);
            HoaDon hd = new HoaDon(cursor.getInt(3));
            SanPham sp = new SanPham(cursor.getInt(4));
            ChiTietHoaDon cthd = new ChiTietHoaDon(cursor.getInt(0), hd, sp, soLuong, tongTien);
            list.add(cthd);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteCTHD(int maCTHD) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete("ChiTietHD", "maCTHD" + "=?", new String[]{String.valueOf(maCTHD)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    public boolean deleteCTHD_MaHD(int maHD) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete("ChiTietHD", "maHD" + "=?", new String[]{String.valueOf(maHD)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    public int updateChiTietHoaDon(ChiTietHoaDon cthd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soLuong", cthd.getSoLuong());
        values.put("maSP", cthd.getSp().getMaSP());
        values.put("tongTien", cthd.getTongTien());
        int result = db.update("ChiTietHD", values, "maCTHD" + "=?", new String[]{String.valueOf(cthd.getMaCTHD())});
        db.close();
        return result;
    }

    //Thống kê
    public int get_SanPham_CTHD_ThongKE(int maSP) {
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(soluong) as soluong from ChiTietHD where maSP = "+maSP, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            i = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        return i;
    }

    public int get_SanPham_CTHD_ThongKE(int maSP,String ngay) {
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(soluong) as soluong from ChiTietHD where maHD in ( select hd.maHD from HoaDon hd where hd.ngayLapHD = '"+ngay+"') and maSP = "+ maSP , null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast() == false) {
            i = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        return i;
    }
}
