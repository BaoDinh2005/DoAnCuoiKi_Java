package model;

import java.util.Date;
import java.util.List;

public class DonHang {

    private int maDH;
    private KhachHang khachHang;
    private Date ngayNhan;
    private Date ngayTra;
    private List<ChiTietDonHang> chiTietDonHang;
    private String trangThai;
    private double tongTien;

    public DonHang() {
    }

    public DonHang(int maDH, KhachHang khachHang, Date ngayNhan, Date ngayTra,
            List<ChiTietDonHang> chiTietDonHang, String trangThai) {
        this.maDH = maDH;
        this.khachHang = khachHang;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.chiTietDonHang = chiTietDonHang;
        this.trangThai = trangThai;
        this.tongTien = tinhTongTien();
    }

    // Constructor mới với tham số tongTien
    public DonHang(int maDH, KhachHang khachHang, Date ngayNhan, Date ngayTra,
            List<ChiTietDonHang> chiTietDonHang, String trangThai, double tongTien) {
        this(maDH, khachHang, ngayNhan, ngayTra, chiTietDonHang, trangThai); // Gọi constructor khác
        this.tongTien = tongTien;
    }

    private double tinhTongTien() {
        double tong = 0;
        if (chiTietDonHang != null) {
            for (ChiTietDonHang ct : chiTietDonHang) {
                tong += ct.getThanhTien();
            }
        }
        return tong;
    }

    // Getter và Setter
    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
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

    public List<ChiTietDonHang> getChiTietDonHang() {
        return chiTietDonHang;
    }

    public void setChiTietDonHang(List<ChiTietDonHang> chiTietDonHang) {
        this.chiTietDonHang = chiTietDonHang;
        this.tongTien = tinhTongTien(); // Tính lại tổng tiền khi cập nhật chi tiết
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
