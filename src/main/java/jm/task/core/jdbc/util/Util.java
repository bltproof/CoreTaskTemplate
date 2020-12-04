package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Util instance;
    private Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mysqldb?useUnicode=true&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root1234";

    public Connection getConnection() {
        return connection;
    }

    private Util() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connection established");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
        }
    }

    public static Util getInstance() {
        try {
            if (instance == null) {
                instance = new Util();
            } else if (instance.getConnection().isClosed()) {
                instance = new Util();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

}
