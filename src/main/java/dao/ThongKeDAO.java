package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class ThongKeDAO {

    // Thống kê tổng quan
    public Map<String, Object> thongKeTongQuan() {
        Map<String, Object> result = new HashMap<>();
        String sql = "SELECT "
                + "COUNT(*) AS tongDonHang, "
                + "SUM(CASE WHEN TrangThai = N'Mới' THEN 1 ELSE 0 END) AS donMoi, "
                + "SUM(CASE WHEN TrangThai = N'Đang xử lý' THEN 1 ELSE 0 END) AS donDangXuLy, "
                + "SUM(CASE WHEN TrangThai = N'Hoàn thành' THEN 1 ELSE 0 END) AS donHoanThanh, "
                + "SUM(CASE WHEN TrangThai = N'Đã hủy' THEN 1 ELSE 0 END) AS donDaHuy, "
                + "SUM(CASE WHEN TrangThai = N'Hoàn thành' THEN TongTien ELSE 0 END) AS tongDoanhThu "
                + "FROM DonHang";

        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                int tongDonHang = rs.getInt("tongDonHang");
                int donMoi = rs.getInt("donMoi");
                int donDangXuLy = rs.getInt("donDangXuLy");
                int donHoanThanh = rs.getInt("donHoanThanh");
                int donDaHuy = rs.getInt("donDaHuy");
                double tongDoanhThu = rs.getDouble("tongDoanhThu");

                result.put("tongDoanhThu", tongDoanhThu);
                result.put("tongDonHang", tongDonHang);
                result.put("donMoi", donMoi);
                result.put("donDangXuLy", donDangXuLy);
                result.put("donHoanThanh", donHoanThanh);
                result.put("donDaHuy", donDaHuy);
                result.put("tyLeHoanThanh", tongDonHang > 0 ? (donHoanThanh * 100.0 / tongDonHang) : 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê doanh thu theo ngày
    public double thongKeDoanhThuTheoNgay(java.sql.Date ngay) {
        String sql = "SELECT SUM(TongTien) AS doanhThu "
                + "FROM DonHang WHERE TrangThai=N'Hoàn thành' AND CAST(NgayNhan AS DATE)=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, ngay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhThu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê doanh thu theo tháng
    public double thongKeDoanhThuTheoThang(int thang, int nam) {
        String sql = "SELECT SUM(TongTien) AS doanhThu "
                + "FROM DonHang WHERE TrangThai=N'Hoàn thành' "
                + "AND MONTH(NgayNhan)=? AND YEAR(NgayNhan)=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhThu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê doanh thu theo năm
    public double thongKeDoanhThuTheoNam(int nam) {
        String sql = "SELECT SUM(TongTien) AS doanhThu "
                + "FROM DonHang WHERE TrangThai=N'Hoàn thành' AND YEAR(NgayNhan)=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhThu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
