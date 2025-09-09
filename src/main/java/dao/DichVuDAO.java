package dao;

import model.DichVu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class DichVuDAO {

    // Lấy tất cả dịch vụ
    public List<DichVu> layTatCaDichVu() {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT MaDV, TenDV, GiaTien, DonViTinh,HoTen, NgayNhan, NgayTra FROM DichVu";
        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDV(rs.getInt("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setGiaTien(rs.getDouble("GiaTien"));
                dv.setDonViTinh(rs.getString("DonViTinh"));
                dv.setHoTen(rs.getString("HoTen"));
                dv.setNgayNhan(rs.getDate("NgayNhan"));
                dv.setNgayTra(rs.getDate("NgayTra"));
                list.add(dv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi load dữ liệu dịch vụ!");
        }
        return list;
    }

    // Thêm dịch vụ
    public boolean themDichVu(DichVu dv) {
        String sql = "INSERT INTO DichVu(TenDV, GiaTien, DonViTinh, HoTen, NgayNhan, NgayTra) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dv.getTenDV());
            ps.setDouble(2, dv.getGiaTien());
            ps.setString(3, dv.getDonViTinh());
            ps.setString(4, dv.getHoTen());
            ps.setDate(5, new java.sql.Date(dv.getNgayNhan().getTime()));
            ps.setDate(6, new java.sql.Date(dv.getNgayTra().getTime()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
// Sửa dịch vụ

    public boolean suaDichVu(DichVu dv) {
        String sql = "UPDATE DichVu SET TenDV=?, GiaTien=?, DonViTinh=?, HoTen=?, NgayNhan=?, NgayTra=? WHERE MaDV=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dv.getTenDV());
            ps.setDouble(2, dv.getGiaTien());
            ps.setString(3, dv.getDonViTinh());
            ps.setString(4, dv.getHoTen());
            ps.setDate(5, dv.getNgayNhan() != null ? new java.sql.Date(dv.getNgayNhan().getTime()) : null);
            ps.setDate(6, dv.getNgayTra() != null ? new java.sql.Date(dv.getNgayTra().getTime()) : null);
            ps.setInt(7, dv.getMaDV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi sửa dịch vụ!");
            e.printStackTrace();
            return false;
        }
    }

// Xóa dịch vụ
    public boolean xoaDichVu(int maDV) {
        String sql = "DELETE FROM DichVu WHERE MaDV=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDV);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi xóa dịch vụ!");
            e.printStackTrace();
            return false;
        }
    }

}
