package com.napier.devops;

import java.sql.*;
import java.util.Scanner;

public class Queries {



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
    /*
    * All the countries in the world organised by largest population to smallest.
All the countries in a continent organised by largest population to smallest.
All the countries in a region organised by largest population to smallest.
    * */
    public void countryPop() throws SQLException {
        String contin ;
        String regin ;
        // add class scanner which takes input from keyboard
        Scanner input = new Scanner(System.in);
        //print all countries in the world ordered by highest population first
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM country ORDER BY Population DESC;");
        while (rset.next()) {
            System.out.println( rset.getString("Code") + " " +
                    rset.getString("Name") + " " +
                    rset.getString("Continent") + " " +
                    rset.getString("Region") + " " +
                    rset.getInt("Population") + " " +
                    rset.getInt("Capital"));
        }
        //take which continent the user would like to view the countries of

        System.out.println("Please enter the continent you would like to see the countries of:");
        contin = input.nextLine();
        //view the countries of the selected continent
        try{
        ResultSet rset2 = stmt.executeQuery("SELECT * FROM country WHERE Continent = '" +contin+ "'  ORDER BY Population DESC;");
        while (rset2.next()) {
            System.out.println( rset2.getString("Code") + " " +
                    rset2.getString("Name") + " " +
                    rset2.getString("Continent") + " " +
                    rset2.getString("Region") + " " +
                    rset2.getInt("Population") + " " +
                    rset2.getInt("Capital"));
        }}// print error message output isn't displayed
        catch(SQLException e){
            System.out.println("Invalid continent");

        }


        // show available regions
        ResultSet regions = stmt.executeQuery("SELECT DISTINCT Region FROM country;");
        System.out.println("\nAvailable regions:");
        while (regions.next()) {
            System.out.println("- " + regions.getString("Region"));
        }
        // ask user to select which region they would like to see the countries of
            System.out.println("\nPlease enter the region you would like to see the countries of:");
            regin = input.nextLine();


            try{
            ResultSet rset3 = stmt.executeQuery("SELECT * FROM country WHERE Region = '" +regin+ "'  ORDER BY Population DESC;");
            while (rset3.next()) {
                System.out.println( rset3.getString("Code") + " " +
                        rset3.getString("Name") + " " +
                        rset3.getString("Continent") + " " +
                        rset3.getString("Region") + " " +
                        rset3.getInt("Population") + " " +
                        rset3.getInt("Capital"));
            }}catch(SQLException e){
            System.out.println("Invalid region");}


    }

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

}
