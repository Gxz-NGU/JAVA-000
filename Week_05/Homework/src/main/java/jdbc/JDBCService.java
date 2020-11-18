package jdbc;

import java.sql.*;

public class JDBCService {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "admin";

        Connection connection = DriverManager.getConnection(url,username,password);
        System.out.println("Database connection is established");
        return connection;
    }

    public void closeConnection(Connection connection,Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }

    public void add() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute("insert person values(1,26,'gxz') ");
        closeConnection(connection,statement);
    }

    public void delete() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute("delete from person where id = 1");
        closeConnection(connection,statement);
    }

    public void update() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute("update person set name ='xiaofeiyang' where id = 1");
        closeConnection(connection,statement);
    }

    public void query() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql  = "select * from person where id = 1";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()) {
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String name = rs.getString("name");
            System.out.println("这兄弟"+age+ "岁，名字是"+name);
        }
        closeConnection(connection,statement);
    }

    public static void main(String[] args) {
        JDBCService jdbcService = new JDBCService();
        try {
            jdbcService.add();
            jdbcService.query();
            jdbcService.update();
            jdbcService.query();
            jdbcService.delete();
            jdbcService.query();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
