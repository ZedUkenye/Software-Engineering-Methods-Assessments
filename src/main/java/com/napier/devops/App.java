package com.napier.devops;

import java.sql.*;

public class App {

    // Database connection object
    private Connection con = null;

    // Connect to the database
    public void connect() {

        // Load Database driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // loop to attempt to connect to database
        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                Thread.sleep(2500);
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected!");
                break;
            }

            // Catch SQL exceptions and print message
            catch (SQLException sqle) {
                System.out.println("Failed to connect to database, attempt " + (i + 1));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted");
            }
        }
    }

    // Get the database connection
    public Connection getConnection() {
        return con;
    }


    // Disconnect from the database
    public void disconnect() {
        if (con != null) {
            try { con.close(); }
            catch (SQLException e) {
                System.out.println("Error closing database connection");
            }
        }
    }

    // Main method
    public static void main(String[] args) throws SQLException {
        // Create new App instance
        App app = new App();
        // Connect to database
        app.connect();
        // Run Main Menu passing the in database connection object
        MainMenu.menu(app.getConnection());
        // Disconnect from database
        app.disconnect();
    }
}
