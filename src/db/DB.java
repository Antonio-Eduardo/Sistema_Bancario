package db;

import Excecoes.DBException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;
    public static Connection getConnection(){
        try {
        if (conn == null){
            Properties props = loadProperties();
            String url = props.getProperty("dburl");

                conn = DriverManager.getConnection(url,props);
            }
            }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    public static void closeConnection(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeStatement(Statement st){
        if (st != null){
            try {
                st.close();
            }catch (SQLException e){
                throw new DBException();
            }
        }
    }
    public static void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DBException();
            }
        }
    }
}
