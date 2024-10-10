package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.SQLException;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try{
            Configuration cfg = new Configuration().addAnnotatedClass(User.class);
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println( "Ошибка создания сессии: " + e.getMessage());
        }
    }

    public static Session getSession() throws SQLException{
        return sessionFactory.getCurrentSession();
    }

}
