package entity;

import java.io.Serializable;

public class ChiTietHoaDon implements Serializable {
    private String maCTHD;
    private HoaDon hd;
    private SanPham sp;
    private int soLuong;
    private double tongTien;

    public ChiTietHoaDon(String maCTHD, HoaDon hd, SanPham sp, int soLuong, double tongTien) {
        this.maCTHD = maCTHD;
        this.hd = hd;
        this.sp = sp;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public ChiTietHoaDon(String maCTHD) {
        this.maCTHD = maCTHD;
    }

    public ChiTietHoaDon() {
    }

    public HoaDon getHd() {
        return hd;
    }

    public void setHd(HoaDon hd) {
        this.hd = hd;
    }

    public SanPham getSp() {
        return sp;
    }

    public void setSp(SanPham sp) {
        this.sp = sp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(String maCTHD) {
        this.maCTHD = maCTHD;
    }

    @Override
    public String toString() {
        return maCTHD + "-" + sp.getMaSP() + "-" + hd.getMaHD() + "-" + soLuong + "-" + tongTien;
    }
}
