package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQuerie.CREATE_TABLE)) {
            preparedStatement.executeUpdate();
            log.info("Table user created");
        } catch (SQLException e) {
            log.error("Failed to create User table", e);
        }

    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQuerie.DROP_TABLE)) {
            preparedStatement.executeUpdate();
            log.info("User table dropped.");
        } catch (SQLException e) {
            log.error("Failed to drop User table.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQuerie.SAVE_USER)) {
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
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQuerie.DELETE_USER)) {
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
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQuerie.SELECT_ALL)) {
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
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UserQuerie.CLEAR)) {
            preparedStatement.executeUpdate();
            log.info("All users have been removed from the table");
        } catch (SQLException e) {
            log.error("Failed to delete all users from table");
        }
    }
}
