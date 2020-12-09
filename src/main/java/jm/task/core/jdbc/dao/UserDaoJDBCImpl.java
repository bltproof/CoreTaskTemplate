package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getJdbcConnection();
    }

    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT, name VARCHAR (100) NOT NULL,\n" +
                "lastName VARCHAR (100) NOT NULL,\n" +
                "age INT NOT NULL,\n" +
                "PRIMARY KEY (id))";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table Users has been created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Users has been dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String query = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)";
        connection.setAutoCommit(false);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            connection.commit();

            System.out.printf("User с именем %s добавлен в базу данных\n", name);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String query = String.format("DELETE FROM users WHERE id = %d", id);
        connection.setAutoCommit(false);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
            System.out.printf("User with id: %d has been removed\n", id);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        String query = "DELETE FROM users";
        connection.setAutoCommit(false);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
            System.out.println("Table Users has been cleaned");

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            connection.setAutoCommit(true);
        }
    }
}