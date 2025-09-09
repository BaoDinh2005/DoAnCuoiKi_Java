package dao;

import model.KhachHang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    // Lấy tất cả khách hàng
    public List<KhachHang> layTatCaKhachHang() {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT MaKH, HoTen, SoDienThoai, DiaChi FROM KhachHang ORDER BY MaKH";

        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int maKH = rs.getInt("MaKH");
                String hoTen = rs.getString("HoTen");
                String sdt = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                ds.add(new KhachHang(maKH, hoTen, sdt, diaChi));
            }

        } catch (SQLException e) {
            System.out.println("Lỗi load danh sách khách hàng!");
            e.printStackTrace();
        }
        return ds;
    }

    // Thêm khách hàng
    public boolean themKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang(HoTen, SoDienThoai, DiaChi) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        kh.setMaKH(keys.getInt(1)); // gán lại id tự tăng cho object
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm khách hàng!");
            e.printStackTrace();
        }
        return false;
    }

    // Sửa khách hàng
    public boolean suaKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET HoTen=?, SoDienThoai=?, DiaChi=? WHERE MaKH=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());
            ps.setInt(4, kh.getMaKH());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi sửa khách hàng!");
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng
    public boolean xoaKhachHang(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi xóa khách hàng!");
            e.printStackTrace();
        }
        return false;
    }

    // Tìm theo mã
    public KhachHang timKhachHangTheoMa(int maKH) {
        String sql = "SELECT MaKH, HoTen, SoDienThoai, DiaChi FROM KhachHang WHERE MaKH=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                            rs.getInt("MaKH"),
                            rs.getString("HoTen"),
                            rs.getString("SoDienThoai"),
                            rs.getString("DiaChi")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm khách hàng theo mã!");
            e.printStackTrace();
        }
        return null;
    }

    // Tìm theo tên
    public List<KhachHang> timKhachHangTheoTen(String ten) {
        List<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT MaKH, HoTen, SoDienThoai, DiaChi FROM KhachHang WHERE HoTen LIKE ? ORDER BY HoTen";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + ten + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new KhachHang(
                            rs.getInt("MaKH"),
                            rs.getString("HoTen"),
                            rs.getString("SoDienThoai"),
                            rs.getString("DiaChi")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm khách hàng theo tên!");
            e.printStackTrace();
        }
        return ds;
    }
}
