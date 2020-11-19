package jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.concurrent.CountDownLatch;

/**
 * 只是知道如何通过Hikari获取Statement，但是不知道如何测试，才能看到数据库连接池有明显的性能提升。
 */
public class HikariService {

    public static void main(String[] args) throws SQLException, InterruptedException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("root");
        config.setPassword("admin");
        config.setMaximumPoolSize(1000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            try(Connection connection = ds.getConnection();) {
//                PreparedStatement ps = connection.prepareStatement("select * from person");
//                ResultSet rs =  ps.executeQuery();
//                while(rs.next()){
//                    int id = rs.getInt("id");
//                    int age = rs.getInt("age");
//                    String name = rs.getString("name");
//                    System.out.println(id +" "+  age +" "+  name);
//                }
//            }
//        }
//        System.out.println("一共用了"+(System.currentTimeMillis()-start) +"ms"); // not set maximumPollSize 22469ms

        long start1 = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(100000);
        for (int i = 0; i < 100000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try(Connection connection = ds.getConnection();) {
                        PreparedStatement ps = connection.prepareStatement("select * from person");
                        ResultSet rs =  ps.executeQuery();
                        Thread.sleep(1000);
                        while(rs.next()){
                            int id = rs.getInt("id");
                            int age = rs.getInt("age");
                            String name = rs.getString("name");
                            System.out.println(id +" "+  age +" "+  name);
                        }
                    } catch (SQLException | InterruptedException throwables) {
                        throwables.printStackTrace();
                    }finally {
                        latch.countDown();
                    }
                }
            }).start();
        }
        latch.await();
        System.out.println("一共用了"+(System.currentTimeMillis()-start1) +"ms"); // not set maximumPollSize 22469ms



    }
}
