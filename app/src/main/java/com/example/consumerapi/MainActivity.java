package com.example.consumerapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consumerapi.Api.SinhVienAPI;
import com.example.consumerapi.Models.SinhVien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edt_timkiem, edt_ten, edt_diachi, edt_lop, edt_ngaysinh;
    ListView lv_sinhvien;
    ArrayAdapter<SinhVien> sinhVienArrayAdapter;
    List<SinhVien> sinhVienList;
    SinhVien sinhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        loadData();
        onClick();
    }

    private void anhXa() {
        edt_timkiem = findViewById(R.id.edt_timkiem);
        edt_ten = findViewById(R.id.edt_hoten);
        edt_diachi = findViewById(R.id.edt_diachi);
        edt_lop = findViewById(R.id.edt_lop);
        edt_ngaysinh = findViewById(R.id.edt_ngaysinh);
        lv_sinhvien = findViewById(R.id.lv_sinhvien);
    }

    private void onClick() {
        findViewById(R.id.btn_timkiem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        findViewById(R.id.btn_them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edt_ten.getText().toString();
                String diachi = edt_diachi.getText().toString();
                String lop = edt_lop.getText().toString();
                String ngaysinh = edt_ngaysinh.getText().toString();
                if (hoten.equals("") || diachi.equals("") || lop.equals("") || ngaysinh.equals("")) {
                    Toast.makeText(getApplicationContext(), "Các trường không được đẻ trống", Toast.LENGTH_LONG).show();
                } else {
                    SinhVien sinhVien = new SinhVien(hoten, diachi, lop, ngaysinh);
                    SinhVienAPI.api.addSinhVien(sinhVien).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.body() != null) {
                                if (response.body() > 0) {
                                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
                                    loadData();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.e("TAG", t.getMessage());
                        }
                    });
                }
            }
        });

        //sự kiện click item listivew
        lv_sinhvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sinhVien = sinhVienList.get(i);
                edt_ten.setText(sinhVien.getHoTen());
                edt_ngaysinh.setText(sinhVien.getNgaySinh());
                edt_diachi.setText(sinhVien.getDiaChi());
                edt_lop.setText(sinhVien.getLop());

            }
        });
        findViewById(R.id.btn_capnhat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sinhVien != null) {
                    String hoten = edt_ten.getText().toString();
                    String diachi = edt_diachi.getText().toString();
                    String lop = edt_lop.getText().toString();
                    String ngaysinh = edt_ngaysinh.getText().toString();
                    if (hoten.equals("") || diachi.equals("") || lop.equals("") || ngaysinh.equals("")) {
                        Toast.makeText(getApplicationContext(), "Các trường không được đẻ trống", Toast.LENGTH_LONG).show();
                    } else {
                        sinhVien.setHoTen(hoten);
                        sinhVien.setDiaChi(diachi);
                        sinhVien.setLop(lop);
                        sinhVien.setNgaySinh(ngaysinh);
                        SinhVienAPI.api.updateSinhVien(sinhVien.getID(), sinhVien).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body() != null) {
                                    if (response.body() > 0) {
                                        Toast.makeText(getApplicationContext(), "cập nhật thành công", Toast.LENGTH_LONG).show();
                                        loadData();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.e("TAG", t.getMessage());
                            }
                        });
                    }
                }
            }
        });
        findViewById(R.id.btn_xoa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sinhVien!=null){
                    SinhVienAPI.api.deleteSinhVien(sinhVien.getID()).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.body() != null) {
                                if (response.body() > 0) {
                                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                                    loadData();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }


    private void loadData() {
        sinhVienList = new ArrayList<>();
        SinhVienAPI.api.getDSSinhVienQuery(edt_timkiem.getText().toString()).enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                if (response.body() != null) {
                    sinhVienList = response.body();
                    sinhVienArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, sinhVienList);
                    lv_sinhvien.setAdapter(sinhVienArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {

            }
        });
    }
}