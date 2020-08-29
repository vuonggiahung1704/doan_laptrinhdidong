package entity;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private int phanQuyen;

    public NhanVien(String tenDangNhap, String matKhau, String hoTen, int phanQuyen) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.phanQuyen = phanQuyen;
    }

    public NhanVien() {
    }

    public NhanVien(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    @Override
    public String toString() {
        return tenDangNhap + "-" +  matKhau + " - " + phanQuyen + " - " + hoTen ;
    }
}
