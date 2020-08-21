package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao user = new UserDaoJDBCImpl();
        user.createUsersTable();
        user.saveUser("Vasy", "Ivanov", (byte) 24);
        user.saveUser("Denis", "Moshkov", (byte) 26);
        user.saveUser("Roman", "Petrov", (byte) 21);
        user.saveUser("Vlad", "Samsonov", (byte) 56);
        user.saveUser("Dima", "Alekseev", (byte) 34);
        user.getAllUsers();
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
