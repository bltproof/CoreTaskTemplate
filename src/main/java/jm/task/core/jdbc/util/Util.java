package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Util instance;
    private static Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mysqldb?useUnicode=true&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root1234";

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                HashMap<String, String> dbSettings = new HashMap<>();
                dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                dbSettings.put(Environment.HBM2DDL_AUTO, "update");
                dbSettings.put(Environment.DRIVER, DRIVER);
                dbSettings.put(Environment.USER, LOGIN);
                dbSettings.put(Environment.PASS, PASSWORD);
                dbSettings.put(Environment.URL, URL);

                registryBuilder.applySettings(dbSettings);

                registry = registryBuilder.build();

                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(User.class);
                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build(); //удаляет предыдущие записи из таблицы??

            } catch (Exception e) {
                e.printStackTrace();

                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }

            }
        }
        return sessionFactory;
    }

    public Connection getJdbcConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connection established");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
        }
        return connection;
    }

    public static Util getInstance() {
        try {
            if (instance == null) {
                instance = new Util();
            } else if (instance.getJdbcConnection().isClosed()) {
                instance = new Util();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

}