package com.example.consumerapi.Models;

public class SinhVien {
    private String StudentID;
    private String ClassID;
    private String FullName;
    private boolean Gender;

    public SinhVien(String studentID, String classID, String fullName, boolean gender) {
        StudentID = studentID;
        ClassID = classID;
        FullName = fullName;
        Gender = gender;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getClassID() {
        return ClassID;
    }

    public void setClassID(String classID) {
        ClassID = classID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        if (Gender) {
            return "Mã sinh viên" + StudentID + "\nHọ tên: " + FullName + "\nMã lớp: " + ClassID + "\nGiới tính:" + "Nam";
        } else {
            return "Mã sinh viên" + StudentID + "\nHọ tên: " + FullName + "\nMã lớp: " + ClassID + "\nGiới tính:" + "Nữ";
        }

    }
}
