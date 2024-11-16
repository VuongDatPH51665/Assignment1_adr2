package com.vn.assignment_adr2.Screen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.assignment_adr2.Adapter.ListAdapter;
import com.vn.assignment_adr2.DAO.DAO;
import com.vn.assignment_adr2.Database.DbHelper;
import com.vn.assignment_adr2.R;
import com.vn.assignment_adr2.model.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuanLyFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rcl_list);

        Context context = QuanLyFragment.this.getContext();
        DbHelper dbHelper = new DbHelper(context);
        DAO dao = new DAO(context,dbHelper);
        List<SanPham> list = new ArrayList<>();
        ListAdapter adapter = new ListAdapter(context, list, dao);

        refresh(recyclerView,context,dao,list,adapter);

        if(context == null) return view;

        view.findViewById(R.id.btn_them).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            View viewThem = layoutInflater.inflate(R.layout.layout_them_va_sua, null);
            builder.setView(viewThem);
            AlertDialog dialog = builder.create();

            EditText edtTen = viewThem.findViewById(R.id.edt_ten_sp_add_update),
                    edtGia = viewThem.findViewById(R.id.edt_gia_sp_add_update),
                    edtSL = viewThem.findViewById(R.id.edt_sl_sp_add_update);

            AtomicInteger iD = new AtomicInteger();
            iD.set(1);
            viewThem.findViewById(R.id.btn_sv_add_update).setOnClickListener(v1 -> {
                String ten = edtTen.getText().toString();
                int gia = 0, sl = 0;

                try {
                    gia = Integer.parseInt(edtGia.getText().toString());
                    sl = Integer.parseInt(edtSL.getText().toString());
                } catch (Exception ignored) {}

                SanPham sanPham = new SanPham(iD.get(),ten,gia,sl);
                iD.getAndIncrement();

                boolean check = dao.addSP(sanPham);
                if (check) {
                    refresh(recyclerView,context,dao,list,adapter);
                    Toast.makeText(context, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            });

            viewThem.findViewById(R.id.btn_out_add_update).setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });
        return view;
    }

    private void refresh(RecyclerView recyclerView, Context context, DAO dao, List<SanPham> list,  ListAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list.clear();
        list.addAll(dao.layDSSP());
        recyclerView.setAdapter(adapter);
    }
}