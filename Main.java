//主程序，整合所有功能。

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountService accountService = new AccountService();
        ReportGenerator reportGenerator = new ReportGenerator();

        while (true) {
            System.out.println("请选择操作：1-创建账户，2-查看账户，3-存钱，4-取钱，5-转账，6-修改密码，7-导出Excel，8-导入Excel，9-生成年终报告，10-退出");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // 创建账户逻辑
                    System.out.print("请输入用户ID：");
                    String userId = scanner.next();
                    System.out.print("请输入用户名：");
                    String username = scanner.next();
                    System.out.print("请输入密码：");
                    String password = scanner.next();
                    System.out.print("请输入身份证号：");
                    String idNumber = scanner.next();
                    System.out.print("请输入电话号码：");
                    String phoneNumber = scanner.next();
                    System.out.print("请输入性别：");
                    String gender = scanner.next();
                    System.out.print("请输入出生日期：");
                    String birthDate = scanner.next();
                    System.out.print("请输入初始余额：");
                    double balance = scanner.nextDouble();
                    Account newAccount = new Account();
                    newAccount.setUserId(userId);
                    newAccount.setUsername(username);
                    newAccount.setPassword(password);
                    newAccount.setIdNumber(idNumber);
                    newAccount.setPhoneNumber(phoneNumber);
                    newAccount.setGender(gender);
                    newAccount.setBirthDate(birthDate);
                    newAccount.setBalance(balance);
                    accountService.createAccount(newAccount);
                    break;
                case 2:
                    // 查看账户逻辑
                    System.out.print("请输入账户ID：");
                    int id = scanner.nextInt();
                    Account account = accountService.getAccount(id);
                    if (account != null) {
                        System.out.println("账户ID: " + account.getId());
                        System.out.println("用户ID: " + account.getUserId());
                        System.out.println("用户名: " + account.getUsername());
                        System.out.println("身份证号: " + account.getIdNumber());
                        System.out.println("电话号码: " + account.getPhoneNumber());
                        System.out.println("性别: " + account.getGender());
                        System.out.println("出生日期: " + account.getBirthDate());
                        System.out.println("余额: " + account.getBalance());
                    } else {
                        System.out.println("账户不存在");
                    }
                    break;
                    case 3:
                    // 存钱逻辑
                    System.out.print("请输入账户ID：");
                    String depositId = scanner.next();
                    System.out.print("请输入密码：");
                    String depositPassword = scanner.next();
                    if (accountService.validatePassword(Integer.parseInt(depositId), depositPassword)) {
                        System.out.print("请输入存款金额：");
                        double depositAmount = scanner.nextDouble();
                        accountService.deposit(Integer.parseInt(depositId), depositAmount);
                        System.out.println("存款成功");
                    } else {
                        System.out.println("密码错误，存款失败");
                    }
                    break;

                case 4:
                    // 取钱逻辑
                    System.out.print("请输入账户ID：");
                    String withdrawId = scanner.next();
                    System.out.print("请输入密码：");
                    String withdrawPassword = scanner.next();
                    if (accountService.validatePassword(Integer.parseInt(withdrawId), withdrawPassword)) {
                        System.out.print("请输入取款金额：");
                        double withdrawAmount = scanner.nextDouble();
                        accountService.withdraw(Integer.parseInt(withdrawId), withdrawAmount);
                        System.out.println("取款成功");
                    } else {
                        System.out.println("密码错误，取款失败");
                    }
                    break;

                case 5:
                    // 转账逻辑
                    System.out.print("请输入转出账户ID：");
                    String fromId = scanner.next();
                    System.out.print("请输入密码：");
                    String fromPassword = scanner.next();
                    if (accountService.validatePassword(Integer.parseInt(fromId), fromPassword)) {
                        System.out.print("请输入转入账户ID：");
                        String toId = scanner.next();
                        System.out.print("请输入转账金额：");
                        double transferAmount = scanner.nextDouble();
                        accountService.transfer(Integer.parseInt(fromId), Integer.parseInt(toId), transferAmount);
                        System.out.println("转账成功");
                    } else {
                        System.out.println("密码错误，转账失败");
                    }
                    break;
                case 6:
                    // 修改账户信息逻辑
                    System.out.print("请输入账户ID：");
                    String changeId = scanner.next();

                    Account act = accountService.getAccount(Integer.parseInt(changeId));
                    if (act == null) {
                        System.out.println("账户不存在");
                        break;
                    }

                    System.out.print("请输入新的用户名（当前：" + act.getUsername() + "）：");
                    act.setUsername(scanner.next());

                    System.out.print("请输入新的密码：");
                    act.setPassword(scanner.next());

                    System.out.print("请输入新的身份证号（当前：" + act.getIdNumber() + "）：");
                    act.setIdNumber(scanner.next());

                    System.out.print("请输入新的电话号码（当前：" + act.getPhoneNumber() + "）：");
                    act.setPhoneNumber(scanner.next());

                    System.out.print("请输入新的性别（当前：" + act.getGender() + "）：");
                    act.setGender(scanner.next());

                    System.out.print("请输入新的出生日期（当前：" + act.getBirthDate() + "）：");
                    act.setBirthDate(scanner.next());

                    System.out.print("请输入新的余额（当前：" + act.getBalance() + "）：");
                    act.setBalance(scanner.nextDouble());

                    accountService.updateAccount(act);
                    System.out.println("账户信息已更新");
                    break;
                case 7:
                    // 导出Excel逻辑
                    List<Account> accountsExport = accountService.getAllAccounts(); // 修改变量名以避免重复
                    try {
                        ExcelUtil.exportToExcel(accountsExport, "accounts.xlsx");
                        System.out.println("账户信息已导出到accounts.xlsx");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    try {
                        List<Account> accounts = ExcelUtil.importFromExcel("accounts.xlsx");
                        for (Account acc : accounts) {
                            accountService.createAccount(acc);
                        }
                        System.out.println("账户信息已从accounts.xlsx导入");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidFormatException e) {
                        System.out.println("导入失败：文件格式不正确。");
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    // 生成年终报告
                    List<Account> accountsReport = accountService.getAllAccounts(); // 修改变量名以避免重复
                    double totalBalance = accountService.getTotalBalance();
                    reportGenerator.generateAnnualReport(accountsReport, totalBalance);
                    break;
                case 10:
                    // 退出
                    System.out.println("退出系统");
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效选择，请重试。");
                    break;
            }
        }
    }
}
