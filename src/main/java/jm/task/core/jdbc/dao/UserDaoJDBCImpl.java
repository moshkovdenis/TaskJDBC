package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class UserDaoJDBCImpl implements UserDao {
    private static final String SAVE_USER = "INSERT INTO `jm`.`user` (`name`, `lastName`, `age`) VALUES (?, ?, ?)";
    private static final String CREATE_TABLE = "create table if not exists jm.user" +
            "(\n" +
            "\tid int auto_increment primary key,\n" +
            "\tname varchar(35) not null,\n" +
            "\tlastName varchar(45) not null,\n" +
            "\tage int not null,\n" +
            "\tconstraint id_UNIQUE\n" +
            "\t\tunique (id)\n" +
            ");";
    private static final String DROP_TABLE = "DROP TABLE if exists jm.user;";
    private static final String DELETE_USER = "DELETE FROM jm.user WHERE id = ?";
    private static final String CLEAR = "DELETE from jm.user;";
    private static final String SELECT_ALL = "SELECT * FROM jm.user;";

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
            log.info("Table user created");
        } catch (SQLException e) {
            log.error("Failed to create User table", e);
        }

    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
            log.info("User table dropped.");
        } catch (SQLException e) {
            log.error("Failed to drop User table.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            log.info("User named " + name + " added to DB.");
        } catch (SQLException e) {
            log.error("Failed to add user.");
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            log.info("User with id " + id + " delete.");
        } catch (SQLException e) {
            log.error("Failed to delete user with id " + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new LinkedList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                String name = resultset.getString("name");
                String lastName = resultset.getString("lastName");
                byte age = resultset.getByte("age");
                allUsers.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            log.error("Failed to get all users.");
        }
        log.info("All users received: " + allUsers.toString());
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR)) {
            preparedStatement.executeUpdate();
            log.info("All users have been removed from the table");
        } catch (SQLException e) {
            log.error("Failed to delete all users from table");
        }
    }
}
