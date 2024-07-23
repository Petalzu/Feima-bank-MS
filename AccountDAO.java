//数据访问对象类，负责与数据库的交互。

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // 创建账户
    public void createAccount(Account account) {
        String query = "INSERT INTO accounts (userId, username, password, idNumber, phoneNumber, gender, birthDate, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, account.getUserId());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getIdNumber());
            ps.setString(5, account.getPhoneNumber());
            ps.setString(6, account.getGender());
            ps.setString(7, account.getBirthDate());
            ps.setDouble(8, account.getBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取账户信息
    public Account getAccount(int id) {
        Account account = null;
        String query = "SELECT * FROM accounts WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUserId(rs.getString("userId"));
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    account.setIdNumber(rs.getString("idNumber"));
                    account.setPhoneNumber(rs.getString("phoneNumber"));
                    account.setGender(rs.getString("gender"));
                    account.setBirthDate(rs.getString("birthDate"));
                    account.setBalance(rs.getDouble("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    // 存钱
    public void deposit(int id, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 取钱
    public void withdraw(int id, double amount) {
        String query = "UPDATE accounts SET balance = balance - ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 转账
    public void transfer(int fromId, int toId, double amount) {
        Connection conn = null;
        PreparedStatement psWithdraw = null;
        PreparedStatement psDeposit = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String queryWithdraw = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            psWithdraw = conn.prepareStatement(queryWithdraw);
            psWithdraw.setDouble(1, amount);
            psWithdraw.setInt(2, fromId);
            psWithdraw.executeUpdate();

            String queryDeposit = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            psDeposit = conn.prepareStatement(queryDeposit);
            psDeposit.setDouble(1, amount);
            psDeposit.setInt(2, toId);
            psDeposit.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            DBUtil.close(psWithdraw);
            DBUtil.close(psDeposit);
            DBUtil.close(conn);
        }
    }

    // 修改密码
    public void changePassword(int id, String newPassword) {
        String query = "UPDATE accounts SET password = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, newPassword);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取所有账户信息
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT id, userId, username, idNumber, phoneNumber, gender, birthDate, balance FROM accounts";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getString("userId"));
                account.setUsername(rs.getString("username"));
                account.setIdNumber(rs.getString("idNumber"));
                account.setPhoneNumber(rs.getString("phoneNumber"));
                account.setGender(rs.getString("gender"));
                account.setBirthDate(rs.getString("birthDate"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // 获取总金额
    public double getTotalBalance() {
        double totalBalance = 0.0;
        String query = "SELECT SUM(balance) AS total_balance FROM accounts";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalBalance = rs.getDouble("total_balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalBalance;
    }
    //更新账户信息
    public void updateAccount(Account account) {
        String sql = "UPDATE accounts SET username = ?, password = ?, idNumber = ?, phoneNumber = ?, gender = ?, birthDate = ?, balance = ? WHERE userId = ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getIdNumber());
            statement.setString(4, account.getPhoneNumber());
            statement.setString(5, account.getGender());
            statement.setString(6, account.getBirthDate());
            statement.setDouble(7, account.getBalance());
            statement.setString(8, account.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新账户信息失败");
        }
    }
}
