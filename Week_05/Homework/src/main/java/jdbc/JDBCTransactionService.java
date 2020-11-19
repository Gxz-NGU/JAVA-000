package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// 事务操作
public class JDBCTransactionService {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "admin";

        Connection connection = DriverManager.getConnection(url,username,password);
        System.out.println("Database connection is established");
        return connection;
    }

    public static void main(String[] args) {
        JDBCTransactionService jdbcTransactionService = new JDBCTransactionService();
        try(Connection connection = jdbcTransactionService.getConnection();  Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.execute("insert person values(null,26,'gxz')");
            statement.execute("insert person values(null,26,'xiaofeiyang')");
            statement.execute("update person set name ='daSHEEP' where id = 1");
            System.in.read(); // 暂停之后，刷新数据库的数据，是没有更新的
            connection.commit(); // 输入任意字符之后，看到数据库的数据被刷新
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
