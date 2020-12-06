package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mysqldb?useUnicode=true&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root1234";


    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.put(Environment.HBM2DDL_AUTO, "create");
        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.USER, LOGIN);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.URL, URL);

        Configuration cfg = new Configuration();
        cfg.setProperties(properties);
        cfg.addAnnotatedClass(User.class);

        return cfg.buildSessionFactory();
    }

    public static Connection getJdbcConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connection established");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
        }
        return connection;
    }
}