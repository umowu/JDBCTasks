package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static String URL, USER, PASSWORD, DRIVER;

    static {
        try(InputStream is = Util.class.getClassLoader().getResourceAsStream("db.properties")){
            Properties properties = new Properties();
            properties.load(is);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.pass");
            DRIVER = properties.getProperty("db.driver");
            Class.forName(DRIVER);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке конфигурации: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер JDBC не найден: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
