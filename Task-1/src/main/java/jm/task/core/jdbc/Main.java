package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        User user1 = new User("Marina", "Ivanova", (byte) 23);
        User user2 = new User("Petr", "Petrov", (byte) 47);
        User user3 = new User("Elena", "Eremina", (byte) 58);
        User user4 = new User("Ivan", "Ivanov", (byte) 18);


        userService.createUsersTable();
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        for (User allUser : userService.getAllUsers()) {
            System.out.println(allUser);
        }

        userService.removeUserById(3);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}

