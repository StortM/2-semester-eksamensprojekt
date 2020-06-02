package com.example.demo.repository;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
    Singleton klasse som har ansvar for at returnere en database forbindelse
 */

public class DatabaseConnectionManager {
    //singleton af DCM
    private static DatabaseConnectionManager instance;
    private static String user;
    private static String password;
    private static String url;

    private DatabaseConnectionManager() {

    }

    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }

        return instance;
    }

    // Returnerer et Connection objekt ud fra username, password og url i resources/application.properties filen
    public Connection getDatabaseConnection() throws SQLException {
        Properties prop = new Properties();
        try {
            FileInputStream propertyFile = new FileInputStream("src/main/resources/application.properties");
            prop.load(propertyFile);
            this.user = prop.getProperty("spring.datasource.username");
            this.password = prop.getProperty("spring.datasource.password");
            this.url = prop.getProperty("spring.datasource.url");
        }
        catch(FileNotFoundException e){
            System.out.println("File could not be found");
            e.printStackTrace();
        }
        catch(IOException e){
            System.out.println("Property could not be loaded");
            e.printStackTrace();
        }

        return DriverManager.getConnection(url,user,password);
    }
}
