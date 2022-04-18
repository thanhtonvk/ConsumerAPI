package com.example.consumerapi.Api;

import com.example.consumerapi.Models.SinhVien;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SinhVienAPI {
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    SinhVienAPI api = new Retrofit.Builder()
            .baseUrl("https://finaltest-nv7.conveyor.cloud/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SinhVienAPI.class);

    @GET("api/Students/GetStudents")
    Call<List<SinhVien>> getDSSinhVienPath();

    @GET("api/Students/GetStudent/{id}")
    Call<SinhVien> getSVByID(@Path("id") String id);

    @POST("api/Students/PostStudent")
    Call<Void> addSinhVien(@Body SinhVien student);

    @PUT("api/Students/PutStudent")
    Call<Void> updateSinhVien(@Query("id") String id, @Body SinhVien sinhVien);

    @DELETE("api/Students/DeleteStudent")
    Call<Void> DeleteStudent(@Query("id") String id);

}
