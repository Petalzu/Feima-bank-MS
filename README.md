编译 javac -cp ".:lib/*" ./Main.java
运行 java -cp ".:lib/*" Main

mysql部分
账户和密码在DBUtil.java中设置，默认使用root账户和123456密码
需手动在数据库中创建数据库和表
CREATE DATABASE bank_db;
USE bank_db;

CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    idNumber VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255),
    gender VARCHAR(10),
    birthDate DATE,
    balance DOUBLE DEFAULT 0
);



