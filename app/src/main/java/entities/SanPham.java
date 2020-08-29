package entities;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int maSP;
    private LoaiSanPham loaiSP;
    private String tenSP;
    private double donGia;

    public SanPham(int maSP, LoaiSanPham loaiSP, String tenSP, double donGia) {
        this.maSP = maSP;
        this.loaiSP = loaiSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
    }

    public SanPham(int maSP) {
        this.maSP = maSP;
    }

    public SanPham() {
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public LoaiSanPham getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(LoaiSanPham loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
