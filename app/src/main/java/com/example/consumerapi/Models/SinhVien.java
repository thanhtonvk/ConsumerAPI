package com.example.consumerapi.Models;

public class SinhVien {
    private int ID;
    private String HoTen;
    private String DiaChi;
    private String Lop;
    private String NgaySinh;

    public SinhVien(int ID, String hoTen, String diaChi, String lop, String ngaySinh) {
        this.ID = ID;
        HoTen = hoTen;
        DiaChi = diaChi;
        Lop = lop;
        NgaySinh = ngaySinh;
    }

    public SinhVien(String hoTen, String diaChi, String lop, String ngaySinh) {
        HoTen = hoTen;
        DiaChi = diaChi;
        Lop = lop;
        NgaySinh = ngaySinh;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return "Họ tên: " + HoTen + "\nNgày sinh: " + NgaySinh + "\nĐịa chỉ:" + DiaChi;
    }
}
