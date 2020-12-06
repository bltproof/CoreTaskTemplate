package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

//        userService.saveUser("Ivan", "Ivanov", (byte) 22);
//        userService.saveUser("Sidor", "Sidorov", (byte) 25);
//        userService.saveUser("Petr", "Petrov", (byte) 21);
//        userService.saveUser("Sergey", "Sergeev", (byte) 29);

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
//
//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();
//        Util.shutdown();
    }
}