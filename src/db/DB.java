package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    public static Connection getConnection() {
        try {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");

            return DriverManager.getConnection(url, props);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
