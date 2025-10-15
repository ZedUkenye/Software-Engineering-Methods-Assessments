package com.napier.devops;
import java.util.Scanner;  // Import the Scanner class
import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to Database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(10000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected TEST");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    public void countryPop() throws SQLException {}
    public void countryTop() throws SQLException {}
    public void cityTop() throws SQLException {}
    public void popInformation() throws SQLException {}
    public void languageInfo() throws SQLException {}
    public void capitalCity() throws SQLException {}
    public void capitalCityTop() throws SQLException {}
    public void totalPop() throws SQLException {}

/*
*All the cities in the world organised by largest population to smallest.
All the cities in a continent organised by largest population to smallest.
All the cities in a region organised by largest population to smallest.
All the cities in a country organised by largest population to smallest.
All the cities in a district organised by largest population to smallest.
* */
    public void cityPop() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM city LIMIT 20;");
        while (rset.next()) {
            System.out.println(rset.getString("Name"));
        }

    }



    public static void main(String[] args)
    {
        MainMenu.menu();



        }


    }
