package model;

public class ChiTietDonHang {

    private int maCT;
    private int maDH;
    private DichVu dichVu;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietDonHang() {
    }

    public int getMaCT() {
        return maCT;
    }

    public void setMaCT(int maCT) {
        this.maCT = maCT;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) {
        this.dichVu = dichVu;
        if (dichVu != null) {
            this.donGia = dichVu.getGiaTien();
            this.thanhTien = this.donGia * this.soLuong;
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        this.thanhTien = this.donGia * soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }
}
