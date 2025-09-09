package model;

import java.util.Date;

public class DichVu {

    private int maDV;
    private String tenDV;
    private double giaTien;
    private String donViTinh;
    private String hoTen;   
    private Date ngayNhan;
    private Date ngayTra;

    public DichVu() {
    }

    public DichVu(int maDV, String tenDV, double giaTien, String donViTinh,
                  String hoTen, Date ngayNhan, Date ngayTra) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.giaTien = giaTien;
        this.donViTinh = donViTinh;
        this.hoTen = hoTen;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
    }

    // Getter và Setter
    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }
}
