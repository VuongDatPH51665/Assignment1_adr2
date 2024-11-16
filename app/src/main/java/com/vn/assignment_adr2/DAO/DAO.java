package com.vn.assignment_adr2.DAO;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.vn.assignment_adr2.Database.DbHelper;
import com.vn.assignment_adr2.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    final Context context;
    final DbHelper dbHelper;

    public DAO(Context context, DbHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public List<SanPham> layDSSP(){
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try{
            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery("Select * From SanPham",null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    SanPham sp = new SanPham(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3));
                    list.add(sp);
                    cursor.moveToNext();
                }
                database.setTransactionSuccessful();
            } else Toast.makeText(context, "Dữ liệu trống", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Get List SanPham: "+e);
        } finally {
            database.endTransaction();
        }
        return list;
    }

    public boolean addSP(@NonNull SanPham sp){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("masp", sp.ma);
        values.put("tensp", sp.ten);
        values.put("giaban", sp.gia);
        values.put("soluong", sp.sl);
        int row = (int) database.insert("SanPham", null, values);
        return row != -1;
    }

    public boolean removeSP(int masp){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int row = database.delete( "SanPham", "masp = ?", new String[]{String.valueOf(masp)});
        return row != -1;
    }

    public boolean updateSP(SanPham sp){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masp", sp.ma);
        values.put("tensp", sp.ten);
        values.put("giaban", sp.gia);
        values.put("soluong", sp.sl);
        int row = database.update( "SanPham", values, "masp = ?", new String[]{String.valueOf(sp.ma)});
        return row != -1;
    }
}
