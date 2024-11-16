package com.vn.assignment_adr2.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.assignment_adr2.DAO.DAO;
import com.vn.assignment_adr2.R;
import com.vn.assignment_adr2.model.SanPham;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public final Context context;
    public final List<SanPham> list;
    public final DAO dao;

    public ListAdapter(Context context, List<SanPham> list, DAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.danh_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtItem.setText(list.get(position).toString());

        holder.btnSua.setOnClickListener(v -> {
            SanPham sanPham = list.get(holder.getAdapterPosition());
            dialogUpdate(sanPham);
        });

        holder.btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo!");
            builder.setMessage("Bạn có chắc muốn xoá sản phẩm này không?");

            builder.setPositiveButton("Có", (dialog, which) -> {
                int ma = list.get(holder.getAdapterPosition()).ma;
                boolean check = dao.removeSP(ma);
                if (check) {
                    Toast.makeText(context, "Đã xoá thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.layDSSP());
                    notifyItemRemoved(holder.getAdapterPosition());
                } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Không", (dialog, which) -> {});

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void dialogUpdate(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_them_va_sua, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTen = view.findViewById(R.id.edt_ten_sp_add_update),
                edtGia = view.findViewById(R.id.edt_gia_sp_add_update),
                edtSL = view.findViewById(R.id.edt_sl_sp_add_update);

        edtTen.setText(sanPham.ten);
        edtGia.setText(String.valueOf(sanPham.gia));
        edtSL.setText(String.valueOf(sanPham.sl));

        view.findViewById(R.id.btn_sv_add_update).setOnClickListener(v -> {
            try {
                sanPham.setTen(edtTen.getText().toString());
                sanPham.setGia(Integer.parseInt(edtGia.getText().toString()));
                sanPham.setSl(Integer.parseInt(edtSL.getText().toString()));

                boolean check = dao.updateSP(sanPham);
                if (check) {
                    Toast.makeText(context, "Đã thay đổi thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.layDSSP());
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.btn_out_add_update).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtItem;
        public Button btnSua, btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txt_item);
            btnSua = itemView.findViewById(R.id.btn_sua);
            btnXoa = itemView.findViewById(R.id.btn_xoa);
        }
    }
}
