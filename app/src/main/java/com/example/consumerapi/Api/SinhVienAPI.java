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
            .baseUrl("https://thapi.conveyor.cloud/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SinhVienAPI.class);
    @GET("api/SinhViens?tukhoa={tukhoa}")
    Call<List<SinhVien>>getDSSinhVienPath(@Path("tukhoa") String tukhoa);
    @GET("api/SinhViens")
    Call<List<SinhVien>>getDSSinhVienQuery(@Query("tukhoa") String tukhoa);
    @POST("api/SinhViens")
    Call<Integer>addSinhVien(@Body SinhVien sinhVien);
    @PUT("api/SinhViens")
    Call<Integer>updateSinhVien(@Query("id") int id,@Body  SinhVien sinhVien);
    @DELETE("api/SinhViens")
    Call<Integer>deleteSinhVien(@Query("id") int id);
}
