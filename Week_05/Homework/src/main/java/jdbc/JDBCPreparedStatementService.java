package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCPreparedStatementService {
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
        JDBCPreparedStatementService jdbcPreparedStatementServic = new JDBCPreparedStatementService();
        try(Connection connection =  jdbcPreparedStatementServic.getConnection();) {
            String sql = "insert person values(null,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,28);
            preparedStatement.setString(2,"Yy");
            preparedStatement.execute();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

