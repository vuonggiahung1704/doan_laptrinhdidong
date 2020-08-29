package entities;

import java.io.Serializable;

public class LoaiSanPham implements Serializable {
    private int maLoai;
    private String tenLoai;

    public LoaiSanPham(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public LoaiSanPham() {
    }

    public LoaiSanPham(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
