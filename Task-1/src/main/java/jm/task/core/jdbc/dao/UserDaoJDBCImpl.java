package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGSERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastname VARCHAR(255) NOT NULL,"
                + "age SMALLINT NOT NULL"
                + ")";
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement())
        {
            statement.execute(sql);
            System.out.println("Таблица users успешно создана или уже существует. ");
        } catch (SQLException e){
                System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement())
        {
            statement.execute(sql);
            System.out.println("Таблица users успешно удалена или не существовала. ");
        } catch (SQLException e){
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?,?,?)";
        try(Connection connection = Util.getConnection();
            PreparedStatement prStatement = connection.prepareStatement(sql))
        {
            prStatement.setString(1, name);
            prStatement.setString(2, lastName);
            prStatement.setByte(3, age);
            prStatement.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных.");
        } catch (SQLException e){
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try(Connection connection = Util.getConnection();
            PreparedStatement prStatement = connection.prepareStatement(sql))
        {
            prStatement.setLong(1, id);
            prStatement.executeUpdate();
            System.out.println("Пользователь с id " + id + " успешно удален.");
        } catch (SQLException e){
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<User>();
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql))
        {
            while (rs.next()){
                User user = new User(
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getByte("age")
                );
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e){
            System.out.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()){
            int rows = statement.executeUpdate(sql);
            System.out.println("Таблица users успешно очищена. Удалено строк: " + rows);
        } catch (SQLException e){
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }

    }
}
