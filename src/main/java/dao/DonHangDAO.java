package dao;

import model.DonHang;
import model.KhachHang;
import model.ChiTietDonHang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonHangDAO {

    private ChiTietDonHangDAO chiTietDAO;

    public DonHangDAO() {
        chiTietDAO = new ChiTietDonHangDAO();
    }

    // Lấy tất cả đơn hàng
    public List<DonHang> layTatCaDonHang() {
        List<DonHang> list = new ArrayList<>();
        String sql = "SELECT dh.MaDH, dh.MaKH, dh.NgayNhan, dh.NgayTra, dh.TrangThai, dh.TongTien, "
                + "kh.HoTen, kh.SoDienThoai, kh.DiaChi "
                + "FROM DonHang dh INNER JOIN KhachHang kh ON dh.MaKH = kh.MaKH ORDER BY dh.MaDH";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setMaDH(rs.getInt("MaDH"));

                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setHoTen(rs.getString("HoTen"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setDiaChi(rs.getString("DiaChi"));
                dh.setKhachHang(kh);

                dh.setNgayNhan(rs.getTimestamp("NgayNhan"));
                dh.setNgayTra(rs.getTimestamp("NgayTra"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setTongTien(rs.getDouble("TongTien"));

                // Load chi tiết đơn hàng
                List<ChiTietDonHang> chiTiet = chiTietDAO.layChiTietTheoDonHang(dh.getMaDH());
                dh.setChiTietDonHang(chiTiet);

                list.add(dh);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi load dữ liệu đơn hàng!");
            e.printStackTrace();
        }
        return list;
    }

    // Thêm đơn hàng
    public boolean themDonHang(DonHang dh) {
        String sqlKH = "INSERT INTO KhachHang(HoTen, SoDienThoai, DiaChi) VALUES (?, ?, ?)";
        String sqlDH = "INSERT INTO DonHang(MaKH, NgayNhan, NgayTra, TrangThai, TongTien) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Thêm khách hàng
            PreparedStatement psKH = conn.prepareStatement(sqlKH, Statement.RETURN_GENERATED_KEYS);
            psKH.setString(1, dh.getKhachHang().getHoTen());
            psKH.setString(2, dh.getKhachHang().getSoDienThoai());
            psKH.setString(3, dh.getKhachHang().getDiaChi());
            psKH.executeUpdate();

            ResultSet rs = psKH.getGeneratedKeys();
            int maKH = 0;
            if (rs.next()) {
                maKH = rs.getInt(1);
            }

            // Thêm đơn hàng
            PreparedStatement psDH = conn.prepareStatement(sqlDH);
            psDH.setInt(1, maKH);
            psDH.setDate(2, new java.sql.Date(dh.getNgayNhan().getTime()));
            if (dh.getNgayTra() != null) {
                psDH.setDate(3, new java.sql.Date(dh.getNgayTra().getTime()));
            } else {
                psDH.setNull(3, Types.DATE);
            }
            psDH.setString(4, dh.getTrangThai());
            psDH.setDouble(5, dh.getTongTien());
            psDH.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi thêm đơn hàng!");
            e.printStackTrace();
            return false;
        }
    }

    // Sửa đơn hàng
    public boolean suaDonHang(DonHang dh) {
        String sql = "UPDATE DonHang SET NgayTra=?, TrangThai=? WHERE MaDH=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (dh.getNgayTra() != null) {
                ps.setTimestamp(1, new Timestamp(dh.getNgayTra().getTime()));
            } else {
                ps.setNull(1, Types.TIMESTAMP);
            }
            ps.setString(2, dh.getTrangThai());
            ps.setInt(3, dh.getMaDH());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi sửa đơn hàng!");
            e.printStackTrace();
            return false;
        }
    }

    // Xóa đơn hàng
    public boolean xoaDonHang(int maDH) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Xóa chi tiết
            String sqlCT = "DELETE FROM ChiTietDonHang WHERE MaDH=?";
            PreparedStatement psCT = conn.prepareStatement(sqlCT);
            psCT.setInt(1, maDH);
            psCT.executeUpdate();

            // Xóa đơn hàng
            String sqlDH = "DELETE FROM DonHang WHERE MaDH=?";
            PreparedStatement psDH = conn.prepareStatement(sqlDH);
            psDH.setInt(1, maDH);
            psDH.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi xóa đơn hàng!");
            e.printStackTrace();
            return false;
        }
    }

    // Tìm đơn hàng theo mã
    public DonHang timDonHangTheoMa(int maDH) {
        List<DonHang> list = layTatCaDonHang();
        for (DonHang dh : list) {
            if (dh.getMaDH() == maDH) {
                return dh;
            }
        }
        return null;
    }
}
