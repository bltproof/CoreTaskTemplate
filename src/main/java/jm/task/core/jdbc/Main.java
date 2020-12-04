package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Ivanov", (byte) 22);
        userDao.saveUser("Sidor", "Sidorov", (byte) 25);
        userDao.saveUser("Petr", "Petrov", (byte) 21);
        userDao.saveUser("Sergey", "Sergeev", (byte) 29);

        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }

        userDao.cleanUsersTable();

        userDao.dropUsersTable();
    }
}
