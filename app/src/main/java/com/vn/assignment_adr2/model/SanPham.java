package com.vn.assignment_adr2.model;

import androidx.annotation.NonNull;

public class SanPham {
    public int ma;
    public String ten;
    public double gia;
    public int sl;

    public SanPham(int ma, String ten, double gia, int sl) {
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
        this.sl = sl;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    @NonNull
    @Override
    public String toString() {
        return ten + "\n" + "Giá: " + gia + " VND - Số lượng: còn " + sl;
    }
}
