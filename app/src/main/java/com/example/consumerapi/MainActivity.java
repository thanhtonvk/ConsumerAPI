package com.example.consumerapi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.consumerapi.Api.SinhVienAPI;
import com.example.consumerapi.Models.SinhVien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edt_timkiem, edt_ten, edt_lop, edt_masv;
    RadioButton rd_nam, rd_nu;
    ListView lv_sinhvien;
    ArrayAdapter<SinhVien> sinhVienArrayAdapter;
    List<SinhVien> sinhVienList;
    SinhVien sinhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        loadData(edt_timkiem.getText().toString().trim());
        onClick();
    }

    private void anhXa() {
        edt_timkiem = findViewById(R.id.edt_timkiem);
        edt_ten = findViewById(R.id.edt_hoten);
        edt_lop = findViewById(R.id.edt_lop);
        lv_sinhvien = findViewById(R.id.lv_sinhvien);
        rd_nam = findViewById(R.id.rd_nam);
        rd_nam.setChecked(true);
        rd_nu = findViewById(R.id.rd_nu);
        edt_masv = findViewById(R.id.edt_masv);
    }

    private boolean checkExist(String masv) {
        final boolean[] check = {true};
        SinhVienAPI.api.getSVByID(masv).enqueue(new Callback<SinhVien>() {
            @Override
            public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                if (response.body() != null) {
                    check[0] = false;
                }
            }

            @Override
            public void onFailure(Call<SinhVien> call, Throwable t) {

            }
        });
        return check[0];
    }



    private void onClick() {
        findViewById(R.id.btn_timkiem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(edt_timkiem.getText().toString());
            }
        });
        findViewById(R.id.btn_them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edt_ten.getText().toString();
                String lop = edt_lop.getText().toString();
                String masv = edt_masv.getText().toString();
                boolean check = true;
                if (rd_nam.isChecked()) {
                    check = true;
                } else {
                    check = false;
                }
                if (checkExist1(masv)) {
                    if (hoten.equals("") || lop.equals("") || masv.equals("")) {
                        Toast.makeText(getApplicationContext(), "C??c tr?????ng kh??ng ???????c ????? tr???ng", Toast.LENGTH_LONG).show();
                    } else {
                        SinhVien sv = new SinhVien(masv, lop, hoten, check);
                        SinhVienAPI.api.addSinhVien(sv).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.body() != null) {
                                    Toast.makeText(getApplicationContext(), "Th??nh c??ng", Toast.LENGTH_LONG).show();
                                    loadData("");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Check", t.getMessage());
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sinh vi??n ???? t???n t???i", Toast.LENGTH_LONG).show();
                }

            }
        });

        //s??? ki???n click item listivew
        lv_sinhvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sinhVien = sinhVienList.get(i);
                edt_ten.setText(sinhVien.getFullName());
                edt_lop.setText(sinhVien.getClassID());
                edt_masv.setText(sinhVien.getStudentID());
                if (sinhVien.isGender()) {
                    rd_nam.setChecked(true);
                } else {
                    rd_nu.setChecked(true);
                }
            }
        });
        lv_sinhvien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sinhVien = sinhVienList.get(i);
                dialogThongTinSV();
                return true;
            }
        });
        findViewById(R.id.btn_capnhat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sinhVien != null) {
                    String hoten = edt_ten.getText().toString();
                    String lop = edt_lop.getText().toString();
                    String masv = edt_masv.getText().toString();
                    boolean check = true;
                    if (rd_nam.isChecked()) {
                        check = true;
                    } else {
                        check = false;
                    }
                    if (!checkExist1(masv)) {
                        if (hoten.equals("") || lop.equals("")) {
                            Toast.makeText(getApplicationContext(), "C??c tr?????ng kh??ng ???????c ????? tr???ng", Toast.LENGTH_LONG).show();
                        } else {
                            SinhVien sv = new SinhVien(masv, lop, hoten, check);
                            SinhVienAPI.api.updateSinhVien(masv, sv).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Th??nh c??ng", Toast.LENGTH_LONG).show();
                                        loadData("");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Check", t.getMessage());
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sinh vi??n kh??ng t???n t???i", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        findViewById(R.id.btn_xoa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sinhVien != null) {
                    dialogDelete();
                }
            }
        });
    }

    private void dialogDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("X??c nh???n x??a");

        builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SinhVienAPI.api.DeleteStudent(sinhVien.getStudentID()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Th??nh c??ng", Toast.LENGTH_LONG).show();
                            loadData("");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("Check", t.getMessage());
                    }
                });
            }
        });
        builder.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void loadData(String searchString) {
        sinhVienList = new ArrayList<>();
        SinhVienAPI.api.getDSSinhVienPath().enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                if (response.body() != null) {
                    if (searchString.equals("")) {
                        sinhVienList = response.body();
                    } else {
                        for (SinhVien sinhVien : response.body()
                        ) {
                            if (sinhVien.getFullName().toLowerCase().contains(searchString.toLowerCase()) || sinhVien.getClassID().equals(searchString)) {
                                sinhVienList.add(sinhVien);
                            }
                        }
                    }
                    sinhVienArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, sinhVienList);
                    lv_sinhvien.setAdapter(sinhVienArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {

            }
        });
    }

    private void dialogThongTinSV() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_thongtin);
        TextView tv_ten = dialog.findViewById(R.id.tv_hoten);
        TextView tv_lop = dialog.findViewById(R.id.tv_lop);
        TextView tv_masv = dialog.findViewById(R.id.tv_masv);
        TextView tv_gioitinh = dialog.findViewById(R.id.tv_gioitinh);
        checkExist("1");
        if (sinhVien != null) {
            tv_ten.setText(sinhVien.getFullName());
            tv_lop.setText(sinhVien.getClassID());
            tv_masv.setText(sinhVien.getStudentID());
            if (sinhVien.isGender()) {
                tv_gioitinh.setText("Nam");
            } else {
                tv_gioitinh.setText("N???");
            }
        }
        dialog.show();
    }
    private boolean checkExist1(String masv) {
        final boolean[] check = {true};
        for (SinhVien sv : sinhVienList
        ) {
            if (sv.getStudentID().equals(masv)) check[0] = false;
        }
        return check[0];
    }

}