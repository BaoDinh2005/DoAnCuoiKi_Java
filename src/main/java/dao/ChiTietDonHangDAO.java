package dao;

import model.ChiTietDonHang;
import model.DichVu;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangDAO {

    public List<ChiTietDonHang> layChiTietTheoDonHang(int maDH) {
        List<ChiTietDonHang> dsChiTiet = new ArrayList<>();

        String sql = "SELECT ctdh.MaCT, ctdh.MaDV, ctdh.SoLuong, dv.TenDV, dv.GiaTien, dv.DonViTinh "
                   + "FROM ChiTietDonHang ctdh "
                   + "INNER JOIN DichVu dv ON ctdh.MaDV = dv.MaDV "
                   + "WHERE ctdh.MaDH = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDH);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDV(rs.getInt("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setGiaTien(rs.getDouble("GiaTien"));
                dv.setDonViTinh(rs.getString("DonViTinh"));

                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setMaCT(rs.getInt("MaCT"));
                ct.setMaDH(maDH);
                ct.setDichVu(dv);
                ct.setSoLuong(rs.getInt("SoLuong"));

                dsChiTiet.add(ct);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi DAO: " + e);
        }

        return dsChiTiet;
    }
}
