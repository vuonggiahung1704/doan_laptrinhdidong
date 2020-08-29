package entity;

import java.io.Serializable;
import java.sql.Date;

public class HoaDon implements Serializable {
    private String maHD;
    private NhanVien nvLapHD;
    private NhanVien nvThanhToanHD;
    private String thoiGianLapHD;
    private String thoiGianThanhToanHD;
    private double thanhTien;
    private int thanhToan;

    public HoaDon(String maHD, NhanVien nvLapHD, NhanVien nvThanhToanHD, String thoiGianLapHD, String thoiGianThanhToanHD, double thanhTien, int thanhToan) {
        this.maHD = maHD;
        this.nvLapHD = nvLapHD;
        this.nvThanhToanHD = nvThanhToanHD;
        this.thoiGianLapHD = thoiGianLapHD;
        this.thoiGianThanhToanHD = thoiGianThanhToanHD;
        this.thanhTien = thanhTien;
        this.thanhToan = thanhToan;
    }

    public HoaDon(String maHD, NhanVien nvLapHD, String thoiGianLapHD, int thanhToan) {
        this.maHD = maHD;
        this.nvLapHD = nvLapHD;
        this.thoiGianLapHD = thoiGianLapHD;
        this.thanhToan = thanhToan;
    }

    public HoaDon(String maHD) {
        this.maHD = maHD;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public NhanVien getNvLapHD() {
        return nvLapHD;
    }

    public void setNvLapHD(NhanVien nvLapHD) {
        this.nvLapHD = nvLapHD;
    }

    public NhanVien getNvThanhToanHD() {
        return nvThanhToanHD;
    }

    public void setNvThanhToanHD(NhanVien nvThanhToanHD) {
        this.nvThanhToanHD = nvThanhToanHD;
    }

    public String getThoiGianLapHD() {
        return thoiGianLapHD;
    }

    public void setThoiGianLapHD(String thoiGianLapHD) {
        this.thoiGianLapHD = thoiGianLapHD;
    }

    public String getThoiGianThanhToanHD() {
        return thoiGianThanhToanHD;
    }

    public void setThoiGianThanhToanHD(String thoiGianThanhToanHD) {
        this.thoiGianThanhToanHD = thoiGianThanhToanHD;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int isThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(int thanhToan) {
        this.thanhToan = thanhToan;
    }
}
