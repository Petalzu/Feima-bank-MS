//服务类，包含业务逻辑。

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    // createAccount(Account
    // account)方法用于创建一个新的账户。它接收一个Account对象作为参数，并调用AccountDAO的createAccount方法来将该账户信息保存到数据库中。
    public void createAccount(Account account) {
        accountDAO.createAccount(account);
    }

    // getAccount(int
    // id)方法用于获取指定id的账户信息。它接收一个int类型的id作为参数，并调用AccountDAO的getAccount方法来获取该账户信息。
    public Account getAccount(int id) {
        return accountDAO.getAccount(id);
    }

    // deposit(int id, double
    // amount)方法用于向指定id的账户存款。它接收一个int类型的id和一个double类型的amount作为参数，并调用AccountDAO的deposit方法来更新该账户的余额。
    public void deposit(int id, double amount) {
        accountDAO.deposit(id, amount);
    }

    // withdraw(int id, double
    // amount)方法用于从指定id的账户取款。它接收一个int类型的id和一个double类型的amount作为参数，并调用AccountDAO的withdraw方法来更新该账户的余额。
    public void withdraw(int id, double amount) {
        accountDAO.withdraw(id, amount);
    }

    // transfer(int fromId, int toId, double
    // amount)方法用于从一个账户向另一个账户转账。它接收一个int类型的fromId、一个int类型的toId和一个double类型的amount作为参数，并调用AccountDAO的transfer方法来更新这两个账户的余额。
    public void transfer(int fromId, int toId, double amount) {
        accountDAO.transfer(fromId, toId, amount);
    }

    // changePassword(int id, String
    // newPassword)方法用于修改指定id的账户密码。它接收一个int类型的id和一个String类型的newPassword作为参数，并调用AccountDAO的changePassword方法来更新该账户的密码。
    public void changePassword(int id, String newPassword) {
        accountDAO.changePassword(id, newPassword);
    }

    // getAllAccounts()方法用于获取所有账户信息。它调用AccountDAO的getAllAccounts方法来获取所有账户信息。
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    // deleteAccount(int
    // id)方法用于删除指定id的账户。它接收一个int类型的id作为参数，并调用AccountDAO的deleteAccount方法来删除该账户。
    public double getTotalBalance() {
        return accountDAO.getTotalBalance();
    }

    // updateAccount(Account
    // account)方法用于更新账户信息。它接收一个Account对象作为参数，并调用AccountDAO的updateAccount方法来更新该账户信息。
    public void updateAccount(Account account) {
        accountDAO.updateAccount(account);
    }

    // validatePassword(int userId, String
    // password)方法用于验证用户密码。它接收一个int类型的userId和一个String类型的password作为参数，并调用AccountDAO的getAccount方法来获取该用户的账户信息，然后判断该账户的密码是否与传入的密码一致。
    public boolean validatePassword(int userId, String password) {
        Account account = accountDAO.getAccount(userId);
        return account != null && account.getPassword().equals(password);
    }
}
