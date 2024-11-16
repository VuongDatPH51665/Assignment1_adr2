package com.vn.assignment_adr2.Screen;

import java.io.Serializable;

public class NgDung implements Serializable {
    public String taiKhoan, matKhau, hoTen;

    public NgDung(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public NgDung(String taiKhoan, String matKhau, String hoTen) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
    }
}
