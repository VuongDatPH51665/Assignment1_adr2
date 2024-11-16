package com.vn.assignment_adr2.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vn.assignment_adr2.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class DangNhap extends AppCompatActivity {

    public static final List<NgDung> lsTaiKhoan = new ArrayList<>();
    public static final String FILE_NAME = "userListData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        docTaiKhoan();

        EditText edtUN = findViewById(R.id.edt_un_login),
                edtPW = findViewById(R.id.edt_pw_login);

        findViewById(R.id.btn_in_login).setOnClickListener(v -> {
            boolean check = false;
            if(lsTaiKhoan.isEmpty()){
                Toast.makeText(this,"Vui lòng đăng ký", Toast.LENGTH_SHORT).show();
            } else if(edtUN.getText().toString().trim().equals(""))
                Toast.makeText(getApplicationContext(),"Vui lòng nhậm Tên tài khoản",Toast.LENGTH_SHORT).show();
            else if(edtPW.getText().toString().trim().equals(""))
                Toast.makeText(getApplicationContext(),"Vui lòng nhậm Tên Mật khẩu",Toast.LENGTH_SHORT).show();
            else {
                for(NgDung u : lsTaiKhoan){
                    check = edtUN.getText().toString().trim().equals(u.taiKhoan)
                            && edtPW.getText().toString().trim().equals(u.matKhau);
                    if(check)
                        break;
                }
            }

            if(check){
                Toast.makeText(this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, TrangChu.class));
                finish();
            } else Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_out_login).setOnClickListener(v -> {
            Toast.makeText(this,"Thoát", Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btn_sig_login).setOnClickListener(v -> {
            Toast.makeText(this,"Đăng ký", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DangKy.class));
        });
    }

    public void docTaiKhoan(){
        List<NgDung> duLieu = new ArrayList<>();
        try {
            FileInputStream fis = this.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            duLieu = (List<NgDung>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(this, "Đã có lỗi xẩy ra", Toast.LENGTH_SHORT).show();
        }
        lsTaiKhoan.clear();
        lsTaiKhoan.addAll(duLieu);
    }
}