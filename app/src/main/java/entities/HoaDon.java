package entities;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int maHD;
    private NhanVien nvLapHD;
    private NhanVien nvTTHD;
    private String ngayLapHD;
    private String thoiGianVao;
    private String thoiGianRa;
    private double thanhTien;
    private int thanhToan;

    public HoaDon(int maHD, NhanVien nvLapHD, NhanVien nvTTHD, String ngayLapHD, String thoiGianVao, String thoiGianRa, double thanhTien, int thanhToan) {
        this.maHD = maHD;
        this.nvLapHD = nvLapHD;
        this.nvTTHD = nvTTHD;
        this.ngayLapHD = ngayLapHD;
        this.thoiGianVao = thoiGianVao;
        this.thoiGianRa = thoiGianRa;
        this.thanhTien = thanhTien;
        this.thanhToan = thanhToan;
    }

    public HoaDon(int maHD, NhanVien nvLapHD, String ngayLapHD, String thoiGianVao, double thanhTien, int thanhToan) {
        this.maHD = maHD;
        this.nvLapHD = nvLapHD;
        this.ngayLapHD = ngayLapHD;
        this.thoiGianVao = thoiGianVao;
        this.thanhTien = thanhTien;
        this.thanhToan = thanhToan;
    }

    public HoaDon(int maHD) {
        this.maHD = maHD;
    }

    public HoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public NhanVien getNvLapHD() {
        return nvLapHD;
    }

    public void setNvLapHD(NhanVien nvLapHD) {
        this.nvLapHD = nvLapHD;
    }

    public NhanVien getNvTTHD() {
        return nvTTHD;
    }

    public void setNvTTHD(NhanVien nvTTHD) {
        this.nvTTHD = nvTTHD;
    }

    public String getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(String ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public String getThoiGianVao() {
        return thoiGianVao;
    }

    public void setThoiGianVao(String thoiGianVao) {
        this.thoiGianVao = thoiGianVao;
    }

    public String getThoiGianRa() {
        return thoiGianRa;
    }

    public void setThoiGianRa(String thoiGianRa) {
        this.thoiGianRa = thoiGianRa;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(int thanhToan) {
        this.thanhToan = thanhToan;
    }
}
