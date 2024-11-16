package com.vn.assignment_adr2.Screen;


import static com.vn.assignment_adr2.Screen.DangNhap.FILE_NAME;
import static com.vn.assignment_adr2.Screen.DangNhap.lsTaiKhoan;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vn.assignment_adr2.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DangKy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        EditText edtUN = findViewById(R.id.edt_un_signup),
                edtPW = findViewById(R.id.edt_pw_signup);

        findViewById(R.id.btn_sig_signup).setOnClickListener(v -> {
            if(edtUN.getText().toString().trim().equals(""))
                Toast.makeText(getApplicationContext(),"Vui lòng nhậm Tên tài khoản",Toast.LENGTH_SHORT).show();
            else if(edtPW.getText().toString().trim().equals(""))
                Toast.makeText(getApplicationContext(),"Vui lòng nhậm Tên Mật khẩu",Toast.LENGTH_SHORT).show();
            else {
                NgDung ngDung = new NgDung(edtUN.getText().toString().trim(), edtPW.getText().toString().trim());
                lsTaiKhoan.add(ngDung);
                ghiTaiKhoan();
                Toast.makeText(this,"Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        findViewById(R.id.btn_out_signup).setOnClickListener(v -> {
            Toast.makeText(this,"Huỷ đăng ký", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }

    public void ghiTaiKhoan(){
        try {
            FileOutputStream fos = this.openFileOutput(FILE_NAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lsTaiKhoan);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Lỗi FileOutputStream", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Lỗi ObjectOutputStream", Toast.LENGTH_SHORT).show();
        }
    }
}