//数据库连接工具类。

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_db";
    private static final String USER = "czxtc";
    private static final String PASSWORD = "1!aA1234";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL + "?useSSL=false&allowPublicKeyRetrieval=true", USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败", e);
        }
    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
