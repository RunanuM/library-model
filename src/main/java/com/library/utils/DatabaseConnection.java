/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.utils;

/**
 *
 * @author Emperor_RR
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456789";
    
    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("COnnection successful!");
            
        } catch (ClassNotFoundException e){
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e){
            System.out.println("Connection failed: " + e.getMessage());
        }
        return connection;
    }
    
}
