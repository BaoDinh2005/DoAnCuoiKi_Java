package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Thay đổi URL, USER, PASSWORD theo môi trường
    private static final String URL
            = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyTiemGiatUi;encrypt=true;trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Lỗi kết nối DB: " + e);
        }
        return conn;
    }

    // Test kết nối
    public static void main(String[] args) {
        Connection c = DBConnection.getConnection();
        if (c != null) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
