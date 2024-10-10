package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGSERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastname VARCHAR(255) NOT NULL,"
                + "age SMALLINT NOT NULL"
                + ")";
        try(Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();
            System.out.println("Таблица users успешно создана или уже существует. ");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try(Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();
            System.out.println("Таблица users успешно удалена или не существовала. ");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
            System.out.println("Пользователь с id " + id + " успешно удален.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        try(Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            list = session.createQuery("from User").getResultList();

            session.getTransaction().commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            session.createQuery("from User").list().forEach(session::delete);

            session.getTransaction().commit();
            System.out.println("Таблица users успешно очищена. ");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении всех пользователей: " + e.getMessage());
        }
    }
}
